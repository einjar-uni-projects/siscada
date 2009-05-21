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
		boolean error;
		
		while(option >= 0){
			error = false;
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
					dm.setIdentificador(om[option]);
					System.out.println("Elegir el automata: R1 R2 AU1 AU2 AU3");
					String aut = readStr();
					System.out.println("Elegir el estado: true false");
					Boolean bol = readBoolean();
					if(aut != null && bol != null){
						dm.setObject(bol);
						dm.getParametros().add(aut);
					}else
						error = true;
					break;
				case FINCINTALIBRE:
					
					break;
				case FININTERFERENCIA:
					
					break;
				case INTERFERENCIA:
					
					break;
				case MODIFICARCAMPO:
					
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
				if(!error){
					System.out.println("Se manda el mensaje: "+dm);
					post.sendMessageSCADA(dm);
					dm = new DefaultMessage();
				}else
					System.err.println("Parametro incorrecto");
			}else{
				System.err.println("Opción no válida");
			}
		}
	}

	private Boolean readBoolean() {
		Boolean ret = null;
		System.out.print("[Boolean]>");
		try{
			ret = Boolean.parseBoolean(br.readLine());
		}catch (Exception e) {
			// TODO: handle exception
		}
		return ret;
	}

	private void showOptions(OntologiaMSG[] om) {
		System.out.println("\nOpciones de mensaje:");
		int i;
		for (i = 0; i < om.length-1; i+=2) {
				System.out.println(i+"-"+om[i]+"\t\t"+(i+1)+"-"+om[i+1]);
		}
		if(i < om.length){
			System.out.println(i+"-"+om[i]);
		}
		
	}
	
	private String readStr(){
		String line = null;
		System.out.print("[String]>");
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
		System.out.print("[Integer]>");
		try{
			ret = Integer.parseInt(br.readLine());
		}catch (Exception e) {
			// TODO: handle exception
		}
		return ret;
	}
	
	
	
}
