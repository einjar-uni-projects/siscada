package com.umbrella.scada.controller;

import com.umbrella.scada.model.Model;
import com.umbrella.utils.MachineNames;

public class ActUpdateNumberPackages implements Action {

	private int _numberPackages;
	private boolean _goodPackages;
	
	@Override
	public ActionResult execute() {
		ActionResult ret = ActionResult.EXECUTE_CORRECT;
		Model m = Model.getInstance();
		
		if(_goodPackages)
			m.set_numGoodPackages(_numberPackages);
		else
			m.set_numBadPackages(_numberPackages);
			
		if(ret == ActionResult.EXECUTE_CORRECT)
			m.notifyChanges();
		return ret;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		boolean ret = false;
		ActionParamsEnum ape = ActionParamsEnum.NUMBER_PACKAGES;
		Integer numberPackages = (Integer) params.getParam(ape);
		
		ape = ActionParamsEnum.GOOD_PACKAGES;
		Boolean goodPaackage = (Boolean) params.getParam(ape);

		if(numberPackages != null && goodPaackage != null){
			_numberPackages = numberPackages;
			_goodPackages = goodPaackage;
			ret = true;
		}
		
		return ret;
	}

}
