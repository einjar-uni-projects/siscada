package com.umbrella.scada.view.screen;

import com.umbrella.scada.view.localization.LocalizationResources;
import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;

public class MainFrameModel {
	//Variables generales
	private LanguageIDs _selectedLanguage;
	
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
	static MainFrameModel getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static MainFrameModel instance = new MainFrameModel();
	}
	
	public void changeSelectedLanguage(){
		
	}

	public LanguageIDs get_selectedLanguage() {
		return _selectedLanguage;
	}
	
	
	
	
	
	

}
