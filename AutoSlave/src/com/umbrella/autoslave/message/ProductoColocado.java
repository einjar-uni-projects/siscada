package com.umbrella.autoslave.message;

public class ProductoColocado implements MessageInterface{
	
	private String identificador;
	private long click;
	private String producto; //blister o pastel
	
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
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
}
