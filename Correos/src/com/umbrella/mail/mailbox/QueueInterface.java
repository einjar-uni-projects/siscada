/*
 * QueueInterface.java
 *
 * Created on 21 de abril de 2009, 13:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.umbrella.mail.mailbox;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.umbrella.mail.message.MessageInterface;

/**
 *
 * @author l012g412
 */
public interface QueueInterface extends Remote{
    
    public boolean queueMessage(MessageInterface message) throws RemoteException;
    public MessageInterface unqueueMessage() throws RemoteException;
}
