package com.umbrella.autocommon;

import com.umbrella.autocommon.Configuracion;

public class Clock extends Thread{
	
	private long _clock=0;
	Contexto contexto=Contexto.getInstance();
	Configuracion configuracion=Configuracion.getInstance();
	long time=configuracion.get_tiempoReloj();
	
	private static Clock INSTANCE = null;
	
	/*
	 *  creador sincronizado para protegerse de posibles problemas  multi-hilo
	 *  otra prueba para evitar instanciaci�n m�ltiple
	 */ 
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new Clock();
        }
    }
    
    public static Clock getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }
	
	public void run(){
		// Aqu� el c�digo pesado que tarda mucho
		try {
			wait(time);
			_clock++;
			notifyAll();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public synchronized long getClock(){
		return _clock;
	}
}