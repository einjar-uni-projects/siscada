package com.umbrella.scada.view.screen;


public class ConjuntoCintaBlister extends ConjuntoCinta {

	private PaintElementCinta _cinta;
	private PaintElementBlisterIni _blister_ini;
	private PaintElementBlister _blister;
	
	public ConjuntoCintaBlister(ImageLoader loader, int posX, int posY, int maxX, int maxY) {
		super(loader, posX, posY, maxX, maxY);
		_cinta = new PaintElementCinta(loader,posX,posY+maxY-100,maxX,100);
		_blister_ini = new  PaintElementBlisterIni(loader, -30, posY+maxY-127, 100, 100);
		_blister = new  PaintElementBlister(loader, -30, posY+maxY-127, 100, 100);
		_paintElements.add(_cinta);
		_paintElements.add(_blister_ini);
		_paintElements.add(_blister);
	}

	public void cintaOn(boolean on){
		_cinta.setOn(on);
	}

}
