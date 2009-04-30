package com.umbrella.scada.view.screen;

import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class AtributePanel extends JPanel {
	
	protected JLabel _state = new JLabel();
	
	protected AtributePanel() {
		add(_state);
	}
	
	protected abstract void initialize();
}
