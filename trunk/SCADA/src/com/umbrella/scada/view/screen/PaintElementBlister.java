package com.umbrella.scada.view.screen;

import java.awt.Graphics;
import java.awt.Image;

public class PaintElementBlister extends PaintElement {
	
	int estado = 0;
	int position = 0;
	int pos = 0;

	protected PaintElementBlister(ImageLoader loader, int posX, int posY,	int maxX, int maxY) {
		super(loader, posX, posY, maxX, maxY);
	}

	@Override
	public void paint(Graphics g) {
		switch (position) {
		case 0:
			g.drawImage(getBlisterImage(), _posX, _posY, _maxX, _maxY, null);
			break;
		case 1:
			g.drawImage(getBlisterImage(), _posX+75, _posY, _maxX, _maxY, null);
			break;
		case 2:
			g.drawImage(getBlisterImage(), _posX+152, _posY, _maxX, _maxY, null);
			break;
		case 3:
			g.drawImage(getBlisterImage(), _posX+250, _posY, _maxX, _maxY, null);
			break;
		case 4:
			g.drawImage(getBlisterImage(), _posX+320, _posY, _maxX, _maxY, null);
			break;
		default:
			g.drawImage(getBlisterImage(), _posX, _posY, _maxX, _maxY, null);
			break;
		}
		prueba();
	}

	private void prueba() {
		pos++;
		if(pos%100 == 0)
			position++;
	}

	private Image getBlisterImage() {
		Image ret = null;
		switch (estado) {
		case 0:
			ret = _loader.get_blisterBruto();
			break;
		case 1:
			ret = _loader.get_blisterEstampado();
			break;
		case 2:
			ret = _loader.get_blisterCortado();
			break;
		case 3:
			ret = _loader.get_blister1Tarta();
			break;
		case 4:
			ret = _loader.get_blister2Tarta();
			break;
		case 5:
			ret = _loader.get_blister3Tarta();
			break;
		case 6:
			ret = _loader.get_blister4Tarta();
			break;
		}
		return ret;
	}

}
