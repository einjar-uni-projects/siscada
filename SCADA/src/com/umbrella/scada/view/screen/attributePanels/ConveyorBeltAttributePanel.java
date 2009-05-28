package com.umbrella.scada.view.screen.attributePanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import com.umbrella.scada.controller.ActionParams;
import com.umbrella.scada.controller.ActionParamsEnum;
import com.umbrella.scada.view.localization.LocalizatorIDs;
import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;

public abstract class ConveyorBeltAttributePanel extends AttributePanel {

	private static final long serialVersionUID = 1L;
	protected JLabel speedLabel = null;
	protected JLabel speedValueLabel = null;
	protected JTextField newSpeedText = null;
	protected JLabel sizeLabel = null;
	protected JLabel sizeValueLabel = null;
	protected JTextField newSizeText = null;
	protected JLabel titleLabel = null;
	
	public ConveyorBeltAttributePanel(AttributePanel father) {
		super(father);
		initialize();
		updateLanguage();
	}

	@Override
	protected void initialize() {
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.fill = GridBagConstraints.NONE;
		gridBagConstraints11.gridwidth = 2;
		gridBagConstraints11.anchor = GridBagConstraints.CENTER;
		gridBagConstraints11.insets = new Insets(5, 5, 0, 5);
		gridBagConstraints11.gridy = 0;
		titleLabel = new JLabel();
		titleLabel.setText("Conveyor Belt");
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.fill = GridBagConstraints.BOTH;
		gridBagConstraints5.gridy = 6;
		gridBagConstraints5.weightx = 1.0;
		gridBagConstraints5.insets = new Insets(0, 5, 5, 5);
		gridBagConstraints5.gridx = 1;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 1;
		gridBagConstraints4.insets = new Insets(0, 5, 0, 5);
		gridBagConstraints4.gridy = 5;
		sizeValueLabel = new JLabel();
		sizeValueLabel.setText("JLabel");
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints3.anchor = GridBagConstraints.WEST;
		gridBagConstraints3.gridy = 4;
		sizeLabel = new JLabel();
		sizeLabel.setText("Size");
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.fill = GridBagConstraints.BOTH;
		gridBagConstraints2.gridy = 3;
		gridBagConstraints2.weightx = 1.0;
		gridBagConstraints2.insets = new Insets(0, 5, 0, 5);
		gridBagConstraints2.gridx = 1;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridy = 2;
		gridBagConstraints1.anchor = GridBagConstraints.CENTER;
		gridBagConstraints1.fill = GridBagConstraints.NONE;
		gridBagConstraints1.insets = new Insets(0, 5, 0, 5);
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.ipadx = 0;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.gridy = 1;
		speedValueLabel = new JLabel();
		speedValueLabel.setText("JLabel");
		speedLabel = new JLabel();
		//speedLabel.setFont(new Font("SansSerif",Font.,11).);
		speedLabel.setText("Speed");
		//speedLabel.
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
		
		Font f = titleLabel.getFont();
		titleLabel.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
	}

	/**
	 * This method initializes newSpeedText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNewSpeedText() {
		if (newSpeedText == null) {
			newSpeedText = new JTextField();
			newSpeedText.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
					if(newSpeedText.getText().length() == 0){
						newSpeedText.setBackground(Color.WHITE);
						_father.notifyError(false);
					}else{
						try{
							Double.parseDouble(newSpeedText.getText());
							newSpeedText.setBackground(Color.WHITE);
							_father.notifyError(false);
						}catch(NumberFormatException nfe){
							newSpeedText.setBackground(Color.RED);
							_father.notifyError(true);
						}
					}
				}
			});
		}
		return newSpeedText;
	}

	/**
	 * This method initializes newSizeLabel	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNewSizeLabel() {
		if (newSizeText == null) {
			newSizeText = new JTextField();
			newSizeText.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
					if(newSizeText.getText().length() == 0){
						newSizeText.setBackground(Color.WHITE);
						_father.notifyError(false);
					}else{
						try{
							Double.parseDouble(newSizeText.getText());
							newSizeText.setBackground(Color.WHITE);
							_father.notifyError(false);
						}catch(NumberFormatException nfe){
							newSizeText.setBackground(Color.RED);
							_father.notifyError(true);
						}
					}
				}
			});
		}
		return newSizeText;
	}

	@Override
	public void updateLanguage() {
		LanguageIDs l = _model.get_selectedLanguage();
		titleLabel.setText(_languageResources.getLocal(LocalizatorIDs.CONVEYOR_BELT, l));
		speedLabel.setText(_languageResources.getLocal(LocalizatorIDs.SPEED, l)+":");
		sizeLabel.setText(_languageResources.getLocal(LocalizatorIDs.SIZE, l)+":");
	}

	@Override
	public ActionParams getNewAttributes() {
		ActionParams params = new ActionParams();
		if(newSpeedText.getText().length() != 0)
			params.setParam(ActionParamsEnum.SPEED, ActionParamsEnum.SPEED.getEnclosedClass(), Double.parseDouble(newSpeedText.getText()));
		if(newSizeText.getText().length() != 0)
			params.setParam(ActionParamsEnum.SIZE, ActionParamsEnum.SIZE.getEnclosedClass(), Double.parseDouble(newSizeText.getText()));
		return params;
	}

}  //  @jve:decl-index=0:visual-constraint="0,0"
