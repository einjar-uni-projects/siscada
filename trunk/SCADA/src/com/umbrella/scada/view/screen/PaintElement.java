package com.umbrella.scada.view.screen;

import java.awt.Graphics;

/**
 * Clase abstracta que han de extender los objetos que pretendar ser pintados en la interfaz
 * @author Umbrella.Soft
 * @version 1.0
 *
 */
public abstract class PaintElement {

	/**
	 * Instancia del cargador de imágenes del sistema
	 */
	protected ImageLoader _loader;
	
	/**
	 * Posiciones:
	 *  _posX posición izquierda en el eje x
	 *  _posY posición superior en el eje y
	 *  _maxX tamaño en el eje x
	 *  _maxY tamaño en el eje y
	 */
	protected int _posX, _posY, _maxX, _maxY;
	
	/**
	 * Indica si el objeto está o no activado
	 */
	protected boolean _on = false;
	
	/**
	 * Instancia del modelo de la vista
	 */
	protected MainFrameModel _model;
	
	/**
	 * Constructor que almacena los parámetros de un elemento básico
	 * @param loader cargador de imágenes
	 * @param posX posición izquierda en el eje x
	 * @param posY posición superior en el eje y
	 * @param maxX tamaño en el eje x
	 * @param maxY tamaño en el eje y
	 * @param model modelo de la vista
	 */
	protected PaintElement(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model){
		_loader = loader;
		_posX = posX;
		_posY = posY;
		_maxX = maxX;
		_maxY = maxY;
		_model = model;
	}
	
	/**
	 * Método que se encarga de dibujar los elementos necesarios en el Graphics parado por parámetro
	 * @param g Graphics donde se pintarán los elementos
	 */
	public abstract void paint(Graphics g);
	
	/**
	 * Establece el valor de activado del sistema al parámetro pasado
	 * @param on nuevo valor de activado del sistema
	 */
	public void setOn(boolean on){
		_on = on;
	}
}
