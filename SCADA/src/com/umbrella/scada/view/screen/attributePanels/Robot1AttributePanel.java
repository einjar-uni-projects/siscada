package com.umbrella.scada.view.screen.attributePanels;

import com.umbrella.scada.controller.Action;
import com.umbrella.scada.controller.ActionFactoryProvider;
import com.umbrella.scada.controller.ActionKey;
import com.umbrella.scada.controller.ActionParams;
import com.umbrella.scada.controller.ActionParamsEnum;
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

	public Robot1AttributePanel() {
		super(true);
	}
	
	@Override
	public void refreshData() {
		desplTimeDisp.setText(""+_model.get_rb1BlisterDelay());
		interTimeDisp.setText(""+_model.get_rb1CakeDelay());
		if(_model.is_brazoMontaje()){
			_acceptButton.setText(_languageResources.getLocal(LocalizatorIDs.RUNNING_MACHINE, _model.get_selectedLanguage()));
			_acceptButton.setEnabled(false);
		}else{
			_acceptButton.setText(_languageResources.getLocal(LocalizatorIDs.ACCEPT, _model.get_selectedLanguage()));
			_acceptButton.setEnabled(true);
		}
	}
	
	@Override
	public ActionParams getNewAttributes() {
		ActionParams params = new ActionParams();
		if(desplTimeSet.getText().length() != 0)
			params.setParam(ActionParamsEnum.RB1_BLISTER_DELAY, ActionParamsEnum.RB1_BLISTER_DELAY.getEnclosedClass(), Integer.parseInt(desplTimeSet.getText()));
		if(interTimeSet.getText().length() != 0)
			params.setParam(ActionParamsEnum.RB1_CAKE_DELAY, ActionParamsEnum.RB1_CAKE_DELAY.getEnclosedClass(), Integer.parseInt(interTimeSet.getText()));
		return params;
	}
	
	protected void setAcceptAction() {
		_acceptButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				ActionParams params = new ActionParams();
				/*for (AttributePanel panel : _subPanels) {
					params.join(panel.getNewAttributes());
				}*/
				params.join(getNewAttributes());
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
		if(_model.is_cintaPasteles())
			_acceptButton.setText(_languageResources.getLocal(LocalizatorIDs.RUNNING_MACHINE, _model.get_selectedLanguage()));
		else
			_acceptButton.setText(_languageResources.getLocal(LocalizatorIDs.ACCEPT, _model.get_selectedLanguage()));
		robotTitle.setText(_languageResources.getLocal(LocalizatorIDs.TIME_DESPL, _model.get_selectedLanguage()));
		timeDespl.setText(_languageResources.getLocal(LocalizatorIDs.DESPL_BLISTER_TIME, _model.get_selectedLanguage())+":");
		timeInter.setText(_languageResources.getLocal(LocalizatorIDs.DESPL_CAKE_TIME, _model.get_selectedLanguage())+":");
	}
}
