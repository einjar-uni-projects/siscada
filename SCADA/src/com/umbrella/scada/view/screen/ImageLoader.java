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
	private Image _cinta;
	
	private int _auxNumIncremental;
	
	public ImageLoader(JPanel panel){
		MediaTracker tracker = new MediaTracker(panel);
		Toolkit toolkit = panel.getToolkit();
		
		_backImage = toolkit.getImage("resources/backImage.png");
		
		_expendedora = toolkit.getImage("resources/expendedora.png");
		_expendedoraChoc = toolkit.getImage("resources/expendedoraChoc.png");
		_expendedoraCaram = toolkit.getImage("resources/expendedoraCaram.png");
		_estados = toolkit.getImage("resources/estados.png");
		_estadosRojo = toolkit.getImage("resources/estadosRojo.png");
		_estadosAnarillo = toolkit.getImage("resources/estadosAmarillo.png");
		_estadosVerde = toolkit.getImage("resources/estadosVerde.png");
		_masa = toolkit.getImage("resources/masa.png");
		_masaChoc = toolkit.getImage("resources/masaChoc.png");
		_cinta = toolkit.getImage("resources/cinta.png");
		
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
		tracker.addImage(_cinta, getNextNumIncr());
		
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
	
}
