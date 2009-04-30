package com.umbrella.scada.view.screen;

import java.awt.TextField;

import javax.swing.JLabel;
import javax.swing.JPanel;

import sun.swing.plaf.synth.DefaultSynthStyle.StateInfo;

public abstract class AtributePanel extends JPanel {
	
	protected JLabel _state = new JLabel();
	protected TextField _stateInput = new TextField();
	
	protected AtributePanel() {
		add(_state);
		add(_stateInput);
	}
	
	protected abstract void initialize();
}
