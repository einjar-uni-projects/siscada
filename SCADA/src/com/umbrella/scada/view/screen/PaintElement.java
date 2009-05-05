package com.umbrella.scada.view.screen;

import java.awt.Graphics;

public abstract class PaintElement {

	protected ImageLoader _loader;
	protected int _posX, _posY, _maxX, _maxY;
	protected boolean _on = false;
	
	protected PaintElement(ImageLoader loader, int posX, int posY, int maxX, int maxY){
		_loader = loader;
		_posX = posX;
		_posY = posY;
		_maxX = maxX;
		_maxY = maxY;
	}
	
	public abstract void paint(Graphics g);
	
	public void setOn(boolean on){
		_on = on;
	}
}
