package com.umbrella.scada.controller;

import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.scada.model.Postmaster;

public class ActUpdateAutomata1 implements Action {

	private int _cakeNumber, _chocolateQuantity, _caramelQuantity;
	private double _conveyorBeltSpeed, _conveyorBeltSize;
	
	@Override
	public ActionResult execute() {
		Postmaster pm;
		ActionResult ret = ActionResult.EXECUTE_FAIL;
		try {
			pm = Postmaster.getInstance();
			
			DefaultMessage dm;
			
			if(_conveyorBeltSpeed > 0){
				dm = new DefaultMessage();
				dm.setIdentifier(MSGOntology.CONVEYOR_BELT_1_SPEED);
				dm.setObject(_conveyorBeltSpeed);
				pm.sendMessage(dm);
			}
			if(_conveyorBeltSize > 0){
				dm = new DefaultMessage();
				dm.setIdentifier(MSGOntology.CONVEYOR_BELT_1_SIZE);
				dm.setObject(_conveyorBeltSize);
				pm.sendMessage(dm);
			}
			
			ret = ActionResult.EXECUTE_CORRECT;
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		Object speedObj = params.getParam(ActionParamsEnum.SPEED);
		Object sizeObj = params.getParam(ActionParamsEnum.SIZE);
		Object cakeObj = params.getParam(ActionParamsEnum.CAKE_QUANTITY);
		Object chocolatObj = params.getParam(ActionParamsEnum.CHOCOLAT_QUANTITY);
		Object caramelObj = params.getParam(ActionParamsEnum.CARAMEL_QUANTITY);
		
		boolean ret = false;
		try{
			if(speedObj != null){
				_conveyorBeltSpeed = ((Double)speedObj).doubleValue();
				ret = true;
			}else
				_conveyorBeltSpeed = -1;
			if(sizeObj != null){
				_conveyorBeltSize = ((Double)sizeObj).doubleValue();
				ret = true;
			}else
				_conveyorBeltSize = -1;
			if(cakeObj != null){
				_cakeNumber = ((Integer)cakeObj).intValue();
				ret = true;
			}else
				_cakeNumber = -1;
			if(chocolatObj != null){
				_chocolateQuantity = ((Integer)chocolatObj).intValue();
				ret = true;
			}else
				_chocolateQuantity = -1;
			if(caramelObj != null){
				_caramelQuantity = ((Integer)caramelObj).intValue();
				ret = true;
			}else
				_caramelQuantity = -1;
		}catch(ClassCastException e){
			return false;
		}
		
		return ret;
	}

}
