package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.ContextoMaestro;
import com.umbrella.autocommon.ContextoRobot;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.NombreMaquinas;

/**
 * 
 * @author pablo
 * Clase que leera los mensajes enviados por al automata 1 y actuara en consecuencia
 */
public class ReceiveRobot2 extends Thread{

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
			msg=_postmaster.reciveMessageRB2();
			switch (msg.getIdentificador()) {
			case INTERFERENCIA:		
				//envio a la cinta 3
				_postmaster.sendMessageAU3(msg);
				break;
			case FININTERFERENCIA:
				//envio a la cinta 3
				_postmaster.sendMessageAU3(msg);
				break;
			case ACTUALIZARCONTEXTOROBOT:
				_masterContext.set_contextoRobot2((ContextoRobot)msg.getObject());
				break;
			case PRODUCTORECOGIDO:
				// se envia un mensaje a la cinta 3 de PRODUCTORECOGIDO, el mismo mensaje que le llega
				_postmaster.sendMessageAU3(msg);
				break;
			case BLISTERALMACENADO:
				//envia el mensaje a SCADA informando 
				if(msg.getParametros().get(0).equalsIgnoreCase("true")){
					//el blister que se almaceno era valido
				}else{
					//el blister que se almaceno era no valido
				}
				break;
			}
		}while(msg!=null);
	}
	
}
