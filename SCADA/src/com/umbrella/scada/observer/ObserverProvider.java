package com.umbrella.scada.observer;

import java.util.LinkedList;

import com.umbrella.scada.view.Updatable;


public class ObserverProvider implements Observer {
	
	private final LinkedList<Updatable> _updatableList;
	
	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	private ObserverProvider() {
		_updatableList = new LinkedList<Updatable>();
	}
	
	/**
	 * Obtiene la instancia única del objeto, la primera invocación
	 * realiza la creación del mismo.
	 * @return la instancia única de Observer
	 */
	public static Observer getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static ObserverProvider instance = new ObserverProvider();
	}

	@Override
	public void notifyObserver(TransferBuffer buffer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean registerUpdatable(Updatable updatable) {
		boolean ret = false;
		if(_updatableList.contains(updatable))
		// TODO Auto-generated method stub
		return ret;
	}

}
