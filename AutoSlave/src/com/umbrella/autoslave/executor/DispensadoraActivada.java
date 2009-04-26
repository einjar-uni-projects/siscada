package com.umbrella.autoslave.executor;

import com.umbrella.autoslave.logic.Configuracion;
import com.umbrella.autoslave.logic.Contexto;

public class DispensadoraActivada implements Estado{

	/*
	 * util para el espacio entre pasteles
	 */
	private int _contEspacio=0;
	
	/*
	 * indica el estado anterior del que proviene
	 */
	private boolean _estAnterior=false;
	
	private int _pastelesRestantes;
	/*
	 * 
	 */
	private Configuracion configuracion=Configuracion.getInstance();
	private Contexto contexto=Contexto.getInstance();
	
	private static Estado INSTANCE = null;

	/*
	 * 
	 */
	private DispensadoraActivada() {
		_pastelesRestantes=configuracion.getCapacidadPasteles();
	}

	public Estado transitar() {
		/*
		 * debe comprobar que debajo de la dispensadora no hay pastel, se comprueba que entre 
		 * el œltimo pastel y la posicion actual hay un espacio como minimo el solicitado  
		 */
		try {
			// se queda esperando a la se–al de reloj
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!_estAnterior && _contEspacio>=configuracion.getEspEntreBizc()){
			/*
			 * se pone un bizcocho, se cambia el estado actual y se inicializa el contador de espacio
			 * se pone la posicion de la cinta inicial como ocupada
			 */
			_contEspacio=0;
			_estAnterior=true;
			_pastelesRestantes--;
			contexto.setPosicionCinta(configuracion.getPosBizc(), true);
		}
		
		/*
		 * 
		 */
		return MoverCinta.getInstance();
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
	
	public synchronized void incrementar_ContEspacio(){
		_contEspacio++;
	}
	
	public synchronized void modEstAnterior(){
		_estAnterior=false;;
	}
}