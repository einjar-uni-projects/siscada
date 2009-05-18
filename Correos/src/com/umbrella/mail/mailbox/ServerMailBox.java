package com.umbrella.mail.mailbox;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class ServerMailBox {
	public static final String _reciveName = "reciveBox";
	public static final String _sendName = "sendBox";
	
	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	public ServerMailBox(int port) throws RemoteException, MalformedURLException {
		// Se inicia el servidor de colas escuchando en un puerto.
		QueueServer servidor = new QueueServer();
		servidor.startRegistry(port);
		
		/* Se instancias las colas que se quieran*/
		MessageQueue recive = new MessageQueue();
		MessageQueue send = new MessageQueue();

		/* Ahora se registran las colas en el registro que escucha en el puerto 9003
		 * Da excepcion si hay problemas*/
		servidor.registerObject(recive, _reciveName, port);
		servidor.registerObject(send, _sendName, port);
	}

}
