package com.umbrella.autoslave.logic;

/*
 * Author: pablo José Izquierdo Escudero
 * Fecha: 19/04/2009
 * 
 * Objetivo: contiene la informacion del estado del sistema, es toda informacion dinamica y que cambia o puede cambiar en tiempo de ejecucion
 */

import com.umbrella.autoslave.executor.Estado;

public class Contexto {

	private Estado estado;
	
	private Configuracion conf= Configuracion.getInstance();
	
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
	 * posiciones de la cinta validas, un 0 indican q la posicion esta libre y un 1 indica que el pastel ocupa la
	 * posicion marcada
	 */
	private boolean[] cinta=new boolean[conf.getPointsControl()];
	
	private static Contexto INSTANCE = null;
	
	// Private constructor suppresses 
    private Contexto() {
    	
    	for(int i=0;i<dispositivosInternos.length;i++){
    		dispositivosInternos[i]=false;
    	}
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
 		estado=estado.transitar();
 	}
 	
 	public long getTiempoInterno(){
 		return conf.get_tiempoReloj();
 	}
 	
 	/*
 	 * avanza la posicion calculada de los pasteles en la cinta
 	 * AUNQUE LOS SENSORES SE ACTIVAN CON EL CLICK DE RELOJ LA CINTA TIENE UN MOVIMIENTO CONSTANTE DIFERENCIADO DE ESTOS CLICKs
 	 */
 	public synchronized void avanzarCinta(){
 		for(int i=1;i<cinta.length;i++) cinta[i]=cinta[i-1];
 		cinta[0]=false;
 	}
 	
 	public synchronized void nuevoPastelCinta(){
 		cinta[0]=true;
 	}
 	
 	public synchronized boolean getPosicionCinta(int pos){
 		boolean hayAlgo=false;
 		if(pos>=0 && pos < cinta.length) hayAlgo=cinta[pos];
 		return hayAlgo;
 	}
 	
 	public synchronized void setPosicionCinta(int pos, boolean valor){
 		if(pos>=0 && pos < cinta.length) cinta[pos]=valor;
 	}
 	
}
