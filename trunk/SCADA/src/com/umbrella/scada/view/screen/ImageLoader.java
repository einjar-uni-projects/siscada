package com.umbrella.scada.view.screen;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class ImageLoader {
	
	private Image _backImage;
	private Image _expendedora;
	private Image _expendedoraChoc;
	private Image _expendedoraCaram;
	private Image _estados;
	private Image _estadosRojo;
	private Image _estadosAnarillo;
	private Image _estadosVerde;
	private Image _masa;
	private Image _masaChoc;
	private Image _masaCaram;
	private Image _cinta1;
	private Image _cinta2;
	
	private int _auxNumIncremental;
	
	public ImageLoader(JPanel panel){
		MediaTracker tracker = new MediaTracker(panel);
		Toolkit toolkit = panel.getToolkit();
		
		_backImage = toolkit.getImage("resources/backImage.png");
		
		_expendedora = toolkit.getImage("resources/expendedora.png");
		_expendedoraChoc = toolkit.getImage("resources/expendedoraChocolate.png");
		_expendedoraCaram = toolkit.getImage("resources/expendedoraCaramelo.png");
		_estados = toolkit.getImage("resources/estados.png");
		_estadosRojo = toolkit.getImage("resources/estadosRojo.png");
		_estadosAnarillo = toolkit.getImage("resources/estadosAmarillo.png");
		_estadosVerde = toolkit.getImage("resources/estadosVerde.png");
		_masa = toolkit.getImage("resources/masa.png");
		_masaChoc = toolkit.getImage("resources/masaChoc.png");
		_masaCaram = toolkit.getImage("resources/masaCaramelo.png");
		_cinta1 = toolkit.getImage("resources/cinta1.png");
		_cinta2 = toolkit.getImage("resources/cinta2.png");
		
		tracker.addImage(_backImage, getNextNumIncr());
		tracker.addImage(_expendedora, getNextNumIncr());
		tracker.addImage(_expendedoraChoc, getNextNumIncr());
		tracker.addImage(_expendedoraCaram, getNextNumIncr());
		tracker.addImage(_estados, getNextNumIncr());
		tracker.addImage(_estadosRojo, getNextNumIncr());
		tracker.addImage(_estadosAnarillo, getNextNumIncr());
		tracker.addImage(_estadosVerde, getNextNumIncr());
		tracker.addImage(_masa, getNextNumIncr());
		tracker.addImage(_masaChoc, getNextNumIncr());
		tracker.addImage(_masaCaram, getNextNumIncr());
		tracker.addImage(_cinta1, getNextNumIncr());
		tracker.addImage(_cinta2, getNextNumIncr());
		
		try {
			tracker.waitForAll();
			if(tracker.isErrorAny())
				System.err.println("Error al cargar los elementos de imagen");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int getNextNumIncr(){
		return ++_auxNumIncremental;
	}

	public Image get_backImage() {
		return _backImage;
	}

	public Image get_expendedora() {
		return _expendedora;
	}

	public Image get_estados() {
		return _estados;
	}

	public Image get_estadosRojo() {
		return _estadosRojo;
	}

	public Image get_estadosAnarillo() {
		return _estadosAnarillo;
	}

	public Image get_estadosVerde() {
		return _estadosVerde;
	}

	public Image get_masa() {
		return _masa;
	}

	public Image get_masaChoc() {
		return _masaChoc;
	}

	public Image get_expendedoraChoc() {
		return _expendedoraChoc;
	}

	public Image get_expendedoraCaram() {
		return _expendedoraCaram;
	}

	public Image get_cinta1() {
		return _cinta1;
	}

	public Image get_cinta2() {
		return _cinta2;
	}

	public Image get_masaCaram() {
		return _masaCaram;
	}
	
}
