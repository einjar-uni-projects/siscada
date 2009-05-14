package com.umbrella.scada.view.screen.attributePanels;

import java.awt.GridLayout;

import com.umbrella.scada.view.localization.LocalizatorIDs;

/**
 * Clase que extiende AttributePanel recogiendo los valores para la cinta de pasteles
 * @author Umbrella.Soft
 * @version 1.0
 *
 */
public class CakeConveyorBeltAttributePanel extends AttributePanel {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = -1607286910321091549L;
	
	/*private JLabel _availableCakesL = new JLabel();
	private JLabel _conveyorBeltL = new JLabel();
	private JLabel _speedL = new JLabel();
	private TextField _speedInput = new TextField();*/
	
	/**
	 * Crea el panel de atributos, añade los campos necesarios y establece el texto de estos.
	 */
	public CakeConveyorBeltAttributePanel() {
		/*super();
		add(_conveyorBeltL);
		add(_availableCakesL);
		add(_speedL);
		add(_speedInput);*/
		_subPanels = new AttributePanel[4];
		_subPanels[0] = new ConveyorBeltAttributePanel();
		_subPanels[1] = new CakeDispenserAttributePanel();
		_subPanels[2] = new ChocolatDispenserAttributePanel();
		_subPanels[3] = new CaramelDispenserAttributePanel();
		
		initialize();
	}

	/* (non-Javadoc)
	 * @see com.umbrella.scada.view.screen.AttributePanel#initialize()
	 */
	@Override
	protected void initialize() {
		updateLanguage();

		setLayout(new GridLayout(6,1));
		
		add(_title);
		
		for (AttributePanel subPanel : _subPanels) {
			add(subPanel);
		}
		
		add(_acceptButton);
		
		/*GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		add(_title, c);
		c.gridy = 1;
		c.gridwidth = 1;
		add(_conveyorBeltL, c);
		c.gridx = 1;
		add(_availableCakesL, c);*/
	}

	/* (non-Javadoc)
	 * @see com.umbrella.scada.view.screen.AttributePanel#updateLanguage()
	 */
	@Override
	public void updateLanguage() {
		/*_conveyorBeltL.setText(_languageResources.getLocal(LocalizatorIDs.CONVEYOR_BELT, _model.get_selectedLanguage()));*/
		_title.setText(_languageResources.getLocal(LocalizatorIDs.CAKE_CONVEYOR_BELT, _model.get_selectedLanguage()));
		/*_speedL.setText(_languageResources.getLocal(LocalizatorIDs.SPEED, _model.get_selectedLanguage()));
		_speedInput.setText("30 m/min");
		_availableCakesL.setText("fafsf");*/
		_acceptButton.setText(_languageResources.getLocal(LocalizatorIDs.ACCEPT, _model.get_selectedLanguage()));
	}

	@Override
	public void refreshData() {
		for (AttributePanel subPanel : _subPanels) {
			subPanel.refreshData();
		}
	}
	
}
