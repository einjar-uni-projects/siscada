package com.umbrella.scada.view.screen;

import java.awt.TextField;

import javax.swing.JLabel;

public class CakeConveyorBeltAtributePanel extends AtributePanel {

	private JLabel _availableCakesL = new JLabel();
	private JLabel _conveyorBeltL = new JLabel();
	private JLabel _speedL = new JLabel();
	private TextField _speedInput = new TextField();
	
	public CakeConveyorBeltAtributePanel() {
		super();
		add(_conveyorBeltL);
		add(_availableCakesL);
		add(_speedL);
		add(_speedInput);
		initialize();
	}

	@Override
	protected void initialize() {
		_conveyorBeltL.setText("Cinta");
		_title.setText("Cinta pasteles");
		_availableCakesL.setText("Pasteles restantes");
		_speedL.setText("Velocidad:");
		_speedInput.setText("30 m/min");
	}
	
}
