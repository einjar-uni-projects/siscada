package com.umbrella.scada.controller;


public interface ParamGroup<K> {
	
	public <T> void setParam(K param, Class<T> val_class, T value);
	
	public Object getParam(K param);

}
