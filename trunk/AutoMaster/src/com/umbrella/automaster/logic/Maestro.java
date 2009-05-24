package com.umbrella.automaster.logic;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.ContextoMaestro;
import com.umbrella.autocommon.MasterConfiguration;
import com.umbrella.autocommon.Notificable;
import com.umbrella.automaster.comm.ReceiveAutomaton1;
import com.umbrella.automaster.comm.ReceiveAutomaton2;
import com.umbrella.automaster.comm.ReceiveAutomaton3;
import com.umbrella.automaster.comm.ReceiveRobot1;
import com.umbrella.automaster.comm.ReceiveRobot2;
import com.umbrella.automaster.comm.ReceiveSCADA;

/*
 * esta clase gestiona los automatas
 * 
 */
public class Maestro implements Notificable  {

	/*
	 * esta clase no toca el contexto de los demas automatas pero si lo recibe y
	 * lo almacena an caso de parada de emergencia, en caso de arranque desde la
	 * parada de emergencia lo envia desde lo q almaceno y los automatas que
	 * deben de estar parados sustituiran su contexto por el enviado
	 * 
	 * en caso de arranque normal no envia un contexto, solo la orden de
	 * arranque.
	 */
	private ContextoMaestro _contextoMaestro = ContextoMaestro.getInstance();;
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
			//System.out.println("Me duermo"); // TODO quitar
			pauseJoy();
			guardedJoy();
			//System.out.println("Me despierto");
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
		_contextoMaestro = ContextoMaestro.getInstance();
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

	private int cambiarVelCinta(int cinta, double valor) {
		int sal = -1;
		switch (cinta) {
		case 0:
			// aut 1
			if (!_contextoMaestro.getEjecutando()[0]) {
				_configuracion.setVelCintaAut1(valor);
				sal = 1;
			}
			break;
		case 1:
			// aut 2
			if (!_contextoMaestro.getEjecutando()[1]) {
				_configuracion.setVelCintaAut2(valor);
				sal = 1;
			}
			break;
		case 2:
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
		return sal;
	}

	private int cambiarTamanyoCinta(int cinta, double valor) {
		int sal = -1;
		switch (cinta) {
		case 0:
			// aut 1
			if (!_contextoMaestro.getEjecutando()[0]) {
				_configuracion.setSizeCintaAut1(valor);
				sal = 1;
			}
			break;
		case 1:
			// aut 2
			if (!_contextoMaestro.getEjecutando()[1]) {
				_configuracion.setSizeCintaAut2(valor);
				sal = 1;
			}
			break;
		case 2:
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
		return sal;
	}

	private int cambiarTiempoRobot(int robot, int valor) {
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
	public void notifyNoSyncJoy2(String machine) {

	}

	public synchronized void notifyJoy2() {
	}

	public synchronized void pauseJoy2() {
	}

	/*
	 * Leer todos los mensajes DESPUES actuar en consecuencia
	 */

	/*
	 * enviar el estado interno de todos los automatas al scada porque necesita
	 * conocerlo para reflejarlo en la interfaz
	 * _contextoAut1.getDispositivosInernos();
	 * _contextoRobot1.getEstadoInterno();
	 */

	/*
	 * acciones:
	 */

	/*
	 * el estado interno del aut1 me dice q tiene el fin de la cinta ocupado
	 * envio el mensaje al robot 1 si esta en modo reposo y tengo un blister con
	 * posicion libre
	 */

	/*
	 * el robot 1 recogio el pastel: envia el mensaje de pastel recogido
	 */

	/*
	 * el robot 1 abandona la zona de interf con cinta 1: envia el mensaje de
	 * fin de interferencia al aut1
	 */

	/*
	 * el estado interno del aut2 me dice q tiene el fin de la cinta ocupado y
	 * el inicio de la cinta 3 esta libre envio el mensaje al robot 1 si esta en
	 * modo reposo
	 */

	/*
	 * si el robot 1 recogio el blister envio el mensaje al aut2 de fin de cinta
	 * libre
	 */

	/*
	 * si el robot 1 avandona la zona de interferencia con el aut2 envio el
	 * mensaje al aut2 de fin de Interferencia
	 */

	/*
	 * si el robot 1 envia el mensaje de blister colocado cambio mi estado
	 * interno a blistercolocado=true;
	 */

	/*
	 * si el robot 1 envia el mensaje de pastel colocado incremento mi contador
	 * interno de pasteles colocados
	 */

	/*
	 * si mi contadorPasteles interno = 4, envio el mensaje de mover blister
	 * listo al robot1
	 */

	/*
	 * Si recibo el mensaje de blister colocado en cinta 3 y mesa libre cambio
	 * el cont de pasteles a 0 y blistercolocado=false;
	 */

	/*
	 * si el contexto del aut 1 me dice q quedan pocos pasteles envio el mensaje
	 * a SCADA de pocos pasteles
	 */

	/*
	 * si recibo el mensaje de rellenar dispensador lo hago
	 */

	/*
	 * si recibo el mensaje de modificar un campo lo hago, velocidad, tama�o,
	 * tiempos
	 */

	/*
	 * si el estado interno del aut3 me dice q hay blister listo lo cogo el
	 * contexto me dice si el ultimo blister paso el control al enviarle el
	 * mensaje de ir a por el blister tb se le envia esa informacion
	 */

	/*
	 * si recibo el mensaje del robot 2 de interferencia se lo reenvio al aut3
	 */

	/*
	 * si recibo el mensaje de blister recogido del robot2 se lo comnunico al
	 * aut3
	 */

	/*
	 * si recibo el mensaje del robot 2 de fin de interferencia se lo reenvio al
	 * aut3
	 */

	/*
	 * si recibo el mensaje de blister situado del robot 2 se lo comunico al
	 * SCADA y cambio mi valor interno
	 */
}
