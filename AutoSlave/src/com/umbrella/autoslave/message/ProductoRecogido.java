package com.umbrella.autoslave.message;

import com.umbrella.autoslave.utils2.Blister;
import com.umbrella.mail.message.MessageInterface;

public class ProductoRecogido implements MessageInterface{
	//lo envia el robot cuando recoge el producto
	
	private String identificador;
	private long click;
	private String tipo;//blister, pastel, blister Completo
	private int robot;
	
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getRobot() {
		return robot;
	}
	public void setRobot(int robot) {
		this.robot = robot;
	}
}
