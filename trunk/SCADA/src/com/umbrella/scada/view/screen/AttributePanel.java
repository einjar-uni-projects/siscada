package com.umbrella.scada.view.screen;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.umbrella.scada.view.localization.LocalizationResources;

/**
 * Esta clase abstracta debe ser extendida por todas aquellas que quieran actuar como elementos de atributos del panel derecho
 * @author Umbrella.Soft
 * @version 1.0
 *
 */
public abstract class AttributePanel extends JPanel {
	
	/**
	 * Título de la ventana de atributos
	 */
	protected JLabel _title = new JLabel();
	
	/**
	 * instancia almacenada del modelo de la vista
	 */
	protected MainFrameModel _model = MainFrameModel.getInstance();
	
	/**
	 * instancia almacenada de la clase encargada de obtener las cadenas de idioma
	 */
	protected LocalizationResources _languageResources = LocalizationResources.getInstance();
	
	/**
	 * Constructor que añade el título al panel
	 */
	protected AttributePanel() {
		add(_title);
	}
	
	/**
	 * Método encargado de inicializar los valones necesarios de los campos
	 */
	protected abstract void initialize();
	
	/**
	 * Método encargado de actualizar el lenguaje al actual de la aplicación, se deberá llamar cuando se produzca un cambio en el lenguaje
	 */
	public abstract void updateLanguage();
}
