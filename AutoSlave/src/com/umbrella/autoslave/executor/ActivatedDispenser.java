package com.umbrella.autoslave.executor;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.Notifiable;
import com.umbrella.utils.Cake;
import com.umbrella.utils.ThreadState;


/**
 * @author Pablo Izquierdo & Adrian Romero
 * Empresa: Umbrella Soft
 * Fecha de inicio: 
 *
 */
public class ActivatedDispenser extends Thread implements Notifiable{

	
	/*
	 * pasteles que quedan en el deposito
	 */
	private int _remainderCakes;
	
	private Configuration _configuration=Configuration.getInstance();
	private Context _context = Context.getInstance("pastel");
	
	private static  ActivatedDispenser INSTANCE = null;

	private double _position;
	private int _associatedPosition;
	private ThreadState _threadState;
	
	private boolean _joy = true;
	private boolean _joy2 = true;
	private  Clock _clock;
	
	//TODO debug interno propio
	private boolean debug=false;
	/**
	 * @param position posicion que ocupa en la cinta
	 * @param associatedPosition el numero que ocupa en la cadena de 16 bits
	 */
	private ActivatedDispenser(double position, int associatedPosition) {
		_remainderCakes=_configuration.getCapacidadPasteles();
		setThreadState(ThreadState.CREADO);
		this._position=position;
		setAssociatedPosition(associatedPosition);
		//_context.setRemainderCakes(_remainderCakes);
		_clock=Clock.getInstance();
		_clock.addNotificable(this);
		this.start(); // TODO Digo yo no??
	}

	/**
	 * @return posicion donde esta la maquina
	 */
	public synchronized double getPosition() {
		return _position;
	}

	@Override
	public void run(){
		while(!_context.isFIN()){
			pauseJoy2();
			guardedJoy2();
			if(!_context.isApagado()){
				if((getSpaceCounter()-_configuration.getPosBizc())>=(_configuration.getEspEntreBizc()+_configuration.getSizeBizcocho())){

					//_remainderCakes = _context.getPastelesRestantes();
					if(_remainderCakes>0){
						setThreadState(ThreadState.EJECUTANDO);
						if(debug) System.out.println("despierta del wait - DISPENSADORA");
						if(debug) System.out.println("entra en el if de la dispensadora de si tengo espacio  - DISPENSADORA");					
						_context.setDispositivosInternos(getAssociatedPosition(), true);
						/*
						 * se pone un bizcocho, se cambia el estado actual y se inicializa el contador de espacio
						 * se pone la posicion de la cinta inicial como ocupada
						 */
						_remainderCakes--;
						_context.setRemainderCakes(_remainderCakes);
						_context.incrementarNumPasteles();
						_context.incrementarCuadrarPasteles();
						_context.addListaPastel(new Cake());
						_context.setDispositivosInternos(getAssociatedPosition(), false);
						if(_remainderCakes==0){
							setThreadState(ThreadState.ESPERANDO);
						}
					}

				} 
			}
		}
	}


	/**
	 * @param position la posicion en la cinta
	 * @param associatedPosition el numero que ocupa en la cadena de 16 bits
	 */
	private synchronized static  void createInstance(double position, int associatedPosition) {
		if (INSTANCE == null) { 
			INSTANCE = new ActivatedDispenser(position, associatedPosition);
		}
	}

	/**
	 * @param position la posicion en la cinta
	 * @param associatedPosition el numero que ocupa en la cadena de 16 bits
	 * @return la dispensadora activada
	 */
	public static  ActivatedDispenser getInstance(double position, int associatedPosition) {
		if (INSTANCE == null) createInstance(position, associatedPosition);
		return INSTANCE;
	}

	/**
	 * @return la dispensadora activada
	 */
	public  ActivatedDispenser getInstance() {
		return INSTANCE;
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
	 * devuelve la distancia del ultimo pastel a la posicion del dispensador de bizcochos
	 */
	private synchronized double getSpaceCounter(){
		double min=(_configuration.getSizeCinta()+1)*100;
		for(int i=0;i<_context.get_listaPasteles().size();i++){
			double num=_context.get_listaPasteles().get(i).get_posicion();
			if(num<min) min=num;
		}
		return min;
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
	
	/**
	 * @param value
	 */
	public synchronized void fillDeposit(int value){
		if(value+_remainderCakes>50)
			_remainderCakes=50;
		else
			_remainderCakes+=value;	
		_context.setRemainderCakes(_remainderCakes);
	}
	
	public synchronized void fillDeposit(){
		_remainderCakes=50;
	}
	
	/**
	 * @return
	 */
	public synchronized int getRemainderCakes(){
		return _remainderCakes;
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