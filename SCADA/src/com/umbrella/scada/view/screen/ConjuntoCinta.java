package com.umbrella.scada.view.screen;

import java.awt.Graphics;

public abstract class ConjuntoCinta {

	protected ImageLoader _loader;
	protected int _posX, _posY;
	
	protected ConjuntoCinta(ImageLoader loader, int posX, int posY){
		_loader = loader;
		_posX = posX;
		_posY = posY;
	}
	public abstract void paint(Graphics g);
}
