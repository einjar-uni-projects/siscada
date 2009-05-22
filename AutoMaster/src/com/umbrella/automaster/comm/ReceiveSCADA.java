package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
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
import com.umbrella.utils.NombreMaquinas;

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
					sendSCADAMachineState(dm, !con_update_context.isApagado(),
							machine_update_context);

					break;
				case ACTUALIZARCONTEXTOROBOT:
					ContextoRobot con_update_robot_context = (ContextoRobot) msg
							.getObject();
					String machine_update_robot_context = msg.getParametros()
							.get(0);
					sendSCADAMachineState(dm, !con_update_robot_context
							.isApagado(), machine_update_robot_context);
					break;
				}

				dm = new DefaultMessage();
			}
		} while (msg != null);

		/*
		 * si el estado interno nos dice que hay un blister completo listo al
		 * inicio de la cinta
		 */
		if (_masterContext.get_contextoAut3().getDispositivosInternos(
				_configutarion.getPosicionAsociada(NombreMaquinas.INICIO))
				&& _masterContext.getContador() == 4) {

			// comprobar q cabe en la cinta o ya esta hecho???

			MessageInterface mensajeSend = new DefaultMessage();
			mensajeSend.setIdentificador(OntologiaMSG.BLISTERCOMPLETO);
			_postmaster.sendMessageRB1(mensajeSend);
		}

		/*
		 * si el estado interno nos dice que hay un blister comleto y sellado al
		 * final de la cinta
		 */
		if (_masterContext.get_contextoAut3().getDispositivosInternos(
				_configutarion.getPosicionAsociada(NombreMaquinas.FIN_3))) {
			MessageInterface mensajeSend = new DefaultMessage();
			if (valido()) {
				mensajeSend.setIdentificador(OntologiaMSG.BLISTERVALIDO);
			} else {
				mensajeSend.setIdentificador(OntologiaMSG.BLISTERNOVALIDO);
			}
			_postmaster.sendMessageRB1(mensajeSend);
		}
	}

	private void sendSCADAMachineState(DefaultMessage dm, boolean b,
			String machine) {
		dm.setIdentificador(OntologiaMSG.AUTOM_STATE);
		dm.setObject(b);
		dm.getParametros().add(machine);
		_postmaster.sendMessageSCADA(dm);
		dm = new DefaultMessage();
	}

	private boolean valido() {
		boolean sal = false;
		LinkedList<Blister> list = _masterContext.get_contextoAut3()
				.get_listaBlister();
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
