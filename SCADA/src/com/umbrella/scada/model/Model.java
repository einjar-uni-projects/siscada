package com.umbrella.scada.model;

import com.umbrella.scada.observer.Observable;
import com.umbrella.scada.observer.ObservableProvider;

public class Model {
	private final Observable _observable;
	
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
	
	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	private Model() {
		_observable = ObservableProvider.getInstance();
	}

	/**
	 * Obtiene la instancia única del objeto, la primera invocación
	 * realiza la creación del mismo.
	 * @return la instancia única de Model
	 */
	public static Model getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static Model instance = new Model();
	}
}
