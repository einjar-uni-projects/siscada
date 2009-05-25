package com.umbrella.scada.view.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.umbrella.scada.view.screen.elementsGroups.ConjuntoCintaBlister;
import com.umbrella.scada.view.screen.elementsGroups.ConjuntoCintaControl;
import com.umbrella.scada.view.screen.elementsGroups.ConjuntoCintaPasteles;
import com.umbrella.scada.view.screen.elementsGroups.ConjuntoRobot1;
import com.umbrella.scada.view.screen.elementsGroups.ConjuntoRobot2;
import com.umbrella.scada.view.screen.elementsGroups.ElementsGroup;

public class MainPanel extends JPanel{

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 7811238072037060451L;

	private ImageLoader _loader;
	
	private int _wallpaperWidth = 900;
	private int _wallpaperHeight = 600;
	
	
	private ElementsGroup[] _conjuntosCinta = new ElementsGroup[5];
	
	public MainPanel(MainFrameModel model){
		_loader = new ImageLoader(this);
		_conjuntosCinta[0] = new ConjuntoCintaPasteles(_loader, 10,0,400,200, model);
		_conjuntosCinta[1] = new ConjuntoCintaBlister(_loader, 10,375,400,200, model);
		_conjuntosCinta[2] = new ConjuntoCintaControl(_loader, 440,200,450,150, model);
		_conjuntosCinta[3] = new ConjuntoRobot1(_loader, 240,200,137,223, model);
		_conjuntosCinta[4] = new ConjuntoRobot2(_loader, 700,275,137,223, model);
		setMouseListener();
	}

	@Override
	public void paint(Graphics g) {
		
		Image alt = createImage(_wallpaperWidth, _wallpaperHeight);
		Graphics altGr = alt.getGraphics();
		altGr.setColor(Color.WHITE);
		altGr.fillRect(0, 0, _wallpaperWidth, _wallpaperHeight);
		for (int i = 0; i < _conjuntosCinta.length; i++) {
			_conjuntosCinta[i].paint(altGr);
		}

		g.drawImage(alt, 0, 0, getWidth(), getHeight(), null);
	}

	public void startAutRob() {
		for (int i = 0; i < _conjuntosCinta.length; i++) {
			_conjuntosCinta[i].start();
		}
		
	}
	public void stopAutRob() {
		for (int i = 0; i < _conjuntosCinta.length; i++) {
			_conjuntosCinta[i].stop();
		}
		
	}
	
	private void setMouseListener() {
		addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int x = (e.getX()*_wallpaperWidth)/getWidth();
				int y = (e.getY()*_wallpaperHeight)/getHeight();
				MainFrame main = MainFrame.getInstance();
				if(_conjuntosCinta[0].isInto(x, y))
					main.changeRightCard(1);
				else if(_conjuntosCinta[1].isInto(x, y))
					main.changeRightCard(2);
				else if(_conjuntosCinta[2].isInto(x, y))
					main.changeRightCard(3);
				else if(_conjuntosCinta[3].isInto(x, y))
					main.changeRightCard(4);
				else if(_conjuntosCinta[4].isInto(x, y))
					main.changeRightCard(5);
				else
					main.changeRightCard(0);
			}
		});
	}
}
