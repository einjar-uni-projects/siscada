package com.umbrella.scada.view.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationResources {
	private ResourceBundle _spanishResource;
	private ResourceBundle _englishResource;
	
	private static LocalizationResources _instance;
	
	public static final String SPANISHLOCALE = "SP";
	public static final String ENGLISHLOCALE = "EN";

	protected LocalizationResources(){
		Locale spanishLocale = new Locale("es", "ES"); // Español - España
		Locale englishLocale = new Locale("en", "GB"); // Inglés - GB
		_spanishResource = ResourceBundle.getBundle("com.umbrella.scada.view.localization.Resource", spanishLocale);
		_englishResource = ResourceBundle.getBundle("com.umbrella.scada.view.localization.Resource", englishLocale);
	}
	
	public String getLocal(LocalizatorIDs id, String locale){
		if(locale.compareTo(SPANISHLOCALE) == 0)
			return _spanishResource.getString(id.name());
		if(locale.compareTo(ENGLISHLOCALE) == 0)
			return _englishResource.getString(id.name());
		return null;
	}
	
	public static LocalizationResources getInstance(){
		if(_instance == null)
			_instance = new LocalizationResources();
		return _instance;
	}

}
