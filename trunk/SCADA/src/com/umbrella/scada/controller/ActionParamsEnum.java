package com.umbrella.scada.controller;

public enum ActionParamsEnum {
	STATE(Boolean.class),
	MACHINE(String.class),
	SPEED(Integer.class),
	SIZE(Integer.class),
	CAKE_QUANTITY(Integer.class),
	CHOCOLAT_QUANTITY(Integer.class),
	CARAMEL_QUANTITY(Integer.class), 
	CAKE_DEPOT(Integer.class);
	
	private Class _enclosedClass;

	private ActionParamsEnum(Class enclosedClass){
		_enclosedClass = enclosedClass;
	}

	public <T>Class<T> getEnclosedClass() {
		return _enclosedClass;
		
	}
	
}
