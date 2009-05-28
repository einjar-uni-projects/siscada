package com.umbrella.scada.view.screen.attributePanels;

public class CakeConveyorBeltAttributePanel extends ConveyorBeltAttributePanel {

	/**
	 * serial_id
	 */
	private static final long serialVersionUID = 1871980851104624297L;

	public CakeConveyorBeltAttributePanel(AttributePanel father) {
		super(father);
	}
	
	@Override
	public void refreshData() {
		speedValueLabel.setText(""+_model.get_cbCakeSpeed());
		sizeValueLabel.setText(""+_model.get_cbCakeSize());
	}

	@Override
	public void notifyError(boolean error) {
		
		
	}

}
