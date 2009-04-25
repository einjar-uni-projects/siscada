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
