package com.umbrella.autoslave.executor;

import com.umbrella.autoslave.logic.Configuracion;
import com.umbrella.autoslave.logic.Contexto;
import com.umbrella.autoslave.logic.EstateThreads;

public class SalidaPastel extends Thread implements Estado{

	private static Estado INSTANCE = null;

	private EstateThreads _estadoHilo;
	
	private Contexto contexto=Contexto.getInstance();
	private Configuracion configuracion=Configuracion.getInstance();
	
	private SalidaPastel() {
		// TODO Auto-generated constructor stub
		set_estadoHilo(EstateThreads.CREADO);
	}

	@Override
	public void run(){
		set_estadoHilo(EstateThreads.EJECUTANDO);
		
		boolean finCintaLibre=contexto.getPosicionCinta(configuracion.getPosFin());
		while (!finCintaLibre){
			//se espera al siguiente click de la cinta
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//comprobar el estado de la cinta
			finCintaLibre=contexto.getPosicionCinta(configuracion.getPosFin());
		}
		contexto.decrementarNumPasteles();
		contexto.setPosicionCinta(configuracion.getPosFin(), false);
		//se ha recogido el bizcocho del fin de la lista
		set_estadoHilo(EstateThreads.ACABADO);
	}
	
	public void transitar() {
		// return null;
	}
	private synchronized static void createInstance() {
		if (INSTANCE == null) { 
			INSTANCE = new SalidaPastel();
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
}