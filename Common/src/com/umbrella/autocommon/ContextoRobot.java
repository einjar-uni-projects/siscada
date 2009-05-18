package com.umbrella.autocommon;

import java.io.Serializable;

import com.umbrella.autoslave.utils2.EstateRobots;

public class ContextoRobot implements Serializable {
	
	private static boolean FIN=false;
	
	private static EstateRobots estadoInterno;
	private static long tiempo=System.currentTimeMillis();
	
	private static long diffTiempo;
	private static boolean pastel=false;
	private static boolean blister=false;
	private static boolean pastelListo=false;
	private static boolean blisterListo=false;
	private static boolean blisterCompletoListo=false;
	private static boolean valido=false;
	
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
	
	public static synchronized boolean isFIN() {
		return FIN;
	}
	public static synchronized void setFIN(boolean fin) {
		FIN = fin;
	}
	public static synchronized EstateRobots getEstadoInterno() {
		return estadoInterno;
	}
	public static synchronized void setEstadoInterno(EstateRobots estadoInterno) {
		ContextoRobot.estadoInterno = estadoInterno;
	}
	public static synchronized long getTiempo() {
		return tiempo;
	}
	public static synchronized void setTiempo(long tiempo) {
		ContextoRobot.tiempo = tiempo;
	}
	public static synchronized long getDiffTiempo() {
		return diffTiempo;
	}
	public static synchronized void setDiffTiempo(long diffTiempo) {
		ContextoRobot.diffTiempo = diffTiempo;
	}
	
	public static synchronized boolean isPastel() {
		return pastel;
	}
	public static synchronized void setPastel(boolean pastel) {
		ContextoRobot.pastel = pastel;
	}
	
	public static synchronized boolean isBlister() {
		return blister;
	}

	public static synchronized void setBlister(boolean blister) {
		ContextoRobot.blister = blister;
	}

	
	public static synchronized boolean isBlisterCompletoListo() {
		return blisterCompletoListo;
	}

	public static synchronized void setBlisterCompletoListo(
			boolean blisterCompletoListo) {
		ContextoRobot.blisterCompletoListo = blisterCompletoListo;
	}

	public static synchronized boolean isPastelListo() {
		return pastelListo;
	}

	public static synchronized void setPastelListo(boolean pastelListo) {
		ContextoRobot.pastelListo = pastelListo;
	}

	public static synchronized boolean isBlisterListo() {
		return blisterListo;
	}

	public static synchronized void setBlisterListo(boolean blisterListo) {
		ContextoRobot.blisterListo = blisterListo;
	}

	public static synchronized boolean isValido() {
		return valido;
	}
	public static synchronized void setValido(boolean valido) {
		ContextoRobot.valido = valido;
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
