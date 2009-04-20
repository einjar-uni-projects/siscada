package com.umbrella.scada.view.screen;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MainPanel extends JPanel{

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(100, 100, 100, 100);
	}
}
