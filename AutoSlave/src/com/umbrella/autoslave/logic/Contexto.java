package com.umbrella.autoslave.logic;

import com.umbrella.autoslave.executor.Estado;



public class Contexto {

	private Estado estado;
	
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
	
	private static Contexto INSTANCE = null;
	
	
	// Private constructor suppresses 
    private Contexto() {
    	
    	for(int i=0;i<dispositivosInternos.length;i++){
    		
    	}
    }
 
    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    // otra prueba para evitar instanciaci—n mœltiple 
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new Contexto();
        }
    }
 
    
    public static Contexto getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }
	
 	public void setState( Estado state )
 	{
 		this.estado = state;
 	}
 
 	public Estado getState()
 	{
 		return estado;
 	}
 
 	public void request()
 	{
 		estado=estado.transitar();
 	}
}
