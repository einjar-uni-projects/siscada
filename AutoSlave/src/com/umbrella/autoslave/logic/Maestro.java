package com.umbrella.autoslave.logic;

import com.umbrella.autoslave.executor.Apagado;
import com.umbrella.autoslave.executor.Estado;


public class Maestro {


	public static void main(String[] args) {
		try	{
			Estado estado= Apagado.getInstance();
 			Contexto contexto = Contexto.getInstance();
 			contexto.setState( estado ); 
 			contexto.request();
 		}catch( Exception e ){
 			e.printStackTrace();
 		}

	}

}
