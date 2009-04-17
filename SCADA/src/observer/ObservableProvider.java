package observer;

public class ObservableProvider implements Observable {
	
	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	private ObservableProvider() {
	}

	public static Observable getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static ObservableProvider instance = new ObservableProvider();
	}
}
