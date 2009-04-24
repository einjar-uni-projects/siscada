package com.umbrella.autoslave.executor;

public class DispensadoraActivada implements Estado{

	private static Estado INSTANCE = null;

	private DispensadoraActivada() {
		// TODO Auto-generated constructor stub
	}

	public Estado transitar() {
		return null;
	}
	private synchronized static void createInstance() {
		if (INSTANCE == null) { 
			INSTANCE = new DispensadoraActivada();
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
	
}