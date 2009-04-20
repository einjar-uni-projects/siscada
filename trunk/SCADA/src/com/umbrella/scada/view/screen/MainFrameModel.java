package com.umbrella.scada.view.screen;

import java.awt.Image;

import javax.swing.Action;

import sun.awt.image.ToolkitImage;

import com.umbrella.scada.observer.ObserverProvider;
import com.umbrella.scada.observer.TransferBuffer;
import com.umbrella.scada.observer.TransferBufferKeys;
import com.umbrella.scada.view.Updatable;
import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;

public class MainFrameModel implements Updatable{
	//Variables generales
	private LanguageIDs _selectedLanguage;
	private UpdatableInterface _mainFrame;
	private Image _backImage;
	
	//ACTIONS
	
	

	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	private MainFrameModel() {
		_selectedLanguage = LanguageIDs.SPANISHLOCALE;
		_backImage = _mainFrame.getToolkit().getImage("resource/backImage.png");
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
		if(_mainFrame == null){
			_mainFrame = new MainFrame();
			ObserverProvider.getInstance().registerUpdatable(this);
		}
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

	@Override
	public void update(TransferBuffer buffer) {
		//Se obtienen las claves
		TransferBufferKeys[] tbk = TransferBufferKeys.values();
		Object value;
		
		//Se recorren las claves
		for (int i = 0; i < tbk.length; i++) {
			//Se obtiene el valor de la clave
			value = buffer.getElement(tbk[i]);
			if(value != null){
				
			//Hay que incluir un case para cada clave
			switch (tbk[i]) {
				case NULL:
					
					break;
	
				default:
					break;
				}
			}
		}
		_mainFrame.updateData();
	}

	public Image get_backImage() {
		return _backImage;
	}
	
	
	
	
	
	

}
