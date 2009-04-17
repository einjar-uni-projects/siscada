package com.umbrella.scada.observer;

public class ObservableProvider implements Observable {

	private final Observer _observer;
	private TransferBuffer _buffer;

	// El constructor privado no permite que se genere un constructor por
	// defecto
	// (con mismo modificador de acceso que la definicion de la clase)
	private ObservableProvider() {
		_observer = ObserverProvider.getInstance();
		_buffer = new TransferBuffer();
	}
	
	/**
	 * Obtiene la instancia única del objeto, la primera invocación
	 * realiza la creación del mismo.
	 * @return la instancia única de Observable
	 */
	public static Observable getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static ObservableProvider instance = new ObservableProvider();
	}

	@Override
	public boolean addChange(TransferBufferKeys key, Object value) {
		return _buffer.setElement(key, value);
	}

	@Override
	public boolean notifyChanges() {
		boolean ret = false;
		if (_buffer.size() > 0) {
			_observer.notifyObserver(_buffer);
			_buffer = new TransferBuffer();
			ret = true;
		}
		return ret;
	}
}
