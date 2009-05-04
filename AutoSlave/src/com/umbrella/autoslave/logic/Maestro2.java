package com.umbrella.autoslave.logic;

import com.umbrella.autoslave.Utils.EstateThreads;
import com.umbrella.autoslave.Utils.NombreMaquinas;
import com.umbrella.autoslave.executor.Apagado;
import com.umbrella.autoslave.executor.DispensadoraActivada;
import com.umbrella.autoslave.executor.Estado;
import com.umbrella.autoslave.executor.MaquinaInstantanea;
import com.umbrella.autoslave.executor.MaquinaTiempos;
import com.umbrella.autoslave.executor.MoverCinta;
import com.umbrella.autoslave.executor.SalidaCinta;


/*
 * El objetivo de esta clase es llevar el peso de la ejecucion, aqui se crean los hilos q luego se ejecutaran en 
 * paralelo entre ellos
 */
public class Maestro2 {
	
	private static Clock _clock;
	private static MoverCinta _moverCinta;
	private static SalidaCinta _salBlister;
	private static MaquinaInstantanea _cortadora;
	private static MaquinaInstantanea _troqueladora;
	
	private static boolean[] estadoAnterior=new boolean[16];
	/*
	 * apagado deja el automata apagado pero esto lo deja en standby
	 * FIN acaba la ejecucion completamente
	 */
	private static boolean FIN=false;
	
	private static Contexto contexto=Contexto.getInstance("blister");
	private static Configuracion configuracion=Configuracion.getInstance();

	public static void main(String[] args) {
		
		for(int i=0;i<estadoAnterior.length;i++) estadoAnterior[i]=false;
		
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
 			_moverCinta=new MoverCinta(configuracion.getVelCintaAut2(),
 					configuracion.getPosicionAsociada(NombreMaquinas.CINTA_2));
 			_salBlister=new SalidaCinta(configuracion.getPosFinAut2(),
 					configuracion.getPosicionAsociada(NombreMaquinas.FIN_2), "blister");
 			_cortadora=new MaquinaInstantanea(configuracion.getPosCortadora(),
 					configuracion.getPosicionAsociada(NombreMaquinas.CORTADORA));
 			_troqueladora=new MaquinaInstantanea(configuracion.getPosTroqueladora(),
 					configuracion.getPosicionAsociada(NombreMaquinas.TROQUELADORA));
 			
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
 						}else{
 							seEnciendeSensor();
 							if(puedoUsar(NombreMaquinas.CORTADORA) ){
 								_cortadora.run();
 							}
 							if(puedoUsar(NombreMaquinas.TROQUELADORA) ){
 								_troqueladora.run();
 							}
 							
 						}
 						/*
 						 * Aqui hay q repasar todos los sensores
 						 */
 						for(int i=0;i<estadoAnterior.length;i++) estadoAnterior[i]=contexto.getDispositivosInternos(i);
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
 			_troqueladora=null;
 			_salBlister=null;
 			_cortadora=null;
 			
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
		if(_cortadora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_troqueladora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_salBlister.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		return hay;
	}
	
	private synchronized static boolean seEnciendeSensor(){
		boolean salida=false;
		
		if(contexto.activaSensor(_cortadora.get_posicion())>=0){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.CORTADORA), true);
			salida=true;
		}
		if(contexto.activaSensor(_troqueladora.get_posicion())>=0){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.TROQUELADORA), true);
			salida=true;
		}
		if(contexto.activaSensor(_salBlister.get_posicion())>=0){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_2), true);
			salida=true;
		}
		// la dispensadora no tiene sensor asociado
		// if(contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		return salida;
	}
	
	private synchronized static boolean ejecutandoAlgo(NombreMaquinas nombre){
		boolean salida=false;
		if(nombre.equals(NombreMaquinas.TROQUELADORA))
			if(_troqueladora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.CORTADORA))
			if(_cortadora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.FIN_2))
			if(_salBlister.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		return salida;
	}
	
	private synchronized static boolean puedoUsar(NombreMaquinas tipo){
		boolean salida=false;
		if(tipo.equals(NombreMaquinas.CORTADORA))
			if(!ejecutandoAlgo(NombreMaquinas.CORTADORA) && 
					contexto.activaSensor(_cortadora.get_posicion())>=0 &&
						!estadoAnterior[configuracion.getPosicionAsociada(NombreMaquinas.CORTADORA)])
				salida=true;
		
		if(tipo.equals(NombreMaquinas.TROQUELADORA))
			if(!ejecutandoAlgo(NombreMaquinas.TROQUELADORA) && 
					contexto.activaSensor(_troqueladora.get_posicion())>=0 &&
						!estadoAnterior[configuracion.getPosicionAsociada(NombreMaquinas.TROQUELADORA)])
				salida=true;
		if(tipo.equals(NombreMaquinas.FIN_2))
			if(!ejecutandoAlgo(NombreMaquinas.FIN_2) && 
					contexto.activaSensor(_salBlister.get_posicion())>=0 &&
						!estadoAnterior[configuracion.getPosicionAsociada(NombreMaquinas.FIN_2)]) 
				salida=true;
		return salida;
	}
	
	
	private synchronized static void apagarSensores(){
		contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CORTADORA), false);
		contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_TROEULADORA), false);
		contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_2), false);
	}
}
