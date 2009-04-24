package com.umbrella.autoslave.executor;

public interface Estado {
	Estado transitar();
	//public static Estado getInstance();
	void enviaMensaje();
	void cambiaMensaje(boolean [] msg);
}
