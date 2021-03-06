package com.umbrella.mail.message;




/* 
 * tiene el nombre de todos los mensajes que se pueden usar
 */
public enum MSGOntology {
	ACTUALIZARCONTEXTO("ActualizarContexto",0),
	ACTUALIZARCONTEXTOROBOT("ActualizarContextoRobot",0),//se pasa el nuevo contexto en el objeto
	ACTUALIZARCONFIGURACION("ActualizarConfiguracion",0),//se pasa el nuevo configuracion en el objeto
	AVISARUNFALLO("AvisarUnFallo",1),//nombre de la maquina q causa el fallo, sacado del enumerado
	INTERFERENCIA("Interferencia",2), //primer parametro es el robot que la causa y el 2 parametro es la cinta en la que la causa, se saca del enumerado de Maquinas
	FININTERFERENCIA("FinInterferencia",2),//parametro 1 el robot y el 2 la conta
	PASTELLISTO("PastelListo",0),
	BLISTERLISTO("BlisterListo",4),
	BLISTERCOMPLETO("BlisterCompleto",4),
	BLISTERALMACENADO("BlisterAlmacenado",1), // el parametro dice si es valido o no lo es
	BLISTERVALIDO("BlisterValido",0),
	BLISTERNOVALIDO("BlisterNoValido",0),
	START("Start",3),
	PARADA("Parada",2),
	PARADAFALLO("ParadaFallo",2),
	PARADAEMERGENCIA("ParadaEmergencia",2),
	ARRANCARDESDEEMERGENCIA("ArrancarDesdeEmergencia",4),
	RESET("Reset",2),
	PRODUCTORECOGIDO("ProductoRecogido",2), //primer parametro el robot y segundo parametro el tipo de producto
	FINCINTALIBRE("FinCintaLibre",3),
	PRODUCTOCOLOCADO("ProductoColocado",2), //primer parametro es el robot y el 2� es el producto
	RELLENARMAQUINA("RellanarMaquina",3), // en el vector 0 el nombre de la maquina sacado del Numerado y el sig parametro un int contertido a String que es la cantidad
	MODIFICARCAMPO("ModificarCampo",4),
	//MOVERDESDEMESA("MoverDesdeMesa",2), 
	AUTOM_STATE("AutomState",0),//parametro 1 String["AU1", "AU2", "AU3", "RB1", "RB2"], Boolean(true, false)
	CAKE_DEPOT("CakeDepot",0),
	CHOCOLAT_DEPOT("ChocolatDepot",0),
	CARAMEL_DEPOT("CaramelDepot",0),
	AU1_CAKES_POS("Au1CakesPos",0),
	AU2_BLISTER_POS("Au2BlisterPos",0),
	AU3_PACKAGE_POS("Au3PackagePos",0),
	TABLE_CONTENT("TableContent",0),
	ROBOT_SET_CONTENT("RobotSetContent",0),
	CONVEYOR_BELT_1_SPEED("ConveyorBelt1Speed",0),
	CONVEYOR_BELT_1_SIZE("ConveyorBelt1Size",0),
	CONVEYOR_BELT_2_SPEED("ConveyorBelt2Speed",0),
	CONVEYOR_BELT_2_SIZE("ConveyorBelt2Size",0),
	CONVEYOR_BELT_3_SPEED("ConveyorBelt3Speed",0),
	CONVEYOR_BELT_3_SIZE("ConveyorBelt3Size",0),
	CONVEYOR_BELT_MOVE("ConveyorBeltMove",0),
	NUM_GOOD_PACKAGES("NumGoodPackages",0),
	NUM_BAD_PACKAGES("NumGoodPackages",0),
	RB1_BLISTER_DELAY("Rb1BlisterDelay",0),
	RB1_CAKE_DELAY("Rb1CakeDelay",0),
	RB2_BLISTER_DELAY("Rb2BlisterDelay",0);
	
	
	private final String name;
	private int parametersNumber;
	//private String[] parametros;
	
	private MSGOntology(String className, int paramNumber){
		this.name=className;
		this.parametersNumber=paramNumber;
		// this.parametros=parametros;
	}

	public String getName() {
		return name;
	}

	public int getParametersNumber() {
		return parametersNumber;
	}
	
}
