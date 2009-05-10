package com.umbrella.autoslave.message;

public class BlisterAlmacenado implements MessageInterface{
	
	private String identificador;
	private long click;
	private boolean validez;
	
	
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
	public boolean isValidez() {
		return validez;
	}
	public void setValidez(boolean validez) {
		this.validez = validez;
	}
	
	
}
