package com.umbrella.autoslave.executor;

import com.umbrella.autoslave.logic.Configuracion;
import com.umbrella.autoslave.logic.Contexto;
import com.umbrella.autoslave.logic.EstateThreads;

public class MoverCinta extends Thread implements Estado{

	private static Estado INSTANCE = null;
	
	private EstateThreads _estadoHilo;
	
	private Contexto contexto=Contexto.getInstance();
	private Configuracion configuracion=Configuracion.getInstance();

	
	/*
	 * lo q desplaza la cinta en un click
	 */
	double velCintaCMporNanoseg=(configuracion.getVelCinta()*100)/(60*1000);
	/*
	 * espacio que recorre la cinta en un click, medido en CM 
	 */
	double espacioEnClick=velCintaCMporNanoseg*configuracion.get_tiempoReloj();
	
	/*
	 * indica el numero de saltos q dio la cinta desde q empezo a moverse 
	 */
	private int saltosCinta=0;
	
	private MoverCinta() {
		// TODO Auto-generated constructor stub
	}

	/*
	 */
	@Override
	public void run(){
		/*
		 * si se ejecuta la cinta 1 vez la cinta se desplaza minimo una cantidad X, suponemos q eso es siempre superior a un click
		 */
		_estadoHilo=EstateThreads.EJECUTANDO;

		/*
		 * nos dice si algun sensor se va a encender
		 * se da el valor False xq al menos tiene q dar el salto una vez
		 */

		for(int i=0;i<contexto.get_pasteles().size();i++){
			if((contexto.get_pasteles().get(i).get_posicion()+espacioEnClick)<=(configuracion.getSizeCinta()*100))
				contexto.get_pasteles().get(i).incrementarPosicion(espacioEnClick);
			else{
				//el pastel SE HA CAIDO DE LA CINTA
				contexto.get_pasteles().get(i).set_posicion((configuracion.getSizeCinta()*100));
			}
		}

		try {
			//espera al prox ciclo de reloj
			wait();
			// dormimos este tiempo porq no puede ocurrir nada en ese periodo
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		set_estadoHilo(EstateThreads.ESPERANDO);
	}
	
	public void transitar() {
		/*
		 * este estado nunca se queda bloqueado, la cinta no entiende de clicks de reloj
		 * presenta los problemas de tener q estar pendiente de si en un clicl de estado de 
		 * reloj hay un sensor activado tiene q cambiar al estado correspondiente
		 * 
		 * ADEMAS!! 
		 * si hay mas de un sensor activo tiene q activar dos estados, ESTO NO SE PERMITE ACTUALMENTE
		 * 
		 * 
		 */
		//return null;
	}
	private synchronized static void createInstance() {
		if (INSTANCE == null) { 
			INSTANCE = new MoverCinta();
		}
	}

	public static Estado getInstance() {
		if (INSTANCE == null) createInstance();
		return INSTANCE;
	}

	public void enviaMensaje() {
		// TODO Auto-generated method stub
		
	}

	public void cambiaMensaje(boolean[] msg) {
		// TODO Auto-generated method stub
		
	}	
	
	/*
	 * modifica el estado interno
	 */
	private synchronized void setEstadoInterno(){
		/*
		 * 0- sensor dispensadora Bizcocho
		 * 1- sensor dispensadora Chocolate
		 * 2- sensor dispensadroa caramelo
		 * 3- sensor fin cinta
		 * 4- dispensadora bizcochos activa
		 * 5- dispensadora chocolate activa
		 * 6- dispensadora caramelo activa
		 */
	}
	
	public synchronized EstateThreads get_estadoHilo() {
		return _estadoHilo;
	}
	
	private synchronized void set_estadoHilo(EstateThreads estate) {
		this._estadoHilo=estate;
	}
}