package com.umbrella.autocommon;

import java.util.LinkedList;

import com.umbrella.autocommon.Notifiable.NotificableSignal;


public class Clock extends Thread{
	
	private long _clock=0;
	//Context contexto=Context.getInstance();
	private Configuration configuracion=Configuration.getInstance();
	private long time=configuracion.get_tiempoReloj();
	private LinkedList<Notifiable> _lln = new LinkedList<Notifiable>();
	
	private Object mutex = new Object();
	
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
    
    public synchronized static Clock getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }
    
    public synchronized void addNotificable(Notifiable notificable){
    	synchronized (mutex) {
    		_lln.add(notificable);
		}
    }
	
	private Clock() {
	}

	public void run(){
		// Aqu� el c�digo pesado que tarda mucho
		while(true){
			try {
				sleep(time);
				_clock++;
				if(_lln != null)
					notifySignal(NotificableSignal.CLOCK_SIGNAL);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	private void notifySignal(NotificableSignal signal) {
		synchronized (mutex) {
			for (Notifiable n : _lln) {
				n.notifyNoSyncJoy(signal);
			}
		}
		
	}

	public synchronized long getClock(){
		return _clock;
	}
}