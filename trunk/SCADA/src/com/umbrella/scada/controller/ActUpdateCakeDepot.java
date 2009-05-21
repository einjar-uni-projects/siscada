package com.umbrella.scada.controller;

import com.umbrella.scada.model.Model;

public class ActUpdateCakeDepot implements Action {
	private Integer _cakeNum = null;

	@Override
	public ActionResult execute() {
		System.out.println("Ejecutando ActUpdateCakeDepot");
		ActionResult ret = ActionResult.EXECUTE_CORRECT;
		Model m = Model.getInstance();
		if(_cakeNum == null)
			ret = ActionResult.NO_INITIALICE_PARAMS;
		else{
			m.set_au1CakeDepot(_cakeNum);
		}
		
		if(ret == ActionResult.EXECUTE_CORRECT)
			m.notifyChanges();
		return ret;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		boolean ret = false;
		ActionParamsEnum ape = ActionParamsEnum.CAKE_DEPOT;
		Integer num = (Integer) params.getParam(ape);
		if (num != null) {
			_cakeNum = num;
			ret = true;
		}
		return ret;
	}

}
