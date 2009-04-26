package com.umbrella.autoslave.executor;

import com.umbrella.autoslave.logic.Configuracion;
import com.umbrella.autoslave.logic.Contexto;
import com.umbrella.autoslave.logic.EstateThreads;
import com.umbrella.autoslave.logic.Pastel;

public class DispensadoraActivada extends Thread implements Estado{

	
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
			 * si en la posicion donde esta la dispensadora no hay bizcocho
			 * &&
			 * el espacio que hay entre los bizchocos es igual o superior al que yo he dejado
			 */
			if(contexto.activaSensorBizcocho()>=0 && get_contEspacio()>=configuracion.getEspEntreBizc()){
				/*
				 * se pone un bizcocho, se cambia el estado actual y se inicializa el contador de espacio
				 * se pone la posicion de la cinta inicial como ocupada
				 */
				_pastelesRestantes--;
				contexto.incrementarNumPasteles();
				contexto.get_pasteles().add(new Pastel());
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
	
	public synchronized EstateThreads get_estadoHilo() {
		return _estadoHilo;
	}
	
	private synchronized void set_estadoHilo(EstateThreads estate) {
		this._estadoHilo=estate;
	}
	/*
	 * devuelve la distancia del ultimo pastel a la posicion del dispensador de bizcochos
	 */
	private synchronized double get_contEspacio(){
		double min=(configuracion.getSizeCinta()+1)*100;
		for(int i=0;i<contexto.get_pasteles().size();i++){
			double num=contexto.get_pasteles().get(i).get_posicion();
			if(num<min) min=num;
		}
		return min;
	}
}