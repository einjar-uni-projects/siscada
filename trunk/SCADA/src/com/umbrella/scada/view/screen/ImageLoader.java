package com.umbrella.scada.view.screen;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class ImageLoader {

	private static ImageLoader _instance = null;
	
	private Image _backImage;
	private Image _redSquare;
	private Image _yellowSquare;
	private Image _greenSquare;
	private Image _redCircle;
	private Image _yellowCircle;
	private Image _greenCircle;
	
	public ImageLoader(JPanel panel){
		MediaTracker tracker = new MediaTracker(panel);
		Toolkit toolkit = panel.getToolkit();
		
		_backImage = toolkit.getImage("resources/backImage.png");
		_redSquare = toolkit.getImage("resources/redSquare.png");
		_yellowSquare = toolkit.getImage("resources/yellowSquare.png");
		_greenSquare = toolkit.getImage("resources/greenSquare.png");
		_redCircle = toolkit.getImage("resources/redCircle.png");
		_yellowCircle = toolkit.getImage("resources/yellowCircle.png");
		_greenCircle = toolkit.getImage("resources/greenCircle.png");
		
		tracker.addImage(_backImage, 1);
		tracker.addImage(_redSquare, 2);
		tracker.addImage(_yellowSquare, 3);
		tracker.addImage(_greenSquare, 4);
		tracker.addImage(_redCircle, 5);
		tracker.addImage(_yellowCircle, 6);
		tracker.addImage(_greenCircle, 7);
		
		try {
			tracker.waitForAll();
			if(tracker.isErrorAny())
				System.err.println("Error al cargar los elementos de imagen");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Image get_backImage() {
		return _backImage;
	}

	public Image get_redSquare() {
		return _redSquare;
	}

	public Image get_yellowSquare() {
		return _yellowSquare;
	}

	public Image get_greenSquare() {
		return _greenSquare;
	}

	public Image get_redCircle() {
		return _redCircle;
	}

	public Image get_yellowCircle() {
		return _yellowCircle;
	}

	public Image get_greenCircle() {
		return _greenCircle;
	}
	
}
