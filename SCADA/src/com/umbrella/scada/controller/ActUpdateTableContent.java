package com.umbrella.scada.controller;

import com.umbrella.scada.model.Model;

public class ActUpdateTableContent implements Action {

	private Integer _tableContent = null;
	
	@Override
	public ActionResult execute() {
		//System.out.println("Ejecutando ActUpdateTableContent");
		ActionResult ret = ActionResult.EXECUTE_CORRECT;
		Model m = Model.getInstance();
		if(_tableContent == null)
			ret = ActionResult.NO_INITIALICE_PARAMS;
		else{
			m.set_tableContent(_tableContent);
		}
		
		if(ret == ActionResult.EXECUTE_CORRECT)
			m.notifyChanges();
		return ret;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		boolean ret = false;
		ActionParamsEnum ape = ActionParamsEnum.TABLE_CONTENT;
		Integer tc = (Integer) params.getParam(ape);
		if (tc != null) {
			_tableContent = tc;
			ret = true;
		}
		return ret;
	}

}
