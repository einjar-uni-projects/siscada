package com.umbrella.scada.view.screen;

import java.awt.Graphics;
import java.util.LinkedList;

public abstract class ConjuntoCinta {

	protected ImageLoader _loader;
	protected int _posX, _posY, _maxX, _maxY;
	protected LinkedList<PaintElement> _paintElements;
	
	protected ConjuntoCinta(ImageLoader loader, int posX, int posY, int maxX, int maxY){
		_loader = loader;
		_posX = posX;
		_posY = posY;
		_maxX = maxX;
		_maxY = maxY;
		_paintElements = new LinkedList<PaintElement>();
	}
	
	public void paint(Graphics g){
		for (PaintElement elem : _paintElements) {
			elem.paint(g);
		}
	}
	
	public abstract void cintaOn(boolean on);
}
