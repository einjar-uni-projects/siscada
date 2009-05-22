package com.umbrella.autoslave.executor;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.Notificable;
import com.umbrella.utils.ThreadState;


/*
 * Maquina que no tiene un tiempo de ejecucion 
 */
public class InstantaneousMachine extends Thread implements Notificable{

	
	private Configuration configuration=Configuration.getInstance();
	private Context context=Context.getInstance();
	
	private double _position;
	private int _associatedPosition;
	private ThreadState _threadState;
	private boolean _joy = true;
	private  Clock _clock;
	
	/**
	 * @param position
	 * @param associatedPosition
	 */
	public InstantaneousMachine(double position, int associatedPosition) {
		setThreadState(ThreadState.CREADO);
		this._position=position;
		setAssociatedPosition(associatedPosition);
		_clock=Clock.getInstance();
		_clock.setNotificable(this);
	}

	/**
	 * @return
	 */
	public synchronized double getPosition() {
		return _position;
	}
	
	@Override
	public void run(){
		setThreadState(ThreadState.EJECUTANDO);
		context.setDispositivosInternos(_associatedPosition, true);

		pauseJoy();
		guardedJoy();

		// espera un ciclo de reloj para cambiar el estado de la maquina
		context.setDispositivosInternos(_associatedPosition, false);
		setThreadState(ThreadState.ACABADO);
	}

	/**
	 * 
	 */
	public void sendMessage() {
		// TODO Auto-generated method stub
	}
	/**
	 * @param msg
	 */
	public void changeMessage(boolean[] msg) {
		// TODO Auto-generated method stub	
	}
	/**
	 * @return
	 */
	public synchronized ThreadState getThreadState() {
		return _threadState;
	}
	/**
	 * @param state
	 */
	private synchronized void setThreadState(ThreadState state) {
		this._threadState=state;
	}
	/**
	 * @return
	 */
	private synchronized int getAssociatedPosition() {
		return _associatedPosition;
	}
	/**
	 * @param associated
	 */
	private synchronized void setAssociatedPosition(int associated) {
		_associatedPosition = associated;
	}
	public synchronized void guardedJoy() {
		// This guard only loops once for each special event, which may not
		// be the event we're waiting for.
		while (!_joy) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}
	
	@Override
	public void notifyNoSyncJoy() {
		notifyJoy();
	}

	public synchronized void notifyJoy() {
		_joy = true;
		notifyAll();
	}

	public synchronized void pauseJoy() {
		_joy = false;
	}
}