package com.umbrella.scada.controller;

import com.umbrella.scada.model.Model;

public class ActUpdateRobotContent implements Action {
	
	private int _machine = -1;
	private int _state;

	@Override
	public ActionResult execute() {
		//System.out.println("Ejecutando ActUpdateRobotContent");
		ActionResult ret = ActionResult.EXECUTE_CORRECT;
		Model m = Model.getInstance();
		switch (_machine) {
		case 0:
			m.set_rb1Content(_state);
			break;
		case 1:
			m.set_rb2Content(_state);
			break;

		default:
			ret = ActionResult.NO_INITIALICE_PARAMS;
			break;
		}
		
		if(ret == ActionResult.EXECUTE_CORRECT)
			m.notifyChanges();
		return ret;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		boolean ret = false;
		ActionParamsEnum ape = ActionParamsEnum.ROBOT_CONTENT;
		Integer state = (Integer) params.getParam(ape);
		if (state != null) {
			_state = state;
			ape = ActionParamsEnum.MACHINE;
			String machine = (String) params.getParam(ape);
			if (machine != null) {
				ret = true;
			if(machine.compareTo("RB1")==0)
					_machine = 0;
				else if(machine.compareTo("RB2")==0)
					_machine = 1;
				else
					ret = false;
			}
		}
		return ret;
	}

}
