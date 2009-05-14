package com.umbrella.scada.view.screen.elementsGroups;

import com.umbrella.scada.view.screen.ImageLoader;
import com.umbrella.scada.view.screen.MainFrameModel;
import com.umbrella.scada.view.screen.MainFrameModel.ElementsGroupModelEnum;
import com.umbrella.scada.view.screen.paintElements.PaintElementBlisterIni;
import com.umbrella.scada.view.screen.paintElements.PaintElementBlisterPasteles;
import com.umbrella.scada.view.screen.paintElements.PaintElementCinta;


public class ConjuntoCintaControl extends ElementsGroup {

	private PaintElementCinta _cinta;
	private PaintElementBlisterIni _blister_ini;
	private PaintElementBlisterPasteles _paquetes;
	
	public ConjuntoCintaControl(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model, ElementsGroupModelEnum.CINTA3);
		_cinta = new PaintElementCinta(loader,posX,posY+maxY-100,maxX,100, model);
		_paquetes = new  PaintElementBlisterPasteles(loader, posX, posY+maxY-110, 80, 70, model);
		_paintElements.add(_cinta);
		_paintElements.add(_blister_ini);
		_paintElements.add(_paquetes);
	}

	public void cintaOn(boolean on){
		_cinta.setOn(on);
	}

}
