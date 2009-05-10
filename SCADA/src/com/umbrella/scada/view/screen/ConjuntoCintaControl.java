package com.umbrella.scada.view.screen;


public class ConjuntoCintaControl extends ConjuntoCinta {

	private PaintElementCinta _cinta;
	private PaintElementBlisterIni _blister_ini;
	private PaintElementBlister _blister;
	
	public ConjuntoCintaControl(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model);
		_cinta = new PaintElementCinta(loader,posX,posY+maxY-100,maxX,100, model);
		_blister = new  PaintElementBlister(loader, posX, posY+maxY-127, 100, 100, model);
		_paintElements.add(_cinta);
		_paintElements.add(_blister_ini);
		_paintElements.add(_blister);
	}

	public void cintaOn(boolean on){
		_cinta.setOn(on);
	}

}
