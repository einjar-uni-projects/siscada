package com.umbrella.scada.view.screen.attributePanels;

import com.umbrella.scada.controller.Action;
import com.umbrella.scada.controller.ActionFactoryProvider;
import com.umbrella.scada.controller.ActionKey;
import com.umbrella.scada.controller.ActionParams;
import com.umbrella.scada.controller.ActionResult;
import com.umbrella.scada.view.localization.LocalizatorIDs;

/**
 * Clase que extiende AttributePanel recogiendo los valores para el robot 2
 * @author Umbrella.Soft
 * @version 1.0
 *
 */
public class Robot1AttributePanel extends RobotAttributePanel {

	/**
	 * serial_id
	 */
	private static final long serialVersionUID = -6067526276043674145L;

	@Override
	public void refreshData() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public ActionParams getNewAttributes() {
		// No tiene sentido llamarlo para esta clase
		return null;
	}
	
	protected void setAcceptAction() {
		_acceptButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				ActionParams params = new ActionParams();
				for (AttributePanel panel : _subPanels) {
					params.join(panel.getNewAttributes());
				}
				Action action = ActionFactoryProvider.getInstance().factoryMethod(ActionKey.UPDATE_ROBOT1, params);
				ActionResult result = action.execute();
				if (result != ActionResult.EXECUTE_CORRECT)
					System.out.println("Error al ejecutar la acción");
			}
		});
	}
	
	@Override
	public void updateLanguage() {
		_title.setText(_languageResources.getLocal(LocalizatorIDs.ROBOT_1_TITLE, _model.get_selectedLanguage()));
		_acceptButton.setText(_languageResources.getLocal(LocalizatorIDs.ACCEPT, _model.get_selectedLanguage()));
		robotTitle.setText(_languageResources.getLocal(LocalizatorIDs.ARM, _model.get_selectedLanguage()));
		timeDespl.setText(_languageResources.getLocal(LocalizatorIDs.DESPL_TIME, _model.get_selectedLanguage())+":");
	}
}
