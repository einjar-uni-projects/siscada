package com.umbrella.automaster;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.automaster.model.PropertiesFile;
import com.umbrella.mail.mailbox.ClientMailBox;
import com.umbrella.mail.mailbox.ServerMailBox;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.mail.utils.properties.PropertiesFileHandler;
import com.umbrella.mail.utils.properties.PropertyException;

public class LaunchAutMaster {
	public static boolean debug = false;
	private final ClientMailBox _clientMailBox;
	/**
	 * @param args
	 * @throws PropertyException 
	 */
	public static void main(String[] args) throws Exception {
		if(args.length > 0)
			debug = true;
		new LaunchAutMaster();
	}
	
	public LaunchAutMaster() throws PropertyException, RemoteException, MalformedURLException, NotBoundException {
		ServerMailBox smb = new ServerMailBox(9003);
		PropertiesFile pfmodel = PropertiesFile.getInstance();
		PropertiesFileHandler.getInstance().LoadValuesOnModel(pfmodel);
		PropertiesFileHandler.getInstance().writeFile();
		_clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._sendSCADAName, ServerMailBox._reciveSCADAName);
		DefaultMessage dm = new DefaultMessage();
		int i= 0;
		while(true){
			
			System.out.println("\n\n"+(i++));
			System.out.println("Vamos a enviar: "+OntologiaMSG.ACTUALIZARCONFIGURACION);
			dm.setIdentificador(OntologiaMSG.ACTUALIZARCONFIGURACION);
			_clientMailBox.send(dm);
			System.out.println("Vamos a enviar: "+OntologiaMSG.ACTUALIZARCONTEXTO);
			dm.setIdentificador(OntologiaMSG.ACTUALIZARCONTEXTO);
			_clientMailBox.send(dm);
			System.out.println("Vamos a enviar: "+OntologiaMSG.ACTUALIZARCONTEXTOROBOT);
			dm.setIdentificador(OntologiaMSG.ACTUALIZARCONTEXTOROBOT);
			_clientMailBox.send(dm);
			System.out.println("Vamos a enviar: "+OntologiaMSG.ARRANCAR);
			dm.setIdentificador(OntologiaMSG.ARRANCAR);
			_clientMailBox.send(dm);
			System.out.println("Vamos a enviar: "+OntologiaMSG.AVISARUNFALLO);
			dm.setIdentificador(OntologiaMSG.AVISARUNFALLO);
			_clientMailBox.send(dm);
			System.out.println("Vamos a enviar: "+OntologiaMSG.ESTADO_AUTOMATA);
			dm.setIdentificador(OntologiaMSG.ESTADO_AUTOMATA);
			_clientMailBox.send(dm);
		}
		//new Thread(new MainFrameLaunch(),"MainFrameLaunch").start();
	}
	
	
	
}
