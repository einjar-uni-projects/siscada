package com.umbrella.scada.view.screen;

import javax.swing.Action;

import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;

public class MainFrameModel {
	//Variables generales
	private LanguageIDs _selectedLanguage;
	private UpdatableInterface _mainFrame;
	
	//ACTIONS
	
	//
	
	

	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	private MainFrameModel() {
		_selectedLanguage = LanguageIDs.SPANISHLOCALE;
	}

	/**
	 * Obtiene la instancia única del objeto, la primera invocación
	 * realiza la creación del mismo.
	 * @return la instancia única de MainFrameModel
	 */
	public static MainFrameModel getInstance() {
		return SingletonHolder.instance;
	}
	
	public void initialize(){
		if(_mainFrame == null)
			_mainFrame = new MainFrame();
	}

	private static class SingletonHolder {
		private static MainFrameModel instance = new MainFrameModel();
	}

	public LanguageIDs get_selectedLanguage() {
		return _selectedLanguage;
	}

	public void set_selectedLanguage(LanguageIDs language) {
		_selectedLanguage = language;
		_mainFrame.updateLanguage();
		
	}
	
	
	
	
	
	

}
