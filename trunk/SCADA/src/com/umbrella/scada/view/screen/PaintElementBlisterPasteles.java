package com.umbrella.scada.view.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class PaintElementBlisterPasteles extends PaintElement {

	protected PaintElementBlisterPasteles(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model);
	}

	@Override
	public void paint(Graphics g) {
		int [] paquetes = _model.get_paquetes();
		g.setColor(Color.BLACK);
		char[] numPaquetes;
		
		if(paquetes[0] > 0){
			g.drawImage(_loader.get_blister4Tarta(), _posX, _posY, _maxX, _maxY, null);
			numPaquetes = (""+paquetes[0]).toCharArray();
			g.drawChars(numPaquetes, 0, numPaquetes.length, _posX+15, _posY+32);
		}
		if(paquetes[1] > 0){
			g.drawImage(_loader.get_blister4Tarta(), _posX+75, _posY, _maxX, _maxY, null);
			numPaquetes = (""+paquetes[1]).toCharArray();
			g.drawChars(numPaquetes, 0, numPaquetes.length, _posX+80, _posY+32);
		}
		if(paquetes[2] > 0){
			g.drawImage(_loader.get_blister4Tarta(), _posX+152, _posY, _maxX, _maxY, null);
			numPaquetes = (""+paquetes[0]).toCharArray();
			g.drawChars(numPaquetes, 0, numPaquetes.length, _posX+167, _posY+32);
		}
		if(paquetes[3] > 0){
			g.drawImage(_loader.get_blister4Tarta(), _posX+250, _posY, _maxX, _maxY, null);
			numPaquetes = (""+paquetes[3]).toCharArray();
			g.drawChars(numPaquetes, 0, numPaquetes.length, _posX+265, _posY+32);
		}	
	}
}