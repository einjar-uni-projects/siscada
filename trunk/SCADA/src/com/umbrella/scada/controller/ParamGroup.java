package com.umbrella.scada.controller;


public interface ParamGroup<K> {
	
	public <T> void setParam(K param, Class<T> val_class, T value);
	
	public <T> T getParam(K param, Class<T> ret_class);

}
