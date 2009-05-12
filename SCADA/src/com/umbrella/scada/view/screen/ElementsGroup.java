package com.umbrella.scada.view.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import com.umbrella.scada.Launch;
import com.umbrella.scada.view.screen.MainFrameModel.ElementsGroupModelEnum;

public abstract class ElementsGroup {
	protected final ElementsGroupModelEnum _identifierClass;
	protected ImageLoader _loader;
	protected int _posX, _posY, _maxX, _maxY;
	protected LinkedList<PaintElement> _paintElements;
	protected MainFrameModel _model;
	protected boolean _started;
	protected boolean _init;
	
	protected ElementsGroup(ImageLoader loader, int posX, int posY, int maxX, int maxY, MainFrameModel model, ElementsGroupModelEnum identifierClass){
		_identifierClass = identifierClass;
		_loader = loader;
		_posX = posX;
		_posY = posY;
		_maxX = maxX;
		_maxY = maxY;
		_paintElements = new LinkedList<PaintElement>();
		_model = model;
		_started = false;
		_init = false;
	}
	
	public void paint(Graphics g){
		if(Launch.debug){
			g.setColor(Color.BLACK);
			g.drawRect(_posX, _posY, _maxX, _maxY);
		}
		for (PaintElement elem : _paintElements) {
			if(elem != null)
				elem.paint(g);
		}
		
		_started = MainFrameModel.getInstance().isStarted(_identifierClass);
		if(_init){
			if(_started){
				g.drawImage(_loader.get_estadosVerde(), _posX, _maxY+_posY, 52, 19, null);
			}else{
				g.drawImage(_loader.get_estadosAnarillo(), _posX, _maxY+_posY, 52, 19, null);
			}
		}else{
			if(_started){
				g.drawImage(_loader.get_estadosAnarillo(), _posX, _maxY+_posY, 52, 19, null);
			}else{
				g.drawImage(_loader.get_estadosRojo(), _posX, _maxY+_posY, 52, 19, null);
			}
		}
	}
	
	public void start(){
		_init = true;
	}
	
	public void stop(){
		_init = false;
	}
	
	public abstract void cintaOn(boolean on);
}
