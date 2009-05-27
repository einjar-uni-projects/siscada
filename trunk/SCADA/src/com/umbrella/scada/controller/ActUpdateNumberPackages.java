package com.umbrella.scada.controller;

import com.umbrella.scada.model.Model;
import com.umbrella.utils.MachineNames;

public class ActUpdateNumberPackages implements Action {

	private int _numberPackages;
	private boolean _goodPackages;
	private boolean _total;
	
	@Override
	public ActionResult execute() {
		ActionResult ret = ActionResult.EXECUTE_CORRECT;
		Model m = Model.getInstance();
		
		if(_goodPackages)
			if(_total)
				m.set_numGoodPackagesTotal(_numberPackages);
			else
				m.set_numGoodPackages(_numberPackages);
		else
			if(_total)
				m.set_numBadPackagesTotal(_numberPackages);
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
		Boolean goodPackage = (Boolean) params.getParam(ape);
		
		ape = ActionParamsEnum.TOTAL_PACKAGES;
		Boolean total = (Boolean) params.getParam(ape);

		if(numberPackages != null && goodPackage != null && total != null){
			_numberPackages = numberPackages;
			_goodPackages = goodPackage;
			_total = total;
			ret = true;
		}
		
		return ret;
	}

}
