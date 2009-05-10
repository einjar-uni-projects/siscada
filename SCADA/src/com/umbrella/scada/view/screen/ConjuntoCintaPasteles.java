package com.umbrella.scada.view.screen;


public class ConjuntoCintaPasteles extends ConjuntoCinta {

	private PaintElementCinta _cinta;
	private PaintElementExpendedoraMasa _expendedora_masa;
	private PaintElementExpendedoraChocolate _expendedora_chocolate;
	private PaintElementExpendedoraCaramelo _expendedora_caramelo;
	private PaintElementTarta _tarta;
	
	public ConjuntoCintaPasteles(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model);
		_cinta = new PaintElementCinta(loader,posX,posY+maxY-50,maxX,50, model);
		_expendedora_masa = new PaintElementExpendedoraMasa(loader, posX, posY, 100, 100, model);
		_expendedora_chocolate= new PaintElementExpendedoraChocolate(loader, posX+105, posY, 100, 100, model);
		_expendedora_caramelo= new PaintElementExpendedoraCaramelo(loader, posX+210, posY, 100, 100, model);
		_tarta = new PaintElementTarta(loader,posX,posY+maxY-60,40,40, model);
		_paintElements.add(_cinta);
		_paintElements.add(_expendedora_masa);
		_paintElements.add(_expendedora_chocolate);
		_paintElements.add(_expendedora_caramelo);
		_paintElements.add(_tarta);
	}

	public void cintaOn(boolean on){
		_cinta.setOn(on);
	}

}
