package com.umbrella.scada.controller;

import javax.activity.ActivityCompletedException;

public class ActionFactoryProvider implements ActionFactory {
	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	private ActionFactoryProvider() {
	}

	/**
	 * Obtiene la instancia única del objeto, la primera invocación
	 * realiza la creación del mismo.
	 * @return la instancia única de ActionFactoryProvider
	 */
	public static ActionFactoryProvider getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static ActionFactoryProvider instance = new ActionFactoryProvider();
	}

	@Override
	public ActionResult executeAction(Action action) {
		if(action != null)
			return action.execute();
		return ActionResult.NULL_ACTION;
	}

	@Override
	public Action factoryMethod(ActionKey key, ActionParams params) {
		Action ret;
		switch (key) {
		case START:
			ret = new ActStart();
			break;
		case STOP:
			ret = new ActStop();
			break;
		case UPDATE_STATE:
			ret = new ActUpdateState();
			if(!ret.insertParam(params))
				ret = new ActIncorrectParams();
			break;
		case UPDATE_CAKE_CONVEYOR_BELT:
			ret = new ActUpdateCakeConveyorBelt();
			if(!ret.insertParam(params))
				ret = new ActIncorrectParams();
			break;
		case UPDATE_BLISTER_CONVEYOR_BELT:
			ret = new ActUpdateBlisterConveyorBelt();
			if(!ret.insertParam(params))
				ret = new ActIncorrectParams();
			break;
		case UPDATE_PACKAGE_CONVEYOR_BELT:
			ret = new ActUpdatePackageConveyorBelt();
			if(!ret.insertParam(params))
				ret = new ActIncorrectParams();
			break;
		case UPDATE_ROBOT1:
			ret = new ActUpdateRobot1();
			if(!ret.insertParam(params))
				ret = new ActIncorrectParams();
			break;
		case UPDATE_ROBOT2:
			ret = new ActUpdateRobot2();
			if(!ret.insertParam(params))
				ret = new ActIncorrectParams();
			break;
		case UPDATE_CAKE_DEPOT:
			ret = new ActUpdateCakeDepot();
			if(!ret.insertParam(params))
				ret = new ActIncorrectParams();
		default:
			ret = new ActNull();
			break;
		}
		return ret;
	}

	@Override
	public ActionResult executeAction(ActionKey key, ActionParams params) {
		return factoryMethod(key, params).execute();
	}

}
