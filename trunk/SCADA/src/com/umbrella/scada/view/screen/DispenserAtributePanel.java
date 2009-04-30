package com.umbrella.scada.view.screen;

import java.awt.Label;

import javax.swing.JLabel;

public class DispenserAtributePanel extends AtributePanel {

	private JLabel _availableCakes = new JLabel();
	
	public DispenserAtributePanel() {
		super();
		add(_availableCakes);
		initialize();
	}

	@Override
	protected void initialize() {
		_state.setText("Estado");
		_availableCakes.setText("Pasteles: Mazo");
	}
	
}
