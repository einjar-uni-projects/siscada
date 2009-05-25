/*
 * MailBox.java
 *
 * Created on 21 de abril de 2009, 14:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */



import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.mail.mailbox.KeepAliveThread;

/**
 *
 * @author l012g412
 */
public class MailBox {
    
    private QueueInterface _inputQueue = null;
    private QueueInterface _outputQueue = null;
	//Cola de keepalive
	private QueueInterface _keepAliveQueue = null;
    private KeepAliveThread _keepAliveThread  = null;
	
	private State _state;
    
    /** 
     * Constructor de MailBox
     *
     * @param queueServerIp Ip del servidor de colas
     * @param queueServerPort Puerto del servidor de colas
     * @param inputQueue Referencia de la cola de entrada en el servidor de colas
     * @param outputQueue Referencia de la cola de salida en el servidor de colas
     * @param keepAliveQueue Referencia de la cola de keepAlive en el servidor de colas
     * @param slave Indica si el MailBox actua como esclavo en la comunicacion de keepAlive
     * @throws RemoteException No se ha podido obtener la cola del servidor de colas
     * @throws MalformedURLException Ip, puerto o referencia no valida
     * @throws NotBoundException No se ha podido obtener la cola del servidor de colas
      */
    public MailBox(String queueServerIp, int queueServerPort, String inputQueue
			, String outputQueue, String keepAliveQueue, boolean slave) 
			throws RemoteException, MalformedURLException, NotBoundException{      
			
        _inputQueue = (QueueInterface)Naming.lookup("rmi://"+queueServerIp+":"+
                queueServerPort+"/"+inputQueue);
        
        _outputQueue = (QueueInterface)Naming.lookup("rmi://"+queueServerIp+":"
                +queueServerPort+"/"+outputQueue);
		
		//Codigo referenta al hilo encargado d la comunicacion	
		
		_keepAliveQueue = (QueueInterface)Naming.lookup("rmi://"+queueServerIp+
                ":"+queueServerPort+"/"+keepAliveQueue);

		_state = new State(true);

		//Creacion del hilo de keepAlive
		_keepAliveThread = new KeepAliveThread(slave,
             this._keepAliveQueue, this._inputQueue, new ConnectionState(true));
        
		//Inicio del hilo de keepAlive
        this._keepAliveThread.start();
    }
    
    /**
     * Metodo que encola un mensaje en la cola de salida
     * @param message Mensaje a enviar
     * @return true if it was possible to add the element to this queue, else false
     */
    public boolean send(MessageInterface message){
        try{
            return _outputQueue.queueMessage(message);
        }catch(Exception e){
            return false;
        }
    }
    
    /**
     * Metodo bloqueante que desencola un mensaje de la cola de entrada
     * Se queda esperando hasta que haya un mensaje en la cola.
     * @return the head of this queue, or null if this queue is empty.
     */
    public MessageInterface BlockingReceive() throws Exception{
        MessageInterface returnMessage;
        do{

			returnMessage =  _inputQueue.unqueueMessage();
            //Thread.sleep(4000);
            //System.out.println("No llega nada");
			
			if(_state.getState() == false){
				if(returnMessage == null){
					//Lanza excepcion
					throw new TimeOutException();
				}
			}			
        }while(returnMessage == null);
        return returnMessage;
    }

        /**
     * Metodo no bloqueante que desencola un mensaje de la cola de entrada
     * Se queda esperando hasta que haya un mensaje en la cola.
     * @return the head of this queue, or null if this queue is empty.
     */
    public MessageInterface receive() throws Exception{

        MessageInterface returnMessage;
        
        returnMessage =  _inputQueue.unqueueMessage();

        //Si el estado de la conexión es false y se recibio un mensaje
        if(_state.getState() == false && returnMessage != null){
            //Se actualiza el estado
            _state.setState(true);
        }
        
        return returnMessage;
    }

   
}