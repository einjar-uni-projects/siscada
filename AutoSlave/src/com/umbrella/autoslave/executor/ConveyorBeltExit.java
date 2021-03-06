package com.umbrella.autoslave.executor;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.Notifiable;
import com.umbrella.utils.ThreadState;


/**
 * @author 
 *
 */
public class ConveyorBeltExit extends Thread implements Notifiable{

	private double _position;
	private int _associatedPosition;

	private ThreadState _threadState;

	private Context _context=Context.getInstance();
	private Configuration _configuration=Configuration.getInstance();

	private boolean _joy = true;
	private boolean _joy2 = true;
	private  Clock _clock;

	private String _type;

	/**
	 * @param position
	 * @param associatedPosition
	 * @param type
	 */
	public ConveyorBeltExit(double position, int associatedPosition, String type) {
		setThreadState(ThreadState.CREADO);
		this._position=position;
		setAssociatedPosition(associatedPosition);
		this._type=type;
		_clock=Clock.getInstance();
		_clock.addNotificable(this);
	}

	@Override
	public void run(){
		while(!_context.isFIN()){
			pauseJoy2();
			guardedJoy2();
			
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
			_context.decrementarNumPasteles(); // TODO seguro??
			//se ha recogido el bizcocho del fin de la lista
			_context.setDispositivosInternos(getAssociatedPosition(), false);
			setThreadState(ThreadState.ACABADO);
		}
		
	}

	/**
	 * 
	 */
	public void sendMessage() {
		

	}

	/**
	 * @param msg
	 */
	public void changeMessage(boolean[] msg) {
		

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
		if(type.equals("pastel")){
			for(int i=0;i<_context.get_listaPasteles().size();i++){
				if(_context.get_listaPasteles().get(i).get_posicion()>=(getPosition()-_configuration.getErrorSensor()))
					libre=false;
			}
		}else{
			for(int i=0;i<_context.get_listaBlister().size();i++){
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
	public void notifyNoSyncJoy(NotificableSignal signal) {
		notifyJoy();
	}

	public synchronized void notifyJoy() {
		_joy = true;
		notifyAll();
	}

	public synchronized void pauseJoy() {
		_joy = false;
	}

	public synchronized void guardedJoy2() {
		// This guard only loops once for each special event, which may not
		// be the event we're waiting for.
		while (!_joy2) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void notifyNoSyncJoy2() {
		notifyJoy2();
	}

	public synchronized void notifyJoy2() {
			_joy2 = true;
			notifyAll();
	}

	public synchronized void pauseJoy2() {
		_joy2 = false;
	}
}