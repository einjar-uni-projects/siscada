package com.umbrella.scada.view.screen.attributePanels;

import java.awt.Font;

import com.umbrella.scada.view.localization.LocalizatorIDs;
import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;

public class CakeDispenserAttributePanel extends DispenserAttributePanel {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = -7258213644438206646L;

	@Override
	public void refreshData() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void updateLanguage() {
		LanguageIDs l = _model.get_selectedLanguage();
		titleLabel.setText(_languageResources.getLocal(LocalizatorIDs.CAKE_DISPENSER, l));
		Font f = titleLabel.getFont();
		titleLabel.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
		countLabel.setText(_languageResources.getLocal(LocalizatorIDs.AVAILABLE, l));
	}
}
