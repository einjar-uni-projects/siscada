package com.umbrella.autoslave.executor;

import com.umbrella.autocommon.Configuracion;
import com.umbrella.autocommon.Contexto;
import com.umbrella.autoslave.utils2.EstateThreads;
import com.umbrella.autoslave.utils2.Pastel;

/*
 * Maquina que no tiene un tiempo de ejecucion 
 */
public class MaquinaInstantanea extends Thread{

	
	private Configuracion configuracion=Configuracion.getInstance();
	private Contexto contexto=Contexto.getInstance();
	
	private double _posicion;
	private int _posicionAsociada;
	private EstateThreads _estadoHilo;
	
	public MaquinaInstantanea(double posicion, int posAsociada) {
		set_estadoHilo(EstateThreads.CREADO);
		this._posicion=posicion;
		set_posicionAsociada(posAsociada);
	}

	public synchronized double get_posicion() {
		return _posicion;
	}
	
	@Override
	public void run(){
		set_estadoHilo(EstateThreads.EJECUTANDO);
		contexto.setDispositivosInternos(_posicionAsociada, true);
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// espera un ciclo de reloj para cambiar el estado de la maquina
		contexto.setDispositivosInternos(_posicionAsociada, false);
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
	private synchronized int get_posicionAsociada() {
		return _posicionAsociada;
	}
	private synchronized void set_posicionAsociada(int asociada) {
		_posicionAsociada = asociada;
	}
}