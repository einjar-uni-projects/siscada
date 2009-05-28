package com.umbrella.scada.controller;

import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.scada.model.Postmaster;

public class ActUpdateAutomata1 implements Action {

	private boolean _rellenar;
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
			if(_rellenar){
				dm = new DefaultMessage();
				dm.setIdentifier(MSGOntology.RELLENARMAQUINA);
				dm.setObject(_rellenar);
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
		Object rellenar = params.getParam(ActionParamsEnum.RELLENAR);
		
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
			if(rellenar != null){
				_rellenar = ((Boolean)rellenar).booleanValue();
				ret = true;
			}else
				_rellenar = false;
		}catch(ClassCastException e){
			return false;
		}
		
		return ret;
	}

}
