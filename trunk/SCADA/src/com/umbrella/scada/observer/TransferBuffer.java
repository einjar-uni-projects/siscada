package com.umbrella.scada.observer;

import java.util.EnumMap;

public class TransferBuffer {
	
	private EnumMap<TransferBufferKeys, Object> _buffer = new EnumMap<TransferBufferKeys, Object>(TransferBufferKeys.class);
	
	public boolean setElement(TransferBufferKeys key, Object value){
		_buffer.put(key, value);
		return true;
	}
	
	
	public Object getElement(TransferBufferKeys key){
		return null;
	}


	public int size() {
		return _buffer.size();
	}

}
