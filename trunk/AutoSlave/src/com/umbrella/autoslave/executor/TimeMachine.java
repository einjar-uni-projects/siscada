package com.umbrella.autoslave.executor;

import com.umbrella.autocommon.Context;
import com.umbrella.utils.ThreadState;


/*
 * Maquinas que tardan un tiempo '_tiempoEjecucoin' en ejecutarse
 */
public class TimeMachine extends Thread{
	private double _executionTime;
	private double _position;
	/*
	 * posicion del estado interno asociada
	 */
	private int _associatedPosition;
	

	/*
	 * indica el estado interno del hilo
	 */
	private ThreadState _threadState;
	
	private Context context=Context.getInstance();
	
	/**
	 * @param executionTime
	 * @param position
	 * @param associatedPosition
	 */
	public TimeMachine(double executionTime, double position, int associatedPosition) {
		this._executionTime=executionTime;
		this._position=position;
		setThreadState(ThreadState.CREADO);
		setAssociatedPosition(associatedPosition);
	}

	@Override
	public void run(){
		setThreadState(ThreadState.EJECUTANDO);
		context.setDispositivosInternos(getAssociatedPosition(), true);
		double tiempoActual=System.currentTimeMillis(); //medido en milisegundos
		while(((System.currentTimeMillis()-tiempoActual)*1000)<this._executionTime){
			/*
			 *  espero a que el reloj envie la se–al de Click, cuando se envie el click se comprobar‡
			 *  el tiempo de ejecucion y si sobrepaso el tiempo el hilo acaba
			 *  
			 *  da informacion false entre el verdadero tiempo de ejecucion de la maquina dispensadora 
			 *  y el click en el que se ejecuta pero nos da = porq solo nos interesa en el refresco
			 *  de la aplicaccion.
			 */
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		context.setDispositivosInternos(getAssociatedPosition(), false);
		//se ha echado el caramelo en el bizcocho
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
	public synchronized double getPosition() {
		return _position;
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
