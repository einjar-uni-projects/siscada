package com.umbrella.scada.view.screen.attributePanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import com.umbrella.scada.view.localization.LocalizatorIDs;
import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;

public class ConveyorBeltAttributePanel extends AttributePanel {

	private static final long serialVersionUID = 1L;
	private JLabel speedLabel = null;
	private JLabel speedValueLabel = null;
	private JTextField newSpeedText = null;
	private JLabel sizeLabel = null;
	private JLabel sizeValueLabel = null;
	private JTextField newSizeLabel = null;
	private JLabel titleLabel = null;
	
	/**
	 * This is the default constructor
	 */
	public ConveyorBeltAttributePanel() {
		super();
		initialize();
		updateLanguage();
	}

	@Override
	protected void initialize() {
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.fill = GridBagConstraints.NONE;
		gridBagConstraints11.gridwidth = 2;
		gridBagConstraints11.gridy = 0;
		titleLabel = new JLabel();
		titleLabel.setText("Conveyor Belt");
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.fill = GridBagConstraints.BOTH;
		gridBagConstraints5.gridy = 6;
		gridBagConstraints5.weightx = 1.0;
		gridBagConstraints5.gridx = 1;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 1;
		gridBagConstraints4.gridy = 5;
		sizeValueLabel = new JLabel();
		sizeValueLabel.setText("JLabel");
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.gridy = 4;
		sizeLabel = new JLabel();
		sizeLabel.setText("Size");
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.fill = GridBagConstraints.BOTH;
		gridBagConstraints2.gridy = 3;
		gridBagConstraints2.weightx = 1.0;
		gridBagConstraints2.gridx = 1;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridy = 2;
		gridBagConstraints1.anchor = GridBagConstraints.EAST;
		gridBagConstraints1.fill = GridBagConstraints.NONE;
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 1;
		speedValueLabel = new JLabel();
		speedValueLabel.setText("JLabel");
		speedLabel = new JLabel();
		speedLabel.setText("Speed");
		this.setLayout(new GridBagLayout());
		this.setSize(206, 160);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.add(speedLabel, gridBagConstraints);
		this.add(speedValueLabel, gridBagConstraints1);
		this.add(getNewSpeedText(), gridBagConstraints2);
		this.add(sizeLabel, gridBagConstraints3);
		this.add(sizeValueLabel, gridBagConstraints4);
		this.add(getNewSizeLabel(), gridBagConstraints5);
		this.add(titleLabel, gridBagConstraints11);
	}

	/**
	 * This method initializes newSpeedText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNewSpeedText() {
		if (newSpeedText == null) {
			newSpeedText = new JTextField();
		}
		return newSpeedText;
	}

	/**
	 * This method initializes newSizeLabel	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNewSizeLabel() {
		if (newSizeLabel == null) {
			newSizeLabel = new JTextField();
		}
		return newSizeLabel;
	}

	@Override
	public void updateLanguage() {
		LanguageIDs l = _model.get_selectedLanguage();
		titleLabel.setText(_languageResources.getLocal(LocalizatorIDs.CONVEYOR_BELT, l));
		speedLabel.setText(_languageResources.getLocal(LocalizatorIDs.SPEED, l));
		sizeLabel.setText(_languageResources.getLocal(LocalizatorIDs.SIZE, l));
	}

	@Override
	public void refreshData() {
		// TODO sin hacer
	}

}  //  @jve:decl-index=0:visual-constraint="0,0"
