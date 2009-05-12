package com.umbrella.scada.view.screen;

import java.awt.TextField;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public abstract class AtributePanel extends JPanel {
	
	protected JLabel _title = new JLabel();
	private JLabel _conveyorBelt = new JLabel();
	
	protected AtributePanel() {
		add(_title);
	}
	
	protected abstract void initialize();
}
