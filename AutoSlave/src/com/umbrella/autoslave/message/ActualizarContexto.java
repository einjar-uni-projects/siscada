package com.umbrella.autoslave.message;

import com.umbrella.autoslave.logic.Contexto;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.OntologiaMSG;


public class ActualizarContexto implements MessageInterface{

	private String identificador;
	private Contexto contexto;
	private int maquina;
	private long click;

	public ActualizarContexto(){
	}
	public Contexto getContexto() {
		return contexto;
	}
	public void setContexto(Contexto contexto) {
		this.contexto = contexto;
	}
	public long getClick() {
		return click;
	}
	public void setClick(long click) {
		this.click = click;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public int getMaquina() {
		return maquina;
	}
	public void setMaquina(int maquina) {
		this.maquina = maquina;
	}
	
}
