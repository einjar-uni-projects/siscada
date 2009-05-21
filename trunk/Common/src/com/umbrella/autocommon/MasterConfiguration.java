package com.umbrella.autocommon;

/*
 * Author: pablo José Izquierdo Escudero
 * Fecha: 19/04/2009
 * 
 * Objetivo: contiene la informacion de configuracion del sistema, es toda informacion estatica y el nunico modo de cambiarla es parando
 * 			el automata y reiniciando los valores
 */

import java.io.Serializable;

import com.umbrella.utils.NombreMaquinas;



/*
 * Importante!!!
 * 
 * a esta clase solo tiene acceso el AUTOMATA MAESTRO que podra cambiar cualquier cosa
 */

public class MasterConfiguration implements Serializable {

	
	/*
	 * El tiempo de reloj realmente me lo tiene q dar el usuario, se tiene q cargar y estaria muy bien leerlo
	 * de algun sitio para todos los automatas
	 */
	private int _clockTime=200;
	
	/*
	 * indica el error del sensor, en metros
	 */
	private double _sensorError=0.20;
	
	/*
	 * Tamaño de la cinta
	 */
	private double _conveyorBeltSize1=10;
	private double _conveyorBeltSize2=10;
	private double _conveyorBeltSize3=10;
	
	/*
	 * Capacidad del deposito de pasteles
	 */
	private int _cakeCapacity=50;
	private int _caramelCapacity=50;
	private int _chocolateCapacity=50;
	
	/*
	 * Velocidad de la cinta, medida en m/min
	 */
	private double _conveyorBeltSpeed1=20;
	private double _conveyorBeltSpeed2=20;
	private double _conveyorBeltSpeed3=10;
	
	/*
	 * Tiempo de activacion de la valvula de chocolate, en segundos
	 */
	private int _chocolateValveTime=3;
	
	/*
	 * Tiempo de activacion de la valvula de chocolate, en segundos
	 */
	private int _caramelValveTime=2;
	
	/*
	 * Tiempo de activacion de la selladora, en segundos
	 */
	private int _sealingMachineTime=5;
	
	/*
	 * Tiempo que tarda el robot 1 en recoger y colocar un bliser, en segundos
	 */
	private int _moveBlisterTime=5;
	
	/*
	 * Tiempo que tarda el robot 1 en recoger y colocar un pastel, en segundos
	 */
	private int _moveCakeTime=5;
	
	/*
	 * Tiempo que tarda el robot 2 en recoger almacenar un bliser, en segundos
	 */
	private int _storeBlisterTime=5;
	
	/*
	 * Interferencia de los robots en el movimiento de la cinta
	 */
	private int _interference=2;
	
	/*
	 * indica el tamaño del bizcocho y blister, en metros, solo se refiere a la longitud
	 */
	private double _spongeCakeSize=0.10;
	private double _blisterSize=_spongeCakeSize*2+0.10;
	
	/*
	 * Es algo unico, no puede crearse dos veces	
	 */
	private static MasterConfiguration INSTANCE = null;
	
	/*
	 * posicion donde se encuentra el sensor y el dispensador de chocolate, medido en CM
	 */
	private double _chocolatePosition=(_conveyorBeltSize1/3);
	
	/*
	 * posicion donde se encuentra el sensor y el dispensador de caramelo, medido en CM
	 */
	private double _caramelPosition=(_conveyorBeltSize1*2/3);
	
	/*
	 * posicion donde se encuentra el dispensador de bizcochos, medido en CM
	 */
	private double _spongeCakePosition=_spongeCakeSize/2;
	
	private double _stamperMachinePosition=_conveyorBeltSize2*1/3;
	private double _cuttingMachinePosition=_stamperMachinePosition+_blisterSize;
	
	
	private double _aut3InitialPosition=_blisterSize/2;
	private double _qualityPosition=_conveyorBeltSize3*1/3;
	private double _sealingMachinePosition=_conveyorBeltSize3*2/3;
	
	/*
	 * posicion donde se encuentra el fin de la cinta y se espera a que se recoja, medido en CM
	 */
	//private int posFin=pointsControl-1;
	private double _aut1FinalPosition=_conveyorBeltSize1-_spongeCakeSize/2;
	private double _aut2FinalPosition=_conveyorBeltSize2-_blisterSize/2;
	private double _aut3FinalPosition=_conveyorBeltSize3-_blisterSize/2;
	
	/*
	 * espacio entre dos bizcochos, es decir el espacio que hay en la cintra entre 2 biscochos, en metros
	 */
	private double _spaceBetweenSpongeCakes=_spongeCakeSize+0.2;
	/**
	 * 
	 */
	private double _spaceBetweenBlisters=_blisterSize+0.2;
	
	/**
	 * 
	 */
	private double _failurePercentage=0.05;
	
	/**
	 * @return
	 */
	public synchronized double getPorcentajeFallos() {
		return _failurePercentage;
	}

	/**
	 * @param failurePercentage
	 */
	public synchronized void setFailurePercentage(double failurePercentage) {
		this._failurePercentage = failurePercentage;
	}

	/*
	 * posiciones asociadas al estado interno
	 */
	private int _posAssocSensorFinAut1=0;
	private int _posAssocSensorCaramel=1;
	private int _posAssocSensorChocolate=2;
	private int _posAssocCaramel=3;
	private int _posAssocChocolate=4;
	private int _posAssocDispenser=5;
	private int _posAssocConveyorBeltAut1=6;
	
	private int _posAssocSensorFinAut2=0;
	private int _posAssocSensorStamper=1;
	private int _posAssocSensorCutter=2;
	private int _posAssocStamper=3;
	private int _posAssocCutter=4;
	private int _posAssocConveyorBeltAut2=5;
	
	private int _posAssocSensorFinAut3=0;
	private int _posAssocSensorSealing=1;
	private int _posAssocSensorQuality=2;
	private int _posAssocSensorBeginConveyorBelt=3;
	private int _posAssocSealing=4;
	private int _posAssocQuality=5;
	private int _posAssocConveyorBeltAut3=6;
	
    /*
     *  creador sincronizado para protegerse de posibles problemas  multi-hilo
     *  otra prueba para evitar instanciación múltiple
     */ 
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new MasterConfiguration();
        }
    }
    
    /**
     * @return
     */
    public static MasterConfiguration getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }

	/**
	 * @return
	 */
	public int getClockTime() {
		return _clockTime;
	}

	/**
	 * @return
	 */
	public synchronized double getSpongeCakeSize() {
		return _spongeCakeSize;
	}

	public double getErrorSensor() {
		return _sensorError;
	}

	/**
	 * @return
	 */
	public double getConveyorBeltSize() {
		return _conveyorBeltSize1;
	}

	/**
	 * @return
	 */
	public int getCakeCapacity() {
		return _cakeCapacity;
	}

	/**
	 * @return
	 */
	public double getConveyorBeltSpeed() {
		return _conveyorBeltSpeed1;
	}

	/**
	 * @return
	 */
	public int getValvChoc() {
		return _chocolateValveTime;
	}

	/**
	 * @return
	 */
	public int getValvCaram() {
		return _caramelValveTime;
	}

	/**
	 * @return
	 */
	public double getPosChoc() {
		return _chocolatePosition;
	}

	/**
	 * @return
	 */
	public double getPosCaram() {
		return _caramelPosition;
	}

	/**
	 * @return
	 */
	public double getPosSpongeCake() {
		return _spongeCakePosition;
	}

	/**
	 * @return
	 */
	public double getSpaceBetweenSpongeCakes() {
		return _spaceBetweenSpongeCakes;
	}
	
	/**
	 * @return
	 */
	public double getSizeBlister() {
		return _blisterSize;
	}

	/**
	 * @return
	 */
	public synchronized double getSizeCintaAut1() {
		return _conveyorBeltSize1;
	}

	/**
	 * @return
	 */
	public synchronized double getSizeCintaAut2() {
		return _conveyorBeltSize2;
	}

	/**
	 * @return
	 */
	public synchronized double getSizeCintaAut3() {
		return _conveyorBeltSize3;
	}

	/**
	 * @return
	 */
	public synchronized double getVelCintaAut1() {
		return _conveyorBeltSpeed1;
	}

	/**
	 * @return
	 */
	public synchronized double getVelCintaAut2() {
		return _conveyorBeltSpeed2;
	}

	/**
	 * @return
	 */
	public synchronized double getVelCintaAut3() {
		return _conveyorBeltSpeed3;
	}

	/**
	 * @return
	 */
	public synchronized int getSelladora() {
		return _sealingMachineTime;
	}

	/**
	 * @return
	 */
	public synchronized int getMoverBlister() {
		return _moveBlisterTime;
	}

	/**
	 * @return
	 */
	public synchronized int getMoverPastel() {
		return _moveCakeTime;
	}

	public double getPosInicioAut3() {
		return _aut3InitialPosition;
	}

	public double getPosCalidad() {
		return _qualityPosition;
	}

	public double getPosCortadora() {
		return _cuttingMachinePosition;
	}

	public double getPosTroqueladora() {
		return _stamperMachinePosition;
	}

	public double getPosSelladora() {
		return _sealingMachinePosition;
	}

	public double getPosFinAut1() {
		return _aut1FinalPosition;
	}

	public double getPosFinAut2() {
		return _aut2FinalPosition;
	}

	public double getPosFinAut3() {
		return _aut3FinalPosition;
	}
	
	public double getEspEntreBlister() {
		return _spaceBetweenBlisters;
	}

	
	public int getPosicionAsociada(NombreMaquinas nombre){
		int sal=-1;
		if(nombre.equals(NombreMaquinas.FIN_1))
			sal=_posAssocSensorFinAut1;
		else if(nombre.equals(NombreMaquinas.SENSOR_CARAMELO))
			sal=_posAssocSensorCaramel;
		else if(nombre.equals(NombreMaquinas.SENSOR_CHOCOLATE))
			sal=_posAssocSensorChocolate;
		else if(nombre.equals(NombreMaquinas.CARAMELO))
			sal=_posAssocCaramel;
		else if(nombre.equals(NombreMaquinas.CHOCOLATE))
			sal=_posAssocChocolate;
		else if(nombre.equals(NombreMaquinas.DISPENSADORA))
			sal=_posAssocDispenser;
		else if(nombre.equals(NombreMaquinas.CINTA_1))
			sal=_posAssocConveyorBeltAut1;
		
		if(nombre.equals(NombreMaquinas.FIN_2))
			sal=_posAssocSensorFinAut2;
		else if(nombre.equals(NombreMaquinas.SENSOR_TROQUELADORA))
			sal=_posAssocSensorStamper;
		else if(nombre.equals(NombreMaquinas.SENSOR_CORTADORA))
			sal=_posAssocSensorCutter;
		else if(nombre.equals(NombreMaquinas.TROQUELADORA))
			sal=_posAssocStamper;
		else if(nombre.equals(NombreMaquinas.CORTADORA))
			sal=_posAssocCutter;
		else if(nombre.equals(NombreMaquinas.CINTA_2))
			sal=_posAssocConveyorBeltAut2;
		
		if(nombre.equals(NombreMaquinas.FIN_3))
			sal=_posAssocSensorFinAut3;
		else if(nombre.equals(NombreMaquinas.SENSOR_SELLADORA))
			sal=_posAssocSensorSealing;
		else if(nombre.equals(NombreMaquinas.SENSOR_CALIDAD))
			sal=_posAssocSensorQuality;
		else if(nombre.equals(NombreMaquinas.INICIO))
			sal=_posAssocSensorBeginConveyorBelt;
		else if(nombre.equals(NombreMaquinas.SELLADO))
			sal=_posAssocSealing;
		else if(nombre.equals(NombreMaquinas.CONTROL_CALIDAD))
			sal=_posAssocQuality;
		else if(nombre.equals(NombreMaquinas.CINTA_3))
			sal=_posAssocConveyorBeltAut3;
		
		return sal;
		
	}

	public synchronized int getInterferencia() {
		return _interference;
	}

	public synchronized int getAlmacenarBlister() {
		return _storeBlisterTime;
	}

	public synchronized void setAlmacenarBlister(int almacenarBlister) {
		this._storeBlisterTime = almacenarBlister;
	}

	public static synchronized MasterConfiguration getINSTANCE() {
		return INSTANCE;
	}

	public static synchronized void setINSTANCE(MasterConfiguration instance) {
		INSTANCE = instance;
	}

	public synchronized int getPosicionAsociadaSensorFinAut1() {
		return _posAssocSensorFinAut1;
	}

	public synchronized void setPosicionAsociadaSensorFinAut1(
			int posicionAsociadaSensorFinAut1) {
		this._posAssocSensorFinAut1 = posicionAsociadaSensorFinAut1;
	}

	public synchronized int getPosicionAsociadaSensorCaramelo() {
		return _posAssocSensorCaramel;
	}

	public synchronized void setPosicionAsociadaSensorCaramelo(
			int posicionAsociadaSensorCaramelo) {
		this._posAssocSensorCaramel = posicionAsociadaSensorCaramelo;
	}

	public synchronized int getPosicionAsociadaSensorChocolate() {
		return _posAssocSensorChocolate;
	}

	public synchronized void setPosicionAsociadaSensorChocolate(
			int posicionAsociadaSensorChocolate) {
		this._posAssocSensorChocolate = posicionAsociadaSensorChocolate;
	}

	public synchronized int getPosicionAsociadaCaramelo() {
		return _posAssocCaramel;
	}

	public synchronized void setPosicionAsociadaCaramelo(
			int posicionAsociadaCaramelo) {
		this._posAssocCaramel = posicionAsociadaCaramelo;
	}

	public synchronized int getPosicionAsociadaChocolate() {
		return _posAssocChocolate;
	}

	public synchronized void setPosicionAsociadaChocolate(
			int posicionAsociadaChocolate) {
		this._posAssocChocolate = posicionAsociadaChocolate;
	}

	public synchronized int getPosicionAsociadaDispensadora() {
		return _posAssocDispenser;
	}

	public synchronized void setPosicionAsociadaDispensadora(
			int posicionAsociadaDispensadora) {
		this._posAssocDispenser = posicionAsociadaDispensadora;
	}

	public synchronized int getPosicionAsociadaCintaAut1() {
		return _posAssocConveyorBeltAut1;
	}

	public synchronized void setPosicionAsociadaCintaAut1(
			int posicionAsociadaCintaAut1) {
		this._posAssocConveyorBeltAut1 = posicionAsociadaCintaAut1;
	}

	public synchronized int getPosicionAsociadaSensorFinAut2() {
		return _posAssocSensorFinAut2;
	}

	public synchronized void setPosicionAsociadaSensorFinAut2(
			int posicionAsociadaSensorFinAut2) {
		this._posAssocSensorFinAut2 = posicionAsociadaSensorFinAut2;
	}

	public synchronized int getPosicionAsociadaSensorTroqueladora() {
		return _posAssocSensorStamper;
	}

	public synchronized void setPosicionAsociadaSensorTroqueladora(
			int posicionAsociadaSensorTroqueladora) {
		this._posAssocSensorStamper = posicionAsociadaSensorTroqueladora;
	}

	public synchronized int getPosicionAsociadaSensorCortadora() {
		return _posAssocSensorCutter;
	}

	public synchronized void setPosicionAsociadaSensorCortadora(
			int posicionAsociadaSensorCortadora) {
		this._posAssocSensorCutter = posicionAsociadaSensorCortadora;
	}

	public synchronized int getPosicionAsociadaTroqueladora() {
		return _posAssocStamper;
	}

	public synchronized void setPosicionAsociadaTroqueladora(
			int posicionAsociadaTroqueladora) {
		this._posAssocStamper = posicionAsociadaTroqueladora;
	}

	public synchronized int getPosicionAsociadaCortadora() {
		return _posAssocCutter;
	}

	public synchronized void setPosicionAsociadaCortadora(
			int posicionAsociadaCortadora) {
		this._posAssocCutter = posicionAsociadaCortadora;
	}

	public synchronized int getPosicionAsociadaCintaAut2() {
		return _posAssocConveyorBeltAut2;
	}

	public synchronized void setPosicionAsociadaCintaAut2(
			int posicionAsociadaCintaAut2) {
		this._posAssocConveyorBeltAut2 = posicionAsociadaCintaAut2;
	}

	public synchronized int getPosicionAsociadaSensorFinAut3() {
		return _posAssocSensorFinAut3;
	}

	public synchronized void setPosicionAsociadaSensorFinAut3(
			int posicionAsociadaSensorFinAut3) {
		this._posAssocSensorFinAut3 = posicionAsociadaSensorFinAut3;
	}

	public synchronized int getPosicionAsociadaSensorSelladora() {
		return _posAssocSensorSealing;
	}

	public synchronized void setPosicionAsociadaSensorSelladora(
			int posicionAsociadaSensorSelladora) {
		this._posAssocSensorSealing = posicionAsociadaSensorSelladora;
	}

	public synchronized int getPosicionAsociadaSensorCalidad() {
		return _posAssocSensorQuality;
	}

	public synchronized void setPosicionAsociadaSensorCalidad(
			int posicionAsociadaSensorCalidad) {
		this._posAssocSensorQuality = posicionAsociadaSensorCalidad;
	}

	public synchronized int getPosicionAsociadaSensorInicioCinta() {
		return _posAssocSensorBeginConveyorBelt;
	}

	public synchronized void setPosicionAsociadaSensorInicioCinta(
			int posicionAsociadaSensorInicioCinta) {
		this._posAssocSensorBeginConveyorBelt = posicionAsociadaSensorInicioCinta;
	}

	public synchronized int getPosicionAsociadaSelladora() {
		return _posAssocSealing;
	}

	public synchronized void setPosicionAsociadaSelladora(
			int posicionAsociadaSelladora) {
		this._posAssocSealing = posicionAsociadaSelladora;
	}

	public synchronized int getPosicionAsociadaCalidad() {
		return _posAssocQuality;
	}

	public synchronized void setPosicionAsociadaCalidad(int posicionAsociadaCalidad) {
		this._posAssocQuality = posicionAsociadaCalidad;
	}

	public synchronized int getPosicionAsociadaCintaAut3() {
		return _posAssocConveyorBeltAut3;
	}

	public synchronized void setPosicionAsociadaCintaAut3(
			int posicionAsociadaCintaAut3) {
		this._posAssocConveyorBeltAut3 = posicionAsociadaCintaAut3;
	}

	public synchronized void set_tiempoReloj(int reloj) {
		_clockTime = reloj;
	}

	public synchronized void setErrorSensor(double errorSensor) {
		this._sensorError = errorSensor;
	}

	public synchronized void setSizeCintaAut1(double sizeCintaAut1) {
		this._conveyorBeltSize1 = sizeCintaAut1;
	}

	public synchronized void setSizeCintaAut2(double sizeCintaAut2) {
		this._conveyorBeltSize2 = sizeCintaAut2;
	}

	public synchronized void setSizeCintaAut3(double sizeCintaAut3) {
		this._conveyorBeltSize3 = sizeCintaAut3;
	}

	public synchronized void setCapacidadPasteles(int capacidadPasteles) {
		this._cakeCapacity = capacidadPasteles;
	}

	public synchronized void setVelCintaAut1(double velCintaAut1) {
		this._conveyorBeltSpeed1 = velCintaAut1;
	}

	public synchronized void setVelCintaAut2(double velCintaAut2) {
		this._conveyorBeltSpeed2 = velCintaAut2;
	}

	public synchronized void setVelCintaAut3(double velCintaAut3) {
		this._conveyorBeltSpeed3 = velCintaAut3;
	}

	public synchronized void setValvChoc(int valvChoc) {
		this._chocolateValveTime = valvChoc;
	}

	public synchronized void setValvCaram(int valvCaram) {
		this._caramelValveTime = valvCaram;
	}

	public synchronized void setSelladora(int selladora) {
		this._sealingMachineTime = selladora;
	}

	public synchronized void setMoverBlister(int moverBlister) {
		this._moveBlisterTime = moverBlister;
	}

	public synchronized void setMoverPastel(int moverPastel) {
		this._moveCakeTime = moverPastel;
	}

	public synchronized void setInterferencia(int interferencia) {
		this._interference = interferencia;
	}

	public synchronized void setSizeBizcocho(double sizeBizcocho) {
		this._spongeCakeSize = sizeBizcocho;
	}

	public synchronized void setSizeBlister(double sizeBlister) {
		this._blisterSize = sizeBlister;
	}

	public synchronized void setPosChoc(double posChoc) {
		this._chocolatePosition = posChoc;
	}

	public synchronized void setPosCaram(double posCaram) {
		this._caramelPosition = posCaram;
	}

	public synchronized void setPosBizc(double posBizc) {
		this._spongeCakePosition = posBizc;
	}

	public synchronized void setPosTroqueladora(double posTroqueladora) {
		this._stamperMachinePosition = posTroqueladora;
	}

	public synchronized void setPosCortadora(double posCortadora) {
		this._cuttingMachinePosition = posCortadora;
	}

	public synchronized void setPosInicioAut3(double posInicioAut3) {
		this._aut3InitialPosition = posInicioAut3;
	}

	public synchronized void setPosCalidad(double posCalidad) {
		this._qualityPosition = posCalidad;
	}

	public synchronized void setPosSelladora(double posSelladora) {
		this._sealingMachinePosition = posSelladora;
	}

	public synchronized void setPosFinAut1(double posFinAut1) {
		this._aut1FinalPosition = posFinAut1;
	}

	public synchronized void setPosFinAut2(double posFinAut2) {
		this._aut2FinalPosition = posFinAut2;
	}

	public synchronized void setPosFinAut3(double posFinAut3) {
		this._aut3FinalPosition = posFinAut3;
	}

	public synchronized void setEspEntreBizc(double espEntreBizc) {
		this._spaceBetweenSpongeCakes = espEntreBizc;
	}

	public synchronized void setEspEntreBlister(double espEntreBlister) {
		this._spaceBetweenBlisters = espEntreBlister;
	}

	public synchronized int getCapacidadCaramelo() {
		return _caramelCapacity;
	}

	public synchronized void setCapacidadCaramelo(int capacidadCaramelo) {
		this._caramelCapacity = capacidadCaramelo;
	}

	public synchronized int getCapacidadChocolate() {
		return _chocolateCapacity;
	}

	public synchronized void setCapacidadChocolate(int capacidadChocolate) {
		this._chocolateCapacity = capacidadChocolate;
	}

	
	
	
}