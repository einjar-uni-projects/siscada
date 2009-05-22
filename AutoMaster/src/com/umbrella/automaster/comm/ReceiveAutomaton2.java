package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.ContextoMaestro;
import com.umbrella.autocommon.ContextoRobot;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.EstateRobots;
import com.umbrella.utils.NombreMaquinas;

/**
 * 
 * @author pablo Clase que leera los mensajes enviados por al automata 2 y
 *         actuara en consecuencia
 */
public class ReceiveAutomaton2 extends Thread {

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
			msg = _postmaster.reciveMessageAU2();
			
			if (msg != null) {
				System.out.println("AU2 Recive: " + msg.getIdentificador());
				switch (msg.getIdentificador()) {
				case ACTUALIZARCONTEXTO:
					Context con_update_context = (Context) msg.getObject();
					_masterContext.set_contextoAut2(con_update_context);
					
					//Se notifica del estado al SCADA
					dm.setIdentificador(OntologiaMSG.AUTOM_STATE);
					dm.setObject(!con_update_context.isApagado());
					dm.getParametros().add("AU2");
					_postmaster.sendMessageSCADA(dm);
					break;
				}
			}
			dm = new DefaultMessage();
			
		} while (msg != null);
		/*
		 * el estado interno del aut2 me dice q tiene el fin de la cinta ocupado
		 * y el inicio de la cinta 3 esta libre envio el mensaje al robot 1 si
		 * esta en modo reposo de blisterlisto
		 */
		Context context = _masterContext.get_contextoAut2();
		ContextoRobot contextr1 = _masterContext.get_contextoRobot1();
		Context contexta3 = _masterContext.get_contextoAut3();
		if (context != null && contextr1 != null && contexta3 != null) {
			if (context.getDispositivosInternos(_configutarion.getPosicionAsociada(NombreMaquinas.FIN_3))
					&& contextr1.getEstadoInterno().equals(EstateRobots.REPOSO)
					&& !contexta3.getDispositivosInternos(_configutarion.getPosicionAsociada(NombreMaquinas.INICIO))) {
				MessageInterface mensajeSend = new DefaultMessage();
				mensajeSend.setIdentificador(OntologiaMSG.BLISTERLISTO);
				_postmaster.sendMessageRB1(mensajeSend);
			}
		} else {
			System.out.println("Context is null!!! AU2");

		}
	}
}
