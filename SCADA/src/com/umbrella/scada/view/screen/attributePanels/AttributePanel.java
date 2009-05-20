package com.umbrella.scada.view.screen.attributePanels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.umbrella.scada.controller.ActionParams;
import com.umbrella.scada.view.localization.LocalizationResources;
import com.umbrella.scada.view.screen.MainFrameModel;

/**
 * Esta clase abstracta debe ser extendida por todas aquellas que quieran actuar como elementos de atributos del panel derecho
 * @author Umbrella.Soft
 * @version 1.0
 *
 */
public abstract class AttributePanel extends JPanel {
	
	/**
	 * serial id
	 */
	private static final long serialVersionUID = -2151268303324866650L;

	/**
	 * Título de la ventana de atributos
	 */
	protected JLabel _title = new JLabel();
	
	/**
	 * Botón de aceptación de los datos introducidos
	 */
	protected JButton _acceptButton = new JButton("Accept");
	
	/**
	 * Matriz que almacena los paneles hijos
	 */
	protected AttributePanel [] _subPanels;
	
	/**
	 * instancia almacenada del modelo de la vista
	 */
	protected MainFrameModel _model = MainFrameModel.getInstance();
	
	/**
	 * instancia almacenada de la clase encargada de obtener las cadenas de idioma
	 */
	protected LocalizationResources _languageResources = LocalizationResources.getInstance();
	
	/**
	 * Método encargado de inicializar los valones necesarios de los campos
	 */
	protected abstract void initialize();
	
	/**
	 * Método encargado de actualizar el lenguaje al actual de la aplicación, se deberá llamar cuando se produzca un cambio en el lenguaje
	 */
	public abstract void updateLanguage();
	
	/**
	 * Actualiza los datos mostrados desde el modelo
	 */
	public abstract void refreshData();

	/**
	 * Devuelve los datos nuevos introducidos en los campos.
	 */
	public abstract ActionParams getNewAttributes();
}
