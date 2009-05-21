package com.umbrella.scada.view.screen.attributePanels;

public class PackageConveyorBeltAttributePanel extends
		ConveyorBeltAttributePanel {

	@Override
	public void refreshData() {
		speedValueLabel.setText(""+_model.get_cbPackageSpeed());
		sizeValueLabel.setText(""+_model.get_cbPackageSize());
	}

}
