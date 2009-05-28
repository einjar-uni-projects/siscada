/*
 * MailBox.java
 *
 * Created on 21 de abril de 2009, 14:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.umbrella.mail.mailbox;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.mail.message.MessageInterface;

/**
 * 
 * @author l012g412
 */
public class ClientMailBox {

	private QueueInterface _inputQueue;
	private QueueInterface _outputQueue;
	private QueueInterface _inputQueueKA;
	private QueueInterface _outputQueueKA;
	private final String _queueServerIp;
	private final int _queueServerPort;
	private final String _inputQueueS;
	private final String _outputQueueS;
	private KeepAliveThread _keepAliveThread  = null;
	private final boolean _slave;
	private boolean _isStarted = false;

	/**
	 * Constructor de MailBox
	 * 
	 * @param queueServerIp
	 *            Ip del servidor de colas
	 * @param queueServerPort
	 *            Puerto del servidor de colas
	 * @param inputQueue
	 *            Referencia de la cola de entrada en el servidor de colas
	 * @param outputQueue
	 *            Referencia de la cola de salida en el servidor de colas
	 * @throws RemoteException
	 *             No se ha podido obtener la cola del servidor de colas
	 * @throws MalformedURLException
	 *             Ip, puerto o referencia no valida
	 * @throws NotBoundException
	 *             No se ha podido obtener la cola del servidor de colas
	 */
	public ClientMailBox(String queueServerIp, int queueServerPort,
			String inputQueue, String outputQueue, boolean slave) {
		_queueServerIp = queueServerIp;
		_queueServerPort = queueServerPort;
		_inputQueueS = inputQueue;
		_outputQueueS = outputQueue;
		_slave = slave;
		
		Thread t = new Thread(){
			@Override
			public void run() {
				connect();
			}
		};
		
		t.start();
		
	}

	private void connect() {
		int delay = 1000;
		int inc = 2;
		int max = 5000;
		boolean done = false;
		while (!done) {
			try {
				_inputQueue = (QueueInterface) Naming.lookup("rmi://"
						+ _queueServerIp + ":" + _queueServerPort + "/"
						+ _inputQueueS);
				_outputQueue = (QueueInterface) Naming.lookup("rmi://"
						+ _queueServerIp + ":" + _queueServerPort + "/"
						+ _outputQueueS);
				//Se crean los buzones de keepAlive
				_inputQueueKA = (QueueInterface) Naming.lookup("rmi://"
						+ _queueServerIp + ":" + _queueServerPort + "/"
						+ _inputQueueS+ServerMailBox._keepAlive);
				_outputQueueKA = (QueueInterface) Naming.lookup("rmi://"
						+ _queueServerIp + ":" + _queueServerPort + "/"
						+ _outputQueueS+ServerMailBox._keepAlive);
				
				//Creacion del hilo de keepAlive
				_keepAliveThread = new KeepAliveThread(_slave,_inputQueueKA, _outputQueueKA, _outputQueueS);
		        
				//Inicio del hilo de keepAlive
		        this._keepAliveThread.start();
				
				done = true;
				_isStarted = true;
				System.out.println("Conectado con el host: " + _queueServerIp
						+ ":" + _queueServerPort);
			} catch (Exception e) {
				System.out.println("No se ha podido conectar con el host: "
						+ e.getLocalizedMessage());
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				delay *= inc;
				if (delay > max)
					delay = 1000;
			}
		}
	}

	public void reconnect() {
		_isStarted = false;
		connect();
	}

	/**
	 * Metodo que encola un mensaje en la cola de salida
	 * 
	 * @param message
	 *            Mensaje a enviar
	 * @return true if it was possible to add the element to this queue, else
	 *         false
	 */
	public synchronized boolean send(MessageInterface message) {
		boolean done = false;
		boolean ret = false;
	//	System.out.println("Paso por aqui");
	//	System.out.flush();
		if(_isStarted){
			if(message != null)
		//	System.out.println("Mando3: "+message.getIdentifier());
		//	System.out.flush();
			while (!done) {
				try {
					ret = _outputQueue.queueMessage(message);
					done = true;
				} catch (Exception e) {
					e.printStackTrace();
					reconnect();
				}
			}
		}
		return ret;
	}

	/**
	 * Metodo bloqueante que desencola un mensaje de la cola de entrada Se queda
	 * esperando hasta que haya un mensaje en la cola.
	 * 
	 * @return the head of this queue, or null if this queue is empty.
	 */
	public synchronized MessageInterface receiveBlocking() {
		MessageInterface returnMessage = null;
		if(_isStarted){
			do {
				try {
					returnMessage = _inputQueue.unqueueMessage();
				} catch (RemoteException e) {
					reconnect();
				}
			} while (returnMessage == null);
		}
		return returnMessage;
	}

	/**
	 * Metodo no bloqueante que desencola un mensaje de la cola de entrada Si no
	 * hay un mensaje en la cola de entrada la salida ser� null
	 * 
	 * @return the head of this queue, or null if this queue is empty.
	 * @throws RemoteException
	 */
	public synchronized MessageInterface receive() throws RemoteException {
		MessageInterface returnMessage = null;
		if(_isStarted){
			try {
				returnMessage = _inputQueue.unqueueMessage();
			} catch (RemoteException e) {
				reconnect();
			}
		}
		return returnMessage;
	}

	public boolean getState() {
		return _keepAliveThread.get_State();
	}
}
