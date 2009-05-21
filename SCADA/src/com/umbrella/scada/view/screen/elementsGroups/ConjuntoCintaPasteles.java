package com.umbrella.scada.view.screen.elementsGroups;

import java.awt.Graphics;

import com.umbrella.scada.view.screen.ImageLoader;
import com.umbrella.scada.view.screen.MainFrameModel;
import com.umbrella.scada.view.screen.MainFrameModel.ElementsGroupModelEnum;
import com.umbrella.scada.view.screen.paintElements.PaintElementCake;
import com.umbrella.scada.view.screen.paintElements.PaintElementCinta;
import com.umbrella.scada.view.screen.paintElements.PaintElementExpendedoraCaramelo;
import com.umbrella.scada.view.screen.paintElements.PaintElementExpendedoraChocolate;
import com.umbrella.scada.view.screen.paintElements.PaintElementExpendedoraMasa;


public class ConjuntoCintaPasteles extends ElementsGroup {

	private PaintElementCinta _cinta;
	private PaintElementExpendedoraMasa _expendedora_masa;
	private PaintElementExpendedoraChocolate _expendedora_chocolate;
	private PaintElementExpendedoraCaramelo _expendedora_caramelo;
	private PaintElementCake _tarta;
	
	public ConjuntoCintaPasteles(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model, ElementsGroupModelEnum.CINTA1);
		_cinta = new PaintElementCinta(loader,posX,posY+maxY-50,maxX,50, model);
		_expendedora_masa = new PaintElementExpendedoraMasa(loader, posX, posY, 100, 100, model);
		_expendedora_chocolate= new PaintElementExpendedoraChocolate(loader, posX+105, posY, 100, 100, model);
		_expendedora_caramelo= new PaintElementExpendedoraCaramelo(loader, posX+210, posY, 100, 100, model);
		_tarta = new PaintElementCake(loader,posX,posY+maxY-60,40,40, model);
		_paintElements.add(_cinta);
		_paintElements.add(_expendedora_masa);
		_paintElements.add(_expendedora_chocolate);
		_paintElements.add(_expendedora_caramelo);
		_paintElements.add(_tarta);
	}

	@Override
	public void paint(Graphics g) {
		_cinta.setOn(_model.is_cintaPasteles());
		super.paint(g);
	}
}
