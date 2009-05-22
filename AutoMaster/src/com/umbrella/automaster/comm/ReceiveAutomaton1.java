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
		DefaultMessage dm = new DefaultMessage();
		MessageInterface msg = null;
		do {
			msg = _postmaster.reciveMessageAU1();
			
			if (msg != null) {
				System.out.println("AU1 Recive: " + msg.getIdentificador());
				switch (msg.getIdentificador()) {
				case AVISARUNFALLO:
					String emptyMachine = msg.getParametros().get(0);
					_masterContext.setEmptyMachine(emptyMachine);
					break;
				case ACTUALIZARCONTEXTO:
					Context con_update_context = (Context) msg.getObject();
					_masterContext.set_contextoAut1(con_update_context);

					//Se notifica del estado al SCADA
					dm.setIdentificador(OntologiaMSG.AUTOM_STATE);
					dm.setObject(!con_update_context.isApagado());
					dm.getParametros().add("AU1");
					_postmaster.sendMessageSCADA(dm);
					break;
				}
			}

			dm = new DefaultMessage();
			
			/*
			 * si el contexto del aut 1 me dice q quedan pocos pasteles envio el
			 * mensaje a SCADA de pocos pasteles
			 */

		} while (msg != null);
		Context context = _masterContext.get_contextoAut1();
		if(context != null){
			if (context.getDispositivosInternos(
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
			if (context!= null && context.getPastelesRestantes() < _configutarion
					.getUmbralPasteles()) {
				/*
				 * si el contexto del aut 1 me dice q quedan pocos pasteles envio el
				 * mensaje a SCADA de pocos pasteles
				 */
				MessageInterface mensajeSend = new DefaultMessage();
				mensajeSend.getParametros().add(
						NombreMaquinas.DISPENSADORA.getName());
				mensajeSend.getParametros().add(
						context.getPastelesRestantes()
								+ "");
				mensajeSend.setIdentificador(OntologiaMSG.AVISARUNFALLO);
				_postmaster.sendMessageSCADA(mensajeSend);
			}
			if (context.getCapacidadCaramelo() < _configutarion
					.getUmbralCaramelos()) {
				/*
				 * si el contexto del aut 1 me dice q queda poco caramelo envio el
				 * mensaje a SCADA de poco caramelo
				 */
				MessageInterface mensajeSend = new DefaultMessage();
				mensajeSend.getParametros().add(NombreMaquinas.CARAMELO.getName());
				mensajeSend.getParametros().add(
						context.getCapacidadCaramelo()
								+ "");
				mensajeSend.setIdentificador(OntologiaMSG.AVISARUNFALLO);
				_postmaster.sendMessageSCADA(mensajeSend);
			}
			if (context.getCapacidadChocolate() < _configutarion
					.getUmbralChocolate()) {
				/*
				 * si el contexto del aut 1 me dice q queda poco chocolate envio el
				 * mensaje a SCADA de poco chocolate
				 */
				MessageInterface mensajeSend = new DefaultMessage();
				mensajeSend.getParametros().add(NombreMaquinas.CHOCOLATE.getName());
				mensajeSend.getParametros().add(context.getCapacidadChocolate()
								+ "");
				mensajeSend.setIdentificador(OntologiaMSG.AVISARUNFALLO);
				_postmaster.sendMessageSCADA(mensajeSend);
			}
		}else{
			System.out.println("Context is null!!! AU1");
		}
	}
}
