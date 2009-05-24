/*
 * MessagePrueba2.java
 *
 * Created on 29 de abril de 2009, 18:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.umbrella.mail.test;

import java.util.Vector;

import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.MSGOntology;

/**
 *
 * @author l012g412
 */
public class TestMessage2 implements MessageInterface{
    
    String cadena;
    
    /** Creates a new instance of MessagePrueba2 */
    public TestMessage2() {
    }

	@Override
	public MSGOntology getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<String> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIdentifier(MSGOntology idetificador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObject(Object objeto) {
		// TODO Auto-generated method stub
		
	}


    
}
