package com.umbrella.scada.view.screen.elementsGroups;

import java.awt.Graphics;

import com.umbrella.scada.view.localization.LocalizationResources;
import com.umbrella.scada.view.localization.LocalizatorIDs;
import com.umbrella.scada.view.screen.ImageLoader;
import com.umbrella.scada.view.screen.MainFrameModel;
import com.umbrella.scada.view.screen.MainFrameModel.ElementsGroupModelEnum;
import com.umbrella.scada.view.screen.paintElements.PaintElementRobot;


//TODO cambiar ConjuntoCintaPor conjunto robot? o generico?
public class ConjuntoRobot2 extends ElementsGroup {

	private PaintElementRobot _robot;
	private LocalizationResources _languageResources = LocalizationResources.getInstance();
	
	public ConjuntoRobot2(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model) {
		super(loader, posX, posY, maxX, maxY, model, ElementsGroupModelEnum.ROBOT2);
		_robot = new PaintElementRobot(loader,posX,posY,maxX,maxY, model);
		_paintElements.add(_robot);
	}
	
	@Override
	public void paint(Graphics g) {
		MainFrameModel model = MainFrameModel.getInstance();
		String good = _languageResources.getLocal(LocalizatorIDs.CORRECT, _model.get_selectedLanguage())+": "+model.get_goodPackages();
		String bad = _languageResources.getLocal(LocalizatorIDs.INCORRECT, _model.get_selectedLanguage())+": "+model.get_badPackages();
		
		_robot.setState(_model.get_rb2Content());
		super.paint(g);
		g.drawImage(_loader.get_blister4Tarta(), _posX-50, _posY+230, 70, 70, null);
		g.drawImage(_loader.get_blister3Tarta(), _posX+100, _posY+230, 70, 70, null);
		g.drawChars(good.toCharArray(), 0, good.length(), _posX-50, _posY+310);
		g.drawChars(bad.toCharArray(), 0, bad.length(), _posX+100, _posY+310);
	}
}
