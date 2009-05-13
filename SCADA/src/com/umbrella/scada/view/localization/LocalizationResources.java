package com.umbrella.scada.view.localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que carga los ficheros de idioma y permite obtener las cadenas de texto a partir de los LocalizatorID que las identifican y del idioma indicado mediante alguno de los Locale definidos en el anumerado
 * @author Umbrella.Soft
 * @version 1.0
 *
 */
public class LocalizationResources {
	
	private static LocalizationResources _instance;
	
	/**
	 * Enumerado que almacena los objetos de la clase Locale que admite nuestro sistema, castellano e inglés.
	 *
	 */
	public enum LanguageIDs {
		SPANISHLOCALE(new Locale("es", "ES")), // Español - España
		ENGLISHLOCALE(new Locale("en", "GB"));
		
		private ResourceBundle _resource;
		
		private ResourceBundle get_resource() {
			return _resource;
		}

		private LanguageIDs(Locale locale) {
			_resource = ResourceBundle.getBundle("com.umbrella.scada.view.localization.Resource", locale);
		}
		
	}

	protected LocalizationResources(){
		
	}
	
	/**
	 * Obtiene la cadena representada por el identificador indicado para el lenguaje dado.
	 * @param id identificador de la cadena deseada.
	 * @param locale idioma que se desea
	 * @return cadena de caracteres que contiene la representación del identificador en el idioma indicado
	 */
	public String getLocal(LocalizatorIDs id, LanguageIDs locale){
		return locale.get_resource().getString(id.name());
	}
	
	/**
	 * Método estático que permite la obtención de la instancia única (Singleton) de LocalizationResources
	 * @return instancia de LocalizationResources
	 */
	public static LocalizationResources getInstance(){
		if(_instance == null)
			_instance = new LocalizationResources();
		return _instance;
	}

}
