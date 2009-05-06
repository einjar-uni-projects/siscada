package com.umbrella.scada.view.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import com.umbrella.scada.Launch;

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
		if(Launch.debug){
			g.setColor(Color.BLACK);
			g.drawRect(_posX, _posY, _maxX, _maxY);
		}
		for (PaintElement elem : _paintElements) {
			if(elem != null)
				elem.paint(g);
		}
	}
	
	public abstract void cintaOn(boolean on);
}
