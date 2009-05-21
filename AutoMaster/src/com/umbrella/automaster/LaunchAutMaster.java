package com.umbrella.automaster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
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
		if(debug){
			testSCADA();
		}
	}

	private void testSCADA() throws RemoteException, MalformedURLException, NotBoundException, PropertyException {
		int option = 0;
		OntologiaMSG om[] = OntologiaMSG.values();
		Postmaster post = Postmaster.getInstance();
		DefaultMessage dm = new DefaultMessage();
		
		while(option >= 0){
			showOptions(om);
			option = readInt();
			if(option >= 0){
				switch (om[option]) {
				case ACTUALIZARCONFIGURACION:
					
					break;
				case ACTUALIZARCONTEXTO:
					
					break;
				case ACTUALIZARCONTEXTOROBOT:
					
					break;
				case ARRANCAR:
					
					break;
				case ARRANCARDESDEEMERGENCIA:
					
					break;
				case AVISARUNFALLO:
					
					break;
				case BLISTERALMACENADO:
					
					break;
				case BLISTERCOMPLETO:
					
					break;
				case BLISTERLISTO:
					
					break;
				case BLISTERNOVALIDO:
					
					break;
				case BLISTERVALIDO:
					
					break;
				case ESTADO_AUTOMATA:
					
					break;
				case FINCINTALIBRE:
					
					break;
				case FININTERFERENCIA:
					
					break;
				case INTERFERENCIA:
					
					break;
				case MODIFICARCAMPO:
					
					break;
				case MOVERDESDEMESA:
					
					break;
				case PARADA:
					
					break;
				case PARADAEMERGENCIA:
					
					break;
				case PARADAFALLO:
					
					break;
				case PASTELLISTO:
					
					break;
				case PRODUCTOCOLOCADO:
					
					break;
				case PRODUCTORECOGIDO:
					
					break;
				case RELLENARMAQUINA:
					
					break;
				case RESET:
					
					break;
	
				default:
					break;
				}
				
				post.sendMessageSCADA(dm);
			}else{
				System.err.println("Opción no válida");
			}

				
				
		}
	}

	private void showOptions(OntologiaMSG[] om) {
		System.out.println("Opciones de mensaje:");
		for (int i = 0; i < om.length; i++) {
			System.out.println(i+"-"+om[i]);
		}
	}
	
	private String readStr(){
		String line = null;
		System.out.println(">");
		try {
			line = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
	
	private int readInt(){
		int ret = -1;
		System.out.println(">");
		try{
			ret = Integer.parseInt(br.readLine());
		}catch (Exception e) {
			// TODO: handle exception
		}
		return ret;
	}
	
	
	
}
