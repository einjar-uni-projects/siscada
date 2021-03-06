package com.umbrella.scada.model;

import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.scada.observer.Observable;
import com.umbrella.scada.observer.ObservableProvider;
import com.umbrella.scada.observer.TransferBufferKeys;

public class Model {
	
	private static Model INSTANCE;
	
	private static synchronized void getInstanceSync(){
		if(INSTANCE == null){
			INSTANCE = new Model();
		}
	}
	
	/**
	 * Obtiene la instancia única del objeto, la primera invocación
	 * realiza la creación del mismo.
	 * @return la instancia única de Model
	 */
	public static Model getInstance() {
		if(INSTANCE == null)
			getInstanceSync();
		return INSTANCE;
	}
	
	private final Observable _observable;
	
	/*
	 * Los atributos llevan el siguiente nombrado:
	 * _gen para atributos generales
	 * _au1 para el automata 1
	 * _au2 para el automata 2
	 * _au3 para el automata 3
	 * _rb1 para el robot 1
	 * _rb2 para el robot 2
	 */
	/*Atributos generales*/
	private final ModelElementAtribute<Integer> _genClockTime = new ModelElementAtribute<Integer>(TransferBufferKeys.GEN_CLOCK_TIME,new Integer(200));
	private final ModelElementAtribute<Double> _genSensorError = new ModelElementAtribute<Double>(TransferBufferKeys.GEN_SENSOR_ERROR,new Double(0.20));
	private final ModelElementAtribute<Integer> _genRobotInterference = new ModelElementAtribute<Integer>(TransferBufferKeys.GEN_ROBOT_INTERFERENCE, new Integer(2));
	private final ModelElementAtribute<Double> _genCakeSize = new ModelElementAtribute<Double>(TransferBufferKeys.GEN_CAKE_SIZE, new Double(0.1));
	private final ModelElementAtribute<Double> _genBlisterSize = new ModelElementAtribute<Double>(TransferBufferKeys.GEN_BLISTER_SIZE, new Double(_genCakeSize.get_value().doubleValue()*2+0.1));
	private final ModelElementAtribute<String> _genIP = new ModelElementAtribute<String>(TransferBufferKeys.GEN_IP, new String("localhost"));
	private final ModelElementAtribute<Integer> _genPort = new ModelElementAtribute<Integer>(TransferBufferKeys.GEN_PORT, new Integer(9003));
	private final ModelElementAtribute<Integer> _numGoodPackages = new ModelElementAtribute<Integer>(TransferBufferKeys.GOOD_PACKAGES, new Integer(0));
	private final ModelElementAtribute<Integer> _numBadPackages = new ModelElementAtribute<Integer>(TransferBufferKeys.BAD_PACKAGES, new Integer(0));
	private final ModelElementAtribute<Integer> _numGoodPackagesTotal = new ModelElementAtribute<Integer>(TransferBufferKeys.GOOD_PACKAGES_TOTAL, new Integer(0));
	private final ModelElementAtribute<Integer> _numBadPackagesTotal = new ModelElementAtribute<Integer>(TransferBufferKeys.BAD_PACKAGES_TOTAL, new Integer(0));

	/*Atributos del automata 1*/
	private final ModelElementAtribute<Double> _au1ConveyorBeltSize = new ModelElementAtribute<Double>(TransferBufferKeys.AU1_CONVEYOR_BELT_SIZE,new Double(10));
	private final ModelElementAtribute<Double> _au1ConveyorBeltSpeed = new ModelElementAtribute<Double>(TransferBufferKeys.AU1_CONVEYOR_BELT_SPEED,new Double(20));
	private final ModelElementAtribute<Integer> _au1CakeDepot = new ModelElementAtribute<Integer>(TransferBufferKeys.AU1_CAKE_DEPOT, new Integer(0));
	private final ModelElementAtribute<Integer> _au1ChocolatDepot = new ModelElementAtribute<Integer>(TransferBufferKeys.AU1_CHOCOLAT_DEPOT, new Integer(0));
	private final ModelElementAtribute<Integer> _au1CaramelDepot = new ModelElementAtribute<Integer>(TransferBufferKeys.AU1_CARAMEL_DEPOT, new Integer(0));
	private final ModelElementAtribute<Integer> _au1ChocolateValveDelay = new ModelElementAtribute<Integer>(TransferBufferKeys.AU1_CHOCOLATE_VALVE_DELAY, new Integer(3));
	private final ModelElementAtribute<Integer> _au1CaramelValveDelay = new ModelElementAtribute<Integer>(TransferBufferKeys.AU1_CARAMEL_VALVE_DELAY, new Integer(2));
	private final ModelElementAtribute<Integer> _au1CakesPos1 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU1_CAKES_POS1, new Integer(0));
	private final ModelElementAtribute<Integer> _au1CakesPos2 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU1_CAKES_POS2, new Integer(0));
	private final ModelElementAtribute<Integer> _au1CakesPos3 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU1_CAKES_POS3, new Integer(0));
	private final ModelElementAtribute<Integer> _au1CakesPos4 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU1_CAKES_POS4, new Integer(0));
	private final ModelElementAtribute<Integer> _au1CakesPos5 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU1_CAKES_POS5, new Integer(0));
	private final ModelElementAtribute<Integer> _au1CakesPos6 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU1_CAKES_POS6, new Integer(0));
	private final ModelElementAtribute<Integer> _au1CakesPos7 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU1_CAKES_POS7, new Integer(0));
	private final ModelElementAtribute<Boolean> _au1State = new ModelElementAtribute<Boolean>(TransferBufferKeys.AU1_STATE, new Boolean(false));
	private final ModelElementAtribute<Boolean> _au1Move = new ModelElementAtribute<Boolean>(TransferBufferKeys.AU1_MOVE, new Boolean(false));
	
	
	/*Atributos del automata 2*/
	private final ModelElementAtribute<Double> _au2ConveyorBeltSize = new ModelElementAtribute<Double>(TransferBufferKeys.AU2_CONVEYOR_BELT_SIZE,new Double(10));
	private final ModelElementAtribute<Double> _au2ConveyorBeltSpeed = new ModelElementAtribute<Double>(TransferBufferKeys.AU2_CONVEYOR_BELT_SPEED,new Double(20));
	private final ModelElementAtribute<Integer> _au2VacuumSealingMachine = new ModelElementAtribute<Integer>(TransferBufferKeys.AU2_VACUUM_SEALING_MACHINE, new Integer(5));
	private final ModelElementAtribute<Integer> _au2BlistersPos1 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU2_BLISTERS_POS1, new Integer(0));
	private final ModelElementAtribute<Integer> _au2BlistersPos2 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU2_BLISTERS_POS2, new Integer(0));
	private final ModelElementAtribute<Integer> _au2BlistersPos3 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU2_BLISTERS_POS3, new Integer(0));
	private final ModelElementAtribute<Integer> _au2BlistersPos4 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU2_BLISTERS_POS4, new Integer(0));
	private final ModelElementAtribute<Integer> _au2BlistersPos5 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU2_BLISTERS_POS5, new Integer(0));
	private final ModelElementAtribute<Boolean> _au2State = new ModelElementAtribute<Boolean>(TransferBufferKeys.AU2_STATE, new Boolean(false));
	private final ModelElementAtribute<Boolean> _au2Move = new ModelElementAtribute<Boolean>(TransferBufferKeys.AU2_MOVE, new Boolean(false));
	
	/*Atributos de la mesa*/
	private final ModelElementAtribute<Integer> _tableContent = new ModelElementAtribute<Integer>(TransferBufferKeys.TABLE_CONTENT, new Integer(0));
	
	/*Atributos del automata 3*/
	private final ModelElementAtribute<Double> _au3ConveyorBeltSize = new ModelElementAtribute<Double>(TransferBufferKeys.AU3_CONVEYOR_BELT_SIZE,new Double(10));
	private final ModelElementAtribute<Double> _au3ConveyorBeltSpeed = new ModelElementAtribute<Double>(TransferBufferKeys.AU3_CONVEYOR_BELT_SPEED,new Double(10));
	private final ModelElementAtribute<Integer> _au3PackagePos1 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU3_PACKAGE_POS1, new Integer(0));
	private final ModelElementAtribute<Integer> _au3PackagePos2 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU3_PACKAGE_POS2, new Integer(0));
	private final ModelElementAtribute<Integer> _au3PackagePos3 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU3_PACKAGE_POS3, new Integer(0));
	private final ModelElementAtribute<Integer> _au3PackagePos4 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU3_PACKAGE_POS4, new Integer(0));
	private final ModelElementAtribute<Integer> _au3PackagePos5 = new ModelElementAtribute<Integer>(TransferBufferKeys.AU3_PACKAGE_POS5, new Integer(0));
	private final ModelElementAtribute<Boolean> _au3State = new ModelElementAtribute<Boolean>(TransferBufferKeys.AU3_STATE, new Boolean(false));
	private final ModelElementAtribute<Boolean> _au3Move = new ModelElementAtribute<Boolean>(TransferBufferKeys.AU3_MOVE, new Boolean(false));
	
	/*Atributos del robot1*/
	private final ModelElementAtribute<Integer> _rb1BlisterDelay = new ModelElementAtribute<Integer>(TransferBufferKeys.RB1_BLISTER_DELAY, new Integer(5));
	private final ModelElementAtribute<Integer> _rb1CakeDelay = new ModelElementAtribute<Integer>(TransferBufferKeys.RB1_CAKE_DELAY, new Integer(5));
	private final ModelElementAtribute<Integer> _rb1Content = new ModelElementAtribute<Integer>(TransferBufferKeys.RB1_CONTENT, new Integer(0));
	private final ModelElementAtribute<Boolean> _rb1State = new ModelElementAtribute<Boolean>(TransferBufferKeys.RB1_STATE, new Boolean(false));
	
	/*Atributos del robot2*/
	private final ModelElementAtribute<Integer> _rb2BlisterDelay = new ModelElementAtribute<Integer>(TransferBufferKeys.RB2_BLISTER_DELAY, new Integer(5));
	private final ModelElementAtribute<Integer> _rb2Content = new ModelElementAtribute<Integer>(TransferBufferKeys.RB2_CONTENT, new Integer(0));
	private final ModelElementAtribute<Boolean> _rb2State = new ModelElementAtribute<Boolean>(TransferBufferKeys.RB2_STATE, new Boolean(false));
	
	private boolean _modelChanges;
	
	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	private Model() {
		System.out.println("Arrancando el Model");
		_observable = ObservableProvider.getInstance();
		try {
			new Thread(Postmaster.getInstance()).start();
		} catch (PropertyException e) {
			
			e.printStackTrace();
		}
		updateAll();
		notifyChanges();
	}
	
	private void updateAll() {
		_observable.addChange(_numGoodPackages.get_tbk(), _numGoodPackages.get_value());
		_observable.addChange(_numBadPackages.get_tbk(), _numBadPackages.get_value());
		_observable.addChange(_numGoodPackagesTotal.get_tbk(), _numGoodPackagesTotal.get_value());
		_observable.addChange(_numBadPackagesTotal.get_tbk(), _numBadPackagesTotal.get_value());
		
		_observable.addChange(_au1CakeDepot.get_tbk(), _au1CakeDepot.get_value());
		_observable.addChange(_au1ChocolatDepot.get_tbk(), _au1ChocolatDepot.get_value());
		_observable.addChange(_au1CaramelDepot.get_tbk(), _au1CaramelDepot.get_value());
		_observable.addChange(_au1CaramelValveDelay.get_tbk(), _au1CaramelValveDelay.get_value());
		_observable.addChange(_au1CaramelValveDelay.get_tbk(), _au1CaramelValveDelay.get_value());
		_observable.addChange(_au1ConveyorBeltSize.get_tbk(), _au1ConveyorBeltSize.get_value());
		_observable.addChange(_au1ConveyorBeltSpeed.get_tbk(), _au1ConveyorBeltSpeed.get_value());
		_observable.addChange(_au1CakesPos1.get_tbk(), _au1CakesPos1.get_value());
		_observable.addChange(_au1CakesPos2.get_tbk(), _au1CakesPos2.get_value());
		_observable.addChange(_au1CakesPos3.get_tbk(), _au1CakesPos3.get_value());
		_observable.addChange(_au1CakesPos4.get_tbk(), _au1CakesPos4.get_value());
		_observable.addChange(_au1CakesPos5.get_tbk(), _au1CakesPos5.get_value());
		_observable.addChange(_au1CakesPos6.get_tbk(), _au1CakesPos6.get_value());
		_observable.addChange(_au1CakesPos7.get_tbk(), _au1CakesPos7.get_value());
		_observable.addChange(_au1State.get_tbk(), _au1State.get_value());
		_observable.addChange(_au1Move.get_tbk(), _au1Move.get_value());
		
		
		_observable.addChange(_au2ConveyorBeltSize.get_tbk(), _au2ConveyorBeltSize.get_value());
		_observable.addChange(_au2ConveyorBeltSpeed.get_tbk(), _au2ConveyorBeltSpeed.get_value());
		_observable.addChange(_au2VacuumSealingMachine.get_tbk(), _au2VacuumSealingMachine.get_value());
		_observable.addChange(_au2BlistersPos1.get_tbk(), _au2BlistersPos1.get_value());
		_observable.addChange(_au2BlistersPos2.get_tbk(), _au2BlistersPos2.get_value());
		_observable.addChange(_au2BlistersPos3.get_tbk(), _au2BlistersPos3.get_value());
		_observable.addChange(_au2BlistersPos4.get_tbk(), _au2BlistersPos4.get_value());
		_observable.addChange(_au2BlistersPos5.get_tbk(), _au2BlistersPos5.get_value());
		_observable.addChange(_au2State.get_tbk(), _au2State.get_value());
		_observable.addChange(_au2Move.get_tbk(), _au2Move.get_value());
		
		_observable.addChange(_tableContent.get_tbk(), _tableContent.get_value());
		
		_observable.addChange(_au3ConveyorBeltSize.get_tbk(), _au3ConveyorBeltSize.get_value());
		_observable.addChange(_au3ConveyorBeltSpeed.get_tbk(), _au3ConveyorBeltSpeed.get_value());
		_observable.addChange(_au3PackagePos1.get_tbk(), _au3PackagePos1.get_value());
		_observable.addChange(_au3PackagePos2.get_tbk(), _au3PackagePos2.get_value());
		_observable.addChange(_au3PackagePos3.get_tbk(), _au3PackagePos3.get_value());
		_observable.addChange(_au3PackagePos4.get_tbk(), _au3PackagePos4.get_value());
		_observable.addChange(_au3PackagePos5.get_tbk(), _au3PackagePos5.get_value());
		_observable.addChange(_au3State.get_tbk(), _au3State.get_value());
		_observable.addChange(_au3Move.get_tbk(), _au3Move.get_value());
		
		_observable.addChange(_genBlisterSize.get_tbk(), _genBlisterSize.get_value());
		_observable.addChange(_genCakeSize.get_tbk(), _genCakeSize.get_value());
		_observable.addChange(_genClockTime.get_tbk(), _genClockTime.get_value());
		_observable.addChange(_genRobotInterference.get_tbk(), _genRobotInterference.get_value());
		_observable.addChange(_genSensorError.get_tbk(), _genSensorError.get_value());
		_observable.addChange(_genIP.get_tbk(), _genIP.get_value());
		_observable.addChange(_genPort.get_tbk(), _genPort.get_value());
		
		_observable.addChange(_rb1BlisterDelay.get_tbk(), _rb1BlisterDelay.get_value());
		_observable.addChange(_rb1CakeDelay.get_tbk(), _rb1CakeDelay.get_value());
		_observable.addChange(_rb1Content.get_tbk(), _rb1Content.get_value());
		_observable.addChange(_rb1State.get_tbk(), _rb1State.get_value());
		
		_observable.addChange(_rb2BlisterDelay.get_tbk(), _rb2BlisterDelay.get_value());
		_observable.addChange(_rb2Content.get_tbk(), _rb2Content.get_value());
		_observable.addChange(_rb2State.get_tbk(), _rb2State.get_value());
		_modelChanges = true;
	}

	public void notifyChanges(){
		if(_modelChanges){
			_observable.notifyChanges();
			_modelChanges = false;
		}
	} 
	
	public String get_genIP(){
		return _genIP.get_value();
	}
	
	public void set_numGoodPackages(int packages){
		_numGoodPackages.set_value(packages);
		_observable.addChange(_numGoodPackages.get_tbk(), _numGoodPackages.get_value());
		_modelChanges = true;
	}
	
	public void set_numBadPackages(int packages){
		_numBadPackages.set_value(packages);
		_observable.addChange(_numBadPackages.get_tbk(), _numBadPackages.get_value());
		_modelChanges = true;
	}
	
	public void set_numGoodPackagesTotal(int packages){
		_numGoodPackagesTotal.set_value(packages);
		_observable.addChange(_numGoodPackagesTotal.get_tbk(), _numGoodPackagesTotal.get_value());
		_modelChanges = true;
	}
	
	public void set_numBadPackagesTotal(int packages){
		_numBadPackagesTotal.set_value(packages);
		_observable.addChange(_numBadPackagesTotal.get_tbk(), _numBadPackagesTotal.get_value());
		_modelChanges = true;
	}
	
	public void set_rb2Content(int value){
		_rb2Content.set_value(value);
		_observable.addChange(_rb2Content.get_tbk(), _rb2Content.get_value());
		_modelChanges = true;
	}
	
	public void set_rb1Content(int value){
		_rb1Content.set_value(value);
		_observable.addChange(_rb1Content.get_tbk(), _rb1Content.get_value());
		_modelChanges = true;
	}
	
	public void set_au1State(boolean state){
		_au1State.set_value(state);
		_observable.addChange(_au1State.get_tbk(), _au1State.get_value());
		_modelChanges = true;
	}
	
	public void set_au2State(boolean state){
		_au2State.set_value(state);
		_observable.addChange(_au2State.get_tbk(), _au2State.get_value());
		_modelChanges = true;
	}
	
	public void set_au3State(boolean state){
		_au3State.set_value(state);
		_observable.addChange(_au3State.get_tbk(), _au3State.get_value());
		_modelChanges = true;
	}
	
	public void set_rb1State(boolean state){
		_rb1State.set_value(state);
		_observable.addChange(_rb1State.get_tbk(), _rb1State.get_value());
		_modelChanges = true;
	}
	
	public void set_rb2State(boolean state){
		_rb2State.set_value(state);
		_observable.addChange(_rb2State.get_tbk(), _rb2State.get_value());
		_modelChanges = true;
	}
	
	public void set_genIP(String ip){
		_genIP.set_value(ip);
		_observable.addChange(_genIP.get_tbk(), _genIP.get_value());
		_modelChanges = true;
	}
	
	public int get_genPort(){
		return _genPort.get_value();
	}
	
	public void set_genPort(int port){
		_genPort.set_value(port);
		_observable.addChange(_genPort.get_tbk(), _genPort.get_value());
		_modelChanges = true;
	}
	
	public void set_au1CakesPos1(int cakes){
		_au1CakesPos1.set_value(cakes);
		_observable.addChange(_au1CakesPos1.get_tbk(), _au1CakesPos1.get_value());
		_modelChanges = true;
	}
	
	public void set_au1CakesPos2(int cakes){
		_au1CakesPos2.set_value(cakes);
		_observable.addChange(_au1CakesPos2.get_tbk(), _au1CakesPos2.get_value());
		_modelChanges = true;
	}
	
	public void set_au1CakesPos3(int cakes){
		_au1CakesPos3.set_value(cakes);
		_observable.addChange(_au1CakesPos3.get_tbk(), _au1CakesPos3.get_value());
		_modelChanges = true;
	}
	
	public void set_au1CakesPos4(int cakes){
		_au1CakesPos4.set_value(cakes);
		_observable.addChange(_au1CakesPos4.get_tbk(), _au1CakesPos4.get_value());
		_modelChanges = true;
	}
	
	public void set_au1CakesPos5(int cakes){
		_au1CakesPos5.set_value(cakes);
		_observable.addChange(_au1CakesPos5.get_tbk(), _au1CakesPos5.get_value());
		_modelChanges = true;
	}
	
	public void set_au1CakesPos6(int cakes){
		_au1CakesPos6.set_value(cakes);
		_observable.addChange(_au1CakesPos6.get_tbk(), _au1CakesPos6.get_value());
		_modelChanges = true;
	}
	
	public void set_au1CakesPos7(int cakes){
		_au1CakesPos7.set_value(cakes);
		_observable.addChange(_au1CakesPos7.get_tbk(), _au1CakesPos7.get_value());
		_modelChanges = true;
	}
	
	public void set_au1CakeDepot(int cakeDepot) {
		_au1CakeDepot.set_value(cakeDepot);
		_observable.addChange(_au1CakeDepot.get_tbk(), _au1CakeDepot.get_value());
		_modelChanges = true;
	}
	
	public void set_au1ChocolatDepot(int depot) {
		_au1ChocolatDepot.set_value(depot);
		_observable.addChange(_au1ChocolatDepot.get_tbk(), _au1ChocolatDepot.get_value());
		_modelChanges = true;
	}
	
	public void set_au1CaramelDepot(int depot) {
		_au1CaramelDepot.set_value(depot);
		_observable.addChange(_au1CaramelDepot.get_tbk(), _au1CaramelDepot.get_value());
		_modelChanges = true;
	}

	public void set_au1CaramelValveDelay(int caramelValveDelay) {
		_au1CaramelValveDelay.set_value(caramelValveDelay);
		_observable.addChange(_au1CaramelValveDelay.get_tbk(), _au1CaramelValveDelay.get_value());
		_modelChanges = true;
	}

	public void set_au1ChocolateValveDelay(int chocolateValveDelay) {
		_au1ChocolateValveDelay.set_value(chocolateValveDelay);
		_observable.addChange(_au1CaramelValveDelay.get_tbk(), _au1CaramelValveDelay.get_value());
		_modelChanges = true;
	}
	
	public void set_au1ConveyorBeltSize(double conveyorBeltSize) {
		_au1ConveyorBeltSize.set_value(conveyorBeltSize);
		_observable.addChange(_au1ConveyorBeltSize.get_tbk(), _au1ConveyorBeltSize.get_value());
		_modelChanges = true;
	}
	
	public void set_au1ConveyorBeltSpeed(double conveyorBeltSpeed) {
		_au1ConveyorBeltSpeed.set_value(conveyorBeltSpeed);
		_observable.addChange(_au1ConveyorBeltSpeed.get_tbk(), _au1ConveyorBeltSpeed.get_value());
		_modelChanges = true;
	}
	
	public void set_au2ConveyorBeltSize(double conveyorBeltSize) {
		_au2ConveyorBeltSize.set_value(conveyorBeltSize);
		_observable.addChange(_au2ConveyorBeltSize.get_tbk(), _au2ConveyorBeltSize.get_value());
		_modelChanges = true;
	}
	
	public void set_au2ConveyorBeltSpeed(double conveyorBeltSpeed) {
		_au2ConveyorBeltSpeed.set_value(conveyorBeltSpeed);
		_observable.addChange(_au2ConveyorBeltSpeed.get_tbk(), _au2ConveyorBeltSpeed.get_value());
		_modelChanges = true;
	}
	
	public void set_au2VacuumSealingMachine(int vacuumSealingMachine) {
		_au2VacuumSealingMachine.set_value(vacuumSealingMachine);
		_observable.addChange(_au2VacuumSealingMachine.get_tbk(), _au2VacuumSealingMachine.get_value());
		_modelChanges = true;
	}
	
	public void set_au2BlistersPos1(int blisters){
		_au2BlistersPos1.set_value(blisters);
		_observable.addChange(_au2BlistersPos1.get_tbk(), _au2BlistersPos1.get_value());
		_modelChanges = true;
	}
	
	public void set_au2BlistersPos2(int blisters){
		_au2BlistersPos2.set_value(blisters);
		_observable.addChange(_au2BlistersPos2.get_tbk(), _au2BlistersPos2.get_value());
		_modelChanges = true;
	}
	
	public void set_au2BlistersPos3(int blisters){
		_au2BlistersPos3.set_value(blisters);
		_observable.addChange(_au2BlistersPos3.get_tbk(), _au2BlistersPos3.get_value());
		_modelChanges = true;
	}
	
	public void set_au2BlistersPos4(int blisters){
		_au2BlistersPos4.set_value(blisters);
		_observable.addChange(_au2BlistersPos4.get_tbk(), _au2BlistersPos4.get_value());
		_modelChanges = true;
	}
	
	public void set_au2BlistersPos5(int blisters){
		_au2BlistersPos5.set_value(blisters);
		_observable.addChange(_au2BlistersPos5.get_tbk(), _au2BlistersPos5.get_value());
		_modelChanges = true;
	}
	
	public void set_tableContent(int content){
		_tableContent.set_value(content);
		_observable.addChange(_tableContent.get_tbk(), _tableContent.get_value());
		_modelChanges = true;
	}
	
	public void set_au3ConveyorBeltSize(double conveyorBeltSize) {
		_au3ConveyorBeltSize.set_value(conveyorBeltSize);
		_observable.addChange(_au3ConveyorBeltSize.get_tbk(), _au3ConveyorBeltSize.get_value());
		_modelChanges = true;
	}
	
	public void set_au3ConveyorBeltSpeed(double conveyorBeltSpeed) {
		_au3ConveyorBeltSpeed.set_value(conveyorBeltSpeed);
		_observable.addChange(_au3ConveyorBeltSpeed.get_tbk(), _au3ConveyorBeltSpeed.get_value());
		_modelChanges = true;
	}
	
	public void set_au3PackagePos1(int pack){
		_au3PackagePos1.set_value(pack);
		_observable.addChange(_au3PackagePos1.get_tbk(), _au3PackagePos1.get_value());
		_modelChanges = true;
	}
	
	public void set_au3PackagePos2(int pack){
		_au3PackagePos2.set_value(pack);
		_observable.addChange(_au3PackagePos2.get_tbk(), _au3PackagePos2.get_value());
		_modelChanges = true;
	}
	
	public void set_au3PackagePos3(int pack){
		_au3PackagePos3.set_value(pack);
		_observable.addChange(_au3PackagePos3.get_tbk(), _au3PackagePos3.get_value());
		_modelChanges = true;
	}
	
	public void set_au3PackagePos4(int pack){
		_au3PackagePos4.set_value(pack);
		_observable.addChange(_au3PackagePos4.get_tbk(), _au3PackagePos4.get_value());
		_modelChanges = true;
	}
	
	public void set_au3PackagePos5(int pack){
		_au3PackagePos5.set_value(pack);
		_observable.addChange(_au3PackagePos5.get_tbk(), _au3PackagePos5.get_value());
		_modelChanges = true;
	}
	
	public void set_au1Move(boolean move) {
		_au1Move.set_value(move);
		_observable.addChange(_au1Move.get_tbk(), _au1Move.get_value());
		_modelChanges = true;
	}
	
	public void set_au2Move(boolean move) {
		_au2Move.set_value(move);
		_observable.addChange(_au2Move.get_tbk(), _au2Move.get_value());
		_modelChanges = true;
	}
	
	public void set_au3Move(boolean move) {
		_au3Move.set_value(move);
		_observable.addChange(_au3Move.get_tbk(), _au3Move.get_value());
		_modelChanges = true;
	}
	
	public void set_genBlisterSize(double blisterSize) {
		_genBlisterSize.set_value(blisterSize);
		_observable.addChange(_genBlisterSize.get_tbk(), _genBlisterSize.get_value());
		_modelChanges = true;
	}
	
	public void set_genCakeSize(double cakeSize) {
		_genCakeSize.set_value(cakeSize);
		_observable.addChange(_genCakeSize.get_tbk(), _genCakeSize.get_value());
		_modelChanges = true;
	}
	
	public void set_genClockTime(int clockTime) {
		_genClockTime.set_value(clockTime);
		_observable.addChange(_genClockTime.get_tbk(), _genClockTime.get_value());
		_modelChanges = true;
	}
	
	public void set_genRobotInterference(
			int robotInterference) {
		_genRobotInterference.set_value(robotInterference);
		_observable.addChange(_genRobotInterference.get_tbk(), _genRobotInterference.get_value());
		_modelChanges = true;
	}

	public void set_genSensorError(double sensorError) {
		_genSensorError.set_value(sensorError);
		_observable.addChange(_genSensorError.get_tbk(), _genSensorError.get_value());
		_modelChanges = true;
	}
	
	public void set_rb1BlisterDelay(int blisterDelay) {
		_rb1BlisterDelay.set_value(blisterDelay);
		_observable.addChange(_rb1BlisterDelay.get_tbk(), _rb1BlisterDelay.get_value());
		_modelChanges = true;
	}

	public void set_rb1CakeDelay(int cakeDelay) {
		_rb1CakeDelay.set_value(cakeDelay);
		_observable.addChange(_rb1CakeDelay.get_tbk(), _rb1CakeDelay.get_value());
		_modelChanges = true;
	}

	public void set_rb2BlisterDelay(int blisterDelay) {
		_rb2BlisterDelay.set_value(blisterDelay);
		_observable.addChange(_rb2BlisterDelay.get_tbk(), _rb2BlisterDelay.get_value());
		_modelChanges = true;
	}
}
