package com.umbrella.autoslave.executor;

import com.umbrella.autocommon.Configuracion;
import com.umbrella.autocommon.Contexto;
import com.umbrella.autoslave.utils2.EstateThreads;
import com.umbrella.autoslave.utils2.Pastel;

public class DispensadoraActivada extends Thread{

	
	/*
	 * pasteles que quedan en el deposito
	 */
	private int _pastelesRestantes;
	
	private Configuracion configuracion=Configuracion.getInstance();
	private Contexto contexto=Contexto.getInstance();
	
	private static DispensadoraActivada INSTANCE = null;

	private double _posicion;
	private int _posicionAsociada;
	private EstateThreads _estadoHilo;
	/*
	 * 
	 */
	private DispensadoraActivada(double posicion, int posAsociada) {
		_pastelesRestantes=configuracion.getCapacidadPasteles();
		set_estadoHilo(EstateThreads.CREADO);
		this._posicion=posicion;
		set_posicionAsociada(posAsociada);
		contexto.setPastelesRestantes(_pastelesRestantes);
	}

	public synchronized double get_posicion() {
		return _posicion;
	}
	
	@Override
	public void run(){
		set_estadoHilo(EstateThreads.EJECUTANDO);
		
		while(!_estadoHilo.equals(EstateThreads.ACABADO)){
			if(_estadoHilo.equals(EstateThreads.ESPERANDO)) 
				if(_pastelesRestantes>0) set_estadoHilo(EstateThreads.EJECUTANDO);
			
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
				if(get_contEspacio()>=(configuracion.getEspEntreBizc()+configuracion.getSizeBizcocho())){
					contexto.setDispositivosInternos(get_posicionAsociada(), true);
					/*
					 * se pone un bizcocho, se cambia el estado actual y se inicializa el contador de espacio
					 * se pone la posicion de la cinta inicial como ocupada
					 */
					_pastelesRestantes--;
					contexto.incrementarNumPasteles();
					contexto.get_listaPasteles().add(new Pastel());
					contexto.setDispositivosInternos(get_posicionAsociada(), false);
					if(_pastelesRestantes==0){
						set_estadoHilo(EstateThreads.ESPERANDO);
					}
				}else{
					// aqui no tendria q entrar nunca
					System.err.println("Error en la ejecucion del hilo de DsipensadoraActivada");
				}
			} 
		}
		contexto.setPastelesRestantes(_pastelesRestantes);
		set_estadoHilo(EstateThreads.ACABADO);
	}
	
	/*
	 *  - METODO OBSELETO -
	 */
	public void transitar() {
	
	}
	
	private synchronized static void createInstance(double posicion, int posAsociada) {
		if (INSTANCE == null) { 
			INSTANCE = new DispensadoraActivada(posicion, posAsociada);
		}
	}

	public static DispensadoraActivada getInstance(double posicion, int posAsociada) {
		if (INSTANCE == null) createInstance(posicion, posAsociada);
		return INSTANCE;
	}

	public static DispensadoraActivada getInstance() {
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
		for(int i=0;i<contexto.get_listaPasteles().size();i++){
			double num=contexto.get_listaPasteles().get(i).get_posicion();
			if(num<min) min=num;
		}
		return min;
	}

	private synchronized int get_posicionAsociada() {
		return _posicionAsociada;
	}

	private synchronized void set_posicionAsociada(int asociada) {
		_posicionAsociada = asociada;
	}
	
	public synchronized void llenarDeposito(int valor){
		if(valor+_pastelesRestantes>50) _pastelesRestantes=50;
		else _pastelesRestantes+=valor;	
		contexto.setPastelesRestantes(_pastelesRestantes);
	}
	
	public synchronized int get_PastelesRestantes(){
		return _pastelesRestantes;
	}
}