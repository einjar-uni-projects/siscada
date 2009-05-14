package com.umbrella.scada.view.screen;

import java.awt.Graphics;

/**
 * Elemento que representa el blíster inicial
 * @author Umbrella.Soft
 * @version 1.0
 */
public class PaintElementBlisterIni extends PaintElement {

	/**
	 * Llama al padre con los parámetros
	 * @param loader cargador de imágenes
	 * @param posX posición izquierda en el eje x
	 * @param posY posición superior en el eje y
	 * @param maxX tamaño en el eje x
	 * @param maxY tamaño en el eje y
	 * @param model modelo de la vista
	 */
	public PaintElementBlisterIni(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(_loader.get_blisterBruto(), _posX, _posY, _maxX, _maxY, null);
	}

}
