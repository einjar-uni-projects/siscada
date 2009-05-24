package com.umbrella.autoslave.executor;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.Notificable;
import com.umbrella.utils.NombreMaquinas;
import com.umbrella.utils.ThreadState;



/**
 * @author 
 *
 */
public class MoveConveyorBelt extends Thread implements Notificable{
	
	private ThreadState _threadState;
	
	private int _associatedPosition;
	
	private Context context=Context.getInstance();
	private Configuration configuration=Configuration.getInstance();
	
	private boolean _joy = true;
	private boolean _joy2 = true;
	private  Clock _clock;
	/*
	 * lo q desplaza la cinta en un click, en metros
	 */
	private double conveyorBeltSpeedByMilisecond;
	/*
	 * espacio que recorre la cinta en un click, medido en CM 
	 */
	private double spaceElapsedByClick;
	
	private double conveyorBeltSpeed;

	/**
	 * @param speed
	 * @param associatedPosition
	 */
	public MoveConveyorBelt(double speed, int associatedPosition) {
		// TODO Auto-generated constructor stub
		setConveyorBeltSpeed(speed);
		setConveyorBeltSpeedByMilisecond(getConveyorBeltSpeed()/(60*1000));
		setSpaceElapsedByClick(getConveyorBeltSpeedByMilisecond()*configuration.get_tiempoReloj());
		setAssociatedPosition(associatedPosition);
		_clock=Clock.getInstance();
		_clock.addNotificable(this);
	}

	/*
	 */
	@Override
	public void run(){
		while(!context.isApagado()){
			/*
			 * si se ejecuta la cinta 1 vez la cinta se desplaza minimo una cantidad X, suponemos q eso es siempre superior a un click
			 */
			_threadState=ThreadState.EJECUTANDO;

			/*
			 * nos dice si algun sensor se va a encender
			 * se da el valor False xq al menos tiene q dar el salto una vez
			 */
			if(context.getTipo().equalsIgnoreCase("blister")){
				for(int i=0;i<context.get_listaBlister().size();i++){
					if((context.get_listaBlister().get(i).get_posicion()+spaceElapsedByClick)<=configuration.getSizeCinta())
						context.get_listaBlister().get(i).incrementarPosicion(spaceElapsedByClick);
					else{
						//el pastel SE HA CAIDO DE LA CINTA
						context.get_listaBlister().get(i).set_posicion(configuration.getSizeCinta());
					}
				}
			}else{
				for(int i=0;i<context.get_listaPasteles().size();i++){
					if((context.get_listaPasteles().get(i).get_posicion()+spaceElapsedByClick)<=configuration.getSizeCinta())
						context.get_listaPasteles().get(i).incrementarPosicion(spaceElapsedByClick);
					else{
						//el pastel SE HA CAIDO DE LA CINTA
						context.get_listaPasteles().get(i).set_posicion(configuration.getSizeCinta());
					}
				}
			}

			pauseJoy();
			guardedJoy();

			setThreadState(ThreadState.ESPERANDO);
			pauseJoy2();
			guardedJoy2();
		}
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
	private synchronized double getConveyorBeltSpeedByMilisecond() {
		return conveyorBeltSpeedByMilisecond;
	}

	/**
	 * @param conveyorBeltSpeedByMilisecond
	 */
	private synchronized void setConveyorBeltSpeedByMilisecond(double conveyorBeltSpeedByMilisecond) {
		this.conveyorBeltSpeedByMilisecond = conveyorBeltSpeedByMilisecond;
	}

	/**
	 * @return
	 */
	private synchronized double getConveyorBeltSpeed() {
		return conveyorBeltSpeed;
	}

	/**
	 * @param conveyorBeltSpeed
	 */
	private synchronized void setConveyorBeltSpeed(double conveyorBeltSpeed) {
		this.conveyorBeltSpeed = conveyorBeltSpeed;
	}

	/**
	 * @return
	 */
	private synchronized double getSpaceElapsedByClick() {
		return spaceElapsedByClick;
	}

	/**
	 * @param spaceElapsedByClick
	 */
	private synchronized void setSpaceElapsedByClick(double spaceElapsedByClick) {
		this.spaceElapsedByClick = spaceElapsedByClick;
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
	public void notifyNoSyncJoy2(String machine) {
		notifyJoy2(machine);
	}

	public synchronized void notifyJoy2(String machine) {
		if(machine.equals(NombreMaquinas.DISPENSADORA.getName())){
			_joy2 = true;
			notifyAll();
		}
	}

	public synchronized void pauseJoy2() {
		_joy2 = false;
	}
}