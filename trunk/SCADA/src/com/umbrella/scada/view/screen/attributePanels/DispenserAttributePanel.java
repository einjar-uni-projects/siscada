package com.umbrella.scada.view.screen.attributePanels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public abstract class DispenserAttributePanel extends AttributePanel {

	private static final long serialVersionUID = 1L;
	protected JLabel titleLabel = null;
	protected JLabel countLabel = null;
	protected JLabel countValueLabel = null;
	protected JTextField countNewText = null;

	/**
	 * This is the default constructor
	 */
	public DispenserAttributePanel() {
		super();
		initialize();
		updateLanguage();
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
		gridBagConstraints3.insets = new Insets(0, 0, 5, 5);
		gridBagConstraints3.gridx = 1;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.insets = new Insets(0, 0, 0, 5);
		gridBagConstraints2.gridy = 2;
		countValueLabel = new JLabel();
		countValueLabel.setText("JLabel");
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints1.gridy = 1;
		countLabel = new JLabel();
		countLabel.setText("Available");
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 5, 0, 5);
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
		
		Font f = titleLabel.getFont();
		titleLabel.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
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

}
