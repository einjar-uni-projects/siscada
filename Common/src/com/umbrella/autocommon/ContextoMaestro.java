package com.umbrella.autocommon;

import java.io.Serializable;

public class ContextoMaestro implements Serializable{

	/*
	 * tiene informacion de los automatas q estan ejecutandose
	 * aut 1
	 * aut 2
	 * aut 3
	 * robot 1
	 * robot 2
	 */
	private boolean[] ejecutando;
	
	private int contadorPastelesDispensadora=50; 

	private int bienProducidos=0;
	private int malProducidos=0;
	private int bienProducidosTotal=0;
	private int malProducidosTotal=0;
	
	/*
	 * cuenta los fallos en un sensor
	 */
	private int[] contadorCalidadSensor;
	
	
	private int contadorPasteles=0;
	private boolean blisterColocado=false;
	
	private boolean _FIN=false;
	
	private static ContextoMaestro INSTANCE=null;
	
	private ContextoMaestro(){
		ejecutando = new boolean[5];
		for(int i=5;i<ejecutando.length;i++) ejecutando[i]=false;
		contadorCalidadSensor=new int[4];
		for(int i=0;i<contadorCalidadSensor.length;i++)
			contadorCalidadSensor[i]=0;
	}
	
	public synchronized static ContextoMaestro getInstance(){
		if(INSTANCE==null) INSTANCE=new ContextoMaestro();
		return INSTANCE;
	}
	
	public void incrementarProducicos(boolean bien){
		if(bien) {
			bienProducidos++;
			bienProducidosTotal++;
		}else{
			malProducidos++;
			malProducidosTotal++;
		}
	}
	
	/*
	 * se reinicia el contador desde la ultima parada
	 */
	public void resetEjecucion(){
		bienProducidos=0;
		malProducidos=0;
	}
	
	public void incrementarContadorCalidadSensor(int pos){
		contadorCalidadSensor[pos]++;
	}
	
	public void resetContadorCalidadSensor(int pos){
		for(int i=0;i<contadorCalidadSensor.length;i++)
			contadorCalidadSensor[i]=0;
	}

	public boolean[] getEjecutando() {
		return ejecutando;
	}

	public int getContadorPastelesDispensadora() {
		return contadorPastelesDispensadora;
	}

	public int getBienProducidos() {
		return bienProducidos;
	}

	public int getMalProducidos() {
		return malProducidos;
	}

	public int getBienProducidosTotal() {
		return bienProducidosTotal;
	}

	public int getMalProducidosTotal() {
		return malProducidosTotal;
	}

	public int[] getContadorCalidadSensor() {
		return contadorCalidadSensor;
	}

	public int getContador() {
		return contadorPasteles;
	}

	public void incrementarContador() {
		contadorPasteles++;
		contadorPasteles=contadorPasteles%5; // xq van del 0 al 4 ambos inlcuidos
	}
	
	public ContextoMaestro getINSTANCE() {
		return INSTANCE;
	}

	public boolean is_FIN() {
		return _FIN;
	}

	public void set_FIN(boolean _fin) {
		_FIN = _fin;
	}

	public boolean isBlisterColocado() {
		return blisterColocado;
	}

	public void setBlisterColocado(boolean blisterColocado) {
		this.blisterColocado = blisterColocado;
	}
	
	public void cambiarBlisterColocado(){
		if(blisterColocado) blisterColocado=false;
		else blisterColocado=true;
	}
}
