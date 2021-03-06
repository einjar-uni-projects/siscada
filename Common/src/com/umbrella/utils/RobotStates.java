package com.umbrella.utils;

import java.io.Serializable;

public enum RobotStates implements Serializable{
	REPOSO("Reposo","Esperando ordenes, sobre una posicion que no provoca interferencias", 0),
	CAMINOPOSICION_1("caminoposicion1","se desplaza a la posicion 1",1),
	SOBREPOSICION_1("sobreposicion1","Se encuentra sobre la posicion 1", 2),
	CAMINOPOSICION_2("caminoposicion2","se desplaza a la posicion 2",3),
	SOBREPOSICION_2("sobreposicion2","Se encuentra sobre la posicion 2", 3),
	CAMINOPOSICION_3("caminoposicion3","se desplaza a la posicion 3",5),
	SOBREPOSICION_3("sobreposicion3","Se encuentra sobre la posicion 3", 6),
	DESPLAZARBLISTERCOMPLETO("DesplazarBlisterCompleto","Desplaza el blister desde la mesa a la cinta 3", 7);
	
	
	private final String name;
	private final String descripcion;
	private final int valor;
	
	
	private RobotStates(String name, String descripcion, int valor){
		this.name = name;
		this.descripcion = descripcion;
		this.valor = valor;
	}

	public String getName() {
		return name;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public int getValor() {
		return valor;
	}
	
	public boolean equals(RobotStates obj){
		boolean sal=true;
		if(this.name.compareTo(obj.getName())!=0) sal=false;
		if(this.descripcion.compareTo(obj.getDescripcion())!=0) sal=false;
		if(this.valor!=obj.getValor()) sal = false;
		return sal;
	}
}
