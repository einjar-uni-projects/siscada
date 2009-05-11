package com.umbrella.scada.model;

import com.umbrella.scada.observer.TransferBufferKeys;

public class ModelElementAtribute<T> {
	private final TransferBufferKeys _tbk;
	private T _value;
	
	public ModelElementAtribute(TransferBufferKeys key, T value){
		_tbk = key;
		_value = value;
	}

	public T get_value() {
		return _value;
	}

	public void set_value(T value) {
		this._value = value;
	}

	public TransferBufferKeys get_tbk() {
		return _tbk;
	}
}
