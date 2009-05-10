package com.umbrella.autoslave.utils2;



public enum EstateThreads {
	CREADO("creado","hilo creado pero sin ejecutarse"),
	ESPERANDO("esperando","hilo en ejecucion que esta esperando por una viariable, similar al bloqueado"),
	EJECUTANDO("ejecutando","hilo ejecutando"),
	ACABADO("acabado","el hilo ha acabado, pero no esta muerto");
	
	private final String name;
	private final String descripcion;
	
	
	private EstateThreads(String name, String descripcion){
		this.name = name;
		this.descripcion = descripcion;
	}

	public String getName() {
		return name;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public boolean equals(EstateThreads obj){
		boolean sal=true;
		if(this.name.compareTo(obj.getName())!=0) sal=false;
		if(this.descripcion.compareTo(obj.getDescripcion())!=0) sal=false;
		return sal;
	}
}