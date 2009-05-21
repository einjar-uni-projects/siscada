package com.umbrella.scada.view.screen.attributePanels;

public class CakeConveyorBeltAttributePanel extends ConveyorBeltAttributePanel {

	@Override
	public void refreshData() {
		speedValueLabel.setText(""+_model.get_cbCakeSpeed());
		sizeValueLabel.setText(""+_model.get_cbCakeSize());
	}

}
