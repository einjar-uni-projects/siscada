package com.umbrella.scada.view.screen;

import java.awt.Graphics;

public class PaintElementExpendedoraMasa extends PaintElement {

	//TODO necesario? boolean paso = false;
	
	protected PaintElementExpendedoraMasa(ImageLoader loader, int posX, int posY,	int maxX, int maxY) {
		super(loader, posX, posY, maxX, maxY);
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(_loader.get_expendedora(), _posX, _posY, _maxX, _maxY, null);

	}

}
