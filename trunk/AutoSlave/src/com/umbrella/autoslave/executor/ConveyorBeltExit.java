package com.umbrella.autoslave.executor;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.Notificable;
import com.umbrella.utils.ThreadState;


/**
 * @author 
 *
 */
public class ConveyorBeltExit extends Thread implements Notificable{

	private double _position;
	private int _associatedPosition;

	private ThreadState _threadState;
	
	private Context _context=Context.getInstance();
	private Configuration _configuration=Configuration.getInstance();
	
	private boolean _joy = true;
	private  Clock _clock;
	
	private String _type;
	
	/**
	 * @param position
	 * @param associatedPosition
	 * @param type
	 */
	public ConveyorBeltExit(double position, int associatedPosition, String type) {
		// TODO Auto-generated constructor stub
		setThreadState(ThreadState.CREADO);
		this._position=position;
		setAssociatedPosition(associatedPosition);
		this._type=type;
		_clock=Clock.getInstance();
		_clock.setNotificable(this);
	}

	@Override
	public void run(){
		setThreadState(ThreadState.EJECUTANDO);
		_context.setDispositivosInternos(getAssociatedPosition(), true);
		
		boolean finCintaLibre=endEmptyConveyorBelt(_type);
		while (!finCintaLibre){
			//se espera al siguiente click de la cinta
			pauseJoy();
			guardedJoy();
			//comprobar el estado de la cinta
			finCintaLibre=endEmptyConveyorBelt(_type);
		}
		_context.decrementarNumPasteles();
		//se ha recogido el bizcocho del fin de la lista
		_context.setDispositivosInternos(getAssociatedPosition(), false);
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
	 * @param type
	 * @return
	 */
	private synchronized boolean endEmptyConveyorBelt(String type){
		boolean libre=true;
		for(int i=0;i<_context.get_listaPasteles().size();i++){
			if(type.equals("pastel")){
				if(_context.get_listaPasteles().get(i).get_posicion()>=(getPosition()-_configuration.getErrorSensor()))
					libre=false;
			}else{
				if(_context.get_listaBlister().get(i).get_posicion()>=(getPosition()-_configuration.getErrorSensor()))
					libre=false;
			}
		}
		return libre;
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