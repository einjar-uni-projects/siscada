package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.LinkedList;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.MasterContext;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.Blister;
import com.umbrella.utils.MachineNames;

/**
 * 
 * @author pablo Clase que leera los mensajes enviados por al automata 2 y
 *         actuara en consecuencia
 */
public class ReceiveAutomaton3 extends Thread {

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
			msg = _postmaster.reciveMessageAU3();
			
			if (msg != null) {
				System.out.println("AU3 Recive: " + msg.getIdentifier());
				switch (msg.getIdentifier()) {
				case ACTUALIZARCONTEXTO:
					Context con_update_context = (Context) msg.getObject();
					_masterContext.set_contextoAut3(con_update_context);
					
					//Se notifica del estado al SCADA
					dm.setIdentifier(MSGOntology.AUTOM_STATE);
					dm.setObject(!con_update_context.isApagado());
					dm.getParameters().add("AU3");
					_postmaster.sendMessageSCADA(dm);
					break;
				}
			}
			dm = new DefaultMessage();
			
		} while (msg != null);

		Context context = _masterContext.get_contextoAut3();

		if (context != null) {

			/*
			 * si el estado interno nos dice que hay un blister completo listo
			 * al inicio de la cinta
			 */
			if (context.getDispositivosInternos(_configutarion
					.getPosicionAsociada(MachineNames.INICIO))
					&& _masterContext.getContador() == 4) {

				// comprobar q cabe en la cinta o ya esta hecho???

				MessageInterface mensajeSend = new DefaultMessage();
				mensajeSend.setIdentifier(MSGOntology.BLISTERCOMPLETO);
				_postmaster.sendMessageRB1(mensajeSend);
			}

			/*
			 * si el estado interno nos dice que hay un blister comleto y
			 * sellado al final de la cinta
			 */
			if (context.getDispositivosInternos(
					_configutarion.getPosicionAsociada(MachineNames.FIN_3))) {
				MessageInterface mensajeSend = new DefaultMessage();
				if (valido()) {
					mensajeSend.setIdentifier(MSGOntology.BLISTERVALIDO);
				} else {
					mensajeSend.setIdentifier(MSGOntology.BLISTERNOVALIDO);
				}
				_postmaster.sendMessageRB1(mensajeSend);
			}
		}else{
			System.out.println("Context is null!!! AU3");
		}
	}

	private boolean valido() {
		boolean sal = false;
		LinkedList<Blister> list = _masterContext.get_contextoAut3().get_listaBlister();
		double max = 0;
		int pos = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get_posicion() > max) {
				max = list.get(i).get_posicion();
				pos = i;
			}
		}
		sal = list.get(pos).getCalidad()[0];
		return sal;
	}
}
