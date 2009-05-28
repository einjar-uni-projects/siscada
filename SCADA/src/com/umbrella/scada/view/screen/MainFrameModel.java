package com.umbrella.scada.view.screen;

import com.umbrella.scada.observer.ObserverProvider;
import com.umbrella.scada.observer.TransferBuffer;
import com.umbrella.scada.observer.TransferBufferKeys;
import com.umbrella.scada.view.Updatable;
import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;

public class MainFrameModel implements Updatable {

	public enum ElementsGroupModelEnum {
		CINTA1, CINTA2, CINTA3, ROBOT1, ROBOT2;
	}

	// Cerrojo
	private Object[] _cerrojos;
	
	// Estado de los automatas y robot
	private boolean []_statesAutRob;
	// Variables generales
	private LanguageIDs _selectedLanguage;
	private UpdatableInterface _mainFrame;

	// ESTADOS
	
	private int[] _pasteles = new int[7];
	private int[] _blisters = new int[5];
	private int[] _paquetes = new int[5];
	private int _tableContent;
	
	private double _cbCakeSize, _cbBlisterSize, _cbPackageSize, _cbCakeSpeed, _cbBlisterSpeed, _cbPackageSpeed;
	
	private int _cakeDepot;
	
	private int _rb1Content, _rb2Content;
	
	private boolean _au1Move, _au2Move, _au3Move;
	
	private int _rb1BlisterDelay, _rb2BlisterDelay, _rb1CakeDelay;
	
	private int _numGoodPackages, _numBadPackages, _numGoodPackagesTotal, _numBadPackagesTotal;

	// ACTIONS

	// El constructor privado no permite que se genere un constructor por
	// defecto
	// (con mismo modificador de acceso que la definicion de la clase)
	private MainFrameModel() {
		System.out.println("Arrancando MainFrameModel");
		_statesAutRob = new boolean[5];
		for (int i = 0; i < _statesAutRob.length; i++) {
			_statesAutRob[i] = false;
		}
		_cerrojos = new Object[TransferBufferKeys.values().length];
		for (int i = 0; i < _cerrojos.length; i++) {
			_cerrojos[i] = new Object();
		}
		_selectedLanguage = LanguageIDs.SPANISHLOCALE;
	}

	/**
	 * Obtiene la instancia única del objeto, la primera invocación realiza la
	 * creación del mismo.
	 * 
	 * @return la instancia única de MainFrameModel
	 */
	public static MainFrameModel getInstance() {
		return SingletonHolder.instance;
	}

	public void initialize() {
		if (_mainFrame == null) {
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
		// Se obtienen las claves
		TransferBufferKeys[] tbk = TransferBufferKeys.values();
		Object value;
		// Se recorren las claves
		for (int i = 0; i < tbk.length; i++) {
			// Se obtiene el valor de la clave
			value = buffer.getElement(tbk[i]);
			if (value != null) {
				updateValue(tbk[i], tbk[i].get_class(), value);
			}
		}
		_mainFrame.updateData();
	}

	private void updateValue(TransferBufferKeys key, Class<?> object_class,
			Object o) {
		switch (key) {
		case AU1_CAKE_DEPOT:
			synchronized (_cerrojos[TransferBufferKeys.AU1_CAKE_DEPOT.ordinal()]) {
				_cakeDepot = ((Integer)o).intValue();
			}
			break;
		case AU1_CARAMEL_VALVE_DELAY:
			synchronized (_cerrojos[TransferBufferKeys.AU1_CARAMEL_VALVE_DELAY
					.ordinal()]) {

			}
			break;
		case AU1_CHOCOLATE_VALVE_DELAY:
			synchronized (_cerrojos[TransferBufferKeys.AU1_CHOCOLATE_VALVE_DELAY
					.ordinal()]) {

			}
			break;
		case AU1_CONVEYOR_BELT_SIZE:
			synchronized (_cerrojos[TransferBufferKeys.AU1_CONVEYOR_BELT_SIZE
					.ordinal()]) {
				_cbCakeSize = ((Double)o).doubleValue();
			}
			break;
		case AU1_CONVEYOR_BELT_SPEED:
			synchronized (_cerrojos[TransferBufferKeys.AU1_CONVEYOR_BELT_SPEED
					.ordinal()]) {
				_cbCakeSpeed = ((Double)o).doubleValue();
			}
			break;
		case AU1_CAKES_POS1:
			synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS1.ordinal()]) {
				_pasteles[0] = (Integer) o;
			}
			break;
		case AU1_CAKES_POS2:
			synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS2.ordinal()]) {
				_pasteles[1] = (Integer) o;
			}
			break;
		case AU1_CAKES_POS3:
			synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS3.ordinal()]) {
				_pasteles[2] = (Integer) o;
			}
			break;
		case AU1_CAKES_POS4:
			synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS4.ordinal()]) {
				_pasteles[3] = (Integer) o;
			}
			break;
		case AU1_CAKES_POS5:
			synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS5.ordinal()]) {
				_pasteles[4] = (Integer) o;
			}
			break;
		case AU1_CAKES_POS6:
			synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS6.ordinal()]) {
				_pasteles[5] = (Integer) o;
			}
			break;
		case AU1_CAKES_POS7:
			synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS7.ordinal()]) {
				_pasteles[6] = (Integer) o;
			}
			break;
		case AU1_STATE:
			synchronized (_cerrojos[TransferBufferKeys.AU1_STATE.ordinal()]) {
				_statesAutRob[0] = (Boolean) o;
			}
			break;
		case AU2_CONVEYOR_BELT_SIZE:
			synchronized (_cerrojos[TransferBufferKeys.AU2_CONVEYOR_BELT_SIZE
					.ordinal()]) {
				_cbBlisterSize = ((Double)o).doubleValue();
			}
			break;
		case AU2_CONVEYOR_BELT_SPEED:
			synchronized (_cerrojos[TransferBufferKeys.AU2_CONVEYOR_BELT_SPEED
					.ordinal()]) {
				_cbBlisterSpeed = ((Double)o).doubleValue();
			}
			break;
		case AU2_VACUUM_SEALING_MACHINE:
			synchronized (_cerrojos[TransferBufferKeys.AU2_VACUUM_SEALING_MACHINE
					.ordinal()]) {

			}
			break;
		case AU2_BLISTERS_POS1:
			synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS1.ordinal()]) {
				_blisters[0] = (Integer) o;
			}
			break;
		case AU2_BLISTERS_POS2:
			synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS2.ordinal()]) {
				_blisters[1] = (Integer) o;
			}
			break;
		case AU2_BLISTERS_POS3:
			synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS3.ordinal()]) {
				_blisters[2] = (Integer) o;
			}
			break;
		case AU2_BLISTERS_POS4:
			synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS4.ordinal()]) {
				_blisters[3] = (Integer) o;
			}
			break;
		case AU2_BLISTERS_POS5:
			synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS5.ordinal()]) {
				_blisters[4] = (Integer) o;
			}
			break;
		case AU2_STATE:
			synchronized (_cerrojos[TransferBufferKeys.AU2_STATE.ordinal()]) {
				_statesAutRob[1] = (Boolean) o;
			}
			break;
		case TABLE_CONTENT:
			synchronized (_cerrojos[TransferBufferKeys.TABLE_CONTENT.ordinal()]) {
				_tableContent = (Integer) o;
			}
			break;
		case AU3_CONVEYOR_BELT_SIZE:
			synchronized (_cerrojos[TransferBufferKeys.AU3_CONVEYOR_BELT_SIZE
					.ordinal()]) {
				_cbPackageSize = ((Double)o).doubleValue();
			}
			break;
		case AU3_CONVEYOR_BELT_SPEED:
			synchronized (_cerrojos[TransferBufferKeys.AU3_CONVEYOR_BELT_SPEED
					.ordinal()]) {
				_cbPackageSpeed = ((Double)o).doubleValue();
			}
			break;
		case AU3_PACKAGE_POS1:
			synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS1.ordinal()]) {
				_paquetes[0] = (Integer) o;
			}
			break;
		case AU3_PACKAGE_POS2:
			synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS2.ordinal()]) {
				_paquetes[1] = (Integer) o;
			}
			break;
		case AU3_PACKAGE_POS3:
			synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS3.ordinal()]) {
				_paquetes[2] = (Integer) o;
			}
			break;
		case AU3_PACKAGE_POS4:
			synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS4.ordinal()]) {
				_paquetes[3] = (Integer) o;
			}
			break;
		case AU3_PACKAGE_POS5:
			synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS5.ordinal()]) {
				_paquetes[4] = (Integer) o;
			}
			break;
		case AU3_STATE:
			synchronized (_cerrojos[TransferBufferKeys.AU3_STATE.ordinal()]) {
				_statesAutRob[2] = (Boolean) o;
			}
			break;
		case GEN_BLISTER_SIZE:
			synchronized (_cerrojos[TransferBufferKeys.GEN_BLISTER_SIZE
					.ordinal()]) {

			}
			break;
		case GEN_CAKE_SIZE:
			synchronized (_cerrojos[TransferBufferKeys.GEN_CAKE_SIZE.ordinal()]) {

			}
			break;
		case GEN_CLOCK_TIME:
			synchronized (_cerrojos[TransferBufferKeys.GEN_CLOCK_TIME.ordinal()]) {

			}
			break;
		case GEN_ROBOT_INTERFERENCE:
			synchronized (_cerrojos[TransferBufferKeys.GEN_ROBOT_INTERFERENCE
					.ordinal()]) {

			}
			break;
		case GEN_SENSOR_ERROR:
			synchronized (_cerrojos[TransferBufferKeys.GEN_SENSOR_ERROR
					.ordinal()]) {

			}
			break;
		case RB1_BLISTER_DELAY:
			synchronized (_cerrojos[TransferBufferKeys.RB1_BLISTER_DELAY
					.ordinal()]) {
				_rb1BlisterDelay = ((Integer)o).intValue();
			}
			break;
		case RB1_CAKE_DELAY:
			synchronized (_cerrojos[TransferBufferKeys.RB1_CAKE_DELAY.ordinal()]) {
				_rb1CakeDelay = ((Integer)o).intValue();
			}
			break;
		case RB1_STATE:
			synchronized (_cerrojos[TransferBufferKeys.RB1_STATE.ordinal()]) {
				_statesAutRob[3] = (Boolean) o;
			}
			break;
		case RB1_CONTENT:
			synchronized (_cerrojos[TransferBufferKeys.RB1_CONTENT.ordinal()]) {
				_rb1Content = ((Integer)o).intValue();
			}
			break;
		case RB2_BLISTER_DELAY:
			synchronized (_cerrojos[TransferBufferKeys.RB2_BLISTER_DELAY
					.ordinal()]) {
				_rb2BlisterDelay = ((Integer)o).intValue();
			}
			break;
		case RB2_STATE:
			synchronized (_cerrojos[TransferBufferKeys.RB2_STATE.ordinal()]) {
				_statesAutRob[4] = (Boolean) o;
			}
			break;
		case RB2_CONTENT:
			synchronized (_cerrojos[TransferBufferKeys.RB2_CONTENT.ordinal()]) {
				_rb2Content = ((Integer)o).intValue();
			}
			break;
		case AU1_MOVE:
			synchronized (_cerrojos[TransferBufferKeys.AU1_MOVE.ordinal()]) {
				_au1Move = ((Boolean)o).booleanValue();
			}
			break;
		case AU2_MOVE:
			synchronized (_cerrojos[TransferBufferKeys.AU2_MOVE.ordinal()]) {
				_au2Move = ((Boolean)o).booleanValue();
			}
			break;
		case AU3_MOVE:
			synchronized (_cerrojos[TransferBufferKeys.AU3_MOVE.ordinal()]) {
				_au3Move = ((Boolean)o).booleanValue();
			}
			break;
		case GOOD_PACKAGES:
			synchronized (_cerrojos[TransferBufferKeys.GOOD_PACKAGES.ordinal()]) {
				_numGoodPackages = ((Integer)o).intValue();
			}
			break;
		case BAD_PACKAGES:
			synchronized (_cerrojos[TransferBufferKeys.BAD_PACKAGES.ordinal()]) {
				_numBadPackages = ((Integer)o).intValue();
			}
			break;
		case GOOD_PACKAGES_TOTAL:
			synchronized (_cerrojos[TransferBufferKeys.GOOD_PACKAGES_TOTAL.ordinal()]) {
				_numGoodPackagesTotal = ((Integer)o).intValue();
			}
			break;
		case BAD_PACKAGES_TOTAL:
			synchronized (_cerrojos[TransferBufferKeys.BAD_PACKAGES_TOTAL.ordinal()]) {
				_numBadPackagesTotal = ((Integer)o).intValue();
			}
			break;

		default:
			break;
		}

	}

	public boolean is_cintaPasteles() {
		synchronized (_cerrojos[TransferBufferKeys.AU1_STATE.ordinal()]) {
			return _statesAutRob[0];
		}
	}

	public boolean is_cintaBlister() {
		synchronized (_cerrojos[TransferBufferKeys.AU2_STATE.ordinal()]) {
			return _statesAutRob[1];
		}
	}

	public boolean is_cintaMontaje() {
		synchronized (_cerrojos[TransferBufferKeys.AU3_STATE.ordinal()]) {
			return _statesAutRob[2];
		}
	}

	public boolean is_brazoMontaje() {
		synchronized (_cerrojos[TransferBufferKeys.RB1_STATE.ordinal()]) {
			return _statesAutRob[3];
		}
	}

	public boolean is_brazoDesechar() {
		synchronized (_cerrojos[TransferBufferKeys.RB2_STATE.ordinal()]) {
			return _statesAutRob[4];
		}
	}

	public int[] get_pasteles() {
		int[] ret = new int[_pasteles.length];

		synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS1.ordinal()]) {
			ret[0] = _pasteles[0];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS2.ordinal()]) {
			ret[1] = _pasteles[1];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS3.ordinal()]) {
			ret[2] = _pasteles[2];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS4.ordinal()]) {
			ret[3] = _pasteles[3];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS5.ordinal()]) {
			ret[4] = _pasteles[4];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS6.ordinal()]) {
			ret[5] = _pasteles[5];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU1_CAKES_POS7.ordinal()]) {
			ret[6] = _pasteles[6];
		}

		return ret;
	}
	
	public int[] get_blisters() {
		int[] ret = new int[_blisters.length];

		synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS1.ordinal()]) {
			ret[0] = _blisters[0];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS2.ordinal()]) {
			ret[1] = _blisters[1];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS3.ordinal()]) {
			ret[2] = _blisters[2];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS4.ordinal()]) {
			ret[3] = _blisters[3];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU2_BLISTERS_POS5.ordinal()]) {
			ret[4] = _blisters[4];
		}

		return ret;
	}
	
	public int[] get_paquetes() {
		int[] ret = new int[_paquetes.length];

		synchronized (_cerrojos[TransferBufferKeys.AU3_PACKAGE_POS1.ordinal()]) {
			ret[0] = _paquetes[0];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU3_PACKAGE_POS2.ordinal()]) {
			ret[1] = _paquetes[1];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU3_PACKAGE_POS3.ordinal()]) {
			ret[2] = _paquetes[2];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU3_PACKAGE_POS4.ordinal()]) {
			ret[3] = _paquetes[3];
		}
		synchronized (_cerrojos[TransferBufferKeys.AU3_PACKAGE_POS5.ordinal()]) {
			ret[4] = _paquetes[4];
		}

		return ret;
	}

	public boolean isStarted(ElementsGroupModelEnum egme) {
		boolean ret = false;
		switch (egme) {
			case CINTA1:
				synchronized (_cerrojos[TransferBufferKeys.AU1_STATE.ordinal()]) {
					ret = _statesAutRob[0];
				}
				break;
			case CINTA2:
				synchronized (_cerrojos[TransferBufferKeys.AU2_STATE.ordinal()]) {
					ret = _statesAutRob[1];
				}	
				break;
			case CINTA3:
				synchronized (_cerrojos[TransferBufferKeys.AU3_STATE.ordinal()]) {
					ret = _statesAutRob[2];
				}
				break;
			case ROBOT1:
				synchronized (_cerrojos[TransferBufferKeys.RB1_STATE.ordinal()]) {
					ret = _statesAutRob[3];
				}
				break;
			case ROBOT2:
				synchronized (_cerrojos[TransferBufferKeys.RB2_STATE.ordinal()]) {
					ret = _statesAutRob[4];
				}
				break;
		}

		return ret;
	}

	public double get_cbCakeSize() {
		synchronized (_cerrojos[TransferBufferKeys.AU1_CONVEYOR_BELT_SIZE
		    					.ordinal()]) {
			return _cbCakeSize;
		}
	}

	public double get_cbBlisterSize() {
		synchronized (_cerrojos[TransferBufferKeys.AU2_CONVEYOR_BELT_SIZE
		    					.ordinal()]) {
			return _cbBlisterSize;
		}
	}
	
	public double get_cbPackageSize() {
		synchronized (_cerrojos[TransferBufferKeys.AU3_CONVEYOR_BELT_SIZE
		    					.ordinal()]) {
			return _cbPackageSize;
		}
	}

	public double get_cbCakeSpeed() {
		synchronized (_cerrojos[TransferBufferKeys.AU1_CONVEYOR_BELT_SPEED
		    					.ordinal()]) {
			return _cbCakeSpeed;
		}
	}

	public double get_cbBlisterSpeed() {
		synchronized (_cerrojos[TransferBufferKeys.AU2_CONVEYOR_BELT_SPEED
		    					.ordinal()]) {
			return _cbBlisterSpeed;
		}
	}

	public double get_cbPackageSpeed() {
		synchronized (_cerrojos[TransferBufferKeys.AU3_CONVEYOR_BELT_SPEED
		    					.ordinal()]) {
			return _cbPackageSpeed;
		}
	}

	public int get_cakeDepot() {
		synchronized (_cerrojos[TransferBufferKeys.AU1_CAKE_DEPOT.ordinal()]) {
			return _cakeDepot;
		}
	}

	public int get_rb1Content() {
		synchronized (_cerrojos[TransferBufferKeys.RB1_CONTENT.ordinal()]) {
			return _rb1Content;
		}
	}
	
	public int get_rb2Content() {
		synchronized (_cerrojos[TransferBufferKeys.RB2_CONTENT.ordinal()]) {
			return _rb2Content;
		}
	}
	
	public int get_tableContent() {
		synchronized (_cerrojos[TransferBufferKeys.TABLE_CONTENT.ordinal()]) {
			return _tableContent;
		}
	}

	public boolean is_au1Move() {
		synchronized (_cerrojos[TransferBufferKeys.AU1_MOVE.ordinal()]) {
			return _au1Move;
		}
	}

	public boolean is_au2Move() {
		synchronized (_cerrojos[TransferBufferKeys.AU2_MOVE.ordinal()]) {
			return _au2Move;
		}
	}

	public boolean is_au3Move() {
		synchronized (_cerrojos[TransferBufferKeys.AU3_MOVE.ordinal()]) {
			return _au3Move;
		}
	}
	
	public int get_goodPackages() {
		synchronized (_cerrojos[TransferBufferKeys.GOOD_PACKAGES.ordinal()]) {
			return _numGoodPackages;
		}
	}
	
	public int get_badPackages() {
		synchronized (_cerrojos[TransferBufferKeys.BAD_PACKAGES.ordinal()]) {
			return _numBadPackages;
		}
	}
	
	public int get_goodPackagesTotal() {
		synchronized (_cerrojos[TransferBufferKeys.GOOD_PACKAGES_TOTAL.ordinal()]) {
			return _numGoodPackagesTotal;
		}
	}
	
	public int get_badPackagesTotal() {
		synchronized (_cerrojos[TransferBufferKeys.BAD_PACKAGES_TOTAL.ordinal()]) {
			return _numBadPackagesTotal;
		}
	}
	
	public int get_rb1BlisterDelay() {
		synchronized (_cerrojos[TransferBufferKeys.RB1_BLISTER_DELAY.ordinal()]) {
			return _rb1BlisterDelay;
		}
	}
	
	public int get_rb2BlisterDelay() {
		synchronized (_cerrojos[TransferBufferKeys.RB2_BLISTER_DELAY.ordinal()]) {
			return _rb2BlisterDelay;
		}
	}
	
	public int get_rb1CakeDelay() {
		synchronized (_cerrojos[TransferBufferKeys.RB1_CAKE_DELAY.ordinal()]) {
			return _rb1CakeDelay;
		}
	}

}
