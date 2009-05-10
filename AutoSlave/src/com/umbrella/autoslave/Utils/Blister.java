package com.umbrella.autoslave.Utils;

import com.umbrella.autoslave.logic.Configuracion;

public class Blister {
	/*
	 * posicion del pastel medida en CM
	 */
	private double _posicion;
	private int _contPasteles;
	// true en el automata 2, false en el 3
	private boolean _cinta;
	
	/*
	 * posicion 0.- paso el control
	 * posicion 1.- la pocision 1 es valida
	 * posicion 2.- la pocision 2 es valida
	 * posicion 3.- la pocision 3 es valida
	 * posicion 4.- la pocision 4 es valida
	 */
	private boolean[] calidad={false,false,false,false,false};
	
	Configuracion configuracion=Configuracion.getInstance();
	
	public Blister(double posicion){
		set_posicion(posicion);
		set_contPasteles(0);
		set_cinta(true);
	}
	
	public Blister(){
		set_posicion(configuracion.getSizeBlister()/2);
		set_contPasteles(0);
		set_cinta(true);
	}
	public Blister enCinta3(){
		Blister aux=new Blister();
		aux.set_posicion(configuracion.getSizeBlister()/2);
		aux.set_contPasteles(4);
		aux.set_cinta(false);
		return aux;
	}
	public Blister(double _posicion, int pasteles, boolean cinta) {
		set_posicion(_posicion);
		set_contPasteles(pasteles);
		set_cinta(cinta);
	}
	
	
	public synchronized void incrementarPosicion(double _posicion) {
		this._posicion += _posicion;
	}
	public synchronized double get_posicion() {
		return _posicion;
	}
	public synchronized int get_contPasteles() {
		return _contPasteles;
	}
	public synchronized boolean isCinta() {
		return _cinta;
	}
	public synchronized void set_posicion(double _posicion) {
		this._posicion = _posicion;
	}
	private synchronized void set_contPasteles(int pasteles) {
		_contPasteles = pasteles;
	}
	private synchronized void set_cinta(boolean cinta) {
		this._cinta = cinta;
	}

	public synchronized boolean[] getCalidad() {
		return calidad;
	}

	public synchronized void setCalidad(int pos, boolean valor) {
		this.calidad[pos] = valor;
	}
	
	
}
