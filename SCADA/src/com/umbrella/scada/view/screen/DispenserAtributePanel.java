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
		initialize();
	}

	@Override
	protected void initialize() {
		_state.setText("Cinta transportadora pasteles");
		_availableCakes.setText("Estado: encendida");
		_speed.setText("Velocidad: 30 m/min");
	}
	
}
