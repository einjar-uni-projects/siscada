package com.umbrella.scada.view.screen;

import java.awt.Graphics;

public class PaintElementRobot extends PaintElement {
	
	int _state = 0;
	
	protected PaintElementRobot(ImageLoader loader, int posX, int posY,	int maxX, int maxY) {
		super(loader, posX, posY, maxX, maxY);
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

}
