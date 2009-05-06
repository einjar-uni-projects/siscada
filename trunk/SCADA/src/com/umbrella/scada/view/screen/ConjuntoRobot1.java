package com.umbrella.scada.view.screen;


//TODO cambiar ConjuntoCintaPor conjunto robot? o generico?
public class ConjuntoRobot1 extends ConjuntoCinta {

	private PaintElementRobot _robot; 
	
	public ConjuntoRobot1(ImageLoader loader, int posX, int posY, int maxX, int maxY) {
		super(loader, posX, posY, maxX, maxY);
		_robot = new PaintElementRobot(loader,posX,posY,maxX,maxY);
		_paintElements.add(_robot);
	}

	public void cintaOn(boolean on){
		_robot.setOn(on);
	}

}
