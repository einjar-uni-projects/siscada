package com.umbrella.autoslave.message;

import com.umbrella.autoslave.utils2.Blister;

public class BlisterCompleto implements MessageInterface{
	
	private String identificador;
	private Blister blister;
	private long click;
	private int automata;
	
	
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
	public Blister getBlister() {
		return blister;
	}
	public void setBlister(Blister blister) {
		this.blister = blister;
	}
	public int getAutomata() {
		return automata;
	}
	public void setAutomata(int automata) {
		this.automata = automata;
	}
}
