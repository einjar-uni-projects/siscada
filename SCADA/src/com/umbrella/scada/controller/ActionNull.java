package com.umbrella.scada.controller;

public class ActionNull implements Action {

	@Override
	public ActionResult execute() {
		return ActionResult.NULL_ACTION;
	}

}
