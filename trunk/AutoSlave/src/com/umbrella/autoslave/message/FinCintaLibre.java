package com.umbrella.autoslave.message;

public class FinCintaLibre implements MessageInterface{
	
	private String identificador;
	private long click;
	private int numeroCinta;
	
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
	public int getNumeroCinta() {
		return numeroCinta;
	}
	public void setNumeroCinta(int numeroCinta) {
		this.numeroCinta = numeroCinta;
	}
}
