package com.umbrella.autoslave.executor;

import com.umbrella.autoslave.logic.EstateThreads;

public class MaquinaDispensadora extends Thread{
	private double _tiempoEjecucion;
	private double _posicion;
	
	

	/*
	 * indica el estado interno del hilo
	 */
	private EstateThreads _estadoHilo;
	
	public MaquinaDispensadora(double tiempoEjecucion, double posicion) {
		this._tiempoEjecucion=tiempoEjecucion;
		this._posicion=posicion;
		set_estadoHilo(EstateThreads.CREADO);
	}

	@Override
	public void run(){
		set_estadoHilo(EstateThreads.EJECUTANDO);
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
}
