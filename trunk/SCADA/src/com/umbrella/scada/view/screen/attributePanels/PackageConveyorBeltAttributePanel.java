package com.umbrella.scada.view.screen.attributePanels;

public class PackageConveyorBeltAttributePanel extends
		ConveyorBeltAttributePanel {

	/**
	 * serial_id
	 */
	private static final long serialVersionUID = -1856767430327275419L;

	@Override
	public void refreshData() {
		speedValueLabel.setText(""+_model.get_cbPackageSpeed());
		sizeValueLabel.setText(""+_model.get_cbPackageSize());
	}

}
