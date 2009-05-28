package com.umbrella.mail.mailbox;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.umbrella.mail.message.KeepAliveMessage;
import com.umbrella.mail.message.MessageInterface;

public class KeepAliveThread extends Thread {

	private QueueInterface _keepAliveQueue = null;
	private QueueInterface _outputQueueKA = null;
	private boolean _slave = false;
	private boolean _state;
	private Object _mutex = new Object();
	private String _recName;

	private void set_State(boolean state) {
		synchronized (_mutex) {
			if(_state != state){
				System.out.println("Conectado "+_recName+" - "+state);
				//Calendar c = Calendar.getInstance();
				//System.out.println(c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+" "+c.get(Calendar.SECOND)+"."+c.get(Calendar.MILLISECOND)+" Conection: "+_recName+" valor:"+state);
			}
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
			QueueInterface queueKA2, String recName) {
		_state = true;
		_slave = slave;
		this._keepAliveQueue = queueKA;
		this._outputQueueKA = queueKA2;
		_recName = recName;
	}

	private synchronized void receiveKeepAlive() {
		boolean no_end = true;
		while (no_end) {
			try {
				// Esperar 500 mseg
				try {
					Thread.sleep(500);
				} catch (InterruptedException ex) {
					Logger.getLogger(KeepAliveThread.class.getName()).log(
							Level.SEVERE, null, ex);
				}

				// Se no recibe mensaje de keepAlive
				if (reciveMsg()== null) {
					//System.out.println("No recibo keepalive");

					// Si el stado de la conexion era true, se actualiza, y
					// envia el mensaje de ConnectionTimedOut
					if (get_State() == true) {
						set_State(false);
						//_inputQueue.queueMessage(new ConnectionTimedOutMessage());
					}
				} else { // Se recibe el mensajes
					//System.out.println("Recibido keepalive");

					// Si el stado de la conexion era false, se actualiza, y
					// envia el mensaje de ConnectionAlive
					if (get_State() == false) {
						set_State(true);
						//_inputQueue.queueMessage(new ConnectionAliveMessage());
					}
				}
			} catch (RemoteException ex) {
				no_end = false;
				//Logger.getLogger(KeepAliveThread.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		System.out.println("Se finaliza el hilo a la espera de nueva conexión");

	}

	private synchronized MessageInterface reciveMsg() throws RemoteException {
		return _outputQueueKA.unqueueMessage();
	}

	private void sendKeepAlive() {

		MessageInterface syncMessage = new KeepAliveMessage();

		//System.out.println("Actua como esclavo");

		// Actua como esclavo, ha de enviar mensajes keepAlive
		boolean no_end = true;
		while (no_end) {
			try {

				// Esperar timeout
				try {
					Thread.sleep(500);
				} catch (InterruptedException ex) {
					Logger.getLogger(KeepAliveThread.class.getName()).log(
							Level.SEVERE, null, ex);
				}

				send(syncMessage);

			} catch (RemoteException ex) {
				no_end = false;
				//System.out.println("PETERLS");
			}
		}
		System.out.println("Se finaliza el hilo a la espera de nueva conexión");

	}

	private synchronized void send(MessageInterface syncMessage) throws RemoteException {
		// Enviar mensaje de timeout
		_keepAliveQueue.queueMessage(syncMessage);
		//System.out.println("Enviado keepAlive");
	}

	@Override
	public void run() {

		if (_slave) {
			sendKeepAlive();
		} else {
			//System.out.println("No actua como esclavo");
			// No actua como esclavo, ha de recibir mensajes keepAlive
			receiveKeepAlive();

		}
	}
}