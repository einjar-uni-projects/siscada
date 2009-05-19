package com.umbrella.autoslave.executor;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.utils.ThreadState;
import com.umbrella.utils.Pastel;


/**
 * @author Pablo Izquierdo & Adrian Romero
 * Empresa: Umbrella Soft
 * Fecha de inicio: 
 *
 */
public class ActivatedDispenser extends Thread{

	
	/*
	 * pasteles que quedan en el deposito
	 */
	private int _remainderCakes;
	
	private Configuration _configuration=Configuration.getInstance();
	private Context _context=Context.getInstance();
	
	private static ActivatedDispenser INSTANCE = null;

	private double _position;
	private int _associatedPosition;
	private ThreadState _threadState;
	/*
	 * 
	 */
	/**
	 * @param position posicion que ocupa en la cinta
	 * @param associatedPosition el numero que ocupa en la cadena de 16 bits
	 */
	private ActivatedDispenser(double position, int associatedPosition) {
		_remainderCakes=_configuration.getCapacidadPasteles();
		setThreadState(ThreadState.CREADO);
		this._position=position;
		setAssociatedPosition(associatedPosition);
		_context.setRemainderCakes(_remainderCakes);
	}

	/**
	 * @return posicion donde esta la maquina
	 */
	public synchronized double getPosition() {
		return _position;
	}
	
	@Override
	public void run(){
		setThreadState(ThreadState.EJECUTANDO);
		
		while(!_threadState.equals(ThreadState.ACABADO)){
			if(_threadState.equals(ThreadState.ESPERANDO)) 
				if(_remainderCakes>0) setThreadState(ThreadState.EJECUTANDO);
			
			while(_threadState.equals(ThreadState.EJECUTANDO)){
				/*
				 * debe comprobar que debajo de la dispensadora no hay pastel, se comprueba que entre 
				 * el œltimo pastel y la posicion actual hay un espacio como minimo el solicitado  
				 */
				try {
					// se queda esperando a la se–al de reloj, el reloj cada vez q hace un CLICK hace un notifyAll
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 * SE SUPONE Q ESTE IF ES INNECESARIO
				 * si en la posicion donde esta la dispensadora no hay bizcocho
				 * &&
				 * el espacio que hay entre los bizchocos es igual o superior al que yo he dejado
				 */
				if(getSpaceCounter()>=(_configuration.getEspEntreBizc()+_configuration.getSizeBizcocho())){
					_context.setDispositivosInternos(getAssociatedPosition(), true);
					/*
					 * se pone un bizcocho, se cambia el estado actual y se inicializa el contador de espacio
					 * se pone la posicion de la cinta inicial como ocupada
					 */
					_remainderCakes--;
					_context.incrementarNumPasteles();
					_context.get_listaPasteles().add(new Pastel());
					_context.setDispositivosInternos(getAssociatedPosition(), false);
					if(_remainderCakes==0){
						setThreadState(ThreadState.ESPERANDO);
					}
				}else{
					// aqui no tendria q entrar nunca
					System.err.println("Error en la ejecucion del hilo de DsipensadoraActivada");
				}
			} 
		}
		_context.setRemainderCakes(_remainderCakes);
		setThreadState(ThreadState.ACABADO);
	}
	
	
	/**
	 * @param position la posicion en la cinta
	 * @param associatedPosition el numero que ocupa en la cadena de 16 bits
	 */
	private synchronized static void createInstance(double position, int associatedPosition) {
		if (INSTANCE == null) { 
			INSTANCE = new ActivatedDispenser(position, associatedPosition);
		}
	}

	/**
	 * @param position la posicion en la cinta
	 * @param associatedPosition el numero que ocupa en la cadena de 16 bits
	 * @return la dispensadora activada
	 */
	public static ActivatedDispenser getInstance(double position, int associatedPosition) {
		if (INSTANCE == null) createInstance(position, associatedPosition);
		return INSTANCE;
	}

	/**
	 * @return la dispensadora activada
	 */
	public static ActivatedDispenser getInstance() {
		return INSTANCE;
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
		if(value+_remainderCakes>50) _remainderCakes=50;
		else _remainderCakes+=value;	
		_context.setRemainderCakes(_remainderCakes);
	}
	
	/**
	 * @return
	 */
	public synchronized int getRemainderCakes(){
		return _remainderCakes;
	}
}