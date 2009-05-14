package com.umbrella.scada.view.screen.attributePanels;

import java.awt.GridLayout;

import com.umbrella.scada.view.localization.LocalizatorIDs;

/**
 * Clase que extiende AttributePanel recogiendo los valores para la cinta de blísters
 * @author Umbrella.Soft
 * @version 1.0
 *
 */
public class BlisterConveyorBeltAttributePanel extends AttributePanel {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 2201918577598392271L;
	
	/*private JLabel _conveyorBeltL = new JLabel();
	private JLabel _speedL = new JLabel();
	private TextField _speedInput = new TextField();*/
	
	/**
	 * Crea el panel de atributos, añade los campos necesarios y establece el texto de estos.
	 */
	public BlisterConveyorBeltAttributePanel() {
		/*super();
		add(_conveyorBeltL);
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
		//_conveyorBeltL.setText(_languageResources.getLocal(LocalizatorIDs.CONVEYOR_BELT, _model.get_selectedLanguage()));
		_title.setText(_languageResources.getLocal(LocalizatorIDs.BLISTER_CONVEYOR_BELT, _model.get_selectedLanguage()));
		/*_speedL.setText(_languageResources.getLocal(LocalizatorIDs.SPEED, _model.get_selectedLanguage()));
		_speedInput.setText("30 m/min");*/
	}

	@Override
	public void refreshData() {
		for (AttributePanel subPanel : _subPanels) {
			subPanel.refreshData();
		}
	}
	
}
