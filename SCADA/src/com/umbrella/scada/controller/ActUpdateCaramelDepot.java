package com.umbrella.scada.controller;

import com.umbrella.scada.model.Model;

public class ActUpdateCaramelDepot implements Action {
	private Integer _caramelNum = null;

	@Override
	public ActionResult execute() {
		//System.out.println("Ejecutando ActUpdateCakeDepot");
		ActionResult ret = ActionResult.EXECUTE_CORRECT;
		Model m = Model.getInstance();
		if(_caramelNum == null)
			ret = ActionResult.NO_INITIALICE_PARAMS;
		else{
			m.set_au1CaramelDepot(_caramelNum);
		}
		
		if(ret == ActionResult.EXECUTE_CORRECT)
			m.notifyChanges();
		return ret;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		boolean ret = false;
		ActionParamsEnum ape = ActionParamsEnum.CARAMEL_QUANTITY;
		Integer num = (Integer) params.getParam(ape);
		if (num != null) {
			_caramelNum = num;
			ret = true;
		}
		return ret;
	}

}
