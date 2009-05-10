package com.umbrella.scada.view.screen;

import java.awt.Graphics;

public class PaintElementCinta extends PaintElement {

	boolean paso = false;
	
	protected PaintElementCinta(ImageLoader loader, int posX, int posY,	int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model);
	}

	@Override
	public void paint(Graphics g) {
		if(paso)
			g.drawImage(_loader.get_cinta2(), _posX, _posY, _maxX, _maxY, null);
		else
			g.drawImage(_loader.get_cinta1(), _posX, _posY, _maxX, _maxY, null);
		if(_on)
			paso = !paso;
	}

}
