package com.umbrella.scada.view.screen.attributePanels;

import com.umbrella.scada.controller.ActionParams;
import com.umbrella.scada.view.localization.LocalizatorIDs;
import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;

public class ChocolatDispenserAttributePanel extends DispenserAttributePanel {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 40765768524958467L;

	
	public ChocolatDispenserAttributePanel(AttributePanel father) {
		super(father);
	}
	
	@Override
	public void refreshData() {
		countValueLabel.setText(""+_model.get_chocolatDepot());
	}
	
	@Override
	public void updateLanguage() {
		LanguageIDs l = _model.get_selectedLanguage();
		titleLabel.setText(_languageResources.getLocal(LocalizatorIDs.CHOCOLAT_DISPENSER, l));
		countLabel.setText(_languageResources.getLocal(LocalizatorIDs.AVAILABLE, l)+":");
	}

	@Override
	public ActionParams getNewAttributes() {
		/*ActionParams params = new ActionParams();
		if(countNewText.getText().length() != 0)
			params.setParam(ActionParamsEnum.CHOCOLAT_QUANTITY, ActionParamsEnum.CHOCOLAT_QUANTITY.getEnclosedClass(), Integer.parseInt(countNewText.getText()));
		return params;*/
		return null;
	}

	@Override
	public void notifyError(boolean error) {
		// TODO Auto-generated method stub
		
	}

}
