package com.umbrella.autoslave.logic;

import com.umbrella.autoslave.Utils.EstateRobots;
import com.umbrella.mail.Users.pablo.Downloads.modulocomunicacion.MailBox;

public class ContextoRobot {
	
	private static boolean FIN=false;
	
	private static EstateRobots estadoInterno;
	private static long tiempo=System.currentTimeMillis();
	
	private static long diffTiempo;
	private static boolean pastel=false;
	private static boolean valido=false;
	
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
	public static synchronized boolean isValido() {
		return valido;
	}
	public static synchronized void setValido(boolean valido) {
		ContextoRobot.valido = valido;
	}
}
