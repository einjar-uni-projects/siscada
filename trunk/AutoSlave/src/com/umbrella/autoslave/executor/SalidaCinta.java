package com.umbrella.autoslave.executor;

import com.umbrella.autoslave.logic.Configuracion;
import com.umbrella.autoslave.logic.Contexto;
import com.umbrella.autoslave.logic.EstateThreads;

public class SalidaCinta extends Thread implements Estado{

	private double _posicion;
	private int _posicionAsociada;
	
	private static Estado INSTANCE = null;

	private EstateThreads _estadoHilo;
	
	private Contexto contexto=Contexto.getInstance();
	private Configuracion configuracion=Configuracion.getInstance();
	
	private SalidaCinta(double posicion, int posAsociada) {
		// TODO Auto-generated constructor stub
		set_estadoHilo(EstateThreads.CREADO);
		this._posicion=posicion;
		set_posicionAsociada(posAsociada);
	}

	@Override
	public void run(){
		set_estadoHilo(EstateThreads.EJECUTANDO);
		
		boolean finCintaLibre=finCintaLibre();
		while (!finCintaLibre){
			//se espera al siguiente click de la cinta
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//comprobar el estado de la cinta
			finCintaLibre=finCintaLibre();
		}
		contexto.decrementarNumPasteles();
		//se ha recogido el bizcocho del fin de la lista
		set_estadoHilo(EstateThreads.ACABADO);
	}
	
	public void transitar() {
		// return null;
	}
	private synchronized static void createInstance(double posicion, int posAsociada) {
		if (INSTANCE == null) { 
			INSTANCE = new SalidaCinta(posicion, posAsociada);
		}
	}

	public static Estado getInstance(double posicion, int posAsociada) {
		if (INSTANCE == null) createInstance(posicion, posAsociada);
		return INSTANCE;
	}

	public static Estado getInstance() {
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
	
	private synchronized boolean finCintaLibre(){
		boolean libre=true;
		for(int i=0;i<contexto.get_listaPasteles().size();i++){
			if(contexto.get_listaPasteles().get(i).get_posicion()>=(configuracion.getPosFin()-configuracion.getErrorSensor())) libre=false;
		}
		return libre;
	}
	
	public synchronized double get_posicion() {
		return _posicion;
	}
	
	private synchronized int get_posicionAsociada() {
		return _posicionAsociada;
	}

	private synchronized void set_posicionAsociada(int asociada) {
		_posicionAsociada = asociada;
	}
}