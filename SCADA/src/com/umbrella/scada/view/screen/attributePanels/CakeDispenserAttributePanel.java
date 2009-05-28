package com.umbrella.scada.view.screen.attributePanels;

import com.umbrella.scada.controller.ActionParams;
import com.umbrella.scada.view.localization.LocalizatorIDs;
import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;

public class CakeDispenserAttributePanel extends DispenserAttributePanel {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = -7258213644438206646L;

	public CakeDispenserAttributePanel(AttributePanel father) {
		super(father);
	}
	
	@Override
	public void refreshData() {
		countValueLabel.setText(""+_model.get_cakeDepot());
	}
	
	@Override
	public void updateLanguage() {
		LanguageIDs l = _model.get_selectedLanguage();
		titleLabel.setText(_languageResources.getLocal(LocalizatorIDs.CAKE_DISPENSER, l));
		countLabel.setText(_languageResources.getLocal(LocalizatorIDs.AVAILABLE, l)+":");
	}
	
	@Override
	public ActionParams getNewAttributes() {
		/*ActionParams params = new ActionParams();
		if(countNewText.getText().length() != 0)
			params.setParam(ActionParamsEnum.CAKE_QUANTITY, ActionParamsEnum.CAKE_QUANTITY.getEnclosedClass(), Integer.parseInt(countNewText.getText()));
		return params;
		*/
		return null;
	}

	@Override
	public void notifyError(boolean error) {
		
		
	}
}
