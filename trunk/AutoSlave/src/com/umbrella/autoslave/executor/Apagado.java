package com.umbrella.autoslave.executor;



public class Apagado implements Estado{

	private static Estado INSTANCE = null;

	private Apagado() {

	}

	public Estado transitar() {
		return null;
	}
	
	private synchronized static void createInstance() {
		if (INSTANCE == null) { 
			INSTANCE = new Apagado();
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
	
	/*
	 * activa los bits del estado interno de los sensores
	 */
	public void activa(){
		
	}
	
	/*
	 * desactiva los bits del estado interno de los sensores
	 */
	public void desactiva(){
		
	}
	
}
