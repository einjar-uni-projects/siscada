package com.umbrella.scada.view.screen.paintElements;

import java.awt.Color;
import java.awt.Graphics;

import com.umbrella.scada.view.screen.ImageLoader;
import com.umbrella.scada.view.screen.MainFrameModel;

/**
 * Elemento que representa los paquetes montados de blister y pasteles
 * @author Umbrella.Soft
 * @version 1.0
 */
public class PaintElementBlisterPasteles extends PaintElement {

	/**
	 * Llama al padre con los parámetros
	 * @param loader cargador de imágenes
	 * @param posX posición izquierda en el eje x
	 * @param posY posición superior en el eje y
	 * @param maxX tamaño en el eje x
	 * @param maxY tamaño en el eje y
	 * @param model modelo de la vista
	 */
	public PaintElementBlisterPasteles(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model);
	}

	@Override
	public void paint(Graphics g) {
		int [] paquetes = _model.get_paquetes();
		g.setColor(Color.BLACK);
		char[] numPaquetes;
		int dx = 15;
		if(paquetes[0] > 0){
			g.drawImage(_loader.get_blister4Tarta(), _posX+dx, _posY, _maxX, _maxY, null);
			numPaquetes = (""+paquetes[0]).toCharArray();
			g.drawChars(numPaquetes, 0, numPaquetes.length, _posX+dx+(_maxX/2), _posY);
		}
		dx += 75;
		if(paquetes[1] > 0){
			g.drawImage(_loader.get_blister4Tarta(), _posX+dx, _posY, _maxX, _maxY, null);
			numPaquetes = (""+paquetes[1]).toCharArray();
			//g.drawChars(numPaquetes, 0, numPaquetes.length, _posX+80, _posY+32);
		}
		dx += 75;
		if(paquetes[2] > 0){
			g.drawImage(_loader.get_blister4Tarta(), _posX+dx, _posY, _maxX, _maxY, null);
			numPaquetes = (""+paquetes[0]).toCharArray();
			//g.drawChars(numPaquetes, 0, numPaquetes.length, _posX+167, _posY+32);
		}
		dx += 75;
		if(paquetes[3] > 0){
			g.drawImage(_loader.get_blister4Tarta(), _posX+dx, _posY, _maxX, _maxY, null);
			numPaquetes = (""+paquetes[3]).toCharArray();
			g.drawChars(numPaquetes, 0, numPaquetes.length, _posX+dx+(_maxX/2), _posY);
		}
		dx += 75;
		if(paquetes[4] > 0){
			g.drawImage(_loader.get_blister4Tarta(), _posX+dx, _posY, _maxX, _maxY, null);
			numPaquetes = (""+paquetes[4]).toCharArray();
			g.drawChars(numPaquetes, 0, numPaquetes.length, _posX+dx+(_maxX/2), _posY);
		}	
	}
}
