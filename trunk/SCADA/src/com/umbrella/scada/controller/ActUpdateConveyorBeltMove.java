package com.umbrella.scada.controller;

import com.umbrella.scada.model.Model;
import com.umbrella.utils.MachineNames;

public class ActUpdateConveyorBeltMove implements Action {

	private MachineNames _machine;
	private boolean _state;
	
	@Override
	public ActionResult execute() {
		ActionResult ret = ActionResult.EXECUTE_CORRECT;
		Model m = Model.getInstance();
		switch (_machine) {
		case CINTA_1:
			m.set_au1Move(_state);
			break;
		case CINTA_2:
			m.set_au2Move(_state);
			break;
		case CINTA_3:
			m.set_au3Move(_state);
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
		ActionParamsEnum ape = ActionParamsEnum.CONVEYOR_BELT_MOVE;
		Boolean state = (Boolean) params.getParam(ape);
		if (state != null) {
			_state = state;
			ape = ActionParamsEnum.MACHINE;
			String machine = (String) params.getParam(ape);
			if (machine != null) {
				ret = true;
				if(machine.compareTo("AU1")==0)
					_machine = MachineNames.CINTA_1;
				else if(machine.compareTo("AU2")==0)
					_machine = MachineNames.CINTA_2;
				else if(machine.compareTo("AU3")==0)
					_machine = MachineNames.CINTA_3;
				else if(machine.compareTo("RB1")==0)
					_machine = MachineNames.ROBOT_1;
				else if(machine.compareTo("RB2")==0)
					_machine = MachineNames.ROBOT_2;
				else
					ret = false;
			}
		}
		return ret;
	}

}
