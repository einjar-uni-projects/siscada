package com.umbrella.scada.controller;

import java.util.ArrayList;

public enum ActionParamsEnum {
	STATE(Boolean.class),
	MACHINE(String.class),
	SPEED(Integer.class),
	SIZE(Integer.class),
	CAKE_QUANTITY(Integer.class),
	CHOCOLAT_QUANTITY(Integer.class),
	CARAMEL_QUANTITY(Integer.class), 
	CAKE_DEPOT(Integer.class), AU1_CAKES_POS(ArrayList.class);
	
	private Class _enclosedClass;

	private ActionParamsEnum(Class enclosedClass){
		_enclosedClass = enclosedClass;
	}

	public <T>Class<T> getEnclosedClass() {
		return _enclosedClass;
		
	}
	
}
