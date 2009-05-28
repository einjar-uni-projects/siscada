package com.umbrella.scada.controller;

import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.scada.model.Postmaster;

public class ActUpdateAutomata2 implements Action {

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
				dm.setIdentifier(MSGOntology.CONVEYOR_BELT_2_SPEED);
				dm.setObject(_conveyorBeltSpeed);
				pm.sendMessage(dm);
			}
			if(_conveyorBeltSize > 0){
				dm = new DefaultMessage();
				dm.setIdentifier(MSGOntology.CONVEYOR_BELT_2_SIZE);
				dm.setObject(_conveyorBeltSize);
				pm.sendMessage(dm);
			}
			
			ret = ActionResult.EXECUTE_CORRECT;
		} catch (PropertyException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		Object speedObj = params.getParam(ActionParamsEnum.SPEED);
		Object sizeObj = params.getParam(ActionParamsEnum.SIZE);
		
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
		}catch(ClassCastException e){
			return false;
		}
		
		return ret;
	}

}
