package com.umbrella.autoslave.executor;

public class SalidaPastel implements Estado{

	private static Estado INSTANCE = null;

	private SalidaPastel() {
		// TODO Auto-generated constructor stub
	}

	public Estado transitar() {
		return null;
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
}