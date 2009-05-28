package com.umbrella.scada.controller;

import com.umbrella.scada.model.Model;

public class ActFailStop implements Action {

	@Override
	public ActionResult execute() {
		Model m = Model.getInstance();
		m.set_au1State(false);
		m.set_au2State(false);
		m.set_au3State(false);
		m.set_rb1State(false);
		m.set_rb2State(false);
		m.set_au1Move(false);
		m.set_au2Move(false);
		m.set_au3Move(false);
		
		m.notifyChanges();
		return ActionResult.EXECUTE_CORRECT;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		return true;
	}

}
