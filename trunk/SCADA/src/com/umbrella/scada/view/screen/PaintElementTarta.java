package com.umbrella.scada.view.screen;

import java.awt.Graphics;
import java.awt.Image;

public class PaintElementTarta extends PaintElement {

	//TODO necesario? boolean paso = false;
	int estado = 0;
	int position = 0;
	int pos = 0;
	
	protected PaintElementTarta(ImageLoader loader, int posX, int posY,	int maxX, int maxY) {
		super(loader, posX+30, posY, maxY, maxY);	
	}
	
	@Override
	public void paint(Graphics g) {
		switch (position) {
		case 0:
			g.drawImage(getMasaImage(), _posX, _posY, _maxX, _maxY, null);
			break;
		case 1:
			g.drawImage(getMasaImage(), _posX+52, _posY, _maxX, _maxY, null);
			break;
		case 2:
			g.drawImage(getMasaImage(), _posX+105, _posY, _maxX, _maxY, null);
			/*TODO cambiar*/estado = 1;
			break;
		case 3:
			g.drawImage(getMasaImage(), _posX+157, _posY, _maxX, _maxY, null);
			break;
		case 4:
			g.drawImage(getMasaImage(), _posX+210, _posY, _maxX, _maxY, null);
			/*TODO cambiar*/estado = 2;
			break;
		case 5:
			g.drawImage(getMasaImage(), _posX+262, _posY, _maxX, _maxY, null);
			break;
		case 6:
			g.drawImage(getMasaImage(), _posX+310, _posY, _maxX, _maxY, null);
			break;
		default:
			g.drawImage(getMasaImage(), _posX, _posY, _maxX, _maxY, null);
			break;
		}
		
		prueba();
	}
	
	private Image getMasaImage() {
		Image ret = null;
		switch (estado) {
		case 0:
			ret = _loader.get_masa();
			break;
		case 1:
			ret = _loader.get_masaChoc();
			break;
		case 2:
			ret = _loader.get_masaCaram();
			break;
		default:
			ret = _loader.get_masa();
			break;
		}
		return ret;
	}

	private void prueba(){
		pos++;
		if(pos%100 == 0)
			position++;
	}

}
