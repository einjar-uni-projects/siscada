package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.MasterContext;
import com.umbrella.automaster.LaunchAutMaster;
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
			msg = _postmaster.reciveMessageAU3();
			
			if (msg != null) {
				if(LaunchAutMaster.debugSlav3)
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
					
					// Se notifican los paquetes en la cinta
					dm = new DefaultMessage();
					dm.setIdentifier(MSGOntology.AU3_PACKAGE_POS);
					dm.setObject(getPackageList(con_update_context.get_listaBlister()));
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
			if (context.getDispositivosInternos(_configuration
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
					_configuration.getPosicionAsociada(MachineNames.FIN_3))) {
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
	
	private synchronized ArrayList<Integer> getPackageList(LinkedList<Blister> lista) {

		ArrayList<Integer> salida = new ArrayList<Integer>(5);
		for (int i = 0; i < 5; i++) {
			salida.add(i, 0);
		}
		
		for(int i=0;i<lista.size();i++){
			double pos=lista.get(i).get_posicion();
			if( pos<(_configuration.getPosCalidad()-_configuration.getSizeBlister()/2) ){
				salida.set(0, salida.get(0)+1);
System.err.println("caca1 "+pos);
			}if( pos<(_configuration.getPosCalidad()+_configuration.getSizeBlister()/2) ){
				salida.set(1, salida.get(1)+1);
System.err.println("caca2 "+pos);
			/*}else if(pos<(_configuration.getPosSelladora()-_configuration.getSizeBlister()/2)){
				salida.set(2, salida.get(2)+1);*/
			}else if(pos<(_configuration.getPosSelladora()+_configuration.getSizeBlister()/2)){
				salida.set(2, salida.get(2)+1);
System.err.println("caca3 "+pos);
			}else if(pos<(_configuration.getPosFinAut3()-_configuration.getSizeBlister()/2)){
				salida.set(3, salida.get(3)+1);
System.err.println("caca4 "+pos);
			}else if(pos<(_configuration.getPosFinAut3()+_configuration.getSizeBlister()/2)){
				salida.set(4, salida.get(4)+1);
System.err.println("caca5 "+pos);
			}
		}
		return salida;
	}
	
}
