package com.umbrella.autoslave.message;

public class RellanarMaquina implements MessageInterface{
	
	private String identificador;
	private long click;
	private String maquina;
	private int cantidad;
	
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
	public String getMaquina() {
		return maquina;
	}
	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}
	public synchronized int getCantidad() {
		return cantidad;
	}
	public synchronized void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
}
