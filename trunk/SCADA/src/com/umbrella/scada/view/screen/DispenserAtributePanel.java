package com.umbrella.scada.view.screen;

import java.awt.Label;
import java.awt.TextField;

import javax.swing.JLabel;

public class DispenserAtributePanel extends AtributePanel {

	private JLabel _availableCakes = new JLabel();
	private JLabel _speed = new JLabel();
	private TextField _speedInput = new TextField();
	
	public DispenserAtributePanel() {
		super();
		add(_availableCakes);
		add(_speed);
		add(_speedInput);
		initialize();
	}

	@Override
	protected void initialize() {
		_state.setText("Estado");
		_stateInput.setText("Encendido");
		_availableCakes.setText("Pasteles restantes");
		_speed.setText("Velocidad:");
		_speedInput.setText("30 m/min");
	}
	
}
