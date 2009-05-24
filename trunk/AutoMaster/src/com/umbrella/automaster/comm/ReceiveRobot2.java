package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.ContextoMaestro;
import com.umbrella.autocommon.ContextoRobot;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.EstateRobots;

/**
 * 
 * @author pablo Clase que leera los mensajes enviados por al automata 1 y
 *         actuara en consecuencia
 */
public class ReceiveRobot2 extends Thread {

	Postmaster _postmaster;
	ContextoMaestro _masterContext;
	Configuration _configutarion;

	public synchronized void inicializar() {
		try {
			_postmaster = Postmaster.getInstance();
			_masterContext = ContextoMaestro.getInstance();
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
				System.out.println("RB2 Recive: " + msg.getIdentificador());
				switch (msg.getIdentificador()) {
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
					dm.setIdentificador(OntologiaMSG.AUTOM_STATE);
					dm.setObject(!con_update_context.isApagado());
					dm.getParametros().add("RB2");
					_postmaster.sendMessageSCADA(dm);
					
					//Envia el estado
					dm = new DefaultMessage();
					dm.setIdentificador(OntologiaMSG.ROBOT_SET_CONTENT);
					if(con_update_context.getEstadoInterno() == EstateRobots.CAMINOPOSICION_2){
							dm.setObject(1);
					}else if(con_update_context.getEstadoInterno() == EstateRobots.CAMINOPOSICION_3){
						dm.setObject(2);
					}else
						dm.setObject(0);
					
					dm.getParametros().add("RB1");
					_postmaster.sendMessageSCADA(dm);
					
					break;
				case PRODUCTORECOGIDO:
					// se envia un mensaje a la cinta 3 de PRODUCTORECOGIDO, el
					// mismo mensaje que le llega
					_postmaster.sendMessageAU3(msg);
					break;
				case BLISTERALMACENADO:
					// envia el mensaje a SCADA informando
					if (msg.getParametros().get(0).equalsIgnoreCase("true")) {
						// el blister que se almaceno era valido
					} else {
						// el blister que se almaceno era no valido
					}
					break;
				}
			}
			
			dm = new DefaultMessage();
			
		} while (msg != null);
	}
	
}
