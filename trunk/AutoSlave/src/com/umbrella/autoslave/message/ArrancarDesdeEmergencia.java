package com.umbrella.autoslave.message;

import com.umbrella.autoslave.logic.Configuracion;
import com.umbrella.autoslave.logic.Contexto;

public class ArrancarDesdeEmergencia implements MessageInterface{
	
	private String identificador;
	private Configuracion configuracion;
	private Contexto contexto;
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
	public Contexto getContexto() {
		return contexto;
	}
	public void setContexto(Contexto contexto) {
		this.contexto = contexto;
	}
	
}
