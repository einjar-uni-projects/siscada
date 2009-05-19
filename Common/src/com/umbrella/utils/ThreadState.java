package com.umbrella.utils;

import java.io.Serializable;



/**
 * @author 
 *
 */
public enum ThreadState implements Serializable{
	CREADO("creado","hilo creado pero sin ejecutarse"),
	ESPERANDO("esperando","hilo en ejecucion que esta esperando por una viariable, similar al bloqueado"),
	EJECUTANDO("ejecutando","hilo ejecutando"),
	ACABADO("acabado","el hilo ha acabado, pero no esta muerto");
	
	private final String name;
	private final String description;
	
	
	/**
	 * @param name
	 * @param description
	 */
	private ThreadState(String name, String description){
		this.name = name;
		this.description = description;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param obj
	 * @return
	 */
	public boolean equals(ThreadState obj){
		boolean sal=true;
		if(this.name.compareTo(obj.getName())!=0) sal=false;
		if(this.description.compareTo(obj.getDescription())!=0) sal=false;
		return sal;
	}
}