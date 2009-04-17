package observer;

import view.Updatable;

public class ObserverProvider implements Observer {
	
	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	private ObserverProvider() {
	}

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
		// TODO Auto-generated method stub
		return false;
	}

}
