/*
 * Main2.java
 *
 * Created on 29 de abril de 2009, 19:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.umbrella.mail.test;

import com.umbrella.mail.mailbox.ClientMailBox;
import com.umbrella.mail.message.MessageInterface;

/**
 * 
 * @author l012g412
 */
public class Main2 {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		/*
		 * Se crea la conexi�n con las dos colas. (Explicado en Main.java) Lo
		 * unico a destacar aqui es que en este caso la cola por la que se
		 * recibe es Cola2 y la cola por la que se envia es Cola1
		 */
		try {
			ClientMailBox buzon = new ClientMailBox("192.168.10.100", 9003, "Cola2","Cola1");

			MessageInterface message;

			try {
				/*
				 * La funcion receive es persistente hasta que hay algo en la
				 * cola que leer. Si falla la conexion con el registro de colas,
				 * daria una excepcion
				 */
				message = buzon.receiveBlocking();

				/* Para acceder a los atributos, importante hacer el casting. */
				System.out.println("Recibido: "
						+ ((MessagePrueba1) message).getVarString());

			} catch (Exception e) {
				System.out
						.println("Error al recibir. Conexion con el registro de colas perdida");
			}

		} catch (Exception e) {
			System.out.println("Problemas al conectar con las colas");
		}
	}

}
