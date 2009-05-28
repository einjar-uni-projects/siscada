package com.umbrella.automaster.logic;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.MasterConfiguration;
import com.umbrella.autocommon.MasterContext;
import com.umbrella.autocommon.Notifiable;
import com.umbrella.automaster.comm.ReceiveAutomaton1;
import com.umbrella.automaster.comm.ReceiveAutomaton2;
import com.umbrella.automaster.comm.ReceiveAutomaton3;
import com.umbrella.automaster.comm.ReceiveRobot1;
import com.umbrella.automaster.comm.ReceiveRobot2;
import com.umbrella.automaster.comm.ReceiveSCADA;
import com.umbrella.utils.MachineNames;

/*
 * esta clase gestiona los automatas
 * 
 */
public class Maestro implements Notifiable  {

	/*
	 * esta clase no toca el contexto de los demas automatas pero si lo recibe y
	 * lo almacena an caso de parada de emergencia, en caso de arranque desde la
	 * parada de emergencia lo envia desde lo q almaceno y los automatas que
	 * deben de estar parados sustituiran su contexto por el enviado
	 * 
	 * en caso de arranque normal no envia un contexto, solo la orden de
	 * arranque.
	 */
	private MasterContext _contextoMaestro = MasterContext.getInstance();;
	private MasterConfiguration _configuracion;
	private Configuration _general;
	private ReceiveRobot1 _reciveRB1;
	private ReceiveRobot2 _reciveRB2;
	private ReceiveAutomaton1 _reciveAU1;
	private ReceiveAutomaton2 _reciveAU2;
	private ReceiveAutomaton3 _reciveAU3;
	private ReceiveSCADA _reciveSCADA;
	private boolean _joy = true;

	private Clock _clock;
	private long click;
	private long clickAnterior;

	// El constructor privado no permite que se genere un constructor por
	// defecto
	// (con mismo modificador de acceso que la definicion de la clase)
	private Maestro() {
		inicializar();
	}

	/**
	 * Obtiene la instancia única del objeto, la primera invocación realiza la
	 * creación del mismo.
	 * 
	 * @return la instancia única de Maestro
	 */
	public static Maestro getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private final static Maestro instance = new Maestro();
	}

	public void execute() {
		_clock.start();
		while (!_contextoMaestro.is_FIN()) {
			pauseJoy();
			guardedJoy();
			_reciveAU1.start();
			_reciveAU2.start();
			_reciveRB1.start();
			_reciveAU3.start();
			_reciveRB2.start();
			_reciveSCADA.start();
			rebootThreads();
		}
		apagar();
	}

	private void rebootThreads() {
		_reciveRB1 = new ReceiveRobot1();
		_reciveRB1.inicializar();
		_reciveRB2 = new ReceiveRobot2();
		_reciveRB2.inicializar();
		_reciveAU1 = new ReceiveAutomaton1();
		_reciveAU1.inicializar();
		_reciveAU2 = new ReceiveAutomaton2();
		_reciveAU2.inicializar();
		_reciveAU3 = new ReceiveAutomaton3();
		_reciveAU3.inicializar();
		_reciveSCADA = new ReceiveSCADA();
		_reciveSCADA.inicializar();
	}

	public synchronized void guardedJoy() {
		// This guard only loops once for each special event, which may not
		// be the event we're waiting for.
		while (!_joy) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}
	
	@Override
	public void notifyNoSyncJoy(NotificableSignal signal) {
		notifyJoy();
	}

	public synchronized void notifyJoy() {
		_joy = true;
		notifyAll();
	}

	public synchronized void pauseJoy() {
		_joy = false;
	}

	private void apagar() {
		/*
		 * _reciveRB1.stop(); _reciveRB2.stop(); _reciveAU1.stop();
		 * _reciveAU2.stop(); _reciveAU3.stop(); _reciveSCADA.stop();
		 */
	}

	public void inicializar() {
		_contextoMaestro = MasterContext.getInstance();
		_configuracion = MasterConfiguration.getInstance();
		_general = new Configuration(_configuracion); // configuracion q se pasa
														// a los automatas
		// el contexto es el mismo en todos pero es porque acabamos de
		// inicializar
		_clock = Clock.getInstance();
		_clock.addNotificable(this);

		rebootThreads();

		/*
		 * se envian los mensajes a los destinos con la configuracion el mensaje
		 * les dice q es un arranque desde 0
		 */
	}
	
	private void reiniciarConfiguracionAut(){
		_general = new Configuration(_configuracion);
	}

	/*
	 * Carga los contextos almacenados, se usa para un arranque desde la parada
	 * de emergencia
	 */
	private void cargarContexto() {

	}

	/*
	 * salva los contextos
	 */
	private void salvarContextos() {

	}

	public synchronized int cambiarVelCinta(MachineNames cinta, double valor) {
		int sal = -1;
		switch (cinta) {
		case CINTA_1:
			// aut 1
			if (!_contextoMaestro.getEjecutando()[0]) {
				_configuracion.setVelCintaAut1(valor);
				sal = 1;
			}
			break;
		case CINTA_2:
			// aut 2
			if (!_contextoMaestro.getEjecutando()[1]) {
				_configuracion.setVelCintaAut2(valor);
				sal = 1;
			}
			break;
		case CINTA_3:
			// aut 3
			if (!_contextoMaestro.getEjecutando()[2]) {
				_configuracion.setVelCintaAut3(valor);
				sal = 1;
			}
			break;
		default:
			sal = -2;
			System.out.println("opcion no valida");
			break;
		}
		reiniciarConfiguracionAut();
		return sal;
	}

	public synchronized int cambiarTamanyoCinta(MachineNames cinta, double valor) {
		int sal = -1;
		switch (cinta) {
		case CINTA_1:
			// aut 1
			if (!_contextoMaestro.getEjecutando()[0]) {
				_configuracion.setSizeCintaAut1(valor);
				sal = 1;
			}
			break;
		case CINTA_2:
			// aut 2
			if (!_contextoMaestro.getEjecutando()[1]) {
				_configuracion.setSizeCintaAut2(valor);
				sal = 1;
			}
			break;
		case CINTA_3:
			// aut 3
			if (!_contextoMaestro.getEjecutando()[2]) {
				_configuracion.setSizeCintaAut3(valor);
				sal = 1;
			}
			break;
		default:
			sal = -1;
			System.out.println("opcion no valida");
			break;
		}
		
		reiniciarConfiguracionAut();
		return sal;
	}

	public int cambiarTiempoRobot(int robot, int valor) {
		int sal = -1;
		switch (robot) {
		case 0:
			// robot 1 y mover pastel
			if (!_contextoMaestro.getEjecutando()[3]) {
				_configuracion.setMoverPastel(valor);
				sal = -1;
			}
			break;
		case 1:
			// robot 1 y mover blister
			if (!_contextoMaestro.getEjecutando()[3]) {
	System.err.println("cambia");
				_configuracion.setMoverBlister(valor);
				sal = 1;
			}
			break;
		case 2:
			// robot 2
			if (!_contextoMaestro.getEjecutando()[4]) {
				_configuracion.setAlmacenarBlister(valor);
				sal = 1;
			}
			break;
		default:
			sal = -2;
			break;
		}
		reiniciarConfiguracionAut();
		return sal;
	}

	private int cambiarTiempoMaquina(int maquina, int valor) {
		int sal = -1;
		switch (maquina) {
		case 0:
			// maquina de chocolate
			if (!_contextoMaestro.getEjecutando()[0]) {
				_configuracion.setValvChoc(valor);
				sal = 1;
			}
			break;
		case 1:
			// maquina de caramelo
			if (!_contextoMaestro.getEjecutando()[0]) {
				_configuracion.setValvCaram(valor);
				sal = 1;
			}
			break;
		case 2:
			// maquina de sellado
			if (!_contextoMaestro.getEjecutando()[2]) {
				_configuracion.setSelladora(valor);
				sal = 1;
			}
			break;
		default:
			sal = -2;
			break;
		}
		reiniciarConfiguracionAut();
		return sal;
	}

	private int cambiarTamanyoBiscocho(double valor) {
		int sal = -1;
		if (!_contextoMaestro.getEjecutando()[0]
				&& !_contextoMaestro.getEjecutando()[1]
				&& !_contextoMaestro.getEjecutando()[2]
				&& !_contextoMaestro.getEjecutando()[3]
				&& !_contextoMaestro.getEjecutando()[4]) {
			_configuracion.setSizeBizcocho(valor);
			sal = 1;
		}
		reiniciarConfiguracionAut();
		return sal;
	}

	/*
	 * metodo de puesta en marcha desde 0
	 */
	private int nuevoArranque() {
		int sal = -1;
		return sal;
	}

	/*
	 * metodo de parada
	 */
	private int paradaNormal() {
		int sal = -1;
		return sal;
	}

	/*
	 * metodo de parada de emergencia
	 */
	private int paradaEmergencia() {
		int sal = -1;
		return sal;
	}

	/*
	 * metodo de puesta en marcha desde una parada
	 */
	private int arranqueDesdeParada() {
		int sal = -1;
		return sal;
	}

	/*
	 * metodo de recarga de dispensadora
	 */
	private int recargaDispensadora() {
		// envia un mensaje a la dispensadora diciendole cuantos pasteles se han
		// recargado
		int sal = -1;
		return sal;
	}

	private int recargaDispensadora(int cantidad) {
		// envia un mensaje a la dispensadora diciendole cuantos pasteles se han
		// recargado
		int sal = -1;
		return sal;
	}

	public Configuration getConfiguration() {
		return _general;
	}
	
	public synchronized void guardedJoy2() {
	}
	
	@Override
	public void notifyNoSyncJoy2() {

	}

	public synchronized void notifyJoy2() {
	}

	public synchronized void pauseJoy2() {
	}
}
