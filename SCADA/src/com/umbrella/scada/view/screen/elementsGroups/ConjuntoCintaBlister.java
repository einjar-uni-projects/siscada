package com.umbrella.scada.view.screen.elementsGroups;

import com.umbrella.scada.view.screen.ImageLoader;
import com.umbrella.scada.view.screen.MainFrameModel;
import com.umbrella.scada.view.screen.MainFrameModel.ElementsGroupModelEnum;
import com.umbrella.scada.view.screen.paintElements.PaintElementBlister;
import com.umbrella.scada.view.screen.paintElements.PaintElementBlisterIni;
import com.umbrella.scada.view.screen.paintElements.PaintElementCinta;


public class ConjuntoCintaBlister extends ElementsGroup {

	private PaintElementCinta _cinta;
	private PaintElementBlisterIni _blister_ini;
	private PaintElementBlister _blister;
	
	public ConjuntoCintaBlister(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model, ElementsGroupModelEnum.CINTA2);
		_cinta = new PaintElementCinta(loader,posX,posY+maxY-100,maxX,100, model);
		_blister_ini = new  PaintElementBlisterIni(loader, -30, posY+maxY-110, 100, 70, model);
		_blister = new  PaintElementBlister(loader, -30, posY+maxY-110, 100, 70, model);
		_paintElements.add(_cinta);
		_paintElements.add(_blister_ini);
		_paintElements.add(_blister);
	}

	public void cintaOn(boolean on){
		_cinta.setOn(on);
	}

}
