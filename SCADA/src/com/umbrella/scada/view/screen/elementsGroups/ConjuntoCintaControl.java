package com.umbrella.scada.view.screen.elementsGroups;

import com.umbrella.scada.view.screen.ImageLoader;
import com.umbrella.scada.view.screen.MainFrameModel;
import com.umbrella.scada.view.screen.MainFrameModel.ElementsGroupModelEnum;
import com.umbrella.scada.view.screen.paintElements.PaintElementBlisterIni;
import com.umbrella.scada.view.screen.paintElements.PaintElementBlisterPasteles;
import com.umbrella.scada.view.screen.paintElements.PaintElementQuality;
import com.umbrella.scada.view.screen.paintElements.PaintElementCinta;
import com.umbrella.scada.view.screen.paintElements.PaintElementSelladora;


public class ConjuntoCintaControl extends ElementsGroup {

	private PaintElementCinta _cinta;
	private PaintElementBlisterIni _blister_ini;
	private PaintElementBlisterPasteles _paquetes;
	private PaintElementQuality _quality;
	private PaintElementSelladora _selladora;
	
	public ConjuntoCintaControl(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model, ElementsGroupModelEnum.CINTA3);
		_cinta = new PaintElementCinta(loader,posX,posY+maxY-100,maxX,100, model);
		_paquetes = new  PaintElementBlisterPasteles(loader, posX, posY+maxY-110, 80, 70, model);
		_quality = new PaintElementQuality(loader, posX+40, posY+15, 100, 100, model);
		_selladora = new PaintElementSelladora(loader, posX+140, posY, 100, 100, model);
		_paintElements.add(_cinta);
		_paintElements.add(_blister_ini);
		_paintElements.add(_paquetes);
		_paintElements.add(_quality);
		_paintElements.add(_selladora);
	}

	public void cintaOn(boolean on){
		_cinta.setOn(on);
	}

}
