package com.umbrella.scada.view.screen.paintElements;

import java.awt.Color;
import java.awt.Graphics;

import com.umbrella.scada.view.screen.ImageLoader;
import com.umbrella.scada.view.screen.MainFrameModel;

/**
 * Elemento que representa los pasteles en cualquiera de sus formas
 * @author Umbrella.Soft
 * @version 1.0
 */
public class PaintElementCake extends PaintElement {
	
	/**
	 * Llama al padre con los parámetros
	 * @param loader cargador de imágenes
	 * @param posX posición izquierda en el eje x
	 * @param posY posición superior en el eje y
	 * @param maxX tamaño en el eje x
	 * @param maxY tamaño en el eje y
	 * @param model modelo de la vista
	 */
	public PaintElementCake(ImageLoader loader, int posX, int posY,	int maxX, int maxY, MainFrameModel model) {
		super(loader, posX+30, posY, maxY, maxY, model);	
	}
	
	@Override
	public void paint(Graphics g) {
		int [] pasteles = _model.get_pasteles();
		g.setColor(Color.BLACK);
		char[] numPasteles;
		
		if(pasteles[0] > 0){
			g.drawImage(_loader.get_masa(), _posX, _posY, _maxX, _maxY, null);
			numPasteles = (""+pasteles[0]).toCharArray();
			g.drawChars(numPasteles, 0, numPasteles.length, _posX+15-((numPasteles.length*5)/2), _posY);
		}
		if(pasteles[1] > 0){
			g.drawImage(_loader.get_masa(), _posX+52, _posY, _maxX, _maxY, null);
			numPasteles = (""+pasteles[1]).toCharArray();
			g.drawChars(numPasteles, 0, numPasteles.length, _posX+67-((numPasteles.length*5)/2), _posY+32);
		}
		if(pasteles[2] > 0){
			g.drawImage(_loader.get_masaChoc(), _posX+105, _posY, _maxX, _maxY, null);
			numPasteles = (""+pasteles[2]).toCharArray();
			g.drawChars(numPasteles, 0, numPasteles.length, _posX+120-((numPasteles.length*5)/2), _posY+32);
		}
		if(pasteles[3] > 0){
			g.drawImage(_loader.get_masaChoc(), _posX+157, _posY, _maxX, _maxY, null);
			numPasteles = (""+pasteles[3]).toCharArray();
			g.drawChars(numPasteles, 0, numPasteles.length, _posX+172-((numPasteles.length*5)/2), _posY+32);
		}
		if(pasteles[4] > 0){
			g.drawImage(_loader.get_masaCaram(), _posX+210, _posY, _maxX, _maxY, null);
			numPasteles = (""+pasteles[4]).toCharArray();
			g.drawChars(numPasteles, 0, numPasteles.length, _posX+225-((numPasteles.length*5)/2), _posY+32);
		}
		if(pasteles[5] > 0){
			g.drawImage(_loader.get_masaCaram(), _posX+262, _posY, _maxX, _maxY, null);
			numPasteles = (""+pasteles[5]).toCharArray();
			g.drawChars(numPasteles, 0, numPasteles.length, _posX+277-((numPasteles.length*5)/2), _posY+32);
		}
		if(pasteles[6] > 0){
			g.drawImage(_loader.get_masaCaram(), _posX+310, _posY, _maxX, _maxY, null);
			numPasteles = (""+pasteles[6]).toCharArray();
			g.drawChars(numPasteles, 0, numPasteles.length, _posX+325-((numPasteles.length*5)/2), _posY+32);
		}
	}

}
