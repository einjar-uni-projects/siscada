package com.umbrella.scada.view.screen;

import java.awt.TextField;

import javax.swing.JLabel;

import com.umbrella.scada.view.localization.LocalizationResources;
import com.umbrella.scada.view.localization.LocalizatorIDs;

public class BlisterConveyorBeltAttributePanel extends AttributePanel {

	private JLabel _conveyorBeltL = new JLabel();
	private JLabel _speedL = new JLabel();
	private TextField _speedInput = new TextField();
	
	
	public BlisterConveyorBeltAttributePanel() {
		super();
		add(_conveyorBeltL);
		add(_speedL);
		add(_speedInput);
		initialize();
	}

	@Override
	protected void initialize() {
		updateLanguage();
	}

	@Override
	public void updateLanguage() {
		_conveyorBeltL.setText(_languageResources.getLocal(LocalizatorIDs.CONVEYOR_BELT, _model.get_selectedLanguage()));
		_title.setText(_languageResources.getLocal(LocalizatorIDs.BLISTER_CONVEYOR_BELT, _model.get_selectedLanguage()));
		_speedL.setText(_languageResources.getLocal(LocalizatorIDs.SPEED, _model.get_selectedLanguage()));
		_speedInput.setText("30 m/min");
	}
	
}
