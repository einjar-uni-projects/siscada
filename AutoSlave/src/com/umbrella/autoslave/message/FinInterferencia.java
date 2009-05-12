package com.umbrella.autoslave.message;

import com.umbrella.mail.message.MessageInterface;

public class FinInterferencia implements MessageInterface{

	private String identificador;
	private int robot;
	private int cinta;
	private long click;

	
	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public int getRobot() {
		return robot;
	}

	public void setRobot(int robot) {
		this.robot = robot;
	}

	public int getCinta() {
		return cinta;
	}

	public void setCinta(int cinta) {
		this.cinta = cinta;
	}

	public long getClick() {
		return click;
	}

	public void setClick(long click) {
		this.click = click;
	}
}
