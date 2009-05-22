package com.umbrella.scada.view.screen.paintElements;

import java.awt.Color;
import java.awt.Graphics;

import com.umbrella.scada.view.screen.ImageLoader;
import com.umbrella.scada.view.screen.MainFrameModel;

/**
 * Elemento que representa los blísters en la cinta correspondiente
 * @author Umbrella.Soft
 * @version 1.0
 */
public class PaintElementBlister extends PaintElement {

	/**
	 * Llama al padre con los parámetros
	 * @param loader cargador de imágenes
	 * @param posX posición izquierda en el eje x
	 * @param posY posición superior en el eje y
	 * @param maxX tamaño en el eje x
	 * @param maxY tamaño en el eje y
	 * @param model modelo de la vista
	 */
	public PaintElementBlister(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model);
	}

	@Override
	public void paint(Graphics g) {
		int [] blisters = _model.get_blisters();
		g.setColor(Color.BLACK);
		char[] numBlisters;
		
		/*if(blisters[0] > 0){
			g.drawImage(_loader.get_blisterBruto(), _posX, _posY, _maxX, _maxY, null);
			numBlisters = (""+blisters[0]).toCharArray();
			g.drawChars(numBlisters, 0, numBlisters.length, _posX+15, _posY+80);
		}*/
		if(blisters[1] > 0){
			g.drawImage(_loader.get_blisterEstampado(), _posX+75, _posY, _maxX, _maxY, null);
			numBlisters = (""+blisters[1]).toCharArray();
			g.drawChars(numBlisters, 0, numBlisters.length, _posX+_maxX/2+75, _posY+80);
		}
		if(blisters[2] > 0){
			g.drawImage(_loader.get_blisterEstampado(), _posX+152, _posY, _maxX, _maxY, null);
			numBlisters = (""+blisters[0]).toCharArray();
			g.drawChars(numBlisters, 0, numBlisters.length, _posX+_maxX/2+152, _posY+80);
		}
		if(blisters[3] > 0){
			g.drawImage(_loader.get_blisterCortado(), _posX+235, _posY, _maxX, _maxY, null);
			numBlisters = (""+blisters[3]).toCharArray();
			g.drawChars(numBlisters, 0, numBlisters.length, _posX+_maxX/2+235, _posY+80);
		}
		if(blisters[4] > 0){
			g.drawImage(_loader.get_blisterCortado(), _posX+320, _posY, _maxX, _maxY, null);
			numBlisters = (""+blisters[4]).toCharArray();
			g.drawChars(numBlisters, 0, numBlisters.length, _posX+_maxX/2+320, _posY+80);
		}
	}
}
