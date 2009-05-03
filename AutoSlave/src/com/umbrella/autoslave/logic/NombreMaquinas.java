package com.umbrella.autoslave.logic;



public enum NombreMaquinas {
	CINTA_1("cinta_1","cinta del automata 1", 0),
	CINTA_2("cinta_2","cinta del automata 2", 1),
	CINTA_3("cinta_3","cinta del automata 3", 2),
	CARAMELO("caramelo","maquina de recubierto de caramelo", 3),
	CHOCOLATE("chocolate","maquina de recubierto de chocolate", 4),
	DISPENSADORA("dispensadora","dispensadora de bizcochos", 5),
	CORTADORA("cortadora","coratodora de plasticos para hacer el blister", 6),
	TROQUELADORA("troqueladora","troqueladora del plastico para hacer la forma de los pasteles", 7),
	FIN_1("fin_1","fin de la cinta 1", 8),
	FIN_2("fin_2","fin de la cinta 2", 9),
	FIN_3("fin_3","fin de la cinta 3", 10),
	INICIO("inicio","inicio de la cinta 3", 11),
	CONTROL_CALIDAD("control_calidad","maquina de control de calidad", 12),
	SELLADO("sellado","sellado del blister y los pasteles", 13);
	
	private final String name;
	private final String descripcion;
	private final int descriptor;
	
	
	private NombreMaquinas(String name, String descripcion, int descriptor){
		this.name = name;
		this.descripcion = descripcion;
		this.descriptor = descriptor;
	}

	public String getName() {
		return name;
	}

	public int getDescriptor() {
		return descriptor;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public boolean equals(NombreMaquinas obj){
		boolean sal=true;
		if(this.name.compareTo(obj.getName())!=0) sal=false;
		if(this.descripcion.compareTo(obj.getDescripcion())!=0) sal=false;
		return sal;
	}
	
}