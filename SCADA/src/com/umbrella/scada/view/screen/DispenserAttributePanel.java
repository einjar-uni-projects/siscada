package com.umbrella.scada.view.screen;

import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import com.umbrella.scada.view.localization.LocalizatorIDs;
import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;

public abstract class DispenserAttributePanel extends AttributePanel {

	private static final long serialVersionUID = 1L;
	private JLabel titleLabel = null;
	private JLabel countLabel = null;
	private JLabel countValueLabel = null;
	private JTextField countNewText = null;

	/**
	 * This is the default constructor
	 */
	public DispenserAttributePanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	protected void initialize() {
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = GridBagConstraints.BOTH;
		gridBagConstraints3.gridy = 3;
		gridBagConstraints3.weightx = 1.0;
		gridBagConstraints3.gridx = 1;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.gridy = 2;
		countValueLabel = new JLabel();
		countValueLabel.setText("JLabel");
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		countLabel = new JLabel();
		countLabel.setText("Available");
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridy = 0;
		titleLabel = new JLabel();
		titleLabel.setText("Dispenser");
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.add(titleLabel, gridBagConstraints);
		this.add(countLabel, gridBagConstraints1);
		this.add(countValueLabel, gridBagConstraints2);
		this.add(getCountNewText(), gridBagConstraints3);
	}

	/**
	 * This method initializes countNewText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getCountNewText() {
		if (countNewText == null) {
			countNewText = new JTextField();
		}
		return countNewText;
	}
	
	@Override
	public void updateLanguage() {
		LanguageIDs l = _model.get_selectedLanguage();
		titleLabel.setText(_languageResources.getLocal(LocalizatorIDs.CONVEYOR_BELT, l));
		countLabel.setText(_languageResources.getLocal(LocalizatorIDs.AVAILABLE, l));
	}

}
