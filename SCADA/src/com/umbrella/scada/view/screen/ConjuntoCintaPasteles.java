package com.umbrella.scada.view.screen;

import java.awt.Graphics;

public class ConjuntoCintaPasteles extends ConjuntoCinta {

	private PaintElementCinta _cinta;
	
	public ConjuntoCintaPasteles(ImageLoader loader, int posX, int posY, int maxX, int maxY) {
		super(loader, posX, posY, maxX, maxY);
		_cinta = new PaintElementCinta(loader,posX,posY,posX+150,posY+50);
		
		_paintElements.add(_cinta);
	}

	public void cintaOn(boolean on){
		_cinta.setOn(on);
	}

}
