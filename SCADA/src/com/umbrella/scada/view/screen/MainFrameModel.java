package com.umbrella.scada.view.screen;

import javax.swing.Action;

import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;
import com.umbrella.scada.view.screen.actions.ChangeLanguageAction;

public class MainFrameModel {
	//Variables generales
	private LanguageIDs _selectedLanguage;
	
	//ACTIONS
	private final Action _changeLanguage = new ChangeLanguageAction();
	
	

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

	public LanguageIDs get_selectedLanguage() {
		return _selectedLanguage;
	}
	
	public Action get_changeLanguage() {
		return _changeLanguage;
	}
	
	
	
	
	
	

}
