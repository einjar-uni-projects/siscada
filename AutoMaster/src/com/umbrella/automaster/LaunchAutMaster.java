package com.umbrella.automaster;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.automaster.comm.Postmaster;
import com.umbrella.automaster.model.PropertiesFile;
import com.umbrella.mail.mailbox.ClientMailBox;
import com.umbrella.mail.mailbox.ServerMailBox;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.mail.utils.properties.PropertiesFileHandler;
import com.umbrella.mail.utils.properties.PropertyException;

public class LaunchAutMaster {
	public static boolean debug = false;
	
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
		DefaultMessage dm = new DefaultMessage();
		for (int j = 0; j < 10; j++) {
			Postmaster post = Postmaster.getInstance();
			System.out.println("\n\n"+j);
			System.out.println("Vamos a enviar: "+OntologiaMSG.ACTUALIZARCONFIGURACION);
			dm.setIdentificador(OntologiaMSG.ACTUALIZARCONFIGURACION);
			post.sendMessageSCADA(dm);
			System.out.println("Vamos a enviar: "+OntologiaMSG.ACTUALIZARCONTEXTO);
			dm.setIdentificador(OntologiaMSG.ACTUALIZARCONTEXTO);
			post.sendMessageSCADA(dm);
			System.out.println("Vamos a enviar: "+OntologiaMSG.ACTUALIZARCONTEXTOROBOT);
			dm.setIdentificador(OntologiaMSG.ACTUALIZARCONTEXTOROBOT);
			post.sendMessageSCADA(dm);
			System.out.println("Vamos a enviar: "+OntologiaMSG.ARRANCAR);
			dm.setIdentificador(OntologiaMSG.ARRANCAR);
			post.sendMessageSCADA(dm);
			System.out.println("Vamos a enviar: "+OntologiaMSG.AVISARUNFALLO);
			dm.setIdentificador(OntologiaMSG.AVISARUNFALLO);
			post.sendMessageSCADA(dm);
			System.out.println("Vamos a enviar: "+OntologiaMSG.ESTADO_AUTOMATA);
			dm.setIdentificador(OntologiaMSG.ESTADO_AUTOMATA);
			post.sendMessageSCADA(dm);
		}
		//new Thread(new MainFrameLaunch(),"MainFrameLaunch").start();
	}
	
	
	
}
