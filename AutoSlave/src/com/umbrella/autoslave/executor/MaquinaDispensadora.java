package com.umbrella.autoslave.executor;

import com.umbrella.autoslave.logic.Contexto;
import com.umbrella.autoslave.logic.EstateThreads;

public class MaquinaDispensadora extends Thread{
	private double _tiempoEjecucion;
	private double _posicion;
	/*
	 * posicion del estado interno asociada
	 */
	private int _posicionAsociada;
	

	/*
	 * indica el estado interno del hilo
	 */
	private EstateThreads _estadoHilo;
	
	private Contexto contexto=Contexto.getInstance();
	
	public MaquinaDispensadora(double tiempoEjecucion, double posicion, int posAsociada) {
		this._tiempoEjecucion=tiempoEjecucion;
		this._posicion=posicion;
		set_estadoHilo(EstateThreads.CREADO);
		set_posicionAsociada(posAsociada);
	}

	@Override
	public void run(){
		set_estadoHilo(EstateThreads.EJECUTANDO);
		contexto.setDispositivosInternos(get_posicionAsociada(), true);
		double tiempoActual=System.currentTimeMillis(); //medido en milisegundos
		while(((System.currentTimeMillis()-tiempoActual)*1000)<this._tiempoEjecucion){
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
		contexto.setDispositivosInternos(get_posicionAsociada(), false);
		//se ha echado el caramelo en el bizcocho
		set_estadoHilo(EstateThreads.ACABADO);
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

	public synchronized double get_posicion() {
		return _posicion;
	}

	private synchronized int get_posicionAsociada() {
		return _posicionAsociada;
	}

	private synchronized void set_posicionAsociada(int asociada) {
		_posicionAsociada = asociada;
	}
	
}
