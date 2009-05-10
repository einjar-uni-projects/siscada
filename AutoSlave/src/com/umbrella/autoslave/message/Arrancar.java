package com.umbrella.autoslave.message;

import com.umbrella.autoslave.logic.Configuracion;

public class Arrancar implements MessageInterface{
	
	private String identificador;
	private Configuracion configuracion;
	private long click;
	
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public Configuracion getConfiguracion() {
		return configuracion;
	}
	public void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}
	public long getClick() {
		return click;
	}
	public void setClick(long click) {
		this.click = click;
	}
}
