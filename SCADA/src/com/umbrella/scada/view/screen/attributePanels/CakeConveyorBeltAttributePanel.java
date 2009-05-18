package com.umbrella.scada.view.screen.attributePanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import javax.smartcardio.ATR;

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

		//setLayout(new GridLayout(6,1));
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,0,5,0);
		//c.ipadx = 50;
		//c.ipady = 50;
		c.gridwidth = 1;
		add(_title, c);
		c.gridy = 1;
		c.fill = c.BOTH;
		//c.gridwidth = 1;
		/*add(_conveyorBeltL, c);
		c.gridx = 1;
		add(_availableCakesL, c);*/
		
		//add(_title);
		
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
		AttributedString strTemp;
		/*_conveyorBeltL.setText(_languageResources.getLocal(LocalizatorIDs.CONVEYOR_BELT, _model.get_selectedLanguage()));*/
		/*strTemp = new AttributedString(_languageResources.getLocal(LocalizatorIDs.CAKE_CONVEYOR_BELT, _model.get_selectedLanguage()));
		strTemp.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		_title.setText(strTemp.toString());*/
		_title.setText("<b>"+_languageResources.getLocal(LocalizatorIDs.CAKE_CONVEYOR_BELT, _model.get_selectedLanguage())+"</b>");
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
