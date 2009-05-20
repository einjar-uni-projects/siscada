package com.umbrella.scada.controller;

import java.util.HashMap;


public class ActionParams implements ParamGroup<ActionParamsEnum> {
	
	private HashMap<ActionParamsEnum, InfoJoin> _params = new HashMap<ActionParamsEnum, InfoJoin>();

	public <T> void setParam(ActionParamsEnum state, Class<T> val_class, T b) {
		_params.put(state, new InfoJoin<T>(val_class, b));
	}

	@Override
	public Object getParam(ActionParamsEnum param) {
		InfoJoin info = _params.get(param);
		return info.b;
	}

	private class InfoJoin<T>{
		public InfoJoin(Class<T> val_class, T b) {
			this.val_class = val_class;
			this.b = b;
		}
		
		Class<T> val_class;
		T b;
		
	}

}
