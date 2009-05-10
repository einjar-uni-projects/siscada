package com.umbrella.autoslave.message;

import com.umbrella.autoslave.utils2.Pastel;

public class PastelListo implements MessageInterface{
	
	private String identificador;
	private Pastel pastel;
	private long click;
	
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public Pastel getPastel() {
		return pastel;
	}
	public void setPastel(Pastel pastel) {
		this.pastel = pastel;
	}
	public long getClick() {
		return click;
	}
	public void setClick(long click) {
		this.click = click;
	}
}
