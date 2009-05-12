package com.umbrella.scada.view.screen;

import com.umbrella.scada.observer.ObserverProvider;
import com.umbrella.scada.observer.TransferBuffer;
import com.umbrella.scada.observer.TransferBufferKeys;
import com.umbrella.scada.view.Updatable;
import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;

public class MainFrameModel implements Updatable{
	//Variables generales
	private LanguageIDs _selectedLanguage;
	private UpdatableInterface _mainFrame;
	
	//ESTADOS
	
	private boolean _cintaPasteles;
	private boolean _cintaBlister;
	private boolean _cintaMontaje;
	private int _brazoMontaje;
	private boolean _brazoDesechar;
	private int [] _pasteles = new int[7];
	private int [] _blisters = new int[5];
	private int [] _paquetes = new int[4];
	
	
	//ACTIONS
	
	

	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	private MainFrameModel() {
		_selectedLanguage = LanguageIDs.SPANISHLOCALE;
	}

	/**
	 * Obtiene la instancia única del objeto, la primera invocación
	 * realiza la creación del mismo.
	 * @return la instancia única de MainFrameModel
	 */
	public static MainFrameModel getInstance() {
		return SingletonHolder.instance;
	}
	
	public void initialize(){
		if(_mainFrame == null){
			_mainFrame = MainFrame.getInstance();
			ObserverProvider.getInstance().registerUpdatable(this);
		}
	}

	private static class SingletonHolder {
		private static MainFrameModel instance = new MainFrameModel();
	}

	public LanguageIDs get_selectedLanguage() {
		return _selectedLanguage;
	}

	public void set_selectedLanguage(LanguageIDs language) {
		_selectedLanguage = language;
		_mainFrame.updateLanguage();
	}

	@Override
	public void update(TransferBuffer buffer) {
		//Se obtienen las claves
		TransferBufferKeys[] tbk = TransferBufferKeys.values();
		Object value;
		
		//Se recorren las claves
		for (int i = 0; i < tbk.length; i++) {
			//Se obtiene el valor de la clave
			value = buffer.getElement(tbk[i]);
			if(value != null)
				updateValue(tbk[i], tbk[i].get_class(), value);
		}
		_mainFrame.updateData();
	}
	
	private synchronized void updateValue(TransferBufferKeys key, Class object_class, Object o) {
		switch (key) {
		case AU1_CAKE_DEPOT:
			
			break;
		case AU1_CARAMEL_VALVE_DELAY:

			break;
		case AU1_CHOCOLATE_VALVE_DELAY:

			break;
		case AU1_CONVEYOR_BELT_SIZE:

			break;
		case AU1_CONVEYOR_BELT_SPEED:

			break;
		case AU2_CONVEYOR_BELT_SIZE:

			break;
		case AU2_CONVEYOR_BELT_SPEED:

			break;
		case AU2_VACUUM_SEALING_MACHINE:

			break;
		case AU3_CONVEYOR_BELT_SIZE:

			break;
		case AU3_CONVEYOR_BELT_SPEED:

			break;
		case GEN_BLISTER_SIZE:

			break;
		case GEN_CAKE_SIZE:

			break;
		case GEN_CLOCK_TIME:

			break;
		case GEN_ROBOT_INTERFERENCE:

			break;
		case GEN_SENSOR_ERROR:

			break;
		case RB1_BLISTER_DELAY:

			break;
		case RB1_CAKE_DELAY:

			break;
		case RB2_BLISTER_DELAY:

			break;

		default:
			break;
		}

	}

	public boolean is_cintaPasteles() {
		return _cintaPasteles;
	}

	public boolean is_cintaBlister() {
		return _cintaBlister;
	}

	public boolean is_cintaMontaje() {
		return _cintaMontaje;
	}

	public int get_brazoMontaje() {
		return _brazoMontaje;
	}

	public boolean is_brazoDesechar() {
		return _brazoDesechar;
	}

	public int[] get_pasteles() {
		_pasteles[0] = 3;
		_pasteles[1] = 3;
		_pasteles[2] = 3;
		_pasteles[3] = 3;
		_pasteles[4] = 3;
		_pasteles[5] = 3;
		_pasteles[6] = 3;
		return _pasteles;
	}

	public int[] get_blisters() {
		_blisters[0] = 4;
		_blisters[1] = 4;
		_blisters[2] = 4;
		_blisters[3] = 4;
		_blisters[4] = 4;
		return _blisters;
	}

	public int[] get_paquetes() {
		_paquetes[0] = 6;
		_paquetes[1] = 6;
		_paquetes[2] = 6;
		_paquetes[3] = 6;
		return _paquetes;
	}

}
