

package com.umbrella.autoslave.executor;

/*
 * esta clase por si sola no sirve para nada, para lo q esta es para reflejar que la maquina viene de apagado a encendido
 * 
 * es el mejor lugar para cargar los datos de arranque 
 */

public class TurnOff{

	private static TurnOff INSTANCE=null;

	private TurnOff() {

	}

	/*
	 * Encendida la maquina se pasa al estado, activar dispensadora
	 */
	public void transitar() {
		/*
		 * codigo de carga de los datos de configuracion 
		 */
		//return DispensadoraActivada.getInstance();
	}
	
	private synchronized static void createInstance() {
		if (INSTANCE == null) { 
			INSTANCE = new TurnOff();
		}
	}

	public static TurnOff getInstance() {
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
