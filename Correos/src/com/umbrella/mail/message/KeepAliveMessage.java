package com.umbrella.mail.message;

import java.util.Vector;

/*
 * MessagePrueba1.java
 *
 * Created on 29 de abril de 2009, 18:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */



/**
 *
 * @author l012g412
 */
public class KeepAliveMessage implements MessageInterface{
        
    /** Creates a new instance of KeepAliveMessage */
    public KeepAliveMessage() {        
    }

	@Override
	public MSGOntology getIdentifier() {
		
		return null;
	}

	@Override
	public Object getObject() {
		
		return null;
	}

	@Override
	public Vector<String> getParameters() {
		
		return null;
	}

	@Override
	public void setIdentifier(MSGOntology idetificador) {
		
		
	}

	@Override
	public void setObject(Object objeto) {
		
		
	}
    
}
