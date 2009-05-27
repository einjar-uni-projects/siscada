package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.ContextoRobot;
import com.umbrella.autocommon.MasterContext;
import com.umbrella.automaster.LaunchAutMaster;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.RobotStates;

/**
 * 
 * @author pablo Clase que leera los mensajes enviados por al automata 1 y
 *         actuara en consecuencia
 */
public class ReceiveRobot2 extends Thread {

	Postmaster _postmaster;
	MasterContext _masterContext;
	Configuration _configutarion;

	public synchronized void inicializar() {
		try {
			_postmaster = Postmaster.getInstance();
			_masterContext = MasterContext.getInstance();
			_configutarion = Configuration.getInstance();
		} catch (RemoteException e1) {
			// TODO: handle exception
		} catch (MalformedURLException e2) {
			// TODO: handle exception
		} catch (PropertyException e4) {
			// TODO: handle exception
		}
	}

	@Override
	public void run() {
		DefaultMessage dm = new DefaultMessage();
		MessageInterface msg = null;
		do {
			msg = _postmaster.reciveMessageRB2();
			if (msg != null) {
				if(LaunchAutMaster.debugRobo2)
					System.out.println("RB2 Recive: " + msg.getIdentifier());
				switch (msg.getIdentifier()) {
				case INTERFERENCIA:
					// envio a la cinta 3
					_postmaster.sendMessageAU3(msg);
					break;
				case FININTERFERENCIA:
					// envio a la cinta 3
					_postmaster.sendMessageAU3(msg);
					break;
				case ACTUALIZARCONTEXTOROBOT:
					ContextoRobot con_update_context = (ContextoRobot) msg.getObject();
					_masterContext.set_contextoRobot2(con_update_context);
					
					//Se notifica del estado al SCADA
					dm.setIdentifier(MSGOntology.AUTOM_STATE);
					dm.setObject(!con_update_context.isApagado());
					dm.getParameters().add("RB2");
					_postmaster.sendMessageSCADA(dm);
					
					//Envia el estado
					dm = new DefaultMessage();
					dm.setIdentifier(MSGOntology.ROBOT_SET_CONTENT);
					if(con_update_context.getEstadoInterno() == RobotStates.CAMINOPOSICION_2){
							dm.setObject(1);
					}else if(con_update_context.getEstadoInterno() == RobotStates.CAMINOPOSICION_3){
						dm.setObject(2);
					}else
						dm.setObject(0);
					
					dm.getParameters().add("RB2");
					_postmaster.sendMessageSCADA(dm);
					
					break;
				case PRODUCTORECOGIDO:
					// se env’a un mensaje al AU3 para que elimine el pastel
					dm = new DefaultMessage();
					dm.setIdentifier(MSGOntology.PRODUCTORECOGIDO);
					dm.getParameters().add("RB2");
					_postmaster.sendMessageAU3(dm);
					break;
				case BLISTERALMACENADO:
					boolean good = (Boolean)msg.getObject();
					_masterContext.incrementarProducidos(good);

					// envia el mensaje a SCADA informando
					dm = new DefaultMessage();
					if(good){
						dm.setIdentifier(MSGOntology.NUM_GOOD_PACKAGES);
						dm.setObject(_masterContext.getBienProducidos());
					}else{
						dm.setIdentifier(MSGOntology.NUM_BAD_PACKAGES);
						dm.setObject(_masterContext.getMalProducidos());
					}
					
					dm.getParameters().add("RB2");
					_postmaster.sendMessageSCADA(dm);
					break;
				}
			}
			
			dm = new DefaultMessage();
			
		} while (msg != null);
	}
	
}
