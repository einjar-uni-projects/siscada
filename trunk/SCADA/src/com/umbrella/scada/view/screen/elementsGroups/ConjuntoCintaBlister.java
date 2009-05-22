package com.umbrella.scada.view.screen.elementsGroups;

import java.awt.Graphics;

import com.umbrella.scada.view.screen.ImageLoader;
import com.umbrella.scada.view.screen.MainFrameModel;
import com.umbrella.scada.view.screen.MainFrameModel.ElementsGroupModelEnum;
import com.umbrella.scada.view.screen.paintElements.PaintElementBlister;
import com.umbrella.scada.view.screen.paintElements.PaintElementBlisterIni;
import com.umbrella.scada.view.screen.paintElements.PaintElementCinta;
import com.umbrella.scada.view.screen.paintElements.PaintElementCutter;
import com.umbrella.scada.view.screen.paintElements.PaintElementEstampadora;


public class ConjuntoCintaBlister extends ElementsGroup {

	private PaintElementCinta _cinta;
	private PaintElementBlisterIni _blister_ini;
	private PaintElementBlister _blister;
	private PaintElementEstampadora _estampadora;
	private PaintElementCutter _cutter;
	
	public ConjuntoCintaBlister(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model, ElementsGroupModelEnum.CINTA2);
		_cinta = new PaintElementCinta(loader,posX,posY+maxY-100,maxX,100, model);
		_blister_ini = new  PaintElementBlisterIni(loader, -30, posY+maxY-110, 100, 70, model);
		_blister = new  PaintElementBlister(loader, -30, posY+maxY-110, 100, 70, model);
		_estampadora = new PaintElementEstampadora(loader, posX+27, posY+50, 100, 70, model);
		_cutter = new PaintElementCutter(loader, posX+115, posY+50, 100, 70, model);
		_paintElements.add(_cinta);
		_paintElements.add(_blister_ini);
		_paintElements.add(_blister);
		_paintElements.add(_cutter);
		_paintElements.add(_estampadora);
		
	}

	@Override
	public void paint(Graphics g) {
		_cinta.setOn(_model.is_cintaBlister());
		super.paint(g);
	}
}
