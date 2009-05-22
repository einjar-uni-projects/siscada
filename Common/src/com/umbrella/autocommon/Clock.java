package com.umbrella.autocommon;


public class Clock extends Thread{
	
	private long _clock=0;
	Context contexto=Context.getInstance();
	Configuration configuracion=Configuration.getInstance();
	long time=configuracion.get_tiempoReloj();
	private Notificable _notificable;
	
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
    
    public void setNotificable(Notificable notificable){
    	_notificable = notificable;
    }
	
	public void run(){
		// Aqu� el c�digo pesado que tarda mucho
		try {
			wait(time);
			_clock++;
			if(_notificable != null)
				_notificable.notifyNoSyncJoy();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public synchronized long getClock(){
		return _clock;
	}
}