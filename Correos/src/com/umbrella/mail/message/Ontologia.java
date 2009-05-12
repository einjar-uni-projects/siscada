package com.umbrella.mail.message;




/* 
 * tiene el nombre de todos los mensajes que se pueden usar
 */
public enum Ontologia {
	//ACTUALIZARCONTEXTO("ActualizarContexto",4,new String[]{"identificador", "click", "contexto", "maquina"}),
	ACTUALIZARCONTEXTO("ActualizarContexto",4),
	ACTUALIZARCONTEXTOROBOT("ActualizarContextoRobot",4),
	INTERFERENCIA("Interferencia",4),
	FININTERFERENCIA("FinInterferencia",4),
	PASTELLISTO("PastelListo",3),
	BLISTERLISTO("BlisterListo",4),
	BLISTERALMACENADO("BlisterAlmacenado",3),
	ARRANCAR("Arrancar",3),
	PARADA("Parada",2),
	PARADAEMERGENCIA("ParadaEmergencia",2),
	ARRANCARDESDEEMERGENCIA("ArrancarDesdeEmergencia",4),
	RESET("Reset",2),
	PRODUCTORECOGIDO("ProductoRecogido",4),
	FINCINTALIBRE("FinCintaLibre",3),
	PRODUCTOCOLOCADO("ProductoColocado",3),
	RELLENARMAQUINA("RellanarMaquina",3),
	MODIFICARCAMPO("ModificarCampo",4),
	MOVERDESDEMESA("MoverDesdeMesa",2);
	
	
	private final String nombre;
	private int numParametros;
	//private String[] parametros;
	
	private Ontologia(String nombreClase, int numParametros){
		this.nombre=nombreClase;
		this.numParametros=numParametros;
		// this.parametros=parametros;
	}

	public String getNombre() {
		return nombre;
	}

	public int getNumParametros() {
		return numParametros;
	}
	
}
