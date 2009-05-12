package com.umbrella.scada.controller;

public interface ActionFactory extends Factory<Action, ActionKey, ActionParams> {
	
	public ActionResult executeAction(Action action);
	
	public ActionResult executeAction(ActionKey key, ActionParams params);

}
