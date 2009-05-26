package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.ContextoRobot;
import com.umbrella.autocommon.MasterContext;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.Blister;
import com.umbrella.utils.MachineNames;
import com.umbrella.utils.RobotStates;

/**
 * 
 * @author pablo Clase que leera los mensajes enviados por al automata 2 y
 *         actuara en consecuencia
 */
public class ReceiveAutomaton2 extends Thread {

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
			msg = _postmaster.reciveMessageAU2();
			
			if (msg != null) {
				System.out.println("AU2 Recive: " + msg.getIdentifier());
				switch (msg.getIdentifier()) {
				case ACTUALIZARCONTEXTO:
					Context con_update_context = (Context) msg.getObject();
					_masterContext.set_contextoAut2(con_update_context);
					
					//Se notifica del estado al SCADA
					dm.setIdentifier(MSGOntology.AUTOM_STATE);
					dm.setObject(!con_update_context.isApagado());
					dm.getParameters().add("AU2");
					_postmaster.sendMessageSCADA(dm);
					
					// Se notifican los blisters en la cinta
					dm = new DefaultMessage();
					dm.setIdentifier(MSGOntology.AU2_BLISTER_POS);
					dm.setObject(getListaBlisters(con_update_context.get_listaBlister()));
					dm.getParameters().add("AU2");
					_postmaster.sendMessageSCADA(dm);
					
					break;
				}
			}
			dm = new DefaultMessage();
			
		} while (msg != null);
		/*
		 * el estado interno del aut2 me dice q tiene el fin de la cinta ocupado
		 * y el inicio de la cinta 3 esta libre envio el mensaje al robot 1 si
		 * esta en modo reposo de blisterlisto y no hay blisteres en la mesa
		 */
		Context context = _masterContext.get_contextoAut2();
		ContextoRobot contextr1 = _masterContext.get_contextoRobot1();
		Context contexta3 = _masterContext.get_contextoAut3();
		
		if (context != null && contextr1 != null && contexta3 != null) {
			if (context.getDispositivosInternos(_configuration.getPosicionAsociada(MachineNames.FIN_3))
					&& contextr1.getEstadoInterno().equals(RobotStates.REPOSO)
					&& !_masterContext.isBlisterColocado()
					/* Sobra porque va contra la mesa&& !contexta3.getDispositivosInternos(_configuration.getPosicionAsociada(MachineNames.INICIO))*/) {
				MessageInterface mensajeSend = new DefaultMessage();
				mensajeSend.setIdentifier(MSGOntology.BLISTERLISTO);
				_postmaster.sendMessageRB1(mensajeSend);
			}
		} else {
			System.out.println("Context is null!!! AU2");

		}
	}
	
	private synchronized ArrayList<Integer> getListaBlisters(LinkedList<Blister> lista) {

		ArrayList<Integer> salida = new ArrayList<Integer>(5);
		for (int i = 0; i < 5; i++) {
			salida.add(i, 0);
		}
		
		for(int i=0;i<lista.size();i++){
			double pos=lista.get(i).get_posicion();
			if( pos<(_configuration.getPosTroqueladora()+_configuration.getSizeBlister()/2) ){
				salida.set(0, salida.get(0)+1);
			}else if(pos<(_configuration.getPosCortadora()-_configuration.getSizeBlister()/2)){
				salida.set(1, salida.get(1)+1);
			}else if(pos<(_configuration.getPosCortadora()+_configuration.getSizeBlister()/2)){
				salida.set(2, salida.get(2)+1);
			}else if(pos<(_configuration.getPosFinAut2()-_configuration.getSizeBlister()/2)){
				salida.set(3, salida.get(3)+1);
			}else if(pos<(_configuration.getPosFinAut2()+_configuration.getSizeBlister()/2)){
				salida.set(4, salida.get(4)+1);
			}
		}
		return salida;
	}
	
}
