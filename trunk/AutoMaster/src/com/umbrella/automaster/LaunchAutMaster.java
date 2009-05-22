package com.umbrella.automaster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.umbrella.automaster.comm.Postmaster;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.OntologiaMSG;
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
				dm.setIdentificador(om[option]);
				switch (om[option]) {
				case ACTUALIZARCONFIGURACION:
					
					break;
				case ACTUALIZARCONTEXTO:
					
					break;
				case ACTUALIZARCONTEXTOROBOT:
					
					break;
				case START:
					
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
				case AUTOM_STATE:					
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
				case CAKE_DEPOT:
					System.out.println("Elegir la cantidad de pasteles en el deposito: <Entero>");
					Integer n_cake_depot = readInt();
					if(n_cake_depot != null){
						dm.setObject(n_cake_depot);
					}else
						error = true;
					break;
				case AU1_CAKES_POS:
					ArrayList<Integer> ali = new ArrayList<Integer>(7);
					System.out.println("Elegir la cantidad de pasteles en la posicion 1: <Entero>");
					Integer au1_cakes_pos = readInt();
					if(au1_cakes_pos != null)
						ali.add(0, au1_cakes_pos);
					else
						error = true;
					System.out.println("Elegir la cantidad de pasteles en la posicion 2: <Entero>");
					au1_cakes_pos = readInt();
					if(au1_cakes_pos != null)
						ali.add(1, au1_cakes_pos);
					else
						error = true;
					System.out.println("Elegir la cantidad de pasteles en la posicion 3: <Entero>");
					au1_cakes_pos = readInt();
					if(au1_cakes_pos != null)
						ali.add(2, au1_cakes_pos);
					else
						error = true;
					System.out.println("Elegir la cantidad de pasteles en la posicion 4: <Entero>");
					au1_cakes_pos = readInt();
					if(au1_cakes_pos != null)
						ali.add(3, au1_cakes_pos);
					else
						error = true;
					System.out.println("Elegir la cantidad de pasteles en la posicion 5: <Entero>");
					au1_cakes_pos = readInt();
					if(au1_cakes_pos != null)
						ali.add(4, au1_cakes_pos);
					else
						error = true;
					System.out.println("Elegir la cantidad de pasteles en la posicion 6: <Entero>");
					au1_cakes_pos = readInt();
					if(au1_cakes_pos != null)
						ali.add(5, au1_cakes_pos);
					else
						error = true;
					System.out.println("Elegir la cantidad de pasteles en la posicion 7: <Entero>");
					au1_cakes_pos = readInt();
					if(au1_cakes_pos != null)
						ali.add(6, au1_cakes_pos);
					else
						error = true;
					
					dm.setObject(ali);
					
					break;
				case ROBOT_SET_CONTENT:
					System.out.println("Elegir el automata: R1 R2");
					String robot_content_machine = readStr();
					System.out.println("Elegir el estado: vacia(0), pastel o blisterCorrecto(1) blisterVacio o blister(2)");
					Integer state = readInt();
					if(robot_content_machine != null && state != null){
						dm.setObject(state);
						dm.getParametros().add(robot_content_machine);
					}else
						error = true;
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
	
	private Integer readInt(){
		Integer ret = null;
		System.out.print("[Integer]>");
		try{
			ret = Integer.parseInt(br.readLine());
		}catch (Exception e) {
			// TODO: handle exception
		}
		return ret;
	}
	
	
	
}
