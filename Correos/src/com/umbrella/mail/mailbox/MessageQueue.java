/*
 * MessageQueue.java
 *
 * Created on 21 de abril de 2009, 13:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.umbrella.mail.mailbox;
import java.util.*;
import java.rmi.RemoteException;

import com.umbrella.mail.message.MessageInterface;

/**
 *
 * @author l012g412
 */
public class MessageQueue extends java.rmi.server.UnicastRemoteObject implements QueueInterface{
    
    /*Cola de mensajes*/
    LinkedList<MessageInterface> _messageList;
    
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
    public boolean queueMessage (MessageInterface message) throws RemoteException{        
        return _messageList.offer(message);                
    }
    
    /**
     * Metodo que extrae un mensaje de la cola
     * @return the head of this queue, or null if this queue is empty.
     */
    public MessageInterface unqueueMessage() throws RemoteException{
        return _messageList.poll();                        
    }    
}
