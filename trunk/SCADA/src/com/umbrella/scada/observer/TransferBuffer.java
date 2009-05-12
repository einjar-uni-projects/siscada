package com.umbrella.scada.observer;

import java.util.EnumMap;

public class TransferBuffer {
	
	private EnumMap<TransferBufferKeys, Object> _buffer = new EnumMap<TransferBufferKeys, Object>(TransferBufferKeys.class);
	
	public boolean setElement(TransferBufferKeys key, Object value){
		_buffer.put(key, value);
		return true;
	}
	
	
	public Object getElement(TransferBufferKeys key){
		return _buffer.get(key);
	}


	public int size() {
		return _buffer.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		TransferBufferKeys[] tbk = TransferBufferKeys.values();
		Object value;
		
		/*TODO Debug*/System.out.println("> Actualizando");
		//Se recorren las claves
		for (int i = 0; i < tbk.length; i++) {
			//Se obtiene el valor de la clave
			System.out.println(tbk[i]);
			value = getElement(tbk[i]);
			System.out.println("="+value);
			
			if(value != null){
				sb.append(tbk[i]);
				sb.append(" ");
				sb.append(value);
				sb.append("\n");
			}
		}
		
		
		return sb.toString();
	}
}
