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
import com.umbrella.utils.MachineNames;
import com.umbrella.utils.RobotStates;

/**
 * 
 * @author pablo Clase que leera los mensajes enviados por al automata 1 y
 *         actuara en consecuencia
 */
public class ReceiveRobot1 extends Thread {

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
			msg = _postmaster.reciveMessageRB1();
			
			if (msg != null) {
				if(LaunchAutMaster.debugRobo1)
					System.out.println("RB1 Recive: " + msg.getIdentifier());
				switch (msg.getIdentifier()) {
				case INTERFERENCIA:
					String cinta = msg.getParameters().get(1);
					if (cinta.equals(MachineNames.CINTA_1.getName())) {
						// envio a la cinta 1
						_postmaster.sendMessageAU1(msg);
					} else if (cinta.equals(MachineNames.CINTA_2.getName())) {
						// envio a la cinta 2
						_postmaster.sendMessageAU2(msg);
					}
					break;
				case PRODUCTORECOGIDO:
					// primer paraemtro el robot y 2 el producto
					String producto = msg.getParameters().get(1);
					if (producto.equals("pastel")) {
						
						// se env’a un mensaje al AU1 para que elimine el pastel
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.PRODUCTORECOGIDO);
						dm.getParameters().add("RB1");
						_postmaster.sendMessageAU1(dm);
						/*_masterContext
								.get_contextoAut1()
								.setDispositivosInternos(
										_configutarion
												.getPosicionAsociada(MachineNames.FIN_1),
										false);*/
					} else if (producto.equals("blister")) {
						// se env’a un mensaje al AU2 para que elimine el blister
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.PRODUCTORECOGIDO);
						dm.getParameters().add("RB1");
						_postmaster.sendMessageAU2(dm);
						/*_masterContext.get_contextoAut2()
								.setDispositivosInternos(
										_configutarion
												.getPosicionAsociada(MachineNames.FIN_2),
										false);*/
					}else { // blisterCompleto
						// TODO Se asume que son 4
						_masterContext.setBlisterColocado(false);
						_masterContext.resetContador();
						
						// Se notifica la mesa vacia
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.TABLE_CONTENT);
						dm.setObject(0);
						dm.getParameters().add("RB1");
						_postmaster.sendMessageSCADA(dm);
					}
					
					break;
				case FININTERFERENCIA:
					String cinta2 = msg.getParameters().get(1);
					if (cinta2.equals(MachineNames.CINTA_1.getName())) {
						// envio a la cinta 1
						_postmaster.sendMessageAU1(msg);
					} else if (cinta2.equals(MachineNames.CINTA_2.getName())) {
						// envio a la cinta 2
						_postmaster.sendMessageAU2(msg);
					}
					break;
				case PRODUCTOCOLOCADO:
					/*
					 * SUPOSICION QUE NO LA PIFIAMOS A LA HORA DE PEDIR UN
					 * MOVIMIENTO Y NO PONEMOS2 BLISTER SEGUIDOS casos: pongo un
					 * blister en la mesa pongo un pastel entre 0 y 4 pongo el
					 * 4ï¿½ pastel
					 */
					String colocado = msg.getParameters().get(1);
					if (colocado.equals("blister")) {
						_masterContext
								.get_contextoAut3()
								.setDispositivosInternos(
										_configutarion
												.getPosicionAsociada(MachineNames.INICIO),
										true);
						_masterContext.setBlisterColocado(true);
						_masterContext.resetContador();
						
						// Se notifican el blister de la mesa
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.TABLE_CONTENT);
						dm.setObject(1);
						dm.getParameters().add("RB1");
						_postmaster.sendMessageSCADA(dm);
						
					} else if (colocado.equals("pastel")) {
						// pongo un pastel
						_masterContext.incrementarContador();
						
						// Se notifican el blister y los pasteles de la mesa
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.TABLE_CONTENT);
						dm.setObject(_masterContext.getContador()+1);
						dm.getParameters().add("RB1");
						_postmaster.sendMessageSCADA(dm);
						
						// Se pide al robot que lo desplace hasta el aut—mata 3 si ya est‡n todos
						// y sy hay espacio en este
						if(_masterContext.getContador() >= 4 && !_masterContext.get_contextoAut3().isBlisterListoInicioCinta3()){
							MessageInterface mensajeSend = new DefaultMessage();
							mensajeSend.setIdentifier(MSGOntology.BLISTERCOMPLETO);
							_postmaster.sendMessageRB1(mensajeSend);
						}
						
					} else { // blisterCompleto
						// TODO Se asume que son 4
						// TODO enviar mensaje al autï¿½mata 3, no vale con cambiar su contexto
						/*_masterContext
								.get_contextoAut3()
								.setDispositivosInternos(
										_configutarion
												.getPosicionAsociada(MachineNames.INICIO),
										false);*/
						/*_masterContext.setBlisterColocado(false);
						_masterContext.resetContador();*/
						
						// Se notifica la mesa vacia
						/*dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.TABLE_CONTENT);
						dm.setObject(0);
						dm.getParameters().add("RB1");
						_postmaster.sendMessageSCADA(dm);*/
						
						// Se env’a un blister al aut—mata 3
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.BLISTERCOMPLETO);
						//dm.setObject(0);
						dm.getParameters().add("RB1");
						_postmaster.sendMessageAU3(dm);
					}

					break;
				case ACTUALIZARCONTEXTOROBOT:
					ContextoRobot con_update_context = (ContextoRobot) msg.getObject();
					_masterContext.set_contextoRobot1(con_update_context);
					
					//Se notifica del estado al SCADA
					dm.setIdentifier(MSGOntology.AUTOM_STATE);
					dm.setObject(!con_update_context.isApagado());
					dm.getParameters().add("RB1");
					_postmaster.sendMessageSCADA(dm);
					
					//Envia el estado
					dm = new DefaultMessage();
					dm.setIdentifier(MSGOntology.ROBOT_SET_CONTENT);
					if(con_update_context.getEstadoInterno() == RobotStates.CAMINOPOSICION_3){
						if(con_update_context.isPastel())
							dm.setObject(1);
						else
							dm.setObject(2);
					}else if(con_update_context.getEstadoInterno() == RobotStates.DESPLAZARBLISTERCOMPLETO){
						dm.setObject(3);
					}else
						dm.setObject(0);
					
					dm.getParameters().add("RB1");
					_postmaster.sendMessageSCADA(dm);
					
					break;
				}
			}
			
			dm = new DefaultMessage();

		} while (msg != null);
	}
}
