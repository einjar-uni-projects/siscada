package com.umbrella.autoslave.message;

import com.umbrella.autoslave.logic.Configuracion;

public class Parada implements MessageInterface{
	
	private String identificador;
	private long click;
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public long getClick() {
		return click;
	}
	public void setClick(long click) {
		this.click = click;
	}
}
