package com.umbrella.scada.view.screen.attributePanels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import com.umbrella.scada.controller.Action;
import com.umbrella.scada.controller.ActionFactoryProvider;
import com.umbrella.scada.controller.ActionKey;
import com.umbrella.scada.controller.ActionParams;
import com.umbrella.scada.controller.ActionResult;
import com.umbrella.scada.view.localization.LocalizatorIDs;

/**
 * Clase que extiende AttributePanel recogiendo los valores para la cinta de blísters
 * @author Umbrella.Soft
 * @version 1.0
 *
 */
public class Automata2AttributePanel extends AttributePanel {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 2201918577598392271L;
	
	/**
	 * Crea el panel de atributos, añade los campos necesarios y establece el texto de estos.
	 */
	public Automata2AttributePanel() {
		_subPanels = new AttributePanel[1];
		_subPanels[0] = new BlisterConveyorBeltAttributePanel();
		
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
		
		add(_acceptButton, c);
	}

	/* (non-Javadoc)
	 * @see com.umbrella.scada.view.screen.AttributePanel#updateLanguage()
	 */
	@Override
	public void updateLanguage() {
		_title.setText(_languageResources.getLocal(LocalizatorIDs.BLISTER_CONVEYOR_BELT, _model.get_selectedLanguage()));
	}

	@Override
	public void refreshData() {
		for (AttributePanel subPanel : _subPanels) {
			subPanel.refreshData();
		}
	}

	@Override
	public ActionParams getNewAttributes() {
		// No tiene sentido llamarlo para esta clase
		return null;
	}
	
	private void setAcceptAction() {
		_acceptButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				ActionParams params = new ActionParams();
				for (AttributePanel panel : _subPanels) {
					params.join(panel.getNewAttributes());
				}
				Action action = ActionFactoryProvider.getInstance().factoryMethod(ActionKey.UPDATE_BLISTER_CONVEYOR_BELT, params);
				ActionResult result = action.execute();
				if (result != ActionResult.EXECUTE_CORRECT)
					System.out.println("Error al ejecutar la acción");
			}
		});
	}
	
}
