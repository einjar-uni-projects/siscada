package com.umbrella.scada.view.screen.attributePanels;

import com.umbrella.scada.controller.ActionParams;
import com.umbrella.scada.view.localization.LocalizatorIDs;

/**
 * Clase que extiende AttributePanel presentando un panel vacio
 * @author Umbrella.Soft
 * @version 1.0
 *
 */
public class VoidAttributePanel extends AttributePanel {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 6370095854785886082L;

	/**
	 * Llama al constructor padre
	 */
	public VoidAttributePanel() {
		super(null);
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
		_title.setText(_languageResources.getLocal(LocalizatorIDs.VOID_CONVEYOR_BELT, _model.get_selectedLanguage()));
	}

	@Override
	public void refreshData() {
		// No hace nada
	}
	
	@Override
	public ActionParams getNewAttributes() {
		// No tiene sentido llamarlo para esta clase
		return null;
	}

	@Override
	public void notifyError(boolean error) {
		// TODO Auto-generated method stub
		
	}

}
