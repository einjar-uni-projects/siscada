package com.umbrella.mail.mailbox;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.umbrella.mail.message.KeepAliveMessage;
import com.umbrella.mail.message.MessageInterface;

public class KeepAliveThread extends Thread {

	QueueInterface _keepAliveQueue = null;
	QueueInterface _inputQueue = null;
	boolean _slave = false;
	private boolean _state;
	private Object _mutex = new Object();

	private void set_State(boolean state) {
		synchronized (_mutex) {
			_state = state;
		}
	}

	public boolean get_State() {
		boolean ret;
		synchronized (_mutex) {
			ret = _state;
		}
		return ret;
	}

	KeepAliveThread(boolean slave, QueueInterface queueKA,
			QueueInterface queueKA2) {
		_state = true;
		_slave = slave;
		this._keepAliveQueue = queueKA;
		this._inputQueue = queueKA2;
	}

	private void receiveKeepAlive() {
		while (true) {
			try {
				// Esperar 1000 mseg
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(KeepAliveThread.class.getName()).log(
							Level.SEVERE, null, ex);
				}

				// Se no recibe mensaje de keepAlive
				if (_keepAliveQueue.unqueueMessage() == null) {
					System.out.println("No recibo keepalive");

					// Si el stado de la conexion era true, se actualiza, y
					// envia el mensaje de ConnectionTimedOut
					if (get_State() == true) {
						set_State(false);
						//_inputQueue.queueMessage(new ConnectionTimedOutMessage());
					}
				} else { // Se recibe el mensajes
					System.out.println("Recibido keepalive");

					// Si el stado de la conexion era false, se actualiza, y
					// envia el mensaje de ConnectionAlive
					if (get_State() == false) {
						set_State(true);
						//_inputQueue.queueMessage(new ConnectionAliveMessage());
					}
				}
			} catch (RemoteException ex) {
				Logger.getLogger(KeepAliveThread.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

	}

	private void sendKeepAlive() {

		MessageInterface syncMessage = new KeepAliveMessage();

		System.out.println("Actua como esclavo");

		// Actua como esclavo, ha de enviar mensajes keepAlive
		while (true) {
			try {

				// Esperar timeout
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(KeepAliveThread.class.getName()).log(
							Level.SEVERE, null, ex);
				}

				// Enviar mensaje de timeout
				_keepAliveQueue.queueMessage(syncMessage);
				System.out.println("Enviado keepAlive");

			} catch (RemoteException ex) {
				System.out.println("PETERLS");
			}
		}

	}

	@Override
	public void run() {

		if (_slave) {
			sendKeepAlive();
		} else {
			System.out.println("No actua como esclavo");
			// No actua como esclavo, ha de recibir mensajes keepAlive
			receiveKeepAlive();

		}
	}
}