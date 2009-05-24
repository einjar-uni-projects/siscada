package com.umbrella.autoslave.logic;


import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.ContextoRobot;
import com.umbrella.autocommon.Notifiable;
import com.umbrella.autocommon.Notifiable.NotificableSignal;
import com.umbrella.autoslave.executor.PropertiesFile;
import com.umbrella.mail.mailbox.ClientMailBox;
import com.umbrella.mail.mailbox.ServerMailBox;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.utils.properties.PropertiesFileHandler;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.RobotStates;
import com.umbrella.utils.NombreMaquinas;

/*
 * este robot tiene el estado reposo, el estado voy por blister y voy por pastel
 */
public class Robot1 implements Notifiable{

	private Configuration _configuracion= Configuration.getInstance();
	private ContextoRobot _contexto= ContextoRobot.getInstance();

	private Clock _clock;
	private boolean _joy = true;
	
	
	private ClientMailBox _buzon;

	private PropertiesFile pfmodel;

	/**
	 * @param args
	 */
	
	public Robot1(){
		_clock=new Clock();
		_clock.start();
		_clock.addNotificable(this);

		try {
			pfmodel = PropertiesFile.getInstance();
			PropertiesFileHandler.getInstance().LoadValuesOnModel(pfmodel);
		} catch (PropertyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PropertiesFileHandler.getInstance().writeFile();
		_buzon = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._reciveR1Name, ServerMailBox._sendR1Name);

		//_buzon=new ClientMailBox(host,puerto,"EntradaRobot1","SalidaRobot1");

		_contexto.setEstadoInterno(RobotStates.REPOSO);
	}
	public void execute() {
		// TODO Auto-generated method stub
		while(!_contexto.isFIN()){
			System.out.println("antes de dormir");
			pauseJoy();
			guardedJoy();
			System.out.println("despues de dormir");
			MessageInterface mensaje=null;
			do{
				try {
					System.out.println("antes de recibir");
					mensaje=_buzon.receive();
					System.out.println("despues de recibir");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(mensaje!=null){
					System.out.println("recibiendo el mensaje: " + mensaje.getIdentifier());
					switch (mensaje.getIdentifier()) {
					case ACTUALIZARCONTEXTO:							
						_contexto=(ContextoRobot)mensaje.getObject();
						break;
					case ACTUALIZARCONFIGURACION: 						
						_configuracion=(Configuration)mensaje.getObject();
						_contexto.setApagado(false);
						break;
					case START:
						_contexto=_contexto.reset();
						_contexto.setApagado(false);
						break;
					case PARADA:
						_contexto.setParadaCorrecta(true);
						break;
					case PARADAEMERGENCIA:
						_contexto.setApagado(true);
						break;
					case PASTELLISTO:
						_contexto.setPastelListo(true);
						break;
					case BLISTERLISTO:
						_contexto.setBlisterListo(true);
						break;
					case RESET:
						_contexto=_contexto.reset();
						break;
					case PARADAFALLO:
						_contexto.setFallo(true);
						break;
					case BLISTERCOMPLETO:
						_contexto.setBlisterCompletoListo(true);
						break;
					}
				}
			}while(mensaje!=null);

			if(_contexto.isParadaCorrecta()){
				//se para directamente porque no se controla
				_contexto.setApagado(true);
			}
			if(!_contexto.isFallo()){
				if(!_contexto.isApagado()){

					_contexto.setDiffTiempo(System.currentTimeMillis()-_contexto.getTiempo());
					if(_contexto.getEstadoInterno().equals(RobotStates.REPOSO)){
						/*
						 * si recibe un mensaje de recoger blister pues pasa al estado: CAMINOPOSICION_2 y coge el tiempo
						 * si recibe un mensaje de recoger pastel pues pasa al estado: CAMINOPOSICION_1 y coge el tiempo
						 * si recibe un mensaje de moverblistercompleto pues pasa al estado: DESPLAZARBLISTERCOMPLETO y coge el tiempo
						 */
						if(_contexto.isBlisterListo()) _contexto.setEstadoInterno(RobotStates.CAMINOPOSICION_2);
						if(_contexto.isPastelListo()) _contexto.setEstadoInterno(RobotStates.CAMINOPOSICION_1);
						if(_contexto.isBlisterCompletoListo()) _contexto.setEstadoInterno(RobotStates.DESPLAZARBLISTERCOMPLETO);

						_contexto.setTiempo(System.currentTimeMillis());
						_contexto.setDiffTiempo(System.currentTimeMillis()-_contexto.getTiempo());
					}else if(_contexto.getEstadoInterno().equals(RobotStates.CAMINOPOSICION_1)){
						// controlar interferencias, mejor lo hace el maestro
						if( _contexto.getDiffTiempo() > ((_configuracion.getMoverPastel() -_configuracion.getInterferencia()/2)*1000)){
							_contexto.setEstadoInterno(RobotStates.SOBREPOSICION_1);
							/*
							 * envia el mensaje de interferencia sobre la cinta 1
							 */
							MessageInterface send=new DefaultMessage();
							send.setIdentifier(MSGOntology.INTERFERENCIA);
							send.getParameters().add(NombreMaquinas.ROBOT_1.getDescripcion());
							send.getParameters().add(NombreMaquinas.CINTA_1.getDescripcion());
							_buzon.send(send);
						}
					}else if(_contexto.getEstadoInterno().equals(RobotStates.SOBREPOSICION_1)){
						//cogo el pastel
						if( (System.currentTimeMillis()-_contexto.getTiempo()) > (_configuracion.getMoverPastel()*1000)){
							_contexto.setEstadoInterno(RobotStates.CAMINOPOSICION_3);
							_contexto.setPastel(true);
							/*
							 * Envia el mensaje de pastel recogido
							 */
							MessageInterface send=new DefaultMessage();
							send.setIdentifier(MSGOntology.PRODUCTORECOGIDO);
							send.getParameters().add(NombreMaquinas.ROBOT_1.getDescripcion());
							send.getParameters().add("pastel");
							_buzon.send(send);
							_contexto.setPastelListo(false);
						}
					}else if(_contexto.getEstadoInterno().equals(RobotStates.CAMINOPOSICION_2)){
						// controlar interferencias, mejor lo hace el maestro
						if(  _contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() -_configuracion.getInterferencia()/2)*1000)){
							_contexto.setEstadoInterno(RobotStates.SOBREPOSICION_2);
							/*
							 * envia el mensaje de interferencia sobre la cinta 2
							 */
							MessageInterface send=new DefaultMessage();
							send.setIdentifier(MSGOntology.INTERFERENCIA);
							send.getParameters().add(NombreMaquinas.ROBOT_1.getDescripcion());
							send.getParameters().add(NombreMaquinas.CINTA_2.getDescripcion());
							_buzon.send(send);
						}
					}else if(_contexto.getEstadoInterno().equals(RobotStates.SOBREPOSICION_2)){
						//cogo el blister
						/*
						 * envia el mensaje de blister recogido
						 */
						//cogo el pastel
						if( (System.currentTimeMillis()-_contexto.getTiempo()) > (_configuracion.getMoverBlister()*1000)){
							_contexto.setEstadoInterno(RobotStates.CAMINOPOSICION_3);
							_contexto.setPastel(false);
							/*
							 * Envia el mensaje de blister recogido
							 */
							MessageInterface send=new DefaultMessage();
							send.setIdentifier(MSGOntology.PRODUCTORECOGIDO);
							send.getParameters().add(NombreMaquinas.ROBOT_1.getDescripcion());
							send.getParameters().add("blister");
							_buzon.send(send);
							_contexto.setPastelListo(false);
						}
					}else if(_contexto.getEstadoInterno().equals(RobotStates.CAMINOPOSICION_3)){
						// controlar interferencias, mejor lo hace el maestro
						if(_contexto.isPastel()){
							if( _contexto.getDiffTiempo() > ((_configuracion.getMoverPastel() +_configuracion.getInterferencia()/2)*1000)){
								/*
								 * envia el mensaje de FIN interferencia sobre la cinta 1
								 */
								MessageInterface send=new DefaultMessage();
								send.setIdentifier(MSGOntology.FININTERFERENCIA);
								send.getParameters().add(NombreMaquinas.ROBOT_1.getDescripcion());
								send.getParameters().add(NombreMaquinas.CINTA_1.getDescripcion());
								_buzon.send(send);
							}
							if( _contexto.getDiffTiempo() > (_configuracion.getMoverPastel()*2*1000)){
								_contexto.setEstadoInterno(RobotStates.SOBREPOSICION_3);
							}
						}else{
							if( _contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() +_configuracion.getInterferencia()/2)*1000)){
								/*
								 * envia el mensaje de FIN interferencia sobre la cinta 2
								 */
								MessageInterface send=new DefaultMessage();
								send.setIdentifier(MSGOntology.FININTERFERENCIA);
								send.getParameters().add(NombreMaquinas.ROBOT_1.getDescripcion());
								send.getParameters().add(NombreMaquinas.CINTA_2.getDescripcion());
								_buzon.send(send);
							}
							if( _contexto.getDiffTiempo() > (_configuracion.getMoverBlister()*2)){
								_contexto.setEstadoInterno(RobotStates.SOBREPOSICION_3);
							}
						}
					}else if(_contexto.getEstadoInterno().equals(RobotStates.SOBREPOSICION_3)){
						//dejo el pastel o blister
						if(_contexto.isPastel()){
							if( _contexto.getDiffTiempo() > (_configuracion.getMoverPastel()*2)){
								/*
								 * envia el mensaje de pastel colocado
								 */
								MessageInterface send=new DefaultMessage();
								send.setIdentifier(MSGOntology.PRODUCTOCOLOCADO);
								send.getParameters().add(NombreMaquinas.ROBOT_1.getDescripcion());
								send.getParameters().add("pastel");
								_buzon.send(send);
								_contexto.setEstadoInterno(RobotStates.REPOSO);
							}
						}else{
							if( _contexto.getDiffTiempo() > (_configuracion.getMoverBlister()*2)){
								/*
								 * envia el mensaje de pastel colocado
								 */
								MessageInterface send=new DefaultMessage();
								send.setIdentifier(MSGOntology.PRODUCTOCOLOCADO);
								send.getParameters().add(NombreMaquinas.ROBOT_1.getDescripcion());
								send.getParameters().add("blister");
								_buzon.send(send);
								_contexto.setEstadoInterno(RobotStates.REPOSO);
							}
						}
					}else if(_contexto.getEstadoInterno().equals(RobotStates.DESPLAZARBLISTERCOMPLETO)){
						if( _contexto.getDiffTiempo() > _configuracion.getMoverBlister()){
							/*
							 * envia el mensaje de blister completo colocado en la cinta 3
							 */
							MessageInterface send=new DefaultMessage();
							send.setIdentifier(MSGOntology.PRODUCTOCOLOCADO);
							send.getParameters().add(NombreMaquinas.ROBOT_1.getDescripcion());
							send.getParameters().add("blisterCompleto");
							_buzon.send(send);
							_contexto.setEstadoInterno(RobotStates.REPOSO);
						}
					}

				}//si esta apagadp
			}// si hay fallo

			// envia el mensaje de contexto
			DefaultMessage mensajeSend=new DefaultMessage();
			mensajeSend.setIdentifier(MSGOntology.ACTUALIZARCONTEXTOROBOT);
			mensajeSend.setObject(_contexto);
			_buzon.send(mensajeSend);
		}//while
	}// del execute()

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
	
	public synchronized void guardedJoy2() {
	}
	
	@Override
	public void notifyNoSyncJoy2(String machine) {
	}

	public synchronized void notifyJoy2() {
	}

	public synchronized void pauseJoy2() {
	}
}//class