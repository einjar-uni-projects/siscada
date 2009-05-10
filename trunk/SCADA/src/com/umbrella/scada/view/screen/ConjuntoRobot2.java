package com.umbrella.scada.view.screen;


//TODO cambiar ConjuntoCintaPor conjunto robot? o generico?
public class ConjuntoRobot2 extends ConjuntoCinta {

	private PaintElementRobot _robot; 
	
	public ConjuntoRobot2(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model);
		_robot = new PaintElementRobot(loader,posX,posY,maxX,maxY, model);
		_paintElements.add(_robot);
	}

	public void cintaOn(boolean on){
		_robot.setOn(on);
	}

}
