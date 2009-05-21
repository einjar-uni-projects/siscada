package com.umbrella.scada.view.screen.elementsGroups;

import java.awt.Graphics;

import com.umbrella.scada.view.screen.ImageLoader;
import com.umbrella.scada.view.screen.MainFrameModel;
import com.umbrella.scada.view.screen.MainFrameModel.ElementsGroupModelEnum;
import com.umbrella.scada.view.screen.paintElements.PaintElementRobot;


//TODO cambiar ConjuntoCintaPor conjunto robot? o generico?
public class ConjuntoRobot2 extends ElementsGroup {

	private PaintElementRobot _robot; 
	
	public ConjuntoRobot2(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model, ElementsGroupModelEnum.ROBOT2);
		_robot = new PaintElementRobot(loader,posX,posY,maxX,maxY, model);
		_paintElements.add(_robot);
	}
	
	@Override
	public void paint(Graphics g) {
		_robot.setState(_model.get_rb2Content());
		super.paint(g);
	}
}
