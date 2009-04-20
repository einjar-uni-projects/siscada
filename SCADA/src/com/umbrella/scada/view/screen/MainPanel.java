package com.umbrella.scada.view.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class MainPanel extends JPanel{

	private Image _backImage;
	private MainFrameModel _model;
	
	public MainPanel(MainFrameModel model){
		_model = model;
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(_model.get_backImage(), 0, 0, WIDTH, HEIGHT, null);
		g.setColor(Color.BLUE);
		g.fillOval(100, 100, 100, 100);
	}
}
