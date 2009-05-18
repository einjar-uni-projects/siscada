package com.umbrella.utils;

import java.io.Serializable;

import com.umbrella.autocommon.Configuracion;

public class Pastel implements Serializable {

	/*
	 * posicion del pastel medida en CM
	 */
	private double _posicion;
	private boolean _chocolate;
	private boolean _caramelo;
	
	private Configuracion configuracion=Configuracion.getInstance();
	
	public Pastel(){
		_posicion=configuracion.getPosBizc();
		_caramelo=false;
		_chocolate=false;
	}

	public synchronized double get_posicion() {
		return _posicion;
	}

	public synchronized void set_posicion(double _posicion) {
		this._posicion = _posicion;
	}
	public synchronized void incrementarPosicion(double _posicion) {
		this._posicion += _posicion;
	}

	public synchronized boolean is_chocolate() {
		return _chocolate;
	}

	public synchronized void set_chocolate() {
		this._chocolate = true;
	}

	public synchronized boolean is_caramelo() {
		return _caramelo;
	}

	public synchronized void set_caramelo() {
		this._caramelo = true;
	}
	
	
}
