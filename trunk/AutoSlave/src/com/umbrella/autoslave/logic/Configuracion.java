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
	private int _tiempoReloj=200;
	
	/*
	 * indica el tamanyo de los pasteles
	 */
	private double size=0.20;
	
	/*
	 * Tamaño de la cinta
	 */
	private double sizeCinta=10;
	
	/*
	 * Capacidad del deposito de pasteles
	 */
	private int capacidadPasteles=50;
	
	/*
	 * Velocidad de la cinta, medida en m/min
	 */
	private double velCinta=20;
	
	/*
	 * Tiempo de activacion de la valvula de chocolate
	 */
	private int valvChoc=3;
	
	/*
	 * Tiempo de activacion de la valvula de chocolate
	 */
	private int valvCaram=3;
	
	/*
	 * puntos de control de la cinta, se entiende como las posiciones en las que pueden estar los pasteles en la cinta
	 */
	private int pointsControl=(int)(sizeCinta/size);
	
	/*
	 * Es algo unico, no puede crearse dos veces	
	 */
	private static Configuracion INSTANCE = null;
	
	/*
	 * posicion donde se encuentra el sensor y el dispensador de chocolate
	 */
	private int posChoc=(int)(pointsControl/3);
	
	/*
	 * posicion donde se encuentra el sensor y el dispensador de caramelo
	 */
	private int posCaram=(int)(pointsControl*2/3);
	
	/*
	 * posicion donde se encuentra el dispensador de bizcochos
	 */
	private int posBizc=0;
	
	/*
	 * posicion donde se encuentra el fin de la cinta y se espera a que se recoja
	 */
	private int posFin=pointsControl-1;
	
	/*
	 * espacio entre dos bizcochos, es decir el espacio que hay en la cintra entre 2 biscochos
	 */
	private int espEntreBizc=0;
	
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

	public double getSize() {
		return size;
	}

	public double getSizeCinta() {
		return sizeCinta;
	}

	public int getCapacidadPasteles() {
		return capacidadPasteles;
	}

	public double getVelCinta() {
		return velCinta;
	}

	public int getValvChoc() {
		return valvChoc;
	}

	public int getValvCaram() {
		return valvCaram;
	}

	public int getPosChoc() {
		return posChoc;
	}

	public int getPosCaram() {
		return posCaram;
	}

	public int getPosBizc() {
		return posBizc;
	}

	public int getPosFin() {
		return posFin;
	}

	public int getEspEntreBizc() {
		return espEntreBizc;
	}
	
}