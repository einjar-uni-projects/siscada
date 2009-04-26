package com.umbrella.autoslave.executor;

public class MoverCinta implements Estado{

	private static Estado INSTANCE = null;

	private MoverCinta() {
		// TODO Auto-generated constructor stub
	}

	public Estado transitar() {
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
		return null;
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
}