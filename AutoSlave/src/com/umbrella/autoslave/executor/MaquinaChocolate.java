package com.umbrella.autoslave.executor;

import com.umbrella.autoslave.logic.Configuracion;
import com.umbrella.autoslave.logic.EstateThreads;

public class MaquinaChocolate extends Thread implements Estado{

	private static Estado INSTANCE = null;

	/*
	 * clicks de reloj pasados desde q se inicia la ejecucion
	 */
	private int _clickPasados=0;
	/*
	 * indica el estado interno del hilo
	 */
	private EstateThreads _estadoHilo;

	
	private Configuracion configuracion=Configuracion.getInstance();
	
	private MaquinaChocolate() {
		// TODO Auto-generated constructor stub
		set_estadoHilo(EstateThreads.CREADO);
	}

	@Override
	public void run(){
		set_estadoHilo(EstateThreads.EJECUTANDO);
		_clickPasados=0;
		while(_clickPasados<configuracion.getValvChoc()){
			set_estadoHilo(EstateThreads.ESPERANDO);
			_clickPasados++;
			// espero a que el reloj envie la se–al de Click
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//se ha echado el caramelo en el bizcocho
		set_estadoHilo(EstateThreads.ACABADO);
	}
	
	public void transitar() {
		//return null;
	}
	private synchronized static void createInstance() {
		if (INSTANCE == null) { 
			INSTANCE = new MaquinaChocolate();
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