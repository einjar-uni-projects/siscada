package com.umbrella.autoslave.message;

import com.umbrella.autoslave.logic.Configuracion;
import com.umbrella.mail.message.MessageInterface;

public class AvisarUnFallo implements MessageInterface{

	private String identificador;
	private int maquina;
	private long click;
	private String motivo;
	public synchronized String getIdentificador() {
		return identificador;
	}
	public synchronized void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public synchronized int getMaquina() {
		return maquina;
	}
	public synchronized void setMaquina(int maquina) {
		this.maquina = maquina;
	}
	public synchronized long getClick() {
		return click;
	}
	public synchronized void setClick(long click) {
		this.click = click;
	}
	public synchronized String getMotivo() {
		return motivo;
	}
	public synchronized void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}