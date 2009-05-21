package com.umbrella.scada.view.screen.attributePanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public abstract class RobotAttributePanel extends AttributePanel {

	private static final long serialVersionUID = 1L;
	private JPanel panelInfo = null;
	private JLabel robotTitle = null;
	private JLabel tiempoDespl = null;
	private JLabel desplTimeDisp = null;
	private JTextField desplTimeSet = null;

	/**
	 * This is the default constructor
	 */
	public RobotAttributePanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	protected void initialize() {
		setAcceptAction();
		updateLanguage();
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		this.setLayout(gridLayout);
		this.setSize(300, 200);
		this.add(getPanelInfo(), null);
	}

	/**
	 * This method initializes panelInfo	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelInfo() {
		if (panelInfo == null) {
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
			desplTimeDisp = new JLabel();
			desplTimeDisp.setText("JLabel");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints1.gridy = 1;
			tiempoDespl = new JLabel();
			tiempoDespl.setText("JLabel");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(5, 5, 0, 5);
			gridBagConstraints.gridwidth = 2;
			gridBagConstraints.gridy = 0;
			robotTitle = new JLabel();
			robotTitle.setText("JLabel");
			panelInfo = new JPanel();
			panelInfo.setLayout(new GridBagLayout());
			panelInfo.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			panelInfo.add(robotTitle, gridBagConstraints);
			panelInfo.add(tiempoDespl, gridBagConstraints1);
			panelInfo.add(desplTimeDisp, gridBagConstraints2);
			panelInfo.add(getDesplTimeSet(), gridBagConstraints3);
		}
		return panelInfo;
	}

	/**
	 * This method initializes desplTimeSet	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getDesplTimeSet() {
		if (desplTimeSet == null) {
			desplTimeSet = new JTextField();
		}
		return desplTimeSet;
	}

	@Override
	public void updateLanguage() {
		// TODO Auto-generated method stub
		
	}
	
	protected abstract void setAcceptAction();

}
