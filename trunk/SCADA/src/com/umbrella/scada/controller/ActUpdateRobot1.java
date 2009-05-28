package com.umbrella.scada.controller;

import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.scada.model.Postmaster;

public class ActUpdateRobot1 implements Action {

	private int _blisterDelay, _cakeDelay;
	
	@Override
	public ActionResult execute() {
		Postmaster pm;
		ActionResult ret = ActionResult.EXECUTE_FAIL;
		try {
			pm = Postmaster.getInstance();
			
			DefaultMessage dm;
			
			if(_blisterDelay > 0){
				dm = new DefaultMessage();
				dm.setIdentifier(MSGOntology.RB1_BLISTER_DELAY);
				dm.setObject(_blisterDelay);
				pm.sendMessage(dm);
			}
			if(_cakeDelay > 0){
				dm = new DefaultMessage();
				dm.setIdentifier(MSGOntology.RB1_CAKE_DELAY);
				dm.setObject(_cakeDelay);
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
		Object blisterDelay = params.getParam(ActionParamsEnum.RB1_BLISTER_DELAY);
		Object cakeDelay = params.getParam(ActionParamsEnum.RB1_CAKE_DELAY);
		
		boolean ret = false;
		try{
			if(blisterDelay != null){
				_blisterDelay = ((Integer)blisterDelay).intValue();
				ret = true;
			}else
				_blisterDelay = -1;
			if(cakeDelay != null){
				_cakeDelay = ((Integer)cakeDelay).intValue();
				ret = true;
			}else
				_cakeDelay = -1;
		}catch(ClassCastException e){
			return false;
		}
		
		return ret;
	}

}
