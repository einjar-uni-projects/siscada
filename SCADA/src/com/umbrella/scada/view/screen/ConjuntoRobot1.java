package com.umbrella.scada.view.screen;

import com.umbrella.scada.view.screen.MainFrameModel.ElementsGroupModelEnum;


//TODO cambiar ConjuntoCintaPor conjunto robot? o generico?
public class ConjuntoRobot1 extends ElementsGroup {

	private PaintElementRobot _robot; 
	
	public ConjuntoRobot1(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model, ElementsGroupModelEnum.ROBOT1);
		_robot = new PaintElementRobot(loader,posX,posY,maxX,maxY, model);
		_paintElements.add(_robot);
	}

	public void cintaOn(boolean on){
		_robot.setOn(on);
	}

}