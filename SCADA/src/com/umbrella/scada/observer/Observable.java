package com.umbrella.scada.observer;

public interface Observable {
	
	/**
	 * Notifica a los observadores de los cambios que se hayan realizado, tras la notificación vacia el buffer de cambios.
	 * @return true si se notificaron los cambios, false si no hay cambios a notificar o no se pudo notificar a ningun observador.
	 */
	public boolean notifyChanges();
	
	/**
	 * Añade un cambio al buffer de cambios, sobreescribe los cambios que no se hayan notificado todavía.
	 * @param key el identificador univoco del elemento que ha cambiado.
	 * @param value el valor de cambio.
	 * @return true si se pudo realizar la inserción y false en caso contrario.
	 */
	public boolean addChange(TransferBufferKeys key, Object value);
}
