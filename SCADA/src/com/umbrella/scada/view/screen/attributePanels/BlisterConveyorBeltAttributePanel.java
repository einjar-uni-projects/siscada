package com.umbrella.scada.view.screen.attributePanels;

public class BlisterConveyorBeltAttributePanel extends
		ConveyorBeltAttributePanel {

	/**
	 * serial_id
	 */
	private static final long serialVersionUID = -7973154746035741577L;

	public BlisterConveyorBeltAttributePanel(AttributePanel father) {
		super(father);
	}
	
	@Override
	public void refreshData() {
		speedValueLabel.setText(""+_model.get_cbBlisterSpeed());
		sizeValueLabel.setText(""+_model.get_cbBlisterSize());
	}

	@Override
	public void notifyError(boolean error) {
		// TODO Auto-generated method stub
		
	}

}
