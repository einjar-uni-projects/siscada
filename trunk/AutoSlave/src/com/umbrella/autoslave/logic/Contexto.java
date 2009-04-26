package com.umbrella.autoslave.logic;

/*
 * Author: pablo José Izquierdo Escudero
 * Fecha: 19/04/2009
 * 
 * Objetivo: contiene la informacion del estado del sistema, es toda informacion dinamica y que cambia o puede cambiar en tiempo de ejecucion
 */

import java.util.LinkedList;

import com.umbrella.autoslave.executor.Estado;

public class Contexto {

	private Estado estado;
	
	private Configuracion conf= Configuracion.getInstance();
	
	private LinkedList<Pastel> _pasteles;
	
	boolean apagado=false;
	
	/*
	 * Fin de la cinta indica si la cinta esta libre o vacia
	 * True = libre / false = ocupada
	 */
	private boolean finCinta;
	
	/*
	 * indica los sensores que estan activos, o los dispositivos activados 
	 */
	private boolean [] dispositivosInternos= new boolean[16];
	/*
	 * 0- sensor dispensadora Bizcocho
	 * 1- sensor dispensadora Chocolate
	 * 2- sensor dispensadroa caramelo
	 * 3- sensor fin cinta
	 * 4- dispensadora bizcochos activa
	 * 5- dispensadora chocolate activa
	 * 6- dispensadora caramelo activa
	 */
		

	Configuracion configuracion=Configuracion.getInstance();
	
	private static Contexto INSTANCE = null;
	
	
	/*
	 * numero de pasteles en la cinta, no sirve para nada tecnicamente solo da informacion
	 */
	private int numPasteles;
	
	// Private constructor suppresses 
    private Contexto() {
    	
    	for(int i=0;i<dispositivosInternos.length;i++){
    		dispositivosInternos[i]=false;
    	}
    	_pasteles=new LinkedList<Pastel>();
    }
 
    /*
     *  creador sincronizado para protegerse de posibles problemas  multi-hilo
     *  otra prueba para evitar instanciación múltiple
     */ 
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new Contexto();
        }
    }
    
    public static Contexto getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }
	
 	public void setState( Estado state ){
 		this.estado = state;
 	}
 
 	public Estado getState(){
 		return estado;
 	}
 
 	public void request(){
 		estado.transitar();
 		//estado=estado.transitar();
 	}
 	
 	public long getTiempoInterno(){
 		return conf.get_tiempoReloj();
 	}

	private synchronized int getNumPasteles() {
		return numPasteles;
	}

	private synchronized void setNumPasteles(int numPasteles) {
		this.numPasteles=numPasteles;
	}
	
	public synchronized void decrementarNumPasteles() {
		this.numPasteles--;
	}
 	
	public synchronized void incrementarNumPasteles() {
		this.numPasteles++;
	}
	
	public synchronized void setDispositivosInternos(int pos, boolean valor){
		dispositivosInternos[pos]=valor;
	}
	public synchronized boolean getDispositivosInternos(int pos){
		return dispositivosInternos[pos];
	}

	public synchronized LinkedList<Pastel> get_pasteles() {
		return _pasteles;
	}
	
	/*
	 * devuelve la posicion del pastel q activa el sensor
	 */
	public synchronized int activaSensorBizcocho(){
		int sal=-1;
		
		/*
		 * posicion donde esta el dispensador de bizcochos
		 */
		double posBizc=configuracion.getPosBizc();
		
		for(int i=0;i<_pasteles.size();i++){
			if(_pasteles.get(i).get_posicion()<(posBizc+configuracion.getErrorSensor()) || 
					_pasteles.get(i).get_posicion()>(posBizc-configuracion.getErrorSensor()) ) 
				if((_pasteles.get(i).get_posicion() - posBizc)>=configuracion.getEspEntreBizc()  )sal=i;
		}
		return sal;
	}
	
	/*
	 * devuelve la posicion del pastel q activa el sensor
	 */
	public synchronized int activaSensorCaramelo(){
		int sal=-1;
		
		/*
		 * posicion donde esta el dispensador de bizcochos
		 */
		double posCaramelo=configuracion.getPosCaram();
		
		for(int i=0;i<_pasteles.size();i++){
			if(_pasteles.get(i).get_posicion()<(posCaramelo+configuracion.getErrorSensor()) || 
					_pasteles.get(i).get_posicion()>(posCaramelo-configuracion.getErrorSensor()) ) sal=i;
		}
		return sal;
	}
	
	/*
	 * devuelve la posicion del pastel q activa el sensor
	 */
	public synchronized int activaSensorChocolate(){
		int sal=-1;
		
		/*
		 * posicion donde esta el dispensador de bizcochos
		 */
		double posChocolate=configuracion.getPosChoc();
		
		for(int i=0;i<_pasteles.size();i++){
			if(_pasteles.get(i).get_posicion()<(posChocolate+configuracion.getErrorSensor()) || 
					_pasteles.get(i).get_posicion()>(posChocolate-configuracion.getErrorSensor()) ) sal=i;
		}
		return sal;
	}
	
	/*
	 * devuelve la posicion del pastel q activa el sensor
	 */
	public synchronized int activaSensorFinal(){
		int sal=-1;
		
		/*
		 * posicion donde esta el dispensador de bizcochos
		 */
		double posFinal=configuracion.getPosFin();
		
		for(int i=0;i<_pasteles.size();i++){
			if(_pasteles.get(i).get_posicion()<(posFinal+configuracion.getErrorSensor()) || 
					_pasteles.get(i).get_posicion()>(posFinal-configuracion.getErrorSensor()) ) sal=i;
		}
		return sal;
	}
}
