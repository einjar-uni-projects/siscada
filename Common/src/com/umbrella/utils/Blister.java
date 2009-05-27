package com.umbrella.utils;

import java.io.Serializable;

import com.umbrella.autocommon.Configuration;

public class Blister implements Serializable{
	/*
	 * posicion del pastel medida en CM
	 */
	private double _posicion;
	private int _contPasteles;
	// true en el automata 2, false en el 3
	private boolean _cinta=false;
	private boolean _cortado=false;
	private boolean _troquelado=false;
	private boolean _sellado=false;
	
	/*
	 * posicion 0.- paso el control
	 * posicion 1.- la pocision 1 es valida
	 * posicion 2.- la pocision 2 es valida
	 * posicion 3.- la pocision 3 es valida
	 * posicion 4.- la pocision 4 es valida
	 */
	private boolean[] calidad={false,false,false,false,false};
	
	Configuration configuracion=Configuration.getInstance();
	
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
	public synchronized boolean passTest() {
		return calidad[0];
	}

	public synchronized void setCalidad(int pos, boolean valor) {
		this.calidad[pos] = valor;
	}

	public synchronized boolean is_cortado() {
		return _cortado;
	}

	public synchronized void set_cortado(boolean _cortado) {
		this._cortado = _cortado;
	}

	public synchronized boolean is_troquelado() {
		return _troquelado;
	}

	public synchronized void set_troquelado(boolean _troquelado) {
		this._troquelado = _troquelado;
	}

	public synchronized boolean is_sellado() {
		return _sellado;
	}

	public synchronized void set_sellado(boolean _sellado) {
		this._sellado = _sellado;
	}

	public synchronized boolean is_cinta() {
		return _cinta;
	}
}
