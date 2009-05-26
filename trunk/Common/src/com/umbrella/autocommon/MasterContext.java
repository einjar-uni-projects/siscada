package com.umbrella.autocommon;

import java.io.Serializable;

public class MasterContext implements Serializable{

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
	
	private String _emptyMachine;
	
	private static MasterContext INSTANCE=null;
	
	private Context _contextoAut1;
	private Context _contextoAut2;
	private Context _contextoAut3;
	private ContextoRobot _contextoRobot1;
	private ContextoRobot _contextoRobot2;
	
	private MasterContext(){
		ejecutando = new boolean[5];
		for(int i=5;i<ejecutando.length;i++) ejecutando[i]=false;
		contadorCalidadSensor=new int[4];
		for(int i=0;i<contadorCalidadSensor.length;i++)
			contadorCalidadSensor[i]=0;
		
		_contextoAut1=Context.getInstance("pastel");
		_contextoAut2=Context.getInstance("blister");
		_contextoAut3=Context.getInstance("blister");
		_contextoRobot1=new ContextoRobot();
		_contextoRobot2=new ContextoRobot();
	}
	
	public synchronized static MasterContext getInstance(){
		if(INSTANCE==null) INSTANCE=new MasterContext();
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

	public synchronized int getContador() {
		return contadorPasteles;
	}

	public synchronized void resetContador() {
		contadorPasteles=0;
	}
	
	public synchronized void incrementarContador() {
		contadorPasteles++;
		contadorPasteles=contadorPasteles%5; // xq van del 0 al 4 ambos inlcuidos
	}
	
	public MasterContext getINSTANCE() {
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

	public synchronized String getEmptyMachine() {
		return _emptyMachine;
	}

	public synchronized void setEmptyMachine(String machine) {
		_emptyMachine = machine;
	}

	public synchronized Context get_contextoAut1() {
		return _contextoAut1;
	}

	public synchronized void set_contextoAut1(Context aut1) {
		_contextoAut1 = aut1;
	}

	public synchronized Context get_contextoAut2() {
		return _contextoAut2;
	}

	public synchronized void set_contextoAut2(Context aut2) {
		_contextoAut2 = aut2;
	}

	public synchronized Context get_contextoAut3() {
		return _contextoAut3;
	}

	public synchronized void set_contextoAut3(Context aut3) {
		_contextoAut3 = aut3;
	}

	public synchronized ContextoRobot get_contextoRobot1() {
		return _contextoRobot1;
	}

	public synchronized void set_contextoRobot1(ContextoRobot robot1) {
		_contextoRobot1 = robot1;
	}

	public synchronized ContextoRobot get_contextoRobot2() {
		return _contextoRobot2;
	}

	public synchronized void set_contextoRobot2(ContextoRobot robot2) {
		_contextoRobot2 = robot2;
	}
}
