package observer;

import view.Updatable;

public interface Observer {
	
	/**
	 * Agrega un nuevo objeto updatabable a la lista de observadores
	 * @param updatable
	 * @return
	 */
	public boolean registerUpdatable(Updatable updatable);
	
	/**
	 * 
	 * @param buffer
	 */
	public void notifyObserver(TransferBuffer buffer);
}
