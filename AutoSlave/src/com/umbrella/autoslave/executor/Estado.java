package com.umbrella.autoslave.executor;

import com.umbrella.autoslave.logic.EstateThreads;

/*
 * obliga a los estado a implementar ciertos metodos, esta clase hay q ajustarla al paquete de comunicacion
 */
public interface Estado {
	void transitar();
	//public static Estado getInstance();
	void enviaMensaje();
	void cambiaMensaje(boolean [] msg);
}
