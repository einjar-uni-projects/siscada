/*
 * MessagePrueba1.java
 *
 * Created on 29 de abril de 2009, 18:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.umbrella.mail.test;

import java.util.Vector;

import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.message.MessageInterface;

/**
 *
 * @author l012g412
 */
public class TestMessage1 implements MessageInterface{
    
    private int varInteger;
    private String varString;
    private boolean varBoolean;
    
    /** Creates a new instance of MessagePrueba1 */
    public TestMessage1() {        
    }

	public void setVarInteger(int varInteger) {
		this.varInteger = varInteger;
	}

	public int getVarInteger() {
		return varInteger;
	}

	public void setVarString(String varString) {
		this.varString = varString;
	}

	public String getVarString() {
		return varString;
	}

	public void setVarBoolean(boolean varBoolean) {
		this.varBoolean = varBoolean;
	}

	public boolean isVarBoolean() {
		return varBoolean;
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