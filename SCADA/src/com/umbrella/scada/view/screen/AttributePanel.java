package com.umbrella.scada.view.screen;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.umbrella.scada.view.localization.LocalizationResources;

public abstract class AttributePanel extends JPanel {
	
	protected JLabel _title = new JLabel();
	protected MainFrameModel _model = MainFrameModel.getInstance();
	protected LocalizationResources _languageResources = LocalizationResources.getInstance();
	
	protected AttributePanel() {
		add(_title);
	}
	
	protected abstract void initialize();
	
	public abstract void updateLanguage();
}
