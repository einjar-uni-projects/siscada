package com.umbrella.scada.controller;

import com.umbrella.scada.model.Model;

public class ActUpdateState implements Action {

	private int _machine = -1;
	private boolean _state;

	@Override
	public ActionResult execute() {
		System.out.println("Ejecutando ActUpdateState");
		ActionResult ret = ActionResult.EXECUTE_CORRECT;
		Model m = Model.getInstance();
		switch (_machine) {
		case 0:
			m.set_au1State(_state);
			break;
		case 1:
			m.set_au2State(_state);
			break;
		case 2:
			m.set_au3State(_state);
			break;
		case 3:
			m.set_rb1State(_state);
			break;
		case 4:
			m.set_rb2State(_state);
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
		ActionParamsEnum ape = ActionParamsEnum.STATE;
		Boolean state = (Boolean) params.getParam(ape);
		if (state != null) {
			_state = state;
			ape = ActionParamsEnum.MACHINE;
			String machine = (String) params.getParam(ape);
			if (machine != null) {
				ret = true;
				if(machine.compareTo("AU1")==0)
					_machine = 0;
				else if(machine.compareTo("AU2")==0)
					_machine = 1;
				else if(machine.compareTo("AU3")==0)
					_machine = 2;
				else if(machine.compareTo("RB1")==0)
					_machine = 3;
				else if(machine.compareTo("RB2")==0)
					_machine = 4;
				else
					ret = false;
				
				
			}
		}
		return ret;
	}

}
