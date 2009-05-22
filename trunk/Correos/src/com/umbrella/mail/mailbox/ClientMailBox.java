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
	private final String _queueServerIp;
	private final int _queueServerPort;
	private final String _inputQueueS;
	private final String _outputQueueS;

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
			String inputQueue, String outputQueue) {
		_queueServerIp = queueServerIp;
		_queueServerPort = queueServerPort;
		_inputQueueS = inputQueue;
		_outputQueueS = outputQueue;
		connect();
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
				done = true;
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
	public boolean send(MessageInterface message) {
		boolean done = false;
		boolean ret = false;

		while (!done) {
			try {
				ret = _outputQueue.queueMessage(message);
				done = true;
			} catch (Exception e) {
				reconnect();
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
	public MessageInterface receiveBlocking() {
		MessageInterface returnMessage = null;
		do {
			try {
				returnMessage = _inputQueue.unqueueMessage();
			} catch (RemoteException e) {
				reconnect();
			}
		} while (returnMessage == null);
		return returnMessage;
	}

	/**
	 * Metodo no bloqueante que desencola un mensaje de la cola de entrada Si no
	 * hay un mensaje en la cola de entrada la salida ser� null
	 * 
	 * @return the head of this queue, or null if this queue is empty.
	 * @throws RemoteException
	 */
	public MessageInterface receive() throws RemoteException {
		MessageInterface returnMessage = null;
		try {
			returnMessage = _inputQueue.unqueueMessage();
		} catch (RemoteException e) {
			reconnect();
		}
		return returnMessage;
	}

	public boolean getState() {
		// TODO Auto-generated method stub
		return true;
	}
}
