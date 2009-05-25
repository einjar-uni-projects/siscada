/*
 * MessagePrueba2.java
 *
 * Created on 29 de abril de 2009, 18:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */



/**
 *
 * @author l012g412
 */
public class ConnectionTimedOutMessage implements MessageInterface{
    
        /** Creates a new instance of ConnectionTimedOutMessage */
    public ConnectionTimedOutMessage(){}

    @Override
    public String toString(){
        return "Connection Timed Out Message";
    }
    
}
