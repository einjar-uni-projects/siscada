package com.umbrella.scada.view.screen.attributePanels;

import java.awt.Font;
import java.awt.GridLayout;

import com.umbrella.scada.controller.ActionParams;
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
	
	/*private JLabel _availableCakesL = new JLabel();
	private JLabel _conveyorBeltL = new JLabel();
	private JLabel _speedL = new JLabel();
	private TextField _speedInput = new TextField();*/
	
	/**
	 * Crea el panel de atributos, añade los campos necesarios y establece el texto de estos.
	 */
	public PackageConveyorBeltAttributePanel() {
		/*super();
		add(_conveyorBeltL);
		add(_availableCakesL);
		add(_speedL);
		add(_speedInput);*/
		
		_subPanels = new AttributePanel[1];
		_subPanels[0] = new ConveyorBeltAttributePanel();
		
		initialize();
	}

	/* (non-Javadoc)
	 * @see com.umbrella.scada.view.screen.AttributePanel#initialize()
	 */
	@Override
	protected void initialize() {
		updateLanguage();
		
		setLayout(new GridLayout(3,1));
		
		add(_title);
		
		for (AttributePanel subPanel : _subPanels) {
			add(subPanel);
		}
		
		add(_acceptButton);
	}

	/* (non-Javadoc)
	 * @see com.umbrella.scada.view.screen.AttributePanel#updateLanguage()
	 */
	@Override
	public void updateLanguage() {
		_title.setText(_languageResources.getLocal(LocalizatorIDs.PACKAGE_CONVEYOR_BELT, _model.get_selectedLanguage()));
		Font f = _title.getFont();
		_title.setFont(f.deriveFont(f.getStyle() ^ (Font.BOLD ^ Font.ITALIC), 16));
	}

	@Override
	public void refreshData() {
		for (AttributePanel subPanel : _subPanels) {
			subPanel.refreshData();
		}
	}
	
	@Override
	public ActionParams getNewAttributes() {
		// No tiene sentido llamarlo para esta clase
		return null;
	}
	
}