package com.umbrella.autoslave.logic;

import com.umbrella.autoslave.Utils.EstateThreads;
import com.umbrella.autoslave.Utils.NombreMaquinas;
import com.umbrella.autoslave.executor.Apagado;
import com.umbrella.autoslave.executor.DispensadoraActivada;
import com.umbrella.autoslave.executor.Estado;
import com.umbrella.autoslave.executor.MaquinaDispensadora;
import com.umbrella.autoslave.executor.MoverCinta;
import com.umbrella.autoslave.executor.SalidaCinta;


/*
 * El objetivo de esta clase es llevar el peso de la ejecucion, aqui se crean los hilos q luego se ejecutaran en 
 * paralelo entre ellos
 */
public class Maestro1 {
	
	private static Clock _clock;
	private static MoverCinta _moverCinta;
	private static DispensadoraActivada _dispensadora;
	private static SalidaCinta _salPastel;
	private static MaquinaDispensadora _chocolate;
	private static MaquinaDispensadora _caramelo;
	
	/*
	 * apagado deja el automata apagado pero esto lo deja en standby
	 * FIN acaba la ejecucion completamente
	 */
	private static boolean FIN=false;
	
	private static Contexto contexto=Contexto.getInstance("pastel");
	private static Configuracion configuracion=Configuracion.getInstance();

	public static void main(String[] args) {
		try	{
			
			Estado estado= Apagado.getInstance();
 			contexto.setState( estado );
 			
 			/*
 			 * tenemos dos hilos, uno es el reloj y el otro es la ejecucion del automata que debe quedarse bloqueado entre 
 			 * los clicks del reloj, para eso el reloj debe hacer un notify a todos los hilos notifyAll()
 			 */
 			_clock=new Clock();
 			_clock.start();
 	
 			/*
 			 * se crean los hilos de ejecucion
 			 */
 			_moverCinta=(MoverCinta)MoverCinta.getInstance(configuracion.getVelCinta(),
 					configuracion.getPosicionAsociada(NombreMaquinas.CINTA_1));
 			_dispensadora=(DispensadoraActivada)DispensadoraActivada.getInstance(configuracion.getPosBizc(),
 					configuracion.getPosicionAsociada(NombreMaquinas.DISPENSADORA));
 			_salPastel=(SalidaCinta)SalidaCinta.getInstance(configuracion.getPosFin(),
 					configuracion.getPosicionAsociada(NombreMaquinas.FIN_1));
 			_caramelo=new MaquinaDispensadora(configuracion.getValvCaram(), configuracion.getPosCaram(),
 					configuracion.getPosicionAsociada(NombreMaquinas.CARAMELO));
 			_chocolate=new MaquinaDispensadora(configuracion.getValvChoc(), configuracion.getPosChoc(),
 					configuracion.getPosicionAsociada(NombreMaquinas.CHOCOLATE));
 			
 			long cicloAct=_clock.getClock();
 			boolean primeraVez=true;
 			
 			if(cicloAct<_clock.getClock()){
 				cicloAct=_clock.getClock();
 				/*
 				 * en cada ciclo de reloj, si aun estoy en el ciclo de reloj me quedo aqui
 				 */
 				while(!FIN){
 					

 					/*
 					 * se intenta leer si llega algun mensaje que nos saque del estado apagado
 					 */
 					if(!contexto.apagado){
 						/*
 						 * se arranca el automata cambiando el estado, 
 						 * lo unico q hace es cargar los valores iniciales 
 						 */
 						if(primeraVez){
 							((Apagado) estado).transitar();
 							primeraVez=false;
 						}

 						/*
 						 * se coordina y ejecutan los hilos de: movercinta, DispensadoraAutomatica, MaquinaCaramelo, MaquinaChocolate, 
 						 * 		moverCinta, salidaPastel
 						 */
 						
 						if(!seEnciendeSensor() && !hayHiloBloqueante()){
 							_moverCinta.run();
 							//si muevo la cinta apago todos los sensores
 							
 						}else{
 							if(puedoUsar(NombreMaquinas.CHOCOLATE)){
 								_chocolate.run();
 								contexto.get_listaPasteles().get(contexto.activaSensor(_chocolate.get_posicion())).set_chocolate();
 							}
 							if(puedoUsar(NombreMaquinas.CARAMELO)){
 								_caramelo.run();
 								contexto.get_listaPasteles().get(contexto.activaSensor(_caramelo.get_posicion())).set_caramelo();
 							}
 							if(puedoUsar(NombreMaquinas.FIN_1)) _salPastel.run();
 						}
 						//no me importa si la cinta se mueve o no, si puede la dispensadora echa un pastel
 						// se pone dentro del while del ciclo de reloj porq solo pone un pastel por click
 						_dispensadora.run();
 					}else{
 						primeraVez=true;
 					}

 					/*
 					 * se intenta leer si nos llega un mensaje q nos saque de la ejecucion
 					 */
 				}
 			}
 			/*
 			 * se matan los hilos
 			 */
 			_moverCinta=null;
 			_dispensadora=null;
 			_caramelo.run();
 			_chocolate.run();
 			_salPastel=null;
 			
 		}catch( Exception e ){
 			e.printStackTrace();
 		}
	}
	
	/*
	 * Nos dice si algun hilo esta bloqueando al resto, es decir uno de los hilos esta en ejecucion
	 * true= algun hilo esta bloqueando
	 */
	private synchronized static boolean hayHiloBloqueante(){
		boolean hay=false;
		if(_dispensadora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_caramelo.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_chocolate.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_salPastel.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		return hay;
	}
	
	private synchronized static boolean seEnciendeSensor(){
		boolean salida=false;
		
		if(contexto.activaSensor(_caramelo.get_posicion())>=0){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CARAMELO), true);
			salida=true;
		}
		if(contexto.activaSensor(_chocolate.get_posicion())>=0){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CHOCOLATE), true);
			salida=true;
		}
		if(contexto.activaSensor(_salPastel.get_posicion())>=0){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1), true);
			salida=true;
		}
		// la dispensadora no tiene sensor asociado
		// if(contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		return salida;
	}
	
	private synchronized static boolean ejecutandoAlgo(NombreMaquinas nombre){
		boolean salida=false;
		if(nombre.equals(NombreMaquinas.DISPENSADORA))
			if(_dispensadora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.CHOCOLATE))
			if(_chocolate.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.CARAMELO))
			if(_caramelo.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.FIN_1))
			if(_salPastel.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		return salida;
	}
	
	private synchronized static boolean puedoUsar(NombreMaquinas tipo){
		boolean salida=false;
		/*
		if(tipo.equals(NombreMaquinas.DISPENSADORA))
			if(!ejecutandoAlgo(NombreMaquinas.DISPENSADORA) && contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		*/
		if(tipo.equals(NombreMaquinas.CHOCOLATE))
			if(!ejecutandoAlgo(NombreMaquinas.CHOCOLATE) && contexto.activaSensor(_chocolate.get_posicion())>=0) salida=true;
		if(tipo.equals(NombreMaquinas.CARAMELO))
			if(!ejecutandoAlgo(NombreMaquinas.CARAMELO) && contexto.activaSensor(_caramelo.get_posicion())>=0) salida=true;
		if(tipo.equals(NombreMaquinas.FIN_1))
			if(!ejecutandoAlgo(NombreMaquinas.FIN_1) && contexto.activaSensor(_salPastel.get_posicion())>=0) salida=true;
		return salida;
	}
	private synchronized static void apagarSensores(){
		contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CARAMELO), false);
		contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CHOCOLATE), false);
		contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1), false);
	}
}
