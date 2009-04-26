package com.umbrella.autoslave.logic;

import com.umbrella.autoslave.executor.Apagado;
import com.umbrella.autoslave.executor.Estado;


public class Maestro {
	
	static Clock c;

	public static void main(String[] args) {
		try	{
			
			Estado estado= Apagado.getInstance();
 			Contexto contexto = Contexto.getInstance();
 			contexto.setState( estado );
 			
 			/*
 			 * tenemos dos hilos, uno es el reloj y el otro es la ejecucion del automata que debe quedarse bloqueado entre 
 			 * los clicks del reloj, para eso el reloj debe hacer un notify a todos los hilos notifyAll()
 			 */
 			c=new Clock();
 			c.start();
 			
 			while(!contexto.apagado){
 				contexto.request();
 			}
 			
 		}catch( Exception e ){
 			e.printStackTrace();
 		}
	}
	
	
}
