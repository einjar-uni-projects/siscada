package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.ContextoMaestro;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.EstateRobots;
import com.umbrella.utils.NombreMaquinas;

/**
 * 
 * @author pablo Clase que leera los mensajes enviados por al automata 1 y
 *         actuara en consecuencia
 */
public class ReceiveAutomaton1 extends Thread {

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
		MessageInterface msg = null;
		do {
			msg = _postmaster.reciveMessageAU1();
			if (msg != null) {
				switch (msg.getIdentificador()) {
				case AVISARUNFALLO:
					String emptyMachine = msg.getParametros().get(0);
					_masterContext.setEmptyMachine(emptyMachine);
					break;
				case ACTUALIZARCONTEXTO:
					_masterContext.set_contextoAut1((Context) msg.getObject());
					break;
				}
			}

			/*
			 * si el contexto del aut 1 me dice q quedan pocos pasteles envio el
			 * mensaje a SCADA de pocos pasteles
			 */

		} while (msg != null);

		if (_masterContext.get_contextoAut1().getDispositivosInternos(
				_configutarion.getPosicionAsociada(NombreMaquinas.FIN_1))
				&& _masterContext.get_contextoRobot1().getEstadoInterno()
						.equals(EstateRobots.REPOSO)
				&& _masterContext.getContador() < 4) {
			/*
			 * el estado interno del aut1 me dice q tiene el fin de la cinta
			 * ocupado envio el mensaje al robot 1 si esta en modo reposo y
			 * tengo un blister con posicion libre, es decir el contador es
			 * menor que 4
			 */
			MessageInterface mensajeSend = new DefaultMessage();
			mensajeSend.setIdentificador(OntologiaMSG.PASTELLISTO);
			_postmaster.sendMessageRB1(mensajeSend);
		}
		if (_masterContext.get_contextoAut1().getPastelesRestantes() < _configutarion
				.getUmbralPasteles()) {
			/*
			 * si el contexto del aut 1 me dice q quedan pocos pasteles envio el
			 * mensaje a SCADA de pocos pasteles
			 */
			MessageInterface mensajeSend = new DefaultMessage();
			mensajeSend.getParametros().add(
					NombreMaquinas.DISPENSADORA.getName());
			mensajeSend.getParametros().add(
					_masterContext.get_contextoAut1().getPastelesRestantes()
							+ "");
			mensajeSend.setIdentificador(OntologiaMSG.AVISARUNFALLO);
			_postmaster.sendMessageSCADA(mensajeSend);
		}
		if (_masterContext.get_contextoAut1().getCapacidadCaramelo() < _configutarion
				.getUmbralCaramelos()) {
			/*
			 * si el contexto del aut 1 me dice q queda poco caramelo envio el
			 * mensaje a SCADA de poco caramelo
			 */
			MessageInterface mensajeSend = new DefaultMessage();
			mensajeSend.getParametros().add(NombreMaquinas.CARAMELO.getName());
			mensajeSend.getParametros().add(
					_masterContext.get_contextoAut1().getCapacidadCaramelo()
							+ "");
			mensajeSend.setIdentificador(OntologiaMSG.AVISARUNFALLO);
			_postmaster.sendMessageSCADA(mensajeSend);
		}
		if (_masterContext.get_contextoAut1().getCapacidadChocolate() < _configutarion
				.getUmbralChocolate()) {
			/*
			 * si el contexto del aut 1 me dice q queda poco chocolate envio el
			 * mensaje a SCADA de poco chocolate
			 */
			MessageInterface mensajeSend = new DefaultMessage();
			mensajeSend.getParametros().add(NombreMaquinas.CHOCOLATE.getName());
			mensajeSend.getParametros().add(
					_masterContext.get_contextoAut1().getCapacidadChocolate()
							+ "");
			mensajeSend.setIdentificador(OntologiaMSG.AVISARUNFALLO);
			_postmaster.sendMessageSCADA(mensajeSend);
		}
	}

}
