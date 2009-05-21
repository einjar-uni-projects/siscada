package com.umbrella.automaster.logic;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.ContextoMaestro;
import com.umbrella.autocommon.ContextoRobot;
import com.umbrella.autocommon.MasterConfiguration;
import com.umbrella.mail.mailbox.ClientMailBox;


/*
 * esta clase gestiona los automatas
 * 
 */
public class Maestro {

	/*
	 * esta clase no toca el contexto de los demas automatas pero si lo recibe y lo almacena an caso de parada de emergencia, en caso de arranque 
	 * desde la parada de emergencia lo envia desde lo q almaceno y los automatas que deben de estar parados sustituiran su contexto por el enviado
	 * 
	 * en caso de arranque normal no envia un contexto, solo la orden de arranque.
	 */
	private static ContextoMaestro _contextoMaestro=ContextoMaestro.getInstance();;
	private static MasterConfiguration _configuracion;
	private static Configuration _general;
	
	
	private static ClientMailBox _correoAut1;
	private static ClientMailBox _correoAut2;
	private static ClientMailBox _correoAut3;
	private static ClientMailBox _correoRobot1;
	private static ClientMailBox _correoRobot2;
	private static ClientMailBox _scada;

	private static Clock _clock;
	private static long click;
	private static long clickAnterior;
	
	private static String host = "localhost";
	private static int puerto = 9003;
	
	public static void main(String [] args){
		
		inicializar();
		clickAnterior=_clock.getClock();
		
		while(!_contextoMaestro.is_FIN()){
			click=_clock.getClock();
			
			if(clickAnterior<click){
				clickAnterior=click;
				
				/*
				 * Leer todos los mensajes
				 * DESPUES actuar en consecuencia
				 */
				
				
				/*
				 * enviar el estado interno de todos los automatas al scada porque necesita conocerlo para reflejarlo en la interfaz 
				 * _contextoAut1.getDispositivosInernos();
				 * _contextoRobot1.getEstadoInterno();
				 */
				
		/*
		 * acciones:
		 */
				
				/*
				 * el estado interno del aut1 me dice q tiene el fin de la cinta ocupado envio el mensaje al robot 1 si esta en modo reposo
				 * y tengo un blister con posicion libre
				 */
				
				/*
				 * el robot 1 recogio el pastel: envia el mensaje de pastel recogido 
				 */
				
				/*
				 * el robot 1 abandona la zona de interf con cinta 1: envia el mensaje de fin de interferencia al aut1 
				 */
				
				/*
				 * el estado interno del aut2 me dice q tiene el fin de la cinta ocupado y el inicio de la cinta 3 esta libre
				 * envio el mensaje al robot 1 si esta en modo reposo
				 */
				
				/*
				 * si el robot 1 recogio el blister envio el mensaje al aut2 de fin de cinta libre
				 */
				
				/*
				 * si el robot 1 avandona la zona de interferencia con el aut2 envio el mensaje al aut2 de fin de Interferencia
				 */
	
				/*
				 * si el robot 1 envia el mensaje de blister colocado cambio mi estado interno a blistercolocado=true;
				 */
				
				/*
				 * si el robot 1 envia el mensaje de pastel colocado incremento mi contador interno de pasteles colocados
				 */
				
				/*
				 * si mi contadorPasteles interno = 4, envio el mensaje de mover blister listo al robot1
				 */
				
				/*
				 * Si recibo el mensaje de blister colocado en cinta 3 y mesa libre cambio el cont de pasteles a 0 y blistercolocado=false; 
				 */
				
				/*
				 * si el contexto del aut 1 me dice q quedan pocos pasteles envio el mensaje a SCADA de pocos pasteles
				 */
				
				/*
				 * si recibo el mensaje de rellenar dispensador lo hago 
				 */
				
				/*
				 * si recibo el mensaje de modificar un campo lo hago, velocidad, tama�o, tiempos
				 */
				
				/*
				 * si el estado interno del aut3 me dice q hay blister listo lo cogo
				 * el contexto me dice si el ultimo blister paso el control
				 * al enviarle el mensaje de ir a por el blister tb se le envia esa informacion 
				 */
				
				/*
				 * si recibo el mensaje del robot 2 de interferencia se lo reenvio al aut3
				 */
				
				/*
				 * si recibo el mensaje de blister recogido del robot2 se lo comnunico al aut3
				 */
				
				/*
				 * si recibo el mensaje del robot 2 de fin de interferencia se lo reenvio al aut3
				 */
				
				/*
				 * si recibo el mensaje de blister situado del robot 2 se lo comunico al SCADA y cambio mi valor interno
				 */
				
			}// fin de: if(clickAnterior<click), es decir lo q esta dentro es lo que se hace en cada click
		}// fin del while(!fin)
		
		apagar();
	}
	
	private static void apagar(){
		/*
		 * enviar el mensaje de apagado a todos
		 */
	}
	private static void inicializar(){
		_contextoMaestro=ContextoMaestro.getInstance();
		_configuracion=MasterConfiguration.getInstance();
		_general=new Configuration(_configuracion); //configuracion q se pasa a los automatas
		// el contexto es el mismo en todos pero es porque acabamos de inicializar
		/*
		_contextoAut1=Context.getInstance();
		_contextoAut2=Context.getInstance();
		_contextoAut3=Context.getInstance();
		_contextoRobot1=ContextoRobot.getInstance();
		_contextoRobot2=ContextoRobot.getInstance();
		*/
		/*
		 * se inicilaizan los buzones
		 */
		try {
				_correoAut1=new ClientMailBox(host,puerto,"SalidaMaestro1","EntradaMaestro1");
				_correoAut2=new ClientMailBox(host,puerto,"SalidaMaestro2","EntradaMaestro2");
				_correoAut3=new ClientMailBox(host,puerto,"SalidaMaestro3","EntradaMaestro3");
				_correoRobot1=new ClientMailBox(host,puerto,"SalidaRobot1","EntradaRobot1");
				_correoRobot2=new ClientMailBox(host,puerto,"SalidaRobot2","EntradaRobot2");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		_clock=Clock.getInstance();
		
		/*
		 * se envian los mensajes a los destinos con la configuracion
		 * el mensaje les dice q es un arranque desde 0
		 */
	}
	
	/*
	 * Carga los contextos almacenados, se usa para un arranque desde la parada de emergencia
	 */
	private static void cargarContexto(){
		
	}
	
	/*
	 * salva los contextos
	 */
	private static void salvarContextos(){
		
	}

	private static int cambiarVelCinta(int cinta, double valor){
		int sal=-1;
		switch (cinta) {
		case 0:
			//aut 1
			if(!_contextoMaestro.getEjecutando()[0]){
				_configuracion.setVelCintaAut1(valor);
				sal=1;
			}
			break;
		case 1:
			//aut 2
			if(!_contextoMaestro.getEjecutando()[1]){
				_configuracion.setVelCintaAut2(valor);
				sal=1;
			}
			break;
		case 2:
			//aut 3
			if(!_contextoMaestro.getEjecutando()[2]){
				_configuracion.setVelCintaAut3(valor);
				sal=1;
			}
			break;
		default:
			sal=-2;
			System.out.println("opcion no valida");
		break;
		}
		return sal;
	}
	
	private static int cambiarTamanyoCinta(int cinta, double valor){
		int sal=-1;
		switch (cinta) {
		case 0:
			//aut 1
			if(!_contextoMaestro.getEjecutando()[0]){
				_configuracion.setSizeCintaAut1(valor);
				sal=1;
			}
			break;
		case 1:
			//aut 2
			if(!_contextoMaestro.getEjecutando()[1]){
				_configuracion.setSizeCintaAut2(valor);
				sal=1;
			}
			break;
		case 2:
			//aut 3
			if(!_contextoMaestro.getEjecutando()[2]){
				_configuracion.setSizeCintaAut3(valor);
				sal=1;
			}
			break;
		default:
			sal=-1;
			System.out.println("opcion no valida");
		break;
		}
		return sal;
	}
	
	private static int cambiarTiempoRobot(int robot, int valor){
		int sal=-1;
		switch (robot) {
		case 0:
			// robot 1 y mover pastel
			if(!_contextoMaestro.getEjecutando()[3]){
				_configuracion.setMoverPastel(valor);
				sal=-1;
			}
			break;
		case 1:
			// robot 1 y mover blister
			if(!_contextoMaestro.getEjecutando()[3]){
				_configuracion.setMoverBlister(valor);
				sal=1;
			}
			break;
		case 2:
			// robot 2 
			if(!_contextoMaestro.getEjecutando()[4]){
				_configuracion.setAlmacenarBlister(valor);
				sal=1;
			}
			break;
		default:
			sal=-2;
			break;
		}
		return sal;
	}
	
	private static  int cambiarTiempoMaquina(int maquina, int valor){
		int sal=-1;
		switch (maquina) {
		case 0:
			// maquina de chocolate
			if(!_contextoMaestro.getEjecutando()[0]){
				_configuracion.setValvChoc(valor);
				sal=1;
			}
			break;
		case 1:
			// maquina de caramelo
			if(!_contextoMaestro.getEjecutando()[0]){
				_configuracion.setValvCaram(valor);
				sal=1;
			}
			break;
		case 2:
			// maquina de sellado
			if(!_contextoMaestro.getEjecutando()[2]){
				_configuracion.setSelladora(valor);
				sal=1;
			}
			break;
		default:
			sal=-2;
			break;
		}
		return sal;
	}
	
	private static int cambiarTamanyoBiscocho(double valor){
		int sal=-1;
		if(!_contextoMaestro.getEjecutando()[0] && !_contextoMaestro.getEjecutando()[1] && !_contextoMaestro.getEjecutando()[2] && 
				!_contextoMaestro.getEjecutando()[3] && !_contextoMaestro.getEjecutando()[4]){
			_configuracion.setSizeBizcocho(valor);
			sal=1;
		}
		return sal;
	}

	/*
	 * metodo de puesta en marcha desde 0
	 */
	private static int nuevoArranque(){
		int sal=-1;
		return sal;
	}
	
	/*
	 * metodo de parada
	 */
	private static int paradaNormal(){
		int sal=-1;
		return sal;
	}
	
	/*
	 * metodo de parada de emergencia
	 */
	private static int paradaEmergencia(){
		int sal=-1;
		return sal;
	}
	
	/*
	 * metodo de puesta en marcha desde una parada
	 */
	private static int arranqueDesdeParada(){
		int sal=-1;
		return sal;
	}
	
	/*
	 * metodo de recarga de dispensadora
	 */
	private static int recargaDispensadora(){
		//envia un mensaje a la dispensadora diciendole cuantos pasteles se han recargado
		int sal=-1;
		return sal;
	}
	
	private static int recargaDispensadora(int cantidad){
		//envia un mensaje a la dispensadora diciendole cuantos pasteles se han recargado
		int sal=-1;
		return sal;
	}
}
