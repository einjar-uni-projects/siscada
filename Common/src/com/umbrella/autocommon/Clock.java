package com.umbrella.autocommon;

import java.util.LinkedList;

import com.umbrella.autocommon.Notificable.NotificableSignal;


public class Clock extends Thread{
	
	private long _clock=0;
	//Context contexto=Context.getInstance();
	Configuration configuracion=Configuration.getInstance();
	long time=configuracion.get_tiempoReloj();
	private final LinkedList<Notificable> _lln = new LinkedList<Notificable>();
	
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
    
    public void addNotificable(Notificable notificable){
    	_lln.add(notificable);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void notifySignal(NotificableSignal signal) {
		for (Notificable n : _lln) {
			n.notifyNoSyncJoy(signal);
		}
		
	}

	public synchronized long getClock(){
		return _clock;
	}
}