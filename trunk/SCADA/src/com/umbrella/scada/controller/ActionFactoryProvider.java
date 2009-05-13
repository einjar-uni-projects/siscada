package com.umbrella.scada.controller;

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
	public Action factoryMethod(ActionKey key, ActionParams param) {
		Action ret;
		switch (key) {
		case START:
			ret = new ActStart();
			
			break;
		case STOP:
			ret = new ActStop();
			break;
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
