package com.umbrella.autoslave.executor;

import com.umbrella.autocommon.Configuracion;
import com.umbrella.autocommon.Contexto;
import com.umbrella.autoslave.utils2.EstateThreads;

public class SalidaCinta extends Thread{

	private double _posicion;
	private int _posicionAsociada;

	private EstateThreads _estadoHilo;
	
	private Contexto contexto=Contexto.getInstance();
	private Configuracion configuracion=Configuracion.getInstance();
	
	private String tipo;
	
	public SalidaCinta(double posicion, int posAsociada, String tipo) {
		// TODO Auto-generated constructor stub
		set_estadoHilo(EstateThreads.CREADO);
		this._posicion=posicion;
		set_posicionAsociada(posAsociada);
		this.tipo=tipo;
	}

	@Override
	public void run(){
		set_estadoHilo(EstateThreads.EJECUTANDO);
		contexto.setDispositivosInternos(get_posicionAsociada(), true);
		
		boolean finCintaLibre=finCintaLibre(tipo);
		while (!finCintaLibre){
			//se espera al siguiente click de la cinta
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//comprobar el estado de la cinta
			finCintaLibre=finCintaLibre(tipo);
		}
		contexto.decrementarNumPasteles();
		//se ha recogido el bizcocho del fin de la lista
		contexto.setDispositivosInternos(get_posicionAsociada(), false);
		set_estadoHilo(EstateThreads.ACABADO);
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
	
	private synchronized boolean finCintaLibre(String tipo){
		boolean libre=true;
		for(int i=0;i<contexto.get_listaPasteles().size();i++){
			if(tipo.equals("pastel")){
				if(contexto.get_listaPasteles().get(i).get_posicion()>=(get_posicion()-configuracion.getErrorSensor()))
					libre=false;
			}else{
				if(contexto.get_listaBlister().get(i).get_posicion()>=(get_posicion()-configuracion.getErrorSensor()))
					libre=false;
			}
		}
		return libre;
	}
	
	public synchronized double get_posicion() {
		return _posicion;
	}
	
	private synchronized int get_posicionAsociada() {
		return _posicionAsociada;
	}

	private synchronized void set_posicionAsociada(int asociada) {
		_posicionAsociada = asociada;
	}
}