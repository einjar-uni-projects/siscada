package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.MasterContext;
import com.umbrella.automaster.LaunchAutMaster;
import com.umbrella.automaster.logic.Maestro;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.MachineNames;

/**
 * 
 * @author pablo Clase que leera los mensajes enviados por al automata 2 y
 *         actuara en consecuencia
 */
public class ReceiveSCADA extends Thread {

	Postmaster _postmaster;
	MasterContext _masterContext;
	Configuration _configutarion;

	public synchronized void inicializar() {
		try {
			_postmaster = Postmaster.getInstance();
			_masterContext = MasterContext.getInstance();
			_configutarion = Configuration.getInstance();
		} catch (PropertyException e4) {
			// TODO: handle exception
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		DefaultMessage dm = new DefaultMessage();
		MessageInterface msg = null;
		do {
			msg = _postmaster.reciveMessageSCADA();
			if (msg != null) {
				if(LaunchAutMaster.debugSCADA)
					System.out.println("SCADA Recive: " + msg.getIdentifier());
				switch (msg.getIdentifier()){
				case START:
					Configuration conf = Maestro.getInstance()
							.getConfiguration();
					dm.setIdentifier(MSGOntology.ACTUALIZARCONFIGURACION);
					dm.setObject(conf);
					_postmaster.isSistemStarted(true);
					_postmaster.sendMessageAU1(dm);
					_postmaster.sendMessageAU2(dm);
					_postmaster.sendMessageAU3(dm);
					_postmaster.sendMessageRB1(dm);
					_postmaster.sendMessageRB2(dm);
					_postmaster.sendMessageSCADA(dm);
					break;
				case PARADA:
					dm.setIdentifier(MSGOntology.PARADA);
					_postmaster.sendMessageAU1(dm);
					break;
				case PARADAEMERGENCIA:
					dm.setIdentifier(MSGOntology.PARADAEMERGENCIA);
					
					_postmaster.sendMessageAU1(dm);
					_postmaster.sendMessageAU2(dm);
					_postmaster.sendMessageAU3(dm);
					_postmaster.sendMessageRB1(dm);
					_postmaster.sendMessageRB2(dm);
					_postmaster.sendMessageSCADA(dm);
					break;
					
				case CONVEYOR_BELT_1_SIZE:
					Double size = (Double)msg.getObject();
					if(_masterContext.get_contextoAut1().isApagado()){
						// Cambiamos su configuraci�n
						Maestro.getInstance().cambiarTamanyoCinta(MachineNames.CINTA_1, size);
						
						// Notificamos al SCADA
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.ACTUALIZARCONFIGURACION);
						dm.setObject(Maestro.getInstance().getConfiguration());
						_postmaster.sendMessageSCADA(dm);
					}
					break;
				case CONVEYOR_BELT_2_SIZE:
					Double size2 = (Double)msg.getObject();
					if(_masterContext.get_contextoAut2().isApagado()){
						// Cambiamos su configuraci�n
						Maestro.getInstance().cambiarTamanyoCinta(MachineNames.CINTA_2, size2);
						
						// Notificamos al SCADA
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.ACTUALIZARCONFIGURACION);
						dm.setObject(Maestro.getInstance().getConfiguration());
						_postmaster.sendMessageSCADA(dm);
					}
					break;
				case CONVEYOR_BELT_3_SIZE:
					Double size3 = (Double)msg.getObject();
					if(_masterContext.get_contextoAut2().isApagado()){
						// Cambiamos su configuraci�n
						Maestro.getInstance().cambiarTamanyoCinta(MachineNames.CINTA_3, size3);
						
						// Notificamos al SCADA
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.ACTUALIZARCONFIGURACION);
						dm.setObject(Maestro.getInstance().getConfiguration());
						_postmaster.sendMessageSCADA(dm);
					}
					break;
				case CONVEYOR_BELT_1_SPEED:
					Double speed = (Double)msg.getObject();
					if(_masterContext.get_contextoAut1().isApagado()){
						// Cambiamos su configuraci�n
						Maestro.getInstance().cambiarVelCinta(MachineNames.CINTA_1, speed);
						
						// Notificamos al SCADA
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.ACTUALIZARCONFIGURACION);
						dm.setObject(Maestro.getInstance().getConfiguration());
						_postmaster.sendMessageSCADA(dm);
					}
					break;
				case CONVEYOR_BELT_2_SPEED:
					Double speed2 = (Double)msg.getObject();
					if(_masterContext.get_contextoAut2().isApagado()){
						// Cambiamos su configuraci�n
						Maestro.getInstance().cambiarVelCinta(MachineNames.CINTA_2, speed2);

						// Notificamos al SCADA
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.ACTUALIZARCONFIGURACION);
						dm.setObject(Maestro.getInstance().getConfiguration());
						_postmaster.sendMessageSCADA(dm);
					}
					break;
				case CONVEYOR_BELT_3_SPEED:
					Double speed3 = (Double)msg.getObject();
					if(_masterContext.get_contextoAut3().isApagado()){
						// Cambiamos su configuraci�n
						Maestro.getInstance().cambiarVelCinta(MachineNames.CINTA_3, speed3);
						
						// Notificamos al SCADA
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.ACTUALIZARCONFIGURACION);
						dm.setObject(Maestro.getInstance().getConfiguration());
						_postmaster.sendMessageSCADA(dm);
					}
					break;
				case RB1_BLISTER_DELAY:
					Integer rspeed1 = (Integer)msg.getObject();
					if(_masterContext.get_contextoRobot1().isApagado()){
						// Cambiamos su configuraci�n
						Maestro.getInstance().cambiarTiempoRobot(1, rspeed1);
						System.err.println("caca "+rspeed1);
						// Notificamos al SCADA
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.ACTUALIZARCONFIGURACION);
						dm.setObject(Maestro.getInstance().getConfiguration());
						_postmaster.sendMessageSCADA(dm);
					}
					break;
				case RB1_CAKE_DELAY:
					Integer rspeed2 = (Integer)msg.getObject();
					if(_masterContext.get_contextoRobot1().isApagado()){
						// Cambiamos su configuraci�n
						Maestro.getInstance().cambiarTiempoRobot(0, rspeed2);
						
						// Notificamos al SCADA
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.ACTUALIZARCONFIGURACION);
						dm.setObject(Maestro.getInstance().getConfiguration());
						_postmaster.sendMessageSCADA(dm);
					}
					break;
				case RB2_BLISTER_DELAY:
					Integer rspeed3 = (Integer)msg.getObject();
					if(_masterContext.get_contextoRobot2().isApagado()){
						// Cambiamos su configuraci�n
						Maestro.getInstance().cambiarTiempoRobot(3, rspeed3);
						
						// Notificamos al SCADA
						dm = new DefaultMessage();
						dm.setIdentifier(MSGOntology.ACTUALIZARCONFIGURACION);
						dm.setObject(Maestro.getInstance().getConfiguration());
						_postmaster.sendMessageSCADA(dm);
					}
					break;
				case RELLENARMAQUINA:
					_postmaster.sendMessageAU1(msg);
					break;
				}

				dm = new DefaultMessage();
			}
		} while (msg != null);
	}
}
