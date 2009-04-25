package com.umbrella.autoslave.logic;

public class Clock extends Thread{
	
	private long _clock=0;
	Contexto contexto=Contexto.getInstance();
	
	private static Clock INSTANCE = null;
	
	/*
	 *  creador sincronizado para protegerse de posibles problemas  multi-hilo
	 *  otra prueba para evitar instanciaci—n mœltiple
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
		// Aquí el código pesado que tarda mucho
		try {
			long time=contexto.getTiempoInterno();
			wait(time);
			_clock++;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public synchronized long getClock(){
		return _clock;
	}
}