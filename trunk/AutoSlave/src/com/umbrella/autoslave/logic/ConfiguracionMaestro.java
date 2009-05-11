package com.umbrella.autoslave.logic;

/*
 * Author: pablo José Izquierdo Escudero
 * Fecha: 19/04/2009
 * 
 * Objetivo: contiene la informacion de configuracion del sistema, es toda informacion estatica y el nunico modo de cambiarla es parando
 * 			el automata y reiniciando los valores
 */

import java.io.Serializable;

import com.umbrella.autoslave.executor.Estado;
import com.umbrella.autoslave.utils2.NombreMaquinas;

/*
 * Importante!!!
 * 
 * a esta clase solo tiene acceso el AUTOMATA MAESTRO que podra cambiar cualquier cosa
 */
public class ConfiguracionMaestro implements Serializable {

	
	/*
	 * El tiempo de reloj realmente me lo tiene q dar el usuario, se tiene q cargar y estaria muy bien leerlo
	 * de algun sitio para todos los automatas
	 */
	private int _tiempoReloj=200;
	
	/*
	 * indica el error del sensor, en metros
	 */
	private double errorSensor=0.20;
	
	/*
	 * Tamaño de la cinta
	 */
	private double sizeCintaAut1=10;
	private double sizeCintaAut2=10;
	private double sizeCintaAut3=10;
	
	/*
	 * Capacidad del deposito de pasteles
	 */
	private int capacidadPasteles=50;
	
	/*
	 * Velocidad de la cinta, medida en m/min
	 */
	private double velCintaAut1=20;
	private double velCintaAut2=20;
	private double velCintaAut3=10;
	
	/*
	 * Tiempo de activacion de la valvula de chocolate, en segundos
	 */
	private int valvChoc=3;
	
	/*
	 * Tiempo de activacion de la valvula de chocolate, en segundos
	 */
	private int valvCaram=2;
	
	/*
	 * Tiempo de activacion de la selladora, en segundos
	 */
	private int selladora=5;
	
	/*
	 * Tiempo que tarda el robot 1 en recoger y colocar un bliser, en segundos
	 */
	private int moverBlister=5;
	
	/*
	 * Tiempo que tarda el robot 1 en recoger y colocar un pastel, en segundos
	 */
	private int moverPastel=5;
	
	/*
	 * Tiempo que tarda el robot 2 en recoger almacenar un bliser, en segundos
	 */
	private int almacenarBlister=5;
	
	/*
	 * Interferencia de los robots en el movimiento de la cinta
	 */
	private int interferencia=2;
	
	/*
	 * indica el tamaño del bizcocho y blister, en metros, solo se refiere a la longitud
	 */
	private double sizeBizcocho=0.10;
	private double sizeBlister=sizeBizcocho*2+0.10;
	
	/*
	 * Es algo unico, no puede crearse dos veces	
	 */
	private static ConfiguracionMaestro INSTANCE = null;
	
	/*
	 * posicion donde se encuentra el sensor y el dispensador de chocolate, medido en CM
	 */
	private double posChoc=(sizeCintaAut1/3);
	
	/*
	 * posicion donde se encuentra el sensor y el dispensador de caramelo, medido en CM
	 */
	private double posCaram=(sizeCintaAut1*2/3);
	
	/*
	 * posicion donde se encuentra el dispensador de bizcochos, medido en CM
	 */
	private double posBizc=sizeBizcocho/2;
	
	private double posTroqueladora=sizeCintaAut2*1/3;
	private double posCortadora=posTroqueladora+sizeBlister;
	
	
	private double posInicioAut3=sizeBlister/2;
	private double posCalidad=sizeCintaAut3*1/3;
	private double posSelladora=sizeCintaAut3*2/3;
	
	/*
	 * posicion donde se encuentra el fin de la cinta y se espera a que se recoja, medido en CM
	 */
	//private int posFin=pointsControl-1;
	private double posFinAut1=sizeCintaAut1-sizeBizcocho/2;
	private double posFinAut2=sizeCintaAut2-sizeBlister/2;
	private double posFinAut3=sizeCintaAut3-sizeBlister/2;
	
	/*
	 * espacio entre dos bizcochos, es decir el espacio que hay en la cintra entre 2 biscochos, en metros
	 */
	private double espEntreBizc=sizeBizcocho+0.2;
	private double espEntreBlister=sizeBlister+0.2;
	
	/*
	 * posiciones asociadas al estado interno
	 */
	private int posicionAsociadaSensorFinAut1=0;
	private int posicionAsociadaSensorCaramelo=1;
	private int posicionAsociadaSensorChocolate=2;
	private int posicionAsociadaCaramelo=3;
	private int posicionAsociadaChocolate=4;
	private int posicionAsociadaDispensadora=5;
	private int posicionAsociadaCintaAut1=6;
	
	private int posicionAsociadaSensorFinAut2=0;
	private int posicionAsociadaSensorTroqueladora=1;
	private int posicionAsociadaSensorCortadora=2;
	private int posicionAsociadaTroqueladora=3;
	private int posicionAsociadaCortadora=4;
	private int posicionAsociadaCintaAut2=5;
	
	private int posicionAsociadaSensorFinAut3=0;
	private int posicionAsociadaSensorSelladora=1;
	private int posicionAsociadaSensorCalidad=2;
	private int posicionAsociadaSensorInicioCinta=3;
	private int posicionAsociadaSelladora=4;
	private int posicionAsociadaCalidad=5;
	private int posicionAsociadaCintaAut3=6;
	
    /*
     *  creador sincronizado para protegerse de posibles problemas  multi-hilo
     *  otra prueba para evitar instanciación múltiple
     */ 
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new ConfiguracionMaestro();
        }
    }
    
    public static ConfiguracionMaestro getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }

	public int get_tiempoReloj() {
		return _tiempoReloj;
	}

	public synchronized double getSizeBizcocho() {
		return sizeBizcocho;
	}

	public double getErrorSensor() {
		return errorSensor;
	}

	public double getSizeCinta() {
		return sizeCintaAut1;
	}

	public int getCapacidadPasteles() {
		return capacidadPasteles;
	}

	public double getVelCinta() {
		return velCintaAut1;
	}

	public int getValvChoc() {
		return valvChoc;
	}

	public int getValvCaram() {
		return valvCaram;
	}

	public double getPosChoc() {
		return posChoc;
	}

	public double getPosCaram() {
		return posCaram;
	}

	public double getPosBizc() {
		return posBizc;
	}

	public double getEspEntreBizc() {
		return espEntreBizc;
	}
	
	public double getSizeBlister() {
		return sizeBlister;
	}

	public synchronized double getSizeCintaAut1() {
		return sizeCintaAut1;
	}

	public synchronized double getSizeCintaAut2() {
		return sizeCintaAut2;
	}

	public synchronized double getSizeCintaAut3() {
		return sizeCintaAut3;
	}

	public synchronized double getVelCintaAut1() {
		return velCintaAut1;
	}

	public synchronized double getVelCintaAut2() {
		return velCintaAut2;
	}

	public synchronized double getVelCintaAut3() {
		return velCintaAut3;
	}

	public synchronized int getSelladora() {
		return selladora;
	}

	public synchronized int getMoverBlister() {
		return moverBlister;
	}

	public synchronized int getMoverPastel() {
		return moverPastel;
	}

	public double getPosInicioAut3() {
		return posInicioAut3;
	}

	public double getPosCalidad() {
		return posCalidad;
	}

	public double getPosCortadora() {
		return posCortadora;
	}

	public double getPosTroqueladora() {
		return posTroqueladora;
	}

	public double getPosSelladora() {
		return posSelladora;
	}

	public double getPosFinAut1() {
		return posFinAut1;
	}

	public double getPosFinAut2() {
		return posFinAut2;
	}

	public double getPosFinAut3() {
		return posFinAut3;
	}
	
	public double getEspEntreBlister() {
		return espEntreBlister;
	}

	
	public int getPosicionAsociada(NombreMaquinas nombre){
		int sal=-1;
		if(nombre.equals(NombreMaquinas.FIN_1))
			sal=posicionAsociadaSensorFinAut1;
		else if(nombre.equals(NombreMaquinas.SENSOR_CARAMELO))
			sal=posicionAsociadaSensorCaramelo;
		else if(nombre.equals(NombreMaquinas.SENSOR_CHOCOLATE))
			sal=posicionAsociadaSensorChocolate;
		else if(nombre.equals(NombreMaquinas.CARAMELO))
			sal=posicionAsociadaCaramelo;
		else if(nombre.equals(NombreMaquinas.CHOCOLATE))
			sal=posicionAsociadaChocolate;
		else if(nombre.equals(NombreMaquinas.DISPENSADORA))
			sal=posicionAsociadaDispensadora;
		else if(nombre.equals(NombreMaquinas.CINTA_1))
			sal=posicionAsociadaCintaAut1;
		
		if(nombre.equals(NombreMaquinas.FIN_2))
			sal=posicionAsociadaSensorFinAut2;
		else if(nombre.equals(NombreMaquinas.SENSOR_TROQUELADORA))
			sal=posicionAsociadaSensorTroqueladora;
		else if(nombre.equals(NombreMaquinas.SENSOR_CORTADORA))
			sal=posicionAsociadaSensorCortadora;
		else if(nombre.equals(NombreMaquinas.TROQUELADORA))
			sal=posicionAsociadaTroqueladora;
		else if(nombre.equals(NombreMaquinas.CORTADORA))
			sal=posicionAsociadaCortadora;
		else if(nombre.equals(NombreMaquinas.CINTA_2))
			sal=posicionAsociadaCintaAut2;
		
		if(nombre.equals(NombreMaquinas.FIN_3))
			sal=posicionAsociadaSensorFinAut3;
		else if(nombre.equals(NombreMaquinas.SENSOR_SELLADORA))
			sal=posicionAsociadaSensorSelladora;
		else if(nombre.equals(NombreMaquinas.SENSOR_CALIDAD))
			sal=posicionAsociadaSensorCalidad;
		else if(nombre.equals(NombreMaquinas.INICIO))
			sal=posicionAsociadaSensorInicioCinta;
		else if(nombre.equals(NombreMaquinas.SELLADO))
			sal=posicionAsociadaSelladora;
		else if(nombre.equals(NombreMaquinas.CONTROL_CALIDAD))
			sal=posicionAsociadaCalidad;
		else if(nombre.equals(NombreMaquinas.CINTA_3))
			sal=posicionAsociadaCintaAut3;
		
		return sal;
		
	}

	public synchronized int getInterferencia() {
		return interferencia;
	}

	public synchronized int getAlmacenarBlister() {
		return almacenarBlister;
	}

	public synchronized void setAlmacenarBlister(int almacenarBlister) {
		this.almacenarBlister = almacenarBlister;
	}

	public static synchronized ConfiguracionMaestro getINSTANCE() {
		return INSTANCE;
	}

	public static synchronized void setINSTANCE(ConfiguracionMaestro instance) {
		INSTANCE = instance;
	}

	public synchronized int getPosicionAsociadaSensorFinAut1() {
		return posicionAsociadaSensorFinAut1;
	}

	public synchronized void setPosicionAsociadaSensorFinAut1(
			int posicionAsociadaSensorFinAut1) {
		this.posicionAsociadaSensorFinAut1 = posicionAsociadaSensorFinAut1;
	}

	public synchronized int getPosicionAsociadaSensorCaramelo() {
		return posicionAsociadaSensorCaramelo;
	}

	public synchronized void setPosicionAsociadaSensorCaramelo(
			int posicionAsociadaSensorCaramelo) {
		this.posicionAsociadaSensorCaramelo = posicionAsociadaSensorCaramelo;
	}

	public synchronized int getPosicionAsociadaSensorChocolate() {
		return posicionAsociadaSensorChocolate;
	}

	public synchronized void setPosicionAsociadaSensorChocolate(
			int posicionAsociadaSensorChocolate) {
		this.posicionAsociadaSensorChocolate = posicionAsociadaSensorChocolate;
	}

	public synchronized int getPosicionAsociadaCaramelo() {
		return posicionAsociadaCaramelo;
	}

	public synchronized void setPosicionAsociadaCaramelo(
			int posicionAsociadaCaramelo) {
		this.posicionAsociadaCaramelo = posicionAsociadaCaramelo;
	}

	public synchronized int getPosicionAsociadaChocolate() {
		return posicionAsociadaChocolate;
	}

	public synchronized void setPosicionAsociadaChocolate(
			int posicionAsociadaChocolate) {
		this.posicionAsociadaChocolate = posicionAsociadaChocolate;
	}

	public synchronized int getPosicionAsociadaDispensadora() {
		return posicionAsociadaDispensadora;
	}

	public synchronized void setPosicionAsociadaDispensadora(
			int posicionAsociadaDispensadora) {
		this.posicionAsociadaDispensadora = posicionAsociadaDispensadora;
	}

	public synchronized int getPosicionAsociadaCintaAut1() {
		return posicionAsociadaCintaAut1;
	}

	public synchronized void setPosicionAsociadaCintaAut1(
			int posicionAsociadaCintaAut1) {
		this.posicionAsociadaCintaAut1 = posicionAsociadaCintaAut1;
	}

	public synchronized int getPosicionAsociadaSensorFinAut2() {
		return posicionAsociadaSensorFinAut2;
	}

	public synchronized void setPosicionAsociadaSensorFinAut2(
			int posicionAsociadaSensorFinAut2) {
		this.posicionAsociadaSensorFinAut2 = posicionAsociadaSensorFinAut2;
	}

	public synchronized int getPosicionAsociadaSensorTroqueladora() {
		return posicionAsociadaSensorTroqueladora;
	}

	public synchronized void setPosicionAsociadaSensorTroqueladora(
			int posicionAsociadaSensorTroqueladora) {
		this.posicionAsociadaSensorTroqueladora = posicionAsociadaSensorTroqueladora;
	}

	public synchronized int getPosicionAsociadaSensorCortadora() {
		return posicionAsociadaSensorCortadora;
	}

	public synchronized void setPosicionAsociadaSensorCortadora(
			int posicionAsociadaSensorCortadora) {
		this.posicionAsociadaSensorCortadora = posicionAsociadaSensorCortadora;
	}

	public synchronized int getPosicionAsociadaTroqueladora() {
		return posicionAsociadaTroqueladora;
	}

	public synchronized void setPosicionAsociadaTroqueladora(
			int posicionAsociadaTroqueladora) {
		this.posicionAsociadaTroqueladora = posicionAsociadaTroqueladora;
	}

	public synchronized int getPosicionAsociadaCortadora() {
		return posicionAsociadaCortadora;
	}

	public synchronized void setPosicionAsociadaCortadora(
			int posicionAsociadaCortadora) {
		this.posicionAsociadaCortadora = posicionAsociadaCortadora;
	}

	public synchronized int getPosicionAsociadaCintaAut2() {
		return posicionAsociadaCintaAut2;
	}

	public synchronized void setPosicionAsociadaCintaAut2(
			int posicionAsociadaCintaAut2) {
		this.posicionAsociadaCintaAut2 = posicionAsociadaCintaAut2;
	}

	public synchronized int getPosicionAsociadaSensorFinAut3() {
		return posicionAsociadaSensorFinAut3;
	}

	public synchronized void setPosicionAsociadaSensorFinAut3(
			int posicionAsociadaSensorFinAut3) {
		this.posicionAsociadaSensorFinAut3 = posicionAsociadaSensorFinAut3;
	}

	public synchronized int getPosicionAsociadaSensorSelladora() {
		return posicionAsociadaSensorSelladora;
	}

	public synchronized void setPosicionAsociadaSensorSelladora(
			int posicionAsociadaSensorSelladora) {
		this.posicionAsociadaSensorSelladora = posicionAsociadaSensorSelladora;
	}

	public synchronized int getPosicionAsociadaSensorCalidad() {
		return posicionAsociadaSensorCalidad;
	}

	public synchronized void setPosicionAsociadaSensorCalidad(
			int posicionAsociadaSensorCalidad) {
		this.posicionAsociadaSensorCalidad = posicionAsociadaSensorCalidad;
	}

	public synchronized int getPosicionAsociadaSensorInicioCinta() {
		return posicionAsociadaSensorInicioCinta;
	}

	public synchronized void setPosicionAsociadaSensorInicioCinta(
			int posicionAsociadaSensorInicioCinta) {
		this.posicionAsociadaSensorInicioCinta = posicionAsociadaSensorInicioCinta;
	}

	public synchronized int getPosicionAsociadaSelladora() {
		return posicionAsociadaSelladora;
	}

	public synchronized void setPosicionAsociadaSelladora(
			int posicionAsociadaSelladora) {
		this.posicionAsociadaSelladora = posicionAsociadaSelladora;
	}

	public synchronized int getPosicionAsociadaCalidad() {
		return posicionAsociadaCalidad;
	}

	public synchronized void setPosicionAsociadaCalidad(int posicionAsociadaCalidad) {
		this.posicionAsociadaCalidad = posicionAsociadaCalidad;
	}

	public synchronized int getPosicionAsociadaCintaAut3() {
		return posicionAsociadaCintaAut3;
	}

	public synchronized void setPosicionAsociadaCintaAut3(
			int posicionAsociadaCintaAut3) {
		this.posicionAsociadaCintaAut3 = posicionAsociadaCintaAut3;
	}

	public synchronized void set_tiempoReloj(int reloj) {
		_tiempoReloj = reloj;
	}

	public synchronized void setErrorSensor(double errorSensor) {
		this.errorSensor = errorSensor;
	}

	public synchronized void setSizeCintaAut1(double sizeCintaAut1) {
		this.sizeCintaAut1 = sizeCintaAut1;
	}

	public synchronized void setSizeCintaAut2(double sizeCintaAut2) {
		this.sizeCintaAut2 = sizeCintaAut2;
	}

	public synchronized void setSizeCintaAut3(double sizeCintaAut3) {
		this.sizeCintaAut3 = sizeCintaAut3;
	}

	public synchronized void setCapacidadPasteles(int capacidadPasteles) {
		this.capacidadPasteles = capacidadPasteles;
	}

	public synchronized void setVelCintaAut1(double velCintaAut1) {
		this.velCintaAut1 = velCintaAut1;
	}

	public synchronized void setVelCintaAut2(double velCintaAut2) {
		this.velCintaAut2 = velCintaAut2;
	}

	public synchronized void setVelCintaAut3(double velCintaAut3) {
		this.velCintaAut3 = velCintaAut3;
	}

	public synchronized void setValvChoc(int valvChoc) {
		this.valvChoc = valvChoc;
	}

	public synchronized void setValvCaram(int valvCaram) {
		this.valvCaram = valvCaram;
	}

	public synchronized void setSelladora(int selladora) {
		this.selladora = selladora;
	}

	public synchronized void setMoverBlister(int moverBlister) {
		this.moverBlister = moverBlister;
	}

	public synchronized void setMoverPastel(int moverPastel) {
		this.moverPastel = moverPastel;
	}

	public synchronized void setInterferencia(int interferencia) {
		this.interferencia = interferencia;
	}

	public synchronized void setSizeBizcocho(double sizeBizcocho) {
		this.sizeBizcocho = sizeBizcocho;
	}

	public synchronized void setSizeBlister(double sizeBlister) {
		this.sizeBlister = sizeBlister;
	}

	public synchronized void setPosChoc(double posChoc) {
		this.posChoc = posChoc;
	}

	public synchronized void setPosCaram(double posCaram) {
		this.posCaram = posCaram;
	}

	public synchronized void setPosBizc(double posBizc) {
		this.posBizc = posBizc;
	}

	public synchronized void setPosTroqueladora(double posTroqueladora) {
		this.posTroqueladora = posTroqueladora;
	}

	public synchronized void setPosCortadora(double posCortadora) {
		this.posCortadora = posCortadora;
	}

	public synchronized void setPosInicioAut3(double posInicioAut3) {
		this.posInicioAut3 = posInicioAut3;
	}

	public synchronized void setPosCalidad(double posCalidad) {
		this.posCalidad = posCalidad;
	}

	public synchronized void setPosSelladora(double posSelladora) {
		this.posSelladora = posSelladora;
	}

	public synchronized void setPosFinAut1(double posFinAut1) {
		this.posFinAut1 = posFinAut1;
	}

	public synchronized void setPosFinAut2(double posFinAut2) {
		this.posFinAut2 = posFinAut2;
	}

	public synchronized void setPosFinAut3(double posFinAut3) {
		this.posFinAut3 = posFinAut3;
	}

	public synchronized void setEspEntreBizc(double espEntreBizc) {
		this.espEntreBizc = espEntreBizc;
	}

	public synchronized void setEspEntreBlister(double espEntreBlister) {
		this.espEntreBlister = espEntreBlister;
	}
	
	
}