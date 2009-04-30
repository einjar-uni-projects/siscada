package com.umbrella.scada.view.screen;

import java.awt.TextField;

import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class AtributePanel extends JPanel {
	
	protected JLabel _state = new JLabel();
	private TextField _stateInput = new TextField();
	
	protected AtributePanel() {
		add(_state);
	}
	
	protected abstract void initialize();
}
