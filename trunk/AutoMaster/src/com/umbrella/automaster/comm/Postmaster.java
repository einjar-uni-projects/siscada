package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.automaster.model.PropertiesFile;
import com.umbrella.mail.mailbox.ClientMailBox;
import com.umbrella.mail.mailbox.ServerMailBox;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.utils.properties.PropertiesFileHandler;
import com.umbrella.mail.utils.properties.PropertyException;

public class Postmaster{
	
	private final ClientMailBox _SCADAmessageConsulter;
	private final ClientMailBox _RB1messageConsulter;
	private final ClientMailBox _RB2messageConsulter;
	private final ClientMailBox _AU1messageConsulter;
	private final ClientMailBox _AU2messageConsulter;
	private final ClientMailBox _AU3AmessageConsulter;
	private static Postmaster instance = null;
	
	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	private Postmaster() throws PropertyException, RemoteException, MalformedURLException, NotBoundException {
		PropertiesFile pfmodel = PropertiesFile.getInstance();
		PropertiesFileHandler.getInstance().LoadValuesOnModel(pfmodel);
		PropertiesFileHandler.getInstance().writeFile();
		
		ServerMailBox smb = new ServerMailBox(pfmodel.getMasterAutPort());
		
		ClientMailBox clientMailBox;
		clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._sendSCADAName, ServerMailBox._reciveSCADAName);
		_SCADAmessageConsulter = clientMailBox;
		
		clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._sendR1Name, ServerMailBox._reciveR1Name);
		_RB1messageConsulter = clientMailBox;
		
		clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._sendR2Name, ServerMailBox._reciveR2Name);
		_RB2messageConsulter = clientMailBox;
		
		clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._sendAU1Name, ServerMailBox._reciveAU1Name);
		_AU1messageConsulter = clientMailBox;

		
		clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._sendAU2Name, ServerMailBox._reciveAU2Name);
		_AU2messageConsulter = clientMailBox;

		
		clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._sendAU3Name, ServerMailBox._reciveAU3Name);
		_AU3AmessageConsulter = clientMailBox;
	}

	/**
	 * Obtiene la instancia única del objeto, la primera invocación
	 * realiza la creación del mismo.
	 * @return la instancia única de Postmaster
	 * @throws PropertyException 
	 */
	public static Postmaster getInstance() throws RemoteException, MalformedURLException, NotBoundException, PropertyException {
		Postmaster ret;
		if(instance == null){
			return getSyncInstance();
		}
		return instance;
	}

	private static synchronized Postmaster getSyncInstance() throws RemoteException, MalformedURLException, NotBoundException, PropertyException{
		if(instance == null)
			instance = new Postmaster();
		return instance;
	}
	
	public boolean sendMessageSCADA(MessageInterface message){
		return _SCADAmessageConsulter.send(message);
	}
	
	public boolean sendMessageAU1(MessageInterface message){
		return _AU1messageConsulter.send(message);
	}
	
	public boolean sendMessageAU2(MessageInterface message){
		return _AU2messageConsulter.send(message);
	}
	
	public boolean sendMessageAU3(MessageInterface message){
		return _AU3AmessageConsulter.send(message);
	}
	
	public boolean sendMessageRB1(MessageInterface message){
		return _RB1messageConsulter.send(message);
	}
	
	public boolean sendMessageRB2(MessageInterface message){
		return _RB2messageConsulter.send(message);
	}
	
	public MessageInterface reciveMessageSCADA(){
		MessageInterface ret = null;
		try {
			ret = _SCADAmessageConsulter.receive();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public MessageInterface reciveMessageAU1(){
		MessageInterface ret = null;
		try {
			ret = _AU1messageConsulter.receive();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public MessageInterface reciveMessageAU2(){
		MessageInterface ret = null;
		try {
			ret = _AU2messageConsulter.receive();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public MessageInterface reciveMessageAU3(){
		MessageInterface ret = null;
		try {
			ret = _AU3AmessageConsulter.receive();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public MessageInterface reciveMessageRB1(){
		MessageInterface ret = null;
		try {
			ret = _RB1messageConsulter.receive();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public MessageInterface reciveMessageRB2(){
		MessageInterface ret = null;
		try {
			ret = _RB2messageConsulter.receive();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public boolean getStateMessageSCADA(){
		return _SCADAmessageConsulter.getState();
	}
	
	public boolean getStateMessageAU1(){
		return _AU1messageConsulter.getState();
	}
	
	public boolean getStateMessageAU2(){
		return _AU2messageConsulter.getState();
	}
	
	public boolean getStateMessageAU3(){
		return _AU3AmessageConsulter.getState();
	}
	
	public boolean getStateMessageRB1(){
		return _RB1messageConsulter.getState();
	}
	
	public boolean getStateMessageRB2(){
		return _RB2messageConsulter.getState();
	}
	
}
