package com.umbrella.scada.model;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;

import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.modulocomunicacion.MailBox;

public class Postmaster extends Thread {
	
	private final MailBox _mailBox;
	private final LinkedList<MessageInterface> llmi;
	private static Postmaster instance = null;
	private boolean _no_end;
	
	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	private Postmaster() throws RemoteException, MalformedURLException, NotBoundException {
		Model model = Model.getInstance();
		_mailBox = new MailBox(model.get_genIP(), model.get_genPort(), "reciveBox", "sendBox");
		llmi = new LinkedList<MessageInterface>();
		_no_end = true;
	}

	/**
	 * Obtiene la instancia única del objeto, la primera invocación
	 * realiza la creación del mismo.
	 * @return la instancia única de Postmaster
	 */
	public static Postmaster getInstance() throws RemoteException, MalformedURLException, NotBoundException {
		Postmaster ret;
		if(instance == null){
			return getSyncInstance();
		}
		return instance;
	}

	private static synchronized Postmaster getSyncInstance() throws RemoteException, MalformedURLException, NotBoundException{
		if(instance == null)
			instance = new Postmaster();
		return instance;
	}
	
	@Override
	public void run() {
		while(_no_end){
			try {
				MessageInterface msg = _mailBox.receiveBlocking();
				switch (msg.getMSGCode()) {
				case AU1ARRANCADO:
					
					break;
				case AU2ARRANCADO:
									
									break;
				case AU3ARRANCADO:
					
					break;
				case RB1ARRANCADO:
					
					break;
				case RB2ARRANCADO:
					
					break;

				default:
					break;
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
