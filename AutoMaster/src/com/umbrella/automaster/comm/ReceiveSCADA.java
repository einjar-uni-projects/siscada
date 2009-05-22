package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.LinkedList;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.ContextoMaestro;
import com.umbrella.autocommon.ContextoRobot;
import com.umbrella.automaster.logic.Maestro;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.Blister;

/**
 * 
 * @author pablo Clase que leera los mensajes enviados por al automata 2 y
 *         actuara en consecuencia
 */
public class ReceiveSCADA extends Thread {

	Postmaster _postmaster;
	ContextoMaestro _masterContext;
	Configuration _configutarion;

	public synchronized void inicializar() {
		try {
			_postmaster = Postmaster.getInstance();
			_masterContext = ContextoMaestro.getInstance();
			_configutarion = Configuration.getInstance();
		} catch (PropertyException e4) {
			// TODO: handle exception
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
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
				System.out.println("SCADA Recive: " + msg.getIdentificador());
				switch (msg.getIdentificador()) {
				case START:
					Configuration conf = Maestro.getInstance()
							.getConfiguration();
					dm.setIdentificador(OntologiaMSG.ACTUALIZARCONFIGURACION);
					dm.setObject(conf);

					_postmaster.sendMessageAU1(dm);
					_postmaster.sendMessageAU2(dm);
					_postmaster.sendMessageAU3(dm);
					_postmaster.sendMessageRB1(dm);
					_postmaster.sendMessageRB2(dm);
					_postmaster.sendMessageSCADA(dm);
					break;
				case ACTUALIZARCONTEXTO:
					Context con_update_context = (Context) msg.getObject();
					String machine_update_context = msg.getParametros().get(0);
					sendSCADAMachineState(dm, !con_update_context.isApagado(),machine_update_context);

					break;
				case ACTUALIZARCONTEXTOROBOT:
					ContextoRobot con_update_robot_context = (ContextoRobot) msg.getObject();
					String machine_update_robot_context = msg.getParametros().get(0);
					sendSCADAMachineState(dm, !con_update_robot_context.isApagado(), machine_update_robot_context);
					break;
				}

				dm = new DefaultMessage();
			}
		} while (msg != null);
	}

	public static void sendSCADAMachineState(DefaultMessage dm, boolean b,
			String machine) {
		dm.setIdentificador(OntologiaMSG.AUTOM_STATE);
		dm.setObject(b);
		dm.getParametros().add(machine);
		try {
			Postmaster.getInstance().sendMessageSCADA(dm);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dm = new DefaultMessage();
	}
}