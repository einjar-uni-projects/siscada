package com.umbrella.scada.view.screen.elementsGroups;

import java.awt.Graphics;

import com.umbrella.scada.view.screen.ImageLoader;
import com.umbrella.scada.view.screen.MainFrameModel;
import com.umbrella.scada.view.screen.MainFrameModel.ElementsGroupModelEnum;
import com.umbrella.scada.view.screen.paintElements.PaintElementBlisterPasteles;
import com.umbrella.scada.view.screen.paintElements.PaintElementCinta;
import com.umbrella.scada.view.screen.paintElements.PaintElementQuality;
import com.umbrella.scada.view.screen.paintElements.PaintElementQualityBk;
import com.umbrella.scada.view.screen.paintElements.PaintElementSelladora;
import com.umbrella.scada.view.screen.paintElements.PaintElementTable;


public class ConjuntoCintaControl extends ElementsGroup {

	private PaintElementCinta _cinta;
	private PaintElementTable _table;

	private PaintElementBlisterPasteles _paquetes;
	private PaintElementQualityBk _qualitybg;
	private PaintElementQuality _qualityfg;
	private PaintElementSelladora _selladora;
	
	public ConjuntoCintaControl(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model, ElementsGroupModelEnum.CINTA3);
		_cinta = new PaintElementCinta(loader,posX,posY+maxY-100,maxX,100, model);
		_table = new PaintElementTable(loader,posX-100,posY+maxY-100,120,100, model);
		_paquetes = new  PaintElementBlisterPasteles(loader, posX, posY+maxY-110, 80, 70, model);
		int qx = 100;
		double qscale = 0.6;
		_qualitybg = new PaintElementQualityBk(loader, posX+qx, posY+15, (int)(14*qscale), 100, model);
		qx += 14*qscale;
		_qualityfg = new PaintElementQuality(loader, posX+qx, posY+15, (int)(86*qscale), 100, model);
		_selladora = new PaintElementSelladora(loader, posX+160, posY-25, 90, 100, model);
		_paintElements.add(_qualitybg);
		_paintElements.add(_cinta);
		_paintElements.add(_table);
		_paintElements.add(_paquetes);
		_paintElements.add(_qualityfg);
		_paintElements.add(_selladora);
	}

	@Override
	public void paint(Graphics g) {
		_cinta.setOn(_model.is_au3Move());
		super.paint(g);
	}
}
