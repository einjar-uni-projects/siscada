package com.umbrella.mail.mailbox;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class ServerMailBox {
	public static final String _reciveSCADAName = "reciveSCADABox";
	public static final String _sendSCADAName = "sendSCADABox";
	
	public static final String _reciveR1Name = "reciveR1Box";
	public static final String _sendR1Name = "sendR1Box";
	
	public static final String _reciveR2Name = "reciveR2Box";
	public static final String _sendR2Name = "sendR2Box";
	
	public static final String _reciveAU1Name = "reciveAU1Box";
	public static final String _sendAU1Name = "sendAU1Box";
	
	public static final String _reciveAU2Name = "reciveAU2Box";
	public static final String _sendAU2Name = "sendAU2Box";
	
	public static final String _reciveAU3Name = "reciveAU3Box";
	public static final String _sendAU3Name = "sendAU3Box";
	
	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	public ServerMailBox(int port) throws RemoteException, MalformedURLException {
		// Se inicia el servidor de colas escuchando en un puerto.
		QueueServer servidor = new QueueServer();
		servidor.startRegistry(port);

		/* Ahora se registran las colas en el registro que escucha en el puerto 9003
		 * Da excepcion si hay problemas*/
		/* Se instancias las colas que se quieran*/
		MessageQueue queue = new MessageQueue();
		servidor.registerObject(queue, _reciveSCADAName, port);
		queue = new MessageQueue();
		servidor.registerObject(queue, _sendSCADAName, port);
		queue = new MessageQueue();
		servidor.registerObject(queue, _reciveR1Name, port);
		queue = new MessageQueue();
		servidor.registerObject(queue, _sendR1Name, port);
		queue = new MessageQueue();
		servidor.registerObject(queue, _reciveR2Name, port);
		queue = new MessageQueue();
		servidor.registerObject(queue, _sendR2Name, port);
		queue = new MessageQueue();
		servidor.registerObject(queue, _reciveAU1Name, port);
		queue = new MessageQueue();
		servidor.registerObject(queue, _sendAU1Name, port);
		queue = new MessageQueue();
		servidor.registerObject(queue, _reciveAU2Name, port);
		queue = new MessageQueue();
		servidor.registerObject(queue, _sendAU2Name, port);
		queue = new MessageQueue();
		servidor.registerObject(queue, _reciveAU3Name, port);
		queue = new MessageQueue();
		servidor.registerObject(queue, _sendAU3Name, port);
	}

}
