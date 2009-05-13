package com.umbrella.scada.controller;

public enum ActionParamsEnum {
	STATE(Boolean.class), MACHINE(String.class);
	
	private Class _enclosedClass;

	private ActionParamsEnum(Class enclosedClass){
		_enclosedClass = enclosedClass;
	}

	public <T>Class<T> getEnclosedClass() {
		return _enclosedClass;
		
	}
	
}
