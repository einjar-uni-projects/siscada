package com.umbrella.scada.view.screen;

import com.umbrella.scada.view.localization.LocalizatorIDs;

public class VoidAttributePanel extends AttributePanel {

	public VoidAttributePanel() {
		super();
	}
	@Override
	protected void initialize() {
		updateLanguage();
	}
	@Override
	public void updateLanguage() {
		_title.setText(_languageResources.getLocal(LocalizatorIDs.VOID_CONVEYOR_BELT, _model.get_selectedLanguage()));
	}

}
