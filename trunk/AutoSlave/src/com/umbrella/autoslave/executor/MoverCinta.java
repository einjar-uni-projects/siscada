package com.umbrella.autoslave.executor;

import com.umbrella.autoslave.Utils.EstateThreads;
import com.umbrella.autoslave.logic.Configuracion;
import com.umbrella.autoslave.logic.Contexto;

public class MoverCinta extends Thread implements Estado{

	private static Estado INSTANCE = null;
	
	private EstateThreads _estadoHilo;
	
	private int _posicionAsociada;
	
	private Contexto contexto=Contexto.getInstance();
	private Configuracion configuracion=Configuracion.getInstance();
	/*
	 * lo q desplaza la cinta en un click, en metros
	 */
	private double velCintaPorNanoseg;
	/*
	 * espacio que recorre la cinta en un click, medido en CM 
	 */
	private double espacioEnClick;
	
	private double velCinta;

	private MoverCinta(double velocidad, int posAsociada) {
		// TODO Auto-generated constructor stub
		setVelCinta(velocidad);
		setVelCintaPorMiliseg(getVelCinta()/(60*1000));
		setEspacioEnClick(getVelCintaPorMiliseg()*configuracion.get_tiempoReloj());
		set_posicionAsociada(posAsociada);
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
		if(contexto.getTipo().equalsIgnoreCase("blister")){
			for(int i=0;i<contexto.get_listaBlister().size();i++){
				if((contexto.get_listaBlister().get(i).get_posicion()+espacioEnClick)<=configuracion.getSizeCinta())
					contexto.get_listaBlister().get(i).incrementarPosicion(espacioEnClick);
				else{
					//el pastel SE HA CAIDO DE LA CINTA
					contexto.get_listaBlister().get(i).set_posicion(configuracion.getSizeCinta());
				}
			}
		}else{
			for(int i=0;i<contexto.get_listaPasteles().size();i++){
				if((contexto.get_listaPasteles().get(i).get_posicion()+espacioEnClick)<=configuracion.getSizeCinta())
					contexto.get_listaPasteles().get(i).incrementarPosicion(espacioEnClick);
				else{
					//el pastel SE HA CAIDO DE LA CINTA
					contexto.get_listaPasteles().get(i).set_posicion(configuracion.getSizeCinta());
				}
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
	
	private synchronized static void createInstance(double velocidad, int posAsociada) {
		if (INSTANCE == null) { 
			INSTANCE = new MoverCinta(velocidad, posAsociada);
		}
	}

	public static Estado getInstance(double velocidad, int posAsociada) {
		if (INSTANCE == null) createInstance(velocidad, posAsociada);
		return INSTANCE;
	}
	
	public static Estado getInstance() {
		return INSTANCE;
	}

	public void enviaMensaje() {
		// TODO Auto-generated method stub
		
	}

	public void cambiaMensaje(boolean[] msg) {
		// TODO Auto-generated method stub
		
	}	
	
	public synchronized EstateThreads get_estadoHilo() {
		return _estadoHilo;
	}
	
	private synchronized void set_estadoHilo(EstateThreads estate) {
		this._estadoHilo=estate;
	}

	private synchronized double getVelCintaPorMiliseg() {
		return velCintaPorNanoseg;
	}

	private synchronized void setVelCintaPorMiliseg(double velCintaPorMiliseg) {
		this.velCintaPorNanoseg = velCintaPorMiliseg;
	}

	private synchronized double getVelCinta() {
		return velCinta;
	}

	private synchronized void setVelCinta(double velCinta) {
		this.velCinta = velCinta;
	}

	private synchronized double getEspacioEnClick() {
		return espacioEnClick;
	}

	private synchronized void setEspacioEnClick(double espacioEnClick) {
		this.espacioEnClick = espacioEnClick;
	}
	
	private synchronized int get_posicionAsociada() {
		return _posicionAsociada;
	}

	private synchronized void set_posicionAsociada(int asociada) {
		_posicionAsociada = asociada;
	}
}