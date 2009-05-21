package com.umbrella.scada.view.screen.attributePanels;

public class BlisterConveyorBeltAttributePanel extends
		ConveyorBeltAttributePanel {

	@Override
	public void refreshData() {
		speedValueLabel.setText(""+_model.get_cbBlisterSpeed());
		sizeValueLabel.setText(""+_model.get_cbBlisterSize());
	}

}
