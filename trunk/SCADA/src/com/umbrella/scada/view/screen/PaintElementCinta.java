package com.umbrella.scada.view.screen;

import java.awt.Graphics;

/**
 * Elemento que representa las cintas transportadoras
 * @author Umbrella.Soft
 * @version 1.0
 */
public class PaintElementCinta extends PaintElement {

	/**
	 * Permite la animación de la cinta identificando el paso actual de esta
	 */
	private boolean paso = false;
	
	/**
	 * Llama al padre con los parámetros
	 * @param loader cargador de imágenes
	 * @param posX posición izquierda en el eje x
	 * @param posY posición superior en el eje y
	 * @param maxX tamaño en el eje x
	 * @param maxY tamaño en el eje y
	 * @param model modelo de la vista
	 */
	public PaintElementCinta(ImageLoader loader, int posX, int posY,	int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model);
	}

	@Override
	public void paint(Graphics g) {
		if(paso)
			g.drawImage(_loader.get_cinta2(), _posX, _posY, _maxX, _maxY, null);
		else
			g.drawImage(_loader.get_cinta1(), _posX, _posY, _maxX, _maxY, null);
		if(_on)
			paso = !paso;
	}

}
