package com.umbrella.autoslave.logic;

/*
 * Author: pablo José Izquierdo Escudero
 * Fecha: 19/04/2009
 * 
 * Objetivo: contiene la informacion de configuracion del sistema, es toda informacion estatica y el nunico modo de cambiarla es parando
 * 			el automata y reiniciando los valores
 */

import com.umbrella.autoslave.executor.Estado;

public class Configuracion {

	
	/*
	 * El tiempo de reloj realmente me lo tiene q dar el usuario, se tiene q cargar y estaria muy bien leerlo
	 * de algun sitio para todos los automatas
	 */
	private int _tiempoReloj=100;
	
	/*
	 * indica el tamanyo de los pasteles
	 */
	private double size=0.20;
	
	/*
	 * Tamaño de la cinta
	 */
	private double sizeCinta=3;
	
	/*
	 * puntos de control de la cinta, se entiende como las posiciones en las que pueden estar los pasteles en la cinta
	 */
	private int pointsControl=(int)(sizeCinta/size);
	
	/*
	 * Es algo unico, no puede crearse dos veces	
	 */
	private static Configuracion INSTANCE = null;
	
    /*
     *  creador sincronizado para protegerse de posibles problemas  multi-hilo
     *  otra prueba para evitar instanciación múltiple
     */ 
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new Configuracion();
        }
    }
    
    public static Configuracion getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }

	public int get_tiempoReloj() {
		return _tiempoReloj;
	}

	public int getPointsControl() {
		return pointsControl;
	}
    
    
}