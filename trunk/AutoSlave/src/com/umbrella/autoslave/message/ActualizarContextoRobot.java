package com.umbrella.autoslave.message;

import com.umbrella.autoslave.logic.ContextoRobot;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.Ontologia;

public class ActualizarContextoRobot implements MessageInterface{

	private String identificador;
	private ContextoRobot contextoRobot;
	private int maquina;
	private long click;

	public ActualizarContextoRobot(){
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
	public ContextoRobot getContextoRobot() {
		return contextoRobot;
	}
	public void setContextoRobot(ContextoRobot contextoRobot) {
		this.contextoRobot = contextoRobot;
	}
	public int getMaquina() {
		return maquina;
	}
	public void setMaquina(int maquina) {
		this.maquina = maquina;
	}
	
}
