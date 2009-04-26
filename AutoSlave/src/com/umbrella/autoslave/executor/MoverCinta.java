package com.umbrella.autoslave.executor;

import com.umbrella.autoslave.logic.Configuracion;
import com.umbrella.autoslave.logic.Contexto;
import com.umbrella.autoslave.logic.EstateThreads;

public class MoverCinta extends Thread implements Estado{

	private static Estado INSTANCE = null;
	
	private EstateThreads _estadoHilo;
	
	private Contexto contexto=Contexto.getInstance();
	private Configuracion configuracion=Configuracion.getInstance();

	/*
	 * indica el numero de saltos q dio la cinta desde q empezo a moverse 
	 */
	private int saltosCinta=0;
	
	private MoverCinta() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(){
		_estadoHilo=EstateThreads.EJECUTANDO;
		
		/*
		 * lo q desplaza la cinta en un click
		 */
		double velCintaCMporNanoseg=(configuracion.getVelCinta()*100)/(60*1000);
		double sizeSectorCintaCM=configuracion.getSizePastel()*100;
		/*
		 * tiempo en NanoSegundos necesarios para avanzar una posicion la cinta 
		 */
		double tiempoNecesario=sizeSectorCintaCM/velCintaCMporNanoseg;
		
		/*
		 * nos dice si algun sensor se va a encender
		 * se da el valor False xq al menos tiene q dar el salto una vez
		 */
		boolean sensoresEncendidos=false;
		
		while(!sensoresEncendidos){
			try {
				sleep((int)tiempoNecesario);
				// dormimos este tiempo porq no puede ocurrir nada en ese periodo
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			contexto.avanzarCinta();
			saltosCinta++;
			sensoresEncendidos=seEnciendeSensor(saltosCinta);
		}
		set_estadoHilo(EstateThreads.ESPERANDO);
	}
	
	public void transitar() {
		/*
		 * este estado nunca se queda bloqueado, la cinta no entiende de clicks de reloj
		 * presenta los problemas de tener q estar pendiente de si en un clicl de estado de 
		 * reloj hay un sensor activado tiene q cambiar al estado correspondiente
		 * 
		 * ADEMAS!! 
		 * si hay mas de un sensor activo tiene q activar dos estados, ESTO NO SE PERMITE ACTUALMENTE
		 * 
		 * 
		 */
		//return null;
	}
	private synchronized static void createInstance() {
		if (INSTANCE == null) { 
			INSTANCE = new MoverCinta();
		}
	}

	public static Estado getInstance() {
		if (INSTANCE == null) createInstance();
		return INSTANCE;
	}

	public void enviaMensaje() {
		// TODO Auto-generated method stub
		
	}

	public void cambiaMensaje(boolean[] msg) {
		// TODO Auto-generated method stub
		
	}	
	
	public synchronized EstateThreads get_estadoHilo() {
		return _estadoHilo;
	}
	
	private synchronized void set_estadoHilo(EstateThreads estate) {
		this._estadoHilo=estate;
	}
	
	private synchronized boolean seEnciendeSensor(int espacio){
		boolean salida=false;
		
		if(contexto.getPosicionCinta(configuracion.getPosCaram())) salida=true;
		if(contexto.getPosicionCinta(configuracion.getPosChoc())) salida=true;
		if(contexto.getPosicionCinta(configuracion.getPosFin())) salida=true;
		if(!contexto.getPosicionCinta(configuracion.getPosBizc()) && espacio>=configuracion.getEspEntreBizc()) salida=true;
		return salida;
	}
	
	public synchronized int getSaltosCinta(){
		return saltosCinta;
	}
}