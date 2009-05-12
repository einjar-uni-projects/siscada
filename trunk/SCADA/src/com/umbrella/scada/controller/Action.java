package com.umbrella.scada.controller;

public interface Action {
	public ActionResult execute();
	public boolean insertParam(ActionParams params);
	
}
