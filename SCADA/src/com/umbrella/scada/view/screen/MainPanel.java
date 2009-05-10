package com.umbrella.scada.view.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class MainPanel extends JPanel{

	private ImageLoader _loader;
	
	private int _wallpaperWidth = 800;
	private int _wallpaperHeight = 600;
	
	private long _time1;
	private long _time2;
	
	
	private ConjuntoCinta[] _conjuntosCinta = new ConjuntoCinta[5];
	
	public MainPanel(MainFrameModel model){
		_loader = new ImageLoader(this);
		_conjuntosCinta[0] = new ConjuntoCintaPasteles(_loader, 10,20,400,200, model);
		_conjuntosCinta[0].cintaOn(true);
		_conjuntosCinta[1] = new ConjuntoCintaBlister(_loader, 10,375,400,200, model);
		_conjuntosCinta[1].cintaOn(true);
		_conjuntosCinta[2] = new ConjuntoCintaControl(_loader, 440,200,350,150, model);
		_conjuntosCinta[2].cintaOn(true);
		_conjuntosCinta[3] = new ConjuntoRobot1(_loader, 250,225,200,150, model);
		_conjuntosCinta[3].cintaOn(true);
		_conjuntosCinta[4] = new ConjuntoRobot2(_loader, 560,275,200,150, model);
		_conjuntosCinta[4].cintaOn(true);
	}
	
	@Override
	public void paint(Graphics g) {
		_time1 = System.currentTimeMillis();
		Image alt = createImage(_wallpaperWidth, _wallpaperHeight);
		Graphics altGr = alt.getGraphics();
		altGr.setColor(Color.WHITE);
		altGr.fillRect(0, 0, _wallpaperWidth, _wallpaperHeight);
		//_conjuntosCinta[0]._loader = _loader; TODO LOL
		for (int i = 0; i < _conjuntosCinta.length; i++) {
			_conjuntosCinta[i].paint(altGr);
		}
		
		/*altGr.drawImage(_loader.get_backImage(), 0, 0, _wallpaperWidth, _wallpaperHeight, null);
		altGr.drawImage(_loader.get_cinta1(), 50, 200, null);
		altGr.drawImage(_loader.get_cinta1(), 50, 450, null);
		altGr.drawImage(_loader.get_cinta1(), 450, 325, null);
		altGr.drawImage(_loader.get_expendedora(), 100, 100, null);
		altGr.drawImage(_loader.get_masa(), 100, 120, null);*/
		
		_time2 = System.currentTimeMillis();
		
		long sleep = 200-(_time2-_time1);
		if(sleep > 0)
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		g.drawImage(alt, 0, 0, getWidth(), getHeight(), null);
		repaint();
	}
}
