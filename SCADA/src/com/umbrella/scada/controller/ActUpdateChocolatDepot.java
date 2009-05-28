package com.umbrella.scada.controller;

import com.umbrella.scada.model.Model;

public class ActUpdateChocolatDepot implements Action {
	private Integer _chocolatNum = null;

	@Override
	public ActionResult execute() {
		System.out.println("Ejecutando ActUpdateCakeDepot");
		ActionResult ret = ActionResult.EXECUTE_CORRECT;
		Model m = Model.getInstance();
		if(_chocolatNum == null)
			ret = ActionResult.NO_INITIALICE_PARAMS;
		else{
			m.set_au1ChocolatDepot(_chocolatNum);
		}
		
		if(ret == ActionResult.EXECUTE_CORRECT)
			m.notifyChanges();
		return ret;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		boolean ret = false;
		ActionParamsEnum ape = ActionParamsEnum.CHOCOLAT_QUANTITY;
		Integer num = (Integer) params.getParam(ape);
		if (num != null) {
			_chocolatNum = num;
			ret = true;
		}
		return ret;
	}

}
