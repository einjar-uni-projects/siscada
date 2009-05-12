package com.umbrella.scada.view.screen;

import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class AttributePanel extends JPanel {
	
	protected JLabel _title = new JLabel();
	
	protected AttributePanel() {
		add(_title);
	}
	
	protected abstract void initialize();
}
