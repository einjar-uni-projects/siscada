/*
 * QueueServer.java
 *
 * Created on 21 de abril de 2009, 14:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.umbrella.mail.modulocomunicacion;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;


/**
 *
 * @author l012g412
 */
public class QueueServer {
    
    static Registry _registry;
        
    /*
     * Metodo que crea un registro RMI en el puerto pasado
     * @param RMIPortNum Puerto donde escuchara el registro RMI
     * @throws RemoteException No se ha iniciado el registro
     */
    public void startRegistry(int RMIPortNum)throws RemoteException{          
        try {
		_registry= LocateRegistry.getRegistry(RMIPortNum);
		_registry.list( );
	}
	catch (RemoteException ex) {
		_registry= LocateRegistry.createRegistry(RMIPortNum);		
	}
    }
    
    /**
     * Metodo que registra un objeto remoto en el servidor de colas
     * @param obj Objeto remoto para registrar
     * @param ref Referencia del objeto remoto en el servidor
     * @param portNum Puerto del registro
     * @throws MalformedURLException puerto o referencia no valida
     * @throws RemoteException No se ha podido registrar en el puerto y referencia indicados
     */
    public void registerObject(java.rmi.server.UnicastRemoteObject obj, String ref, int portNum) throws MalformedURLException, RemoteException{
        Naming.rebind("rmi://localhost:"+portNum+"/"+ref, obj);			
    }
}
