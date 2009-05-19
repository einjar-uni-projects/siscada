package com.umbrella.autoslave.executor;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.utils.ThreadState;


/*
 * Maquina que no tiene un tiempo de ejecucion 
 */
public class InstantaneousMachine extends Thread{

	
	private Configuration configuration=Configuration.getInstance();
	private Context context=Context.getInstance();
	
	private double _position;
	private int _associatedPosition;
	private ThreadState _threadState;
	
	/**
	 * @param position
	 * @param associatedPosition
	 */
	public InstantaneousMachine(double position, int associatedPosition) {
		setThreadState(ThreadState.CREADO);
		this._position=position;
		setAssociatedPosition(associatedPosition);
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
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
}