package com.umbrella.scada.controller;

public interface Factory<T,K,P extends ParamGroup> {
	
	public T factoryMethod(K key, P param);

}
