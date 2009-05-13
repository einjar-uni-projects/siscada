/*
 * MessageInterface.java
 *
 * Created on 21 de abril de 2009, 15:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.umbrella.mail.message;
import java.io.Serializable;

/**
 *
 * @author l012g412
 */
public interface MessageInterface extends Serializable{
	
	public OntologiaMSG getMSGCode();
	
}
