package com.umbrella.autocommon;

import java.io.Serializable;

import com.umbrella.utils.EstateRobots;


public class ContextoRobot implements Serializable {
	
	private boolean FIN=false;
	
	private EstateRobots estadoInterno;
	private long tiempo=System.currentTimeMillis();
	
	private long diffTiempo;
	private boolean pastel=false;
	// private static boolean blister=false;
	private boolean pastelListo=false;
	private boolean blisterListo=false;
	private boolean blisterCompletoListo=false;
	private boolean valido=false;
	
	private boolean apagado=true;
	private boolean paradaCorrecta=false; //sirve para hacer bien la parada
	private boolean fallo=false;
	
	private static ContextoRobot Instancia = null;
	
	private ContextoRobot(){
		
	}
	
	public static synchronized ContextoRobot getInstance() {
		if(Instancia==null) Instancia=new ContextoRobot();
		return Instancia;
	}
	
	public synchronized boolean isFIN() {
		return FIN;
	}
	public synchronized void setFIN(boolean fin) {
		FIN = fin;
	}
	public synchronized EstateRobots getEstadoInterno() {
		return estadoInterno;
	}
	
	public synchronized void setEstadoInterno(EstateRobots estadoInterno) {
		estadoInterno = estadoInterno;
	}
	public synchronized long getTiempo() {
		return tiempo;
	}
	public synchronized void setTiempo(long tiempo) {
		tiempo = tiempo;
	}
	public synchronized long getDiffTiempo() {
		return diffTiempo;
	}
	public  synchronized void setDiffTiempo(long diffTiempo) {
		diffTiempo = diffTiempo;
	}
	
	public synchronized boolean isPastel() {
		return pastel;
	}
	public synchronized void setPastel(boolean pastel) {
		pastel = pastel;
	}
/*	
	public static synchronized boolean isBlister() {
		return blister;
	}

	public static synchronized void setBlister(boolean blister) {
		ContextoRobot.blister = blister;
	}
*/
	
	public  synchronized boolean isBlisterCompletoListo() {
		return blisterCompletoListo;
	}

	public synchronized void setBlisterCompletoListo(
			boolean blisterCompletoListo) {
		blisterCompletoListo = blisterCompletoListo;
	}

	public synchronized boolean isPastelListo() {
		return pastelListo;
	}

	public synchronized void setPastelListo(boolean pastelListo) {
		pastelListo = pastelListo;
	}

	public synchronized boolean isBlisterListo() {
		return blisterListo;
	}

	public synchronized void setBlisterListo(boolean blisterListo) {
		blisterListo = blisterListo;
	}

	public  synchronized boolean isValido() {
		return valido;
	}
	public synchronized void setValido(boolean valido) {
		valido = valido;
	}

	public synchronized boolean isApagado() {
		return apagado;
	}

	public synchronized void setApagado(boolean apagado) {
		this.apagado = apagado;
	}

	public synchronized boolean isParadaCorrecta() {
		return paradaCorrecta;
	}

	public synchronized void setParadaCorrecta(boolean paradaCorrecta) {
		this.paradaCorrecta = paradaCorrecta;
	}

	public synchronized boolean isFallo() {
		return fallo;
	}

	public synchronized void setFallo(boolean fallo) {
		this.fallo = fallo;
	}
	
	public synchronized ContextoRobot reset(){
		Instancia=new ContextoRobot();
		return Instancia;
		
	}
}
