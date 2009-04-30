package com.umbrella.scada.view.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class MainPanel extends JPanel{

	private Image _backImage;
	private MainFrameModel _model;
	private ImageLoader _loader;
	
	private int _wallpaperWidth = 800;
	private int _wallpaperHeight = 600;
	
	public MainPanel(MainFrameModel model){
		_model = model;
		_loader = new ImageLoader(this);
	}
	
	@Override
	public void paint(Graphics g) {
		Image alt = createImage(_wallpaperWidth, _wallpaperHeight);
		Graphics altGr = alt.getGraphics();
		altGr.drawImage(_loader.get_backImage(), 0, 0, _wallpaperWidth, _wallpaperHeight, null);
		altGr.drawImage(_loader.get_cinta1(), 50, 200, null);
		altGr.drawImage(_loader.get_cinta1(), 50, 450, null);
		altGr.drawImage(_loader.get_cinta1(), 450, 325, null);
		altGr.drawImage(_loader.get_expendedora(), 100, 100, null);
		altGr.drawImage(_loader.get_masa(), 100, 120, null);
		
		g.drawImage(alt, 0, 0, getWidth(), getHeight(), null);
	}
}
