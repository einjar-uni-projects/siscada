package com.umbrella.scada.view.screen.attributePanels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public abstract class RobotAttributePanel extends AttributePanel {

	private static final long serialVersionUID = 1L;
	private JPanel panelInfo = null;
	protected JLabel robotTitle = null;
	protected JLabel timeDespl = null;
	private JLabel desplTimeDisp = null;
	private JTextField desplTimeSet = null;

	/**
	 * This is the default constructor
	 */
	public RobotAttributePanel() {
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	protected void initialize() {

		getPanelInfo();
		
		Font f = _title.getFont();
		_title.setFont(f.deriveFont(f.getStyle() ^ (Font.BOLD ^ Font.ITALIC), 16));
		
		f = robotTitle.getFont();
		robotTitle.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
		
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
			
		add(panelInfo, c);
		c.gridy++;
				
		add(_acceptButton, c);
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
			timeDespl = new JLabel();
			timeDespl.setText("JLabel");
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
			panelInfo.add(timeDespl, gridBagConstraints1);
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
	
	protected abstract void setAcceptAction();

}
