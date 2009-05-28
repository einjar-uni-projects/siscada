package com.umbrella.scada.controller;

public class ActIncorrectParams implements Action {

	@Override
	public ActionResult execute() {
		return ActionResult.INCORRECT_PARAMS;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		return true;
	}

}
