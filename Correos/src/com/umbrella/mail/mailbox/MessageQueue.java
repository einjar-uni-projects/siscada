/*
 * MessageQueue.java
 *
 * Created on 21 de abril de 2009, 13:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.umbrella.mail.mailbox;
import java.rmi.RemoteException;
import java.util.LinkedList;

import com.umbrella.mail.message.MessageInterface;

/**
 *
 * @author l012g412
 */
public class MessageQueue extends java.rmi.server.UnicastRemoteObject implements QueueInterface{
    
    /*Cola de mensajes*/
    private LinkedList<MessageInterface> _messageList;
    
    /**
     * Creates a new instance of MessageQueue
     */
    public MessageQueue() throws RemoteException {		
        super();
        _messageList = new LinkedList();
    }
    
    /**
     * Metodo que inserta un mensaje en la cola
     * @param message mensaje a insertar
     * @return true if it was possible to add the element to this queue, else false
     */
    public synchronized boolean queueMessage (MessageInterface message) throws RemoteException{        
        return _messageList.offer(message);                
    }
    
    /**
     * Metodo que extrae un mensaje de la cola
     * @return the head of this queue, or null if this queue is empty.
     */
    public synchronized MessageInterface unqueueMessage() throws RemoteException{
    	//if(_messageList.size() != 0)
    		return _messageList.poll();
    	//return null;
    }    
}
