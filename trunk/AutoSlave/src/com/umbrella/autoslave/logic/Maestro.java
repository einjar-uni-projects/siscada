package com.umbrella.autoslave.logic;

import com.umbrella.autoslave.executor.Apagado;
import com.umbrella.autoslave.executor.DispensadoraActivada;
import com.umbrella.autoslave.executor.Estado;
import com.umbrella.autoslave.executor.MaquinaCaramelo;
import com.umbrella.autoslave.executor.MaquinaChocolate;
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
	private static MaquinaCaramelo _maqCaramelo;
	private static MaquinaChocolate _maqChocolate;
	private static SalidaPastel _salPastel;
	
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
 			_maqCaramelo=(MaquinaCaramelo)MaquinaCaramelo.getInstance();
 			_maqChocolate=(MaquinaChocolate)MaquinaChocolate.getInstance();
 			_salPastel=(SalidaPastel)SalidaPastel.getInstance();
 			
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
 							if(puedoDispensadoraChocolate()) _maqChocolate.run();
 							if(puedoDispensadoraCaramelo()) _maqCaramelo.run();
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
 			_maqCaramelo=null;
 			_maqChocolate=null;
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
		else if(_maqCaramelo.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_maqChocolate.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_salPastel.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		return hay;
	}
	
	private synchronized static boolean seEnciendeSensor(int espacio){
		boolean salida=false;
		
		if(contexto.getPosicionCinta(configuracion.getPosCaram())) salida=true;
		if(contexto.getPosicionCinta(configuracion.getPosChoc())) salida=true;
		if(contexto.getPosicionCinta(configuracion.getPosFin())) salida=true;
		if(!contexto.getPosicionCinta(configuracion.getPosBizc()) && _moverCinta.getSaltosCinta()>=configuracion.getEspEntreBizc()) salida=true;
		return salida;
	}
	
	private synchronized static boolean sensorDispensadoraBizcocho(){
		boolean salida=false;
		if(!contexto.getPosicionCinta(configuracion.getPosBizc()) && _moverCinta.getSaltosCinta()>=configuracion.getEspEntreBizc()) salida=true;
		return salida;
	}
	
	private synchronized static boolean sensorDispensadoraChocolate(){
		boolean salida=false;	
		if(contexto.getPosicionCinta(configuracion.getPosChoc())) salida=true;
		return salida;
	}
	private synchronized static boolean sensorDispensadoraCaramelo(){
		boolean salida=false;
		if(contexto.getPosicionCinta(configuracion.getPosCaram())) salida=true;
		return salida;
	}
	private synchronized static boolean sensorFinCinta(){
		boolean salida=false;
		if(contexto.getPosicionCinta(configuracion.getPosFin())) salida=true;
		return salida;
	}
	
	private synchronized static boolean ejecutandoDispensadoraBizcocho(){
		boolean hay=false;
		if(_dispensadora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		return hay;
	}
	
	private synchronized static boolean ejecutandoDispensadoraChocolate(){
		boolean salida=false;	
		if(_maqChocolate.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		return salida;
	}
	private synchronized static boolean ejecutandoDispensadoraCaramelo(){
		boolean salida=false;
		if(_maqCaramelo.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		return salida;
	}
	private synchronized static boolean ejecutandoFinCinta(){
		boolean salida=false;
		if(_salPastel.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		return salida;
	}
	
	
	private synchronized static boolean puedoDispensadoraBizcocho(){
		boolean salida=true;
		if(!ejecutandoDispensadoraBizcocho() && sensorDispensadoraBizcocho()) salida=true;
		return salida;
	}
	
	private synchronized static boolean puedoDispensadoraChocolate(){
		boolean salida=false;	
		if(!ejecutandoDispensadoraChocolate() && sensorDispensadoraChocolate()) salida=true;
		return salida;
	}
	private synchronized static boolean puedoDispensadoraCaramelo(){
		boolean salida=false;
		if(!ejecutandoDispensadoraCaramelo() && sensorDispensadoraCaramelo()) salida=true;
		return salida;
	}
	private synchronized static boolean puedoFinCinta(){
		boolean salida=false;
		if(!ejecutandoFinCinta() && sensorFinCinta()) salida=true;
		return salida;
	}
}
