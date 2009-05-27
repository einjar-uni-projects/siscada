package com.umbrella.autocommon;

/*
 * Author: pablo Jos� Izquierdo Escudero
 * Fecha: 19/04/2009
 * 
 * Objetivo: contiene la informacion de configuracion del sistema, es toda informacion estatica y el nunico modo de cambiarla es parando
 * 			el automata y reiniciando los valores
 */


//import com.sun.tools.javac.tree.Tree.Synchronized;
import java.io.Serializable;

import com.umbrella.utils.MachineNames;

public class Configuration implements Serializable {

	
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
	 * Tama�o de la cinta
	 */
	private double sizeCintaAut1=10;
	private double sizeCintaAut2=10;
	private double sizeCintaAut3=10;
	
	/*
	 * Capacidad del deposito de pasteles
	 */
	private int capacidadPasteles=50;
	private int capacidadCaramelo=50;
	private int capacidadChocolate=50;
	private int umbralPasteles=10;
	private int umbralCaramelos=10;
	private int umbralChocolate=10;
	
	
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
	 * indica el tama�o del bizcocho y blister, en metros, solo se refiere a la longitud
	 */
	private double sizeBizcocho=0.10;
	private double sizeBlister=sizeBizcocho*2+0.10;
	
	/*
	 * Es algo unico, no puede crearse dos veces	
	 */
	private static Configuration INSTANCE = null;
	
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
	
	private double posTroqueladora=sizeBlister/2;
	private double posCortadora=posTroqueladora+sizeBlister;
	
	
	private double posInicioAut3=sizeBlister/2;
	private double posCalidad=sizeCintaAut3*1/3;
	private double posSelladora=posCalidad+sizeBlister;
	
	/*
	 * posicion donde se encuentra el fin de la cinta y se espera a que se recoja, medido en CM
	 */
	//private int posFin=pointsControl-1;
	private double posFinAut1=sizeCintaAut1-sizeBizcocho/2;
	private double posFinAut2=sizeCintaAut2-sizeBlister/2;
	private double posFinAut3=sizeCintaAut3-sizeBlister/2;
	
	
	private double porcentajeFallos=0.05;
	
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
	private int posicionAsociadaCalidadSensor1=7;
	private int posicionAsociadaCalidadSensor2=8;
	private int posicionAsociadaCalidadSensor3=9;
	private int posicionAsociadaCalidadSensor4=10;
	
	
    
	public Configuration(MasterConfiguration conf){
		this._tiempoReloj=conf.getClockTime();
		this.almacenarBlister=conf.getAlmacenarBlister();
		this.capacidadPasteles=conf.getCakeCapacity();
		this.capacidadCaramelo=conf.getCapacidadCaramelo();
		this.capacidadChocolate=conf.getCapacidadChocolate();
		this.errorSensor=conf.getErrorSensor();
		this.espEntreBizc=conf.getSpaceBetweenSpongeCakes();
		this.espEntreBlister=conf.getEspEntreBlister();
		this.interferencia=conf.getInterferencia();
		this.moverBlister=conf.getMoverBlister();
		this.moverPastel=conf.getMoverPastel();
		this.posBizc=conf.getPosSpongeCake();
		this.posCalidad=conf.getPosCalidad();
		this.posCaram=conf.getPosCaram();
		this.posChoc=conf.getPosChoc();
		this.posCortadora=conf.getPosCortadora();
		this.posFinAut1=conf.getPosFinAut1();
		this.posFinAut2=conf.getPosFinAut2();
		this.posFinAut3=conf.getPosFinAut3();
		this.posInicioAut3=conf.getPosInicioAut3();
		this.posSelladora=conf.getPosSelladora();
		this.posTroqueladora=conf.getPosTroqueladora();
		this.selladora=conf.getSelladora();
		this.sizeBizcocho=conf.getSpongeCakeSize();
		this.sizeBlister=conf.getSizeBlister();
		this.sizeCintaAut1=conf.getSizeCintaAut1();
		this.sizeCintaAut2=conf.getSizeCintaAut2();
		this.sizeCintaAut3=conf.getSizeCintaAut3();
		this.valvCaram=conf.getValvCaram();
		this.valvChoc=conf.getValvChoc();
		this.velCintaAut1=conf.getVelCintaAut1();
		this.velCintaAut2=conf.getVelCintaAut2();
		this.velCintaAut3=conf.getVelCintaAut3();
		this.porcentajeFallos=conf.getPorcentajeFallos();
	}
	
	private Configuration(){
		
	}
	/*
     *  creador sincronizado para protegerse de posibles problemas  multi-hilo
     *  otra prueba para evitar instanciaci�n m�ltiple
     */ 
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new Configuration();
        }
    }
    
    public static Configuration getInstance() {
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
	
	public int getCapacidadCaramelo() {
		return capacidadCaramelo;
	}
	
	public int getCapacidadChocolate() {
		return capacidadChocolate;
	}
	
	
	public synchronized void rellenarChocolate(int cantidad){
		
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

	
	public int getPosicionAsociada(MachineNames nombre){
		int sal=-1;
		if(nombre.equals(MachineNames.FIN_1))
			sal=posicionAsociadaSensorFinAut1;
		else if(nombre.equals(MachineNames.SENSOR_CARAMELO))
			sal=posicionAsociadaSensorCaramelo;
		else if(nombre.equals(MachineNames.SENSOR_CHOCOLATE))
			sal=posicionAsociadaSensorChocolate;
		else if(nombre.equals(MachineNames.CARAMELO))
			sal=posicionAsociadaCaramelo;
		else if(nombre.equals(MachineNames.CHOCOLATE))
			sal=posicionAsociadaChocolate;
		else if(nombre.equals(MachineNames.DISPENSADORA))
			sal=posicionAsociadaDispensadora;
		else if(nombre.equals(MachineNames.CINTA_1))
			sal=posicionAsociadaCintaAut1;
		
		if(nombre.equals(MachineNames.FIN_2))
			sal=posicionAsociadaSensorFinAut2;
		else if(nombre.equals(MachineNames.SENSOR_TROQUELADORA))
			sal=posicionAsociadaSensorTroqueladora;
		else if(nombre.equals(MachineNames.SENSOR_CORTADORA))
			sal=posicionAsociadaSensorCortadora;
		else if(nombre.equals(MachineNames.TROQUELADORA))
			sal=posicionAsociadaTroqueladora;
		else if(nombre.equals(MachineNames.CORTADORA))
			sal=posicionAsociadaCortadora;
		else if(nombre.equals(MachineNames.CINTA_2))
			sal=posicionAsociadaCintaAut2;
		
		if(nombre.equals(MachineNames.FIN_3))
			sal=posicionAsociadaSensorFinAut3;
		else if(nombre.equals(MachineNames.SENSOR_SELLADORA))
			sal=posicionAsociadaSensorSelladora;
		else if(nombre.equals(MachineNames.SENSOR_CALIDAD))
			sal=posicionAsociadaSensorCalidad;
		else if(nombre.equals(MachineNames.INICIO))
			sal=posicionAsociadaSensorInicioCinta;
		else if(nombre.equals(MachineNames.SELLADO))
			sal=posicionAsociadaSelladora;
		else if(nombre.equals(MachineNames.CONTROL_CALIDAD))
			sal=posicionAsociadaCalidad;
		else if(nombre.equals(MachineNames.CINTA_3))
			sal=posicionAsociadaCintaAut3;
		else if(nombre.equals(MachineNames.SENSOR_CALIDAD_SENSOR_1))
			sal=posicionAsociadaCalidadSensor1;
		else if(nombre.equals(MachineNames.SENSOR_CALIDAD_SENSOR_2))
			sal=posicionAsociadaCalidadSensor2;
		else if(nombre.equals(MachineNames.SENSOR_CALIDAD_SENSOR_3))
			sal=posicionAsociadaCalidadSensor3;
		else if(nombre.equals(MachineNames.SENSOR_CALIDAD_SENSOR_4))
			sal=posicionAsociadaCalidadSensor4;
		
		return sal;
		
	}

	public synchronized int getInterferencia() {
		return interferencia;
	}

	public synchronized double getPorcentajeFallos() {
		return porcentajeFallos;
	}

	public synchronized int getUmbralPasteles() {
		return umbralPasteles;
	}

	public synchronized int getUmbralCaramelos() {
		return umbralCaramelos;
	}

	public synchronized int getUmbralChocolate() {
		return umbralChocolate;
	}

	public boolean isCorrect() {
		// TODO Auto-generated method stub
		return true;
	}
	
}