package com.umbrella.scada.view.screen.paintElements;

import java.awt.Graphics;

import com.umbrella.scada.view.screen.ImageLoader;
import com.umbrella.scada.view.screen.MainFrameModel;

/**
 * Elemento que representa las cintas transportadoras
 * @author Umbrella.Soft
 * @version 1.0
 */
public class PaintElementTable extends PaintElement {
	
	/**
	 * Llama al padre con los parámetros
	 * @param loader cargador de imágenes
	 * @param posX posición izquierda en el eje x
	 * @param posY posición superior en el eje y
	 * @param maxX tamaño en el eje x
	 * @param maxY tamaño en el eje y
	 * @param model modelo de la vista
	 */
	public PaintElementTable(ImageLoader loader, int posX, int posY,	int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(_loader.get_table(), _posX, _posY, _maxX, _maxY, null);
		switch(_model.get_tableContent()){
		case 1:
			g.drawImage(_loader.get_blisterCortado(),_posX+20,_posY-10,75,70,null);
			break;
		case 2:
			g.drawImage(_loader.get_blister1Tarta(),_posX+20,_posY-10,75,70,null);
			break;
		case 3:
			g.drawImage(_loader.get_blister2Tarta(),_posX+20,_posY-10,75,70,null);
			break;
		case 4:
			g.drawImage(_loader.get_blister3Tarta(),_posX+20,_posY-10,75,70,null);
			break;
		case 5:
			g.drawImage(_loader.get_blister4Tarta(),_posX+20,_posY-10,75,70,null);
			break;
		}
		//g.drawChars((""+_model.get_tableContent()).toCharArray(), 0, 1, _posX, _posY);

	}

}
