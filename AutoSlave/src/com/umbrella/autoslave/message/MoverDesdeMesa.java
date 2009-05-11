package com.umbrella.autoslave.message;

public class MoverDesdeMesa implements MessageInterface{
	
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
