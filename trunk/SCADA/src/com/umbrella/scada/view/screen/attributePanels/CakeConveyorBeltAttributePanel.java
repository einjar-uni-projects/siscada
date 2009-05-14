package com.umbrella.scada.view.screen.attributePanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

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
	
	private JLabel _availableCakesL = new JLabel();
	private JLabel _conveyorBeltL = new JLabel();
	private JLabel _speedL = new JLabel();
	private TextField _speedInput = new TextField();
	
	/**
	 * Crea el panel de atributos, añade los campos necesarios y establece el texto de estos.
	 */
	public CakeConveyorBeltAttributePanel() {
		/*super();
		add(_conveyorBeltL);
		add(_availableCakesL);
		add(_speedL);
		add(_speedInput);*/
		initialize();
	}

	/* (non-Javadoc)
	 * @see com.umbrella.scada.view.screen.AttributePanel#initialize()
	 */
	@Override
	protected void initialize() {
		updateLanguage();

		setLayout(new GridLayout(6,1));
		
		add(new ConveyorBeltAttributePanel());
		add(new CakeDispenserAttributePanel());
		add(new ChocolatDispenserAttributePanel());
		add(new CaramelDispenserAttributePanel());
		
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
		_conveyorBeltL.setText(_languageResources.getLocal(LocalizatorIDs.CONVEYOR_BELT, _model.get_selectedLanguage()));
		_title.setText(_languageResources.getLocal(LocalizatorIDs.CAKE_CONVEYOR_BELT, _model.get_selectedLanguage()));
		_speedL.setText(_languageResources.getLocal(LocalizatorIDs.SPEED, _model.get_selectedLanguage()));
		_speedInput.setText("30 m/min");
		_availableCakesL.setText("fafsf");
	}

	@Override
	public void refreshData() {
		// TODO Auto-generated method stub
		
	}
	
}
