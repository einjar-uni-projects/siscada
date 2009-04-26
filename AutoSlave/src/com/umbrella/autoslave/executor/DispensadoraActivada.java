package com.umbrella.autoslave.executor;

import com.umbrella.autoslave.logic.Configuracion;
import com.umbrella.autoslave.logic.Contexto;
import com.umbrella.autoslave.logic.EstateThreads;

public class DispensadoraActivada extends Thread implements Estado{

	/*
	 * util para el espacio entre pasteles
	 */
	private int _contEspacio=0;
	
	
	/*
	 * pasteles que quedan en el deposito
	 */
	private int _pastelesRestantes;
	
	private Configuracion configuracion=Configuracion.getInstance();
	private Contexto contexto=Contexto.getInstance();
	
	private static Estado INSTANCE = null;

	private EstateThreads _estadoHilo;
	/*
	 * 
	 */
	private DispensadoraActivada() {
		_pastelesRestantes=configuracion.getCapacidadPasteles();
		set_estadoHilo(EstateThreads.CREADO);
	}

	@Override
	public void run(){
		set_estadoHilo(EstateThreads.EJECUTANDO);
		
		while(_estadoHilo.equals(EstateThreads.EJECUTANDO)){
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
			 * si en la posicion donde esta la dispensadora no hay bizcoho
			 * &&
			 * el espacio que hay entre los bizchocos es igual o superior al que yo he dejado
			 */
			if(!contexto.getPosicionCinta(configuracion.getPosBizc()) && get_contEspacio()>=configuracion.getEspEntreBizc()){
				/*
				 * se pone un bizcocho, se cambia el estado actual y se inicializa el contador de espacio
				 * se pone la posicion de la cinta inicial como ocupada
				 */
				reset_contEspacio();
				_pastelesRestantes--;
				contexto.setPosicionCinta(configuracion.getPosBizc(), true);
				contexto.incrementarNumPasteles();
				set_estadoHilo(EstateThreads.ACABADO);
			}else{
				// aqui no tendria q entrar nunca
				System.err.println("Error en la ejecucion del hilo de DsipensadoraActivada");
			}
		}
	}
	
	/*
	 *  - METODO OBSELETO -
	 */
	public void transitar() {
		/*
		 * debe comprobar que debajo de la dispensadora no hay pastel, se comprueba que entre 
		 * el œltimo pastel y la posicion actual hay un espacio como minimo el solicitado  
		 */
		try {
			// se queda esperando a la se–al de reloj
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(_contEspacio>=configuracion.getEspEntreBizc()){
			/*
			 * se pone un bizcocho, se cambia el estado actual y se inicializa el contador de espacio
			 * se pone la posicion de la cinta inicial como ocupada
			 */
			_contEspacio=0;
			_pastelesRestantes--;
			contexto.setPosicionCinta(configuracion.getPosBizc(), true);
		}
		
		/*
		 * 
		 */
		//return MoverCinta.getInstance();
	}
	
	private synchronized static void createInstance() {
		if (INSTANCE == null) { 
			INSTANCE = new DispensadoraActivada();
		}
	}

	public static Estado getInstance() {
		if (INSTANCE == null) createInstance();
		return INSTANCE;
	}

	public void enviaMensaje() {
		// TODO Auto-generated method stub
		
	}

	public void cambiaMensaje(boolean[] msg) {
		// TODO Auto-generated method stub
		
	}
	
	public synchronized void incrementar_ContEspacio(){
		_contEspacio++;
	}
	
	private synchronized void reset_contEspacio(){
		_contEspacio=0;
	}
	private synchronized int get_contEspacio(){
		return _contEspacio;
	}

	public synchronized EstateThreads get_estadoHilo() {
		return _estadoHilo;
	}
	
	private synchronized void set_estadoHilo(EstateThreads estate) {
		this._estadoHilo=estate;
	}
	
}