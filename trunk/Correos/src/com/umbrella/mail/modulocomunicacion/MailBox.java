/*
 * MailBox.java
 *
 * Created on 21 de abril de 2009, 14:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.umbrella.mail.modulocomunicacion;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.mail.message.MessageInterface;

/**
 *
 * @author l012g412
 */
public class MailBox {
    
    QueueInterface _inputQueue;
    QueueInterface _outputQueue;
    
    /** 
     * Constructor de MailBox
     *
     * @param queueServerIp Ip del servidor de colas
     * @param queueServerPort Puerto del servidor de colas
     * @param inputQueue Referencia de la cola de entrada en el servidor de colas
     * @param outputQueue Referencia de la cola de salida en el servidor de colas
     * @throws RemoteException No se ha podido obtener la cola del servidor de colas
     * @throws MalformedURLException Ip, puerto o referencia no valida
     * @throws NotBoundException No se ha podido obtener la cola del servidor de colas
      */
    public MailBox(String queueServerIp, int queueServerPort, String inputQueue, String outputQueue) throws RemoteException, MalformedURLException, NotBoundException{      
        _inputQueue = (QueueInterface)Naming.lookup("rmi://"+queueServerIp+":"+queueServerPort+"/"+inputQueue);
        _outputQueue = (QueueInterface)Naming.lookup("rmi://"+queueServerIp+":"+queueServerPort+"/"+outputQueue);
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
    public MessageInterface receiveBlocking() throws Exception{
        MessageInterface returnMessage;
        do{		
            returnMessage =  _inputQueue.unqueueMessage();      
        }while(returnMessage == null);
        return returnMessage;
    }
    
    /**
     * Metodo no bloqueante que desencola un mensaje de la cola de entrada
     * Si no hay un mensaje en la cola de entrada la salida ser‡ null
     * @return the head of this queue, or null if this queue is empty.
     */
    public MessageInterface receive() throws Exception{
        MessageInterface returnMessage=null;
        returnMessage =  _inputQueue.unqueueMessage();      
        return returnMessage;
    }
}
