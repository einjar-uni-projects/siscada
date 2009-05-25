

class RegistroColas 
{
	public static void main(String[] args) 
	{
        
        new QueueServer().run();

    }
//		try{
			/* Se inicia el servidor de colas escuchando en un puerto.
			 * En este caso el puerto 9003
			 * Dan excepcion si hay problemas*/
//			QueueServer servidor = new QueueServer();
//			servidor.startRegistry(33447);
		
			
			/* Se instancias las colas que se quieran*/
//			MessageQueue _queue1 = new MessageQueue();
//			MessageQueue _queue2 = new MessageQueue();
//          MessageQueue _queue3 = new MessageQueue();
		
			/* Ahora se registran las colas en el registro que escucha en el puerto 9003
			 * Da excepcion si hay problemas*/
//			servidor.registerObject(_queue1, "cola1", 33447);
//			servidor.registerObject(_queue2, "cola2", 33447);
//            servidor.registerObject(_queue3, "cola3", 33447);
//		}catch(Exception e){

//			System.out.println("Problemas al iniciar servidor o al registrar colas");
//		}
//	}
}
