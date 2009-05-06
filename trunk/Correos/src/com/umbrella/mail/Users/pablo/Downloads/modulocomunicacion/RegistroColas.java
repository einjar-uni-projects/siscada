package com.umbrella.mail.Users.pablo.Downloads.modulocomunicacion;

class RegistroColas 
{
	public static void main(String[] args) 
	{
		try{
			/* Se inicia el servidor de colas escuchando en un puerto.
			 * En este caso el puerto 9003
			 * Dan excepcion si hay problemas*/
			QueueServer servidor = new QueueServer();
			servidor.startRegistry(9003);
		
			
			/* Se instancias las colas que se quieran*/
			MessageQueue _queue1 = new MessageQueue();
			MessageQueue _queue2 = new MessageQueue();
		
			/* Ahora se registran las colas en el registro que escucha en el puerto 9003
			 * Da excepcion si hay problemas*/
			servidor.registerObject(_queue1, "Cola1", 9003);
			servidor.registerObject(_queue2, "Cola2", 9003);
		}catch(Exception e){

			System.out.println("Problemas al iniciar servidor o al registrar colas");
		}
	}
}
