package com.umbrella.autoslave.logic;

import com.umbrella.autoslave.executor.Apagado;
import com.umbrella.autoslave.executor.Estado;


public class Maestro {

	
	static Clock c;
	
	/*
	 * El tiempo de reloj realmente me lo tiene q dar el usuario, se tiene q cargar y estaria muy bien leerlo
	 * de algun sitio para todos los automatas
	 */
	private static int _tiempoReloj=100;
	

	public static void main(String[] args) {
		try	{
			
			Estado estado= Apagado.getInstance();
 			Contexto contexto = Contexto.getInstance();
 			contexto.setState( estado );
 			
 			c=new Clock();
 			c.start();
 	
 			
 			while(!contexto.apagado){
 				contexto.request();
 			}
 			
 		}catch( Exception e ){
 			e.printStackTrace();
 		}
	}
	
	public class Clock extends Thread{
		private long _clock=0;
		public void run(){
			// Aquí el código pesado que tarda mucho
			try {
				wait(_tiempoReloj);
				_clock++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		public synchronized long getClock(){
			return _clock;
		}
	}
}
