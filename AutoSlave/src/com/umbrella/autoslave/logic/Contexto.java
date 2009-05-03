package com.umbrella.autoslave.logic;

/*
 * Author: pablo José Izquierdo Escudero
 * Fecha: 19/04/2009
 * 
 * Objetivo: contiene la informacion del estado del sistema, es toda informacion dinamica y que cambia o puede cambiar en tiempo de ejecucion
 */

import java.util.LinkedList;

import com.umbrella.autoslave.Utils.Blister;
import com.umbrella.autoslave.Utils.Pastel;
import com.umbrella.autoslave.executor.Estado;

public class Contexto {

	private Estado estado;
	
	private Configuracion conf= Configuracion.getInstance();
	
	/*
	 * tipo=pastel o blister
	 */
	private String tipo;
	
	private LinkedList<Pastel> _listaPasteles;
	private LinkedList<Blister> _listaBlister;
	
	boolean apagado=false;
	
	/*
	 * Fin de la cinta indica si la cinta esta libre o vacia
	 * True = libre / false = ocupada
	 */
	private boolean finCinta;
	
	/*
	 * indica los sensores que estan activos, o los dispositivos activados, es el estado interno del automata 
	 */
	private boolean [] dispositivosInternos= new boolean[16];

	Configuracion configuracion=Configuracion.getInstance();
	
	private static Contexto INSTANCE = null;
	
	
	/*
	 * numero de pasteles en la cinta, no sirve para nada tecnicamente solo da informacion
	 */
	private int numPasteles;
	
	// Private constructor suppresses 
    private Contexto(String tipo) {
    	
    	for(int i=0;i<dispositivosInternos.length;i++){
    		dispositivosInternos[i]=false;
    	}
    	this.tipo=tipo;
    	if(tipo.equalsIgnoreCase("pastel"))
    		_listaPasteles=new LinkedList<Pastel>();
    	else if(tipo.equalsIgnoreCase("blister"))
    		_listaBlister=new LinkedList<Blister>();
    	else
    		System.err.println("ese valor no es valido, solo se admite 'pastel' o 'blister'.");
    }
 
    /*
     *  creador sincronizado para protegerse de posibles problemas  multi-hilo
     *  otra prueba para evitar instanciación múltiple
     */ 
    private synchronized static void createInstance(String tipo) {
        if (INSTANCE == null) { 
            INSTANCE = new Contexto(tipo);
        }
    }
    
    public static Contexto getInstance(String tipo) {
        if (INSTANCE == null) createInstance(tipo);
        return INSTANCE;
    }
    
    public static Contexto getInstance() {
        return INSTANCE;
    }
    
 	public void setState( Estado state ){
 		this.estado = state;
 	}
 
 	public Estado getState(){
 		return estado;
 	}
 
 	/*
 	public void request(){
 		estado.transitar();
 		//estado=estado.transitar();
 	}
 	*/
 	
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

	/*
	 * devuelve el numero de pastel q activa el sensor q tiene la posicion pasada,
	 *  -1 si no hay coincidencia
	 */
	public synchronized int activaSensor(double posicion){
		//false = pasteles, true = blister
		int sal=-1;	
		if(tipo.equalsIgnoreCase("blister")){
			for(int i=0;i<_listaBlister.size();i++){
				if(_listaBlister.get(i).get_posicion()<(posicion+configuracion.getErrorSensor()) || 
					_listaBlister.get(i).get_posicion()>(posicion-configuracion.getErrorSensor()) ) sal=i;
			}
		}else{
			for(int i=0;i<_listaPasteles.size();i++){
				if(_listaPasteles.get(i).get_posicion()<(posicion+configuracion.getErrorSensor()) || 
					_listaPasteles.get(i).get_posicion()>(posicion-configuracion.getErrorSensor()) ) sal=i;
			}
		}
		return sal;
	}
	public synchronized String getTipo() {
		return tipo;
	}

	public synchronized LinkedList<Pastel> get_listaPasteles() {
		return _listaPasteles;
	}

	public synchronized LinkedList<Blister> get_listaBlister() {
		return _listaBlister;
	}
}
