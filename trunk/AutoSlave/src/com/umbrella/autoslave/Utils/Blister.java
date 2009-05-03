package com.umbrella.autoslave.Utils;

public class Blister {
	/*
	 * posicion del pastel medida en CM
	 */
	private double _posicion;
	private int _contPasteles;
	// true en el automata 2, false en el 1
	private boolean _cinta;
	
	
	public Blister(double posicion){
		set_posicion(posicion);
		set_contPasteles(0);
		set_cinta(true);
	}
	
	public Blister(){
		set_posicion(0);
		set_contPasteles(0);
		set_cinta(true);
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
}
