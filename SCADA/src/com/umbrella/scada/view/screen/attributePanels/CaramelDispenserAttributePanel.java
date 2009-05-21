package com.umbrella.scada.view.screen.attributePanels;

import java.awt.Font;

import com.umbrella.scada.controller.ActionParams;
import com.umbrella.scada.controller.ActionParamsEnum;
import com.umbrella.scada.view.localization.LocalizatorIDs;
import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;

public class CaramelDispenserAttributePanel extends DispenserAttributePanel {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = -7169235886552716121L;

	@Override
	public void refreshData() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void updateLanguage() {
		LanguageIDs l = _model.get_selectedLanguage();
		titleLabel.setText(_languageResources.getLocal(LocalizatorIDs.CARAMEL_DISPENSER, l));
		Font f = titleLabel.getFont();
		titleLabel.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
		countLabel.setText(_languageResources.getLocal(LocalizatorIDs.AVAILABLE, l));
	}

	@Override
	public ActionParams getNewAttributes() {
		ActionParams params = new ActionParams();
		if(countNewText.getText().length() != 0)
			params.setParam(ActionParamsEnum.CARAMEL_QUANTITY, ActionParamsEnum.CARAMEL_QUANTITY.getEnclosedClass(), Integer.parseInt(countNewText.getText()));
		return params;
	}

}