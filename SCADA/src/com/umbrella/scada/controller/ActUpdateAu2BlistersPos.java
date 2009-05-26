package com.umbrella.scada.controller;

import java.util.ArrayList;

import com.umbrella.scada.model.Model;

public class ActUpdateAu2BlistersPos implements Action {
	
	private Integer _pos1 = null;
	private Integer _pos2 = null;
	private Integer _pos3 = null;
	private Integer _pos4 = null;
	private Integer _pos5 = null;

	@Override
	public ActionResult execute() {
		System.out.println("Ejecutando ActUpdateAu2BlistersPos");
		ActionResult ret = ActionResult.EXECUTE_CORRECT;
		Model m = Model.getInstance();
		if(_pos1 == null || _pos2 == null ||_pos3 == null ||_pos4 == null ||_pos5 == null)
			ret = ActionResult.NO_INITIALICE_PARAMS;
		else{
			m.set_au2BlistersPos1(_pos1);
			m.set_au2BlistersPos2(_pos2);
			m.set_au2BlistersPos3(_pos3);
			m.set_au2BlistersPos4(_pos4);
			m.set_au2BlistersPos5(_pos5);
		}
		
		if(ret == ActionResult.EXECUTE_CORRECT)
			m.notifyChanges();
		return ret;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		boolean ret = false;
		ActionParamsEnum ape = ActionParamsEnum.AU2_BLISTER_POS;
		ArrayList<Integer> lli = (ArrayList<Integer>) params.getParam(ape);
		if (lli != null && lli.size() == 5) {
			_pos1 = lli.get(0);
			_pos2 = lli.get(1);
			_pos3 = lli.get(2);
			_pos4 = lli.get(3);
			_pos5 = lli.get(4);
			ret = true;
		}
		return ret;
	}

}
