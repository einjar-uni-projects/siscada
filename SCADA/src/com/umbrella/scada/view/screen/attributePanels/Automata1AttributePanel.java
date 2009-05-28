package com.umbrella.scada.view.screen.attributePanels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;

import com.umbrella.scada.controller.Action;
import com.umbrella.scada.controller.ActionFactoryProvider;
import com.umbrella.scada.controller.ActionKey;
import com.umbrella.scada.controller.ActionParams;
import com.umbrella.scada.controller.ActionParamsEnum;
import com.umbrella.scada.controller.ActionResult;
import com.umbrella.scada.view.localization.LocalizatorIDs;

/**
 * Clase que extiende AttributePanel recogiendo los valores para la cinta de pasteles
 * @author Umbrella.Soft
 * @version 1.0
 *
 */
public class Automata1AttributePanel extends AttributePanel {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = -1607286910321091549L;
	
	protected JCheckBox _rellenar = new JCheckBox("Rellenar");
	
	private boolean _storedError;
	
	/**
	 * Crea el panel de atributos, añade los campos necesarios y establece el texto de estos.
	 */
	public Automata1AttributePanel() {
		super(null);
		_subPanels = new AttributePanel[4];
		_subPanels[0] = new CakeConveyorBeltAttributePanel(this);
		_subPanels[1] = new CakeDispenserAttributePanel(this);
		_subPanels[2] = new ChocolatDispenserAttributePanel(this);
		_subPanels[3] = new CaramelDispenserAttributePanel(this);
		
		initialize();
		
	}

	/* (non-Javadoc)
	 * @see com.umbrella.scada.view.screen.AttributePanel#initialize()
	 */
	@Override
	protected void initialize() {
		
		Font f = _title.getFont();
		_title.setFont(f.deriveFont(f.getStyle() ^ (Font.BOLD ^ Font.ITALIC), 16));
		
		setAcceptAction();
		updateLanguage();
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,0,5,0);
		c.gridwidth = 1;
		add(_title, c);
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		
		for (AttributePanel subPanel : _subPanels) {
			add(subPanel, c);
			c.gridy++;
		}
		
		add(_rellenar,c);
		c.gridy++;
		
		add(_acceptButton, c);
	}

	private void setAcceptAction() {
		_acceptButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				ActionParams params = new ActionParams();
				for (AttributePanel panel : _subPanels) {
					params.join(panel.getNewAttributes());
				}
				if(_rellenar.isSelected())
					params.setParam(ActionParamsEnum.RELLENAR, ActionParamsEnum.RELLENAR.getEnclosedClass(), true);
				Action action = ActionFactoryProvider.getInstance().factoryMethod(ActionKey.UPDATE_CAKE_CONVEYOR_BELT, params);
				ActionResult result = action.execute();
				if (result != ActionResult.EXECUTE_CORRECT)
					System.out.println("Error al ejecutar la acción");
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.umbrella.scada.view.screen.AttributePanel#updateLanguage()
	 */
	@Override
	public void updateLanguage() {
		_title.setText(_languageResources.getLocal(LocalizatorIDs.CAKE_CONVEYOR_BELT, _model.get_selectedLanguage()));
		if(_model.is_cintaPasteles())
			_acceptButton.setText(_languageResources.getLocal(LocalizatorIDs.RUNNING_MACHINE, _model.get_selectedLanguage()));
		else
			_acceptButton.setText(_languageResources.getLocal(LocalizatorIDs.ACCEPT, _model.get_selectedLanguage()));
		for (AttributePanel panel : _subPanels) {
			panel.updateLanguage();
		}
	}

	@Override
	public void refreshData() {
		for (AttributePanel subPanel : _subPanels) {
			subPanel.refreshData();
		}
		if(_model.is_cintaPasteles()){
			_acceptButton.setText(_languageResources.getLocal(LocalizatorIDs.RUNNING_MACHINE, _model.get_selectedLanguage()));
			_acceptButton.setEnabled(false);
		}else if(_storedError){
			_acceptButton.setText(_languageResources.getLocal(LocalizatorIDs.ACCEPT, _model.get_selectedLanguage()));
			_acceptButton.setEnabled(false);
		}else{
			_acceptButton.setText(_languageResources.getLocal(LocalizatorIDs.ACCEPT, _model.get_selectedLanguage()));
			_acceptButton.setEnabled(true);
		}
	}

	@Override
	public ActionParams getNewAttributes() {
		// No tiene sentido llamarlo para esta clase
		return null;
	}

	@Override
	public void notifyError(boolean error) {
		_storedError = error;
	}
	
}
