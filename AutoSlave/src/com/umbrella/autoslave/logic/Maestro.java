package com.umbrella.autoslave.logic;

import com.umbrella.autoslave.executor.Apagado;
import com.umbrella.autoslave.executor.DispensadoraActivada;
import com.umbrella.autoslave.executor.Estado;
import com.umbrella.autoslave.executor.MaquinaDispensadora;
import com.umbrella.autoslave.executor.MoverCinta;
import com.umbrella.autoslave.executor.SalidaPastel;

/*
 * El objetivo de esta clase es llevar el peso de la ejecucion, aqui se crean los hilos q luego se ejecutaran en 
 * paralelo entre ellos
 */
public class Maestro {
	
	private static Clock _clock;
	
	private static MoverCinta _moverCinta;
	private static DispensadoraActivada _dispensadora;
	private static SalidaPastel _salPastel;
	private static MaquinaDispensadora _chocolate;
	private static MaquinaDispensadora _caramelo;
	
	/*
	 * apagado deja el automata apagado pero esto lo deja en standby
	 * FIN acaba la ejecucion completamente
	 */
	private static boolean FIN=false;
	
	private static Contexto contexto=Contexto.getInstance();
	private static Configuracion configuracion=Configuracion.getInstance();

	public static void main(String[] args) {
		try	{
			
			Estado estado= Apagado.getInstance();
 			Contexto contexto = Contexto.getInstance();
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
 			_moverCinta=(MoverCinta)MoverCinta.getInstance();
 			_dispensadora=(DispensadoraActivada)DispensadoraActivada.getInstance();
 			_salPastel=(SalidaPastel)SalidaPastel.getInstance();
 			_caramelo=new MaquinaDispensadora(configuracion.getValvCaram(), configuracion.getPosCaram());
 			_chocolate=new MaquinaDispensadora(configuracion.getValvChoc(), configuracion.getPosChoc());
 			
 			long cicloAct=_clock.getClock();
 			boolean primeraVez=true;
 			boolean sensorActivo=false;
 			while(!FIN){
 				/*
 				 * en cada ciclo de reloj
 				 */
 				if(cicloAct<_clock.getClock()){
 					cicloAct=_clock.getClock();
 					/*
 					 * se intenta leer si llega algun mensaje que nos saque del estado apagado
 					 */

 					if(!contexto.apagado){
 						/*
 						 * se arranca el automata cambiando el estado, 
 						 * lo unico q hace es cargar los valores iniciales 
 						 */
 						if(primeraVez){
 							contexto.request();
 							primeraVez=false;
 						}

 						/*
 						 * se coordina y ejecutan los hilos de: movercinta, DispensadoraAutomatica, MaquinaCaramelo, MaquinaChocolate, 
 						 * 		moverCinta, salidaPastel
 						 */
 						
 						if(!sensorActivo && !hayHiloBloqueante()){
 							_moverCinta.run();
 						}else{
 							if(puedoDispensadoraBizcocho()) _dispensadora.run();
 							if(puedoDispensadoraChocolate()){
 								_chocolate.run();
 								contexto.get_pasteles().get(contexto.activaSensorChocolate()).set_chocolate();
 							}
 							if(puedoDispensadoraCaramelo()){
 								_caramelo.run();
 								contexto.get_pasteles().get(contexto.activaSensorCaramelo()).set_caramelo();
 							}
 							if(puedoFinCinta()) _salPastel.run();
 						}

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
	
	private synchronized static boolean seEnciendeSensor(int espacio){
		boolean salida=false;
		
		if(contexto.activaSensorCaramelo()>=0) salida=true;
		if(contexto.activaSensorChocolate()>=0) salida=true;
		if(contexto.activaSensorFinal()>=0) salida=true;
		if(contexto.activaSensorBizcocho()>=0) salida=true;
		return salida;
	}
	
	private synchronized static int sensorDispensadoraBizcocho(){
		return contexto.activaSensorBizcocho();
	}
	
	private synchronized static int sensorDispensadoraChocolate(){
		return contexto.activaSensorChocolate();
	}
	private synchronized static int sensorDispensadoraCaramelo(){
		return contexto.activaSensorCaramelo();
	}
	private synchronized static int sensorFinCinta(){
		return contexto.activaSensorFinal();
	}
	
	private synchronized static boolean ejecutandoDispensadoraBizcocho(){
		boolean hay=false;
		if(_dispensadora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		return hay;
	}
	
	private synchronized static boolean ejecutandoDispensadoraChocolate(){
		boolean salida=false;	
		if(_chocolate.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		return salida;
	}
	private synchronized static boolean ejecutandoDispensadoraCaramelo(){
		boolean salida=false;
		if(_caramelo.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		return salida;
	}
	private synchronized static boolean ejecutandoFinCinta(){
		boolean salida=false;
		if(_salPastel.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		return salida;
	}
	
	
	private synchronized static boolean puedoDispensadoraBizcocho(){
		boolean salida=true;
		if(!ejecutandoDispensadoraBizcocho() && sensorDispensadoraBizcocho()>=0) salida=true;
		return salida;
	}
	
	private synchronized static boolean puedoDispensadoraChocolate(){
		boolean salida=false;	
		if(!ejecutandoDispensadoraChocolate() && sensorDispensadoraChocolate()>=0) salida=true;
		return salida;
	}
	private synchronized static boolean puedoDispensadoraCaramelo(){
		boolean salida=false;
		if(!ejecutandoDispensadoraCaramelo() && sensorDispensadoraCaramelo()>=0) salida=true;
		return salida;
	}
	private synchronized static boolean puedoFinCinta(){
		boolean salida=false;
		if(!ejecutandoFinCinta() && sensorFinCinta()>=0) salida=true;
		return salida;
	}
}
