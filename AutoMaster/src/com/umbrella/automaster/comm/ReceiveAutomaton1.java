package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.MasterContext;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.Cake;
import com.umbrella.utils.MachineNames;
import com.umbrella.utils.RobotStates;

/**
 * 
 * @author pablo Clase que leera los mensajes enviados por al automata 1 y
 *         actuara en consecuencia
 */
public class ReceiveAutomaton1 extends Thread {

	Postmaster _postmaster;
	MasterContext _masterContext;
	Configuration _configuration;

	public synchronized void inicializar() {
		try {
			_postmaster = Postmaster.getInstance();
			_masterContext = MasterContext.getInstance();
			_configuration = Configuration.getInstance();
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
				System.out.println("AU1 Recive: " + msg.getIdentifier());
				switch (msg.getIdentifier()) {
				case AVISARUNFALLO:
					String emptyMachine = msg.getParameters().get(0);
					_masterContext.setEmptyMachine(emptyMachine);
					break;
				case ACTUALIZARCONTEXTO:
					Context con_update_context = (Context) msg.getObject();
					_masterContext.set_contextoAut1(con_update_context);

					//Se notifica del estado al SCADA
					dm.setIdentifier(MSGOntology.AUTOM_STATE);
					dm.setObject(!con_update_context.isApagado());
					dm.getParameters().add("AU1");
					_postmaster.sendMessageSCADA(dm);
					
					// Se notifica la cantidad de pasteles en la dispensadora
					dm = new DefaultMessage();
					dm.setIdentifier(MSGOntology.CAKE_DEPOT);
					dm.setObject(con_update_context.getPastelesRestantes());
					dm.getParameters().add("AU1");
					_postmaster.sendMessageSCADA(dm);
					
					// Se notifican los pasteles en la cinta
					dm = new DefaultMessage();
					dm.setIdentifier(MSGOntology.AU1_CAKES_POS);
					dm.setObject(getListaPasteles(con_update_context.get_listaPasteles()));
					dm.getParameters().add("AU1");
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
					_configuration.getPosicionAsociada(MachineNames.FIN_1))
					&& _masterContext.get_contextoRobot1().getEstadoInterno()
							.equals(RobotStates.REPOSO)
					&& _masterContext.getContador() < 4) {
				/*
				 * el estado interno del aut1 me dice q tiene el fin de la cinta
				 * ocupado envio el mensaje al robot 1 si esta en modo reposo y
				 * tengo un blister con posicion libre, es decir el contador es
				 * menor que 4
				 */
				MessageInterface mensajeSend = new DefaultMessage();
				mensajeSend.setIdentifier(MSGOntology.PASTELLISTO);
				_postmaster.sendMessageRB1(mensajeSend);
			}
			if (context!= null && context.getPastelesRestantes() < _configuration
					.getUmbralPasteles()) {
				/*
				 * si el contexto del aut 1 me dice q quedan pocos pasteles envio el
				 * mensaje a SCADA de pocos pasteles
				 */
				MessageInterface mensajeSend = new DefaultMessage();
				mensajeSend.getParameters().add(
						MachineNames.DISPENSADORA.getName());
				mensajeSend.getParameters().add(
						context.getPastelesRestantes()
								+ "");
				mensajeSend.setIdentifier(MSGOntology.AVISARUNFALLO);
				_postmaster.sendMessageSCADA(mensajeSend);
			}
			if (context.getCapacidadCaramelo() < _configuration
					.getUmbralCaramelos()) {
				/*
				 * si el contexto del aut 1 me dice q queda poco caramelo envio el
				 * mensaje a SCADA de poco caramelo
				 */
				MessageInterface mensajeSend = new DefaultMessage();
				mensajeSend.getParameters().add(MachineNames.CARAMELO.getName());
				mensajeSend.getParameters().add(
						context.getCapacidadCaramelo()
								+ "");
				mensajeSend.setIdentifier(MSGOntology.AVISARUNFALLO);
				_postmaster.sendMessageSCADA(mensajeSend);
			}
			if (context.getCapacidadChocolate() < _configuration
					.getUmbralChocolate()) {
				/*
				 * si el contexto del aut 1 me dice q queda poco chocolate envio el
				 * mensaje a SCADA de poco chocolate
				 */
				MessageInterface mensajeSend = new DefaultMessage();
				mensajeSend.getParameters().add(MachineNames.CHOCOLATE.getName());
				mensajeSend.getParameters().add(context.getCapacidadChocolate()
								+ "");
				mensajeSend.setIdentifier(MSGOntology.AVISARUNFALLO);
				_postmaster.sendMessageSCADA(mensajeSend);
			}
		}else{
			System.out.println("Context is null!!! AU1");
		}
	}

	private synchronized ArrayList<Integer> getListaPasteles(LinkedList<Cake> lista) {

		ArrayList<Integer> salida = new ArrayList<Integer>(7);
		for (int i = 0; i < 7; i++) {
			salida.add(i, 0);
		}
		
		for(int i=0;i<lista.size();i++){
			double pos=lista.get(i).get_posicion();
			if( pos<(_configuration.getPosBizc()+_configuration.getSizeBizcocho()/2) ){
				salida.set(0, salida.get(0)+1);
			}else if(pos<(_configuration.getPosChoc()-_configuration.getSizeBizcocho()/2)){
				salida.set(1, salida.get(1)+1);
			}else if(pos<(_configuration.getPosChoc()+_configuration.getSizeBizcocho()/2)){
				salida.set(2, salida.get(2)+1);
			}else if(pos<(_configuration.getPosCaram()-_configuration.getSizeBizcocho()/2)){
				salida.set(3, salida.get(3)+1);
			}else if(pos<(_configuration.getPosCaram()+_configuration.getSizeBizcocho()/2)){
				salida.set(4, salida.get(4)+1);
			}else if(pos<(_configuration.getPosFinAut1()-_configuration.getSizeBizcocho()/2)){
				salida.set(5, salida.get(5)+1);
			}else if(pos<(_configuration.getPosFinAut1()+_configuration.getSizeBizcocho()/2)){
				salida.set(6, salida.get(6)+1);
			}
		}
		return salida;
	}
	
}
