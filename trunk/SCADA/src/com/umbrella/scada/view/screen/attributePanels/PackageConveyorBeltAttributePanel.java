package com.umbrella.scada.view.screen.attributePanels;

import java.awt.TextField;

import javax.swing.JLabel;

import com.umbrella.scada.view.localization.LocalizatorIDs;

/**
 * Clase que extiende AttributePanel recogiendo los valores para la cinta de paquetes
 * @author Umbrella.Soft
 * @version 1.0
 *
 */
public class PackageConveyorBeltAttributePanel extends AttributePanel {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = -2348859836999031383L;
	
	private JLabel _availableCakesL = new JLabel();
	private JLabel _conveyorBeltL = new JLabel();
	private JLabel _speedL = new JLabel();
	private TextField _speedInput = new TextField();
	
	/**
	 * Crea el panel de atributos, añade los campos necesarios y establece el texto de estos.
	 */
	public PackageConveyorBeltAttributePanel() {
		super();
		add(_conveyorBeltL);
		add(_availableCakesL);
		add(_speedL);
		add(_speedInput);
		initialize();
	}

	/* (non-Javadoc)
	 * @see com.umbrella.scada.view.screen.AttributePanel#initialize()
	 */
	@Override
	protected void initialize() {
		updateLanguage();
	}

	/* (non-Javadoc)
	 * @see com.umbrella.scada.view.screen.AttributePanel#updateLanguage()
	 */
	@Override
	public void updateLanguage() {
		_conveyorBeltL.setText(_languageResources.getLocal(LocalizatorIDs.CONVEYOR_BELT, _model.get_selectedLanguage()));
		_title.setText(_languageResources.getLocal(LocalizatorIDs.PACKAGE_CONVEYOR_BELT, _model.get_selectedLanguage()));
		_speedL.setText(_languageResources.getLocal(LocalizatorIDs.SPEED, _model.get_selectedLanguage()));
		_speedInput.setText("30 m/min");
	}

	@Override
	public void refreshData() {
		// TODO Auto-generated method stub
		
	}
	
}
