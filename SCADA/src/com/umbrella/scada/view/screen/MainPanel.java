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
		altGr.drawImage(_loader.get_greenCircle(), 100, 100, null);
		
		g.drawImage(alt, 0, 0, getWidth(), getHeight(), null);
	}
}
