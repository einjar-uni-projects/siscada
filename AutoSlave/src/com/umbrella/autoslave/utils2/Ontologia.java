package com.umbrella.autoslave.utils2;

import java.util.Vector;

import com.umbrella.autoslave.logic.Contexto;

/* 
 * tiene el nombre de todos los mensajes que se pueden usar
 */
public enum Ontologia {
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
	RESET("Reset",2);
	
	private String nombre;
	private int numParametros;
	
	private Ontologia(String nombre, int numParametros){
		this.nombre=nombre;
		this.numParametros=numParametros;
	}

	public String getNombre() {
		return nombre;
	}

	public int getNumParametros() {
		return numParametros;
	}	
}
