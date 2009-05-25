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
import com.umbrella.utils.MachineNames;


/*
 * este robot tiene el estado reposo, el estado voy por blister y voy por pastel
 */
public class Robot2  implements Notifiable{

	private Configuration _configuracion= Configuration.getInstance();
	private ContextoRobot _contexto= ContextoRobot.getInstance();

	private Clock _clock;
	private boolean _joy = true;
	private ClientMailBox _buzon;
	private PropertiesFile pfmodel;

	/**
	 * @param args
	 */
	public Robot2(){
		_clock=Clock.getInstance();
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
		_buzon = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._reciveR2Name, ServerMailBox._sendR2Name);


		_contexto.setEstadoInterno(RobotStates.REPOSO);
	}
	public void execute() {
		// TODO Auto-generated method stub

		while(!_contexto.isFIN()){
			pauseJoy();
			guardedJoy();

			MessageInterface mensaje=new DefaultMessage();
			do{
				try {
					mensaje=_buzon.receive();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(mensaje!=null){
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
					case BLISTERNOVALIDO:
						_contexto.setValido(false);//blister que no pasa el control de calidad
						break;
					case RESET:
						_contexto=_contexto.reset();
						break;
					case PARADAFALLO:
						_contexto.setFallo(true);
						break;
					case BLISTERVALIDO:
						_contexto.setValido(true);//blister que pasa el control de calidad
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
					//posicion 1 es la cinta 3
					//posicion 2 es la caja de validos
					//posicion 3 es la caja de no validos
					_contexto.setDiffTiempo(System.currentTimeMillis()-_contexto.getTiempo());
					if(_contexto.getEstadoInterno().equals(RobotStates.REPOSO)){
						_contexto.setEstadoInterno(RobotStates.CAMINOPOSICION_1);
						_contexto.setTiempo(System.currentTimeMillis());
						_contexto.setDiffTiempo(System.currentTimeMillis()-_contexto.getTiempo());
					}
					//va hacia la cinta3
					else if(_contexto.getEstadoInterno().equals(RobotStates.CAMINOPOSICION_1)){//OK
						// controlar interferencias, mejor lo hace el maestro
						if( _contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() -_configuracion.getInterferencia()/2)*1000)){
							_contexto.setEstadoInterno(RobotStates.SOBREPOSICION_1);
							/*
							 * envia el mensaje de interferencia sobre la cinta 3
							 */
							MessageInterface send=new DefaultMessage();
							send.setIdentifier(MSGOntology.INTERFERENCIA);
							send.getParameters().add(MachineNames.ROBOT_2.getDescripcion());
							send.getParameters().add(MachineNames.CINTA_3.getDescripcion());
							_buzon.send(send);
						}
					}
					else if(_contexto.getEstadoInterno().equals(RobotStates.SOBREPOSICION_1)){//OK
						//cojo el Blister
						if( (System.currentTimeMillis()-_contexto.getTiempo()) > (_configuracion.getMoverBlister()*1000)){
							if(_contexto.isValido()){
								_contexto.setEstadoInterno(RobotStates.CAMINOPOSICION_2);
							}else{
								_contexto.setEstadoInterno(RobotStates.CAMINOPOSICION_3);
							}
							/*
							 * enviar mensaje de FIN de cinta 3 libre
							 */
							MessageInterface send=new DefaultMessage();
							send.setIdentifier(MSGOntology.PRODUCTORECOGIDO);
							send.getParameters().add(MachineNames.ROBOT_1.getDescripcion());
							send.getParameters().add("blisterCompleto");
							_buzon.send(send);
							_contexto.setPastelListo(false);
							_contexto.setPastel(false);
						}
					}else if(_contexto.getEstadoInterno().equals(RobotStates.CAMINOPOSICION_2)){
						if(!_contexto.isPastel() && (_contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() +_configuracion.getInterferencia()/2)*1000))){
							_contexto.setPastel(true);
						}
					}else if(_contexto.getEstadoInterno().equals(RobotStates.SOBREPOSICION_2)){
						if( (System.currentTimeMillis()-_contexto.getTiempo()) > (_configuracion.getMoverBlister()*2*1000)){
							/*
							 * Enviar mensaje de pastel valido depositado
							 */
							MessageInterface send=new DefaultMessage();
							send.setIdentifier(MSGOntology.BLISTERALMACENADO);
							send.getParameters().add("true");
							_buzon.send(send);
							_contexto.setEstadoInterno(RobotStates.REPOSO);
						}

					}else if(_contexto.getEstadoInterno().equals(RobotStates.CAMINOPOSICION_3)){
						if(!_contexto.isPastel() && (_contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() +_configuracion.getInterferencia()/2)*1000))){
							_contexto.setPastel(true);
						}

					}else if(_contexto.getEstadoInterno().equals(RobotStates.SOBREPOSICION_3)){
						if( (System.currentTimeMillis()-_contexto.getTiempo()) > (_configuracion.getMoverBlister()*2*1000)){
							/*
							 * Enviar mensaje de pastel NO valido depositado
							 */
							MessageInterface send=new DefaultMessage();
							send.setIdentifier(MSGOntology.BLISTERALMACENADO);
							send.getParameters().add("false");
							_buzon.send(send);
							_contexto.setEstadoInterno(RobotStates.REPOSO);
						}
					}
				}
			}
			
			// envia el mensaje de contexto
			DefaultMessage mensajeSend=new DefaultMessage();
			mensajeSend.setIdentifier(MSGOntology.ACTUALIZARCONTEXTOROBOT);
			mensajeSend.setObject(_contexto);
			_buzon.send(mensajeSend);
		}
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
