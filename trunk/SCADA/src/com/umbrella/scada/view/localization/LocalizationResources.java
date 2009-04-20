package com.umbrella.scada.view.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationResources {
	
	private static LocalizationResources _instance;
	
	public enum LanguageIDs {
		SPANISHLOCALE(new Locale("es", "ES")), // Espa�ol - Espa�a
		ENGLISHLOCALE(new Locale("en", "GB"));
		
		private ResourceBundle _resource;
		
		public ResourceBundle get_resource() {
			return _resource;
		}

		private LanguageIDs(Locale locale) {
			_resource = ResourceBundle.getBundle("com.umbrella.scada.view.localization.Resource", locale);
		}
		
	}

	protected LocalizationResources(){
		
	}
	
	public String getLocal(LocalizatorIDs id, LanguageIDs locale){
		return locale.get_resource().getString(id.name());
	}
	
	public static LocalizationResources getInstance(){
		if(_instance == null)
			_instance = new LocalizationResources();
		return _instance;
	}

}
