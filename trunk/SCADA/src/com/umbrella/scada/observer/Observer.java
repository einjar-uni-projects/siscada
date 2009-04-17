package com.umbrella.scada.observer;

import com.umbrella.scada.view.Updatable;

public interface Observer {
	
	/**
	 * Agrega un nuevo objeto updatabable a la lista de observadores
	 * @param updatable
	 * @return
	 */
	public boolean registerUpdatable(Updatable updatable);
	
	/**
	 * Notifica a los observadores de los cambios realizados a través de un buffer de transeferncia
	 * @param buffer
	 */
	public void notifyObserver(TransferBuffer buffer);
}
