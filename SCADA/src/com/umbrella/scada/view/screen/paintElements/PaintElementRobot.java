package com.umbrella.scada.view.screen.paintElements;

import java.awt.Graphics;

import com.umbrella.scada.view.screen.ImageLoader;
import com.umbrella.scada.view.screen.MainFrameModel;

/**
 * Elemento que representa los robots del sistema
 * @author Umbrella.Soft
 * @version 1.0
 */
public class PaintElementRobot extends PaintElement {
	
	int _state = 0;
	
	/**
	 * Llama al padre con los parámetros
	 * @param loader cargador de imágenes
	 * @param posX posición izquierda en el eje x
	 * @param posY posición superior en el eje y
	 * @param maxX tamaño en el eje x
	 * @param maxY tamaño en el eje y
	 * @param model modelo de la vista
	 */
	public PaintElementRobot(ImageLoader loader, int posX, int posY,	int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model);
	}

	@Override
	public void paint(Graphics g) {
		switch (_state) {
		case 0:
			g.drawImage(_loader.get_robot(), _posX, _posY, _maxX, _maxY, null);
			break;
		case 1:
			g.drawImage(_loader.get_robotBlister(), _posX, _posY, _maxX, _maxY, null);
			break;
		case 2:
			g.drawImage(_loader.get_robotTarta(), _posX, _posY, _maxX, _maxY, null);
			break;
		case 3:
			g.drawImage(_loader.get_robotTartaDef(), _posX, _posY, _maxX, _maxY, null);
			break;
		case 4:
			//TODO faltan con 4 tartas
			g.drawImage(_loader.get_robot(), _posX, _posY, _maxX, _maxY, null);
			break;
		default:
			g.drawImage(_loader.get_cinta2(), _posX, _posY, _maxX, _maxY, null);
			break;
		}

	}
	
	public void setState(int state){
		_state = state;
	}

}
