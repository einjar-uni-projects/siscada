package com.umbrella.scada.controller;

public class ActUpdateCakeConveyorBelt implements Action {

	private int _conveyorBeltSpeed, _cakeNumber, _chocolateQuantity, _caramelQuantity;
	
	@Override
	public ActionResult execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		Object speedObj = params.getParam(ActionParamsEnum.SPEED);
		Object cakeObj = params.getParam(ActionParamsEnum.CAKE_QUANTITY);
		Object chocolatObj = params.getParam(ActionParamsEnum.CHOCOLAT_QUANTITY);
		Object caramelObj = params.getParam(ActionParamsEnum.CARAMEL_QUANTITY);
		
		if(speedObj != null && cakeObj != null && chocolatObj != null && caramelObj != null){
			try{
				_conveyorBeltSpeed = ((Integer)speedObj).intValue();
				_cakeNumber = ((Integer)speedObj).intValue();
				_chocolateQuantity = ((Integer)speedObj).intValue();
				_caramelQuantity = ((Integer)speedObj).intValue();
			}catch(ClassCastException e){
				return false;
			}
			return true;
		}
		
		return false;
	}

}
