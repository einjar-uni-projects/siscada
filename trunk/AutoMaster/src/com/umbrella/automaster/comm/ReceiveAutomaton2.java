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
 * @author pablo
 * Clase que leera los mensajes enviados por al automata 2 y actuara en consecuencia
 */
public class ReceiveAutomaton2 extends Thread{

	Postmaster _postmaster;
	ContextoMaestro _masterContext;
	Configuration _configutarion;
	
	
	public synchronized void inicializar(){
		try{
			_postmaster=Postmaster.getInstance();
			_masterContext=ContextoMaestro.getInstance();
			_configutarion=Configuration.getInstance();
		}catch (RemoteException e1) {
			// TODO: handle exception
		}catch (MalformedURLException e2) {
			// TODO: handle exception
		}catch (PropertyException e4) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void run(){
		MessageInterface msg=null;
		do{
			msg=_postmaster.reciveMessageAU2();
			switch (msg.getIdentificador()) {
			case ACTUALIZARCONTEXTO:
				_masterContext.set_contextoAut2((Context)msg.getObject());
				break;
			}
		}while(msg!=null);
		/*
		 * el estado interno del aut2 me dice q tiene el fin de la cinta ocupado y el inicio de la cinta 3 esta libre
		 * envio el mensaje al robot 1 si esta en modo reposo de blisterlisto
		 */
		if(_masterContext.get_contextoAut2().getDispositivosInternos(_configutarion.getPosicionAsociada(NombreMaquinas.FIN_3)) && 
				_masterContext.get_contextoRobot1().getEstadoInterno().equals(EstateRobots.REPOSO) && 
					!_masterContext.get_contextoAut3().getDispositivosInternos(_configutarion.getPosicionAsociada(NombreMaquinas.INICIO))){
			MessageInterface mensajeSend=new DefaultMessage();
			mensajeSend.setIdentificador(OntologiaMSG.BLISTERLISTO);
			_postmaster.sendMessageRB1(mensajeSend);
		}
	}
	
}
