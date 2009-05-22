package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.ContextoMaestro;
import com.umbrella.autocommon.ContextoRobot;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.EstateRobots;
import com.umbrella.utils.NombreMaquinas;

/**
 * 
 * @author pablo Clase que leera los mensajes enviados por al automata 1 y
 *         actuara en consecuencia
 */
public class ReceiveRobot1 extends Thread {

	Postmaster _postmaster;
	ContextoMaestro _masterContext;
	Configuration _configutarion;

	public synchronized void inicializar() {
		try {
			_postmaster = Postmaster.getInstance();
			_masterContext = ContextoMaestro.getInstance();
			_configutarion = Configuration.getInstance();
		} catch (RemoteException e1) {
			// TODO: handle exception
		} catch (MalformedURLException e2) {
			// TODO: handle exception
		} catch (PropertyException e4) {
			// TODO: handle exception
		}
	}

	@Override
	public void run() {
		DefaultMessage dm = new DefaultMessage();
		MessageInterface msg = null;
		do {
			msg = _postmaster.reciveMessageRB1();
			
			if (msg != null) {
				System.out.println("RB1 Recive: " + msg.getIdentificador());
				switch (msg.getIdentificador()) {
				case INTERFERENCIA:
					String cinta = msg.getParametros().get(1);
					if (cinta.equals(NombreMaquinas.CINTA_1.getName())) {
						// envio a la cinta 1
						_postmaster.sendMessageAU1(msg);
					} else if (cinta.equals(NombreMaquinas.CINTA_2.getName())) {
						// envio a la cinta 2
						_postmaster.sendMessageAU2(msg);
					}
					break;
				case PRODUCTORECOGIDO:
					// primer paraemtro el robot y 2 el producto
					String producto = msg.getParametros().get(1);
					if (producto.equals("pastel")) {
						// se recoge un pastel de la cinta 1
						_masterContext
								.get_contextoAut1()
								.setDispositivosInternos(
										_configutarion
												.getPosicionAsociada(NombreMaquinas.FIN_1),
										false);
					} else {
						// un blister de la cinta 2
						_masterContext
								.get_contextoAut2()
								.setDispositivosInternos(
										_configutarion
												.getPosicionAsociada(NombreMaquinas.FIN_2),
										false);
					}
					break;
				case FININTERFERENCIA:
					String cinta2 = msg.getParametros().get(1);
					if (cinta2.equals(NombreMaquinas.CINTA_1.getName())) {
						// envio a la cinta 1
						_postmaster.sendMessageAU1(msg);
					} else if (cinta2.equals(NombreMaquinas.CINTA_2.getName())) {
						// envio a la cinta 2
						_postmaster.sendMessageAU2(msg);
					}
					break;
				case PRODUCTOCOLOCADO:
					/*
					 * SUPOSICION QUE NO LA PIFIAMOS A LA HORA DE PEDIR UN
					 * MOVIMIENTO Y NO PONEMOS2 BLISTER SEGUIDOS casos: pongo un
					 * blister en la mesa pongo un pastel entre 0 y 4 pongo el
					 * 4� pastel
					 */
					String colocado = msg.getParametros().get(1);
					if (colocado.equals("blister")) {
						_masterContext
								.get_contextoAut3()
								.setDispositivosInternos(
										_configutarion
												.getPosicionAsociada(NombreMaquinas.INICIO),
										true);
						_masterContext.setBlisterColocado(true);
						_masterContext.resetContador();
					} else if (colocado.equals("pastel")) {
						// pongo un pastel
						_masterContext.incrementarContador();
					} else { // blisterCompleto
						_masterContext
								.get_contextoAut3()
								.setDispositivosInternos(
										_configutarion
												.getPosicionAsociada(NombreMaquinas.INICIO),
										false);
						_masterContext.setBlisterColocado(false);
						_masterContext.resetContador();
					}

					break;
				case ACTUALIZARCONTEXTOROBOT:
					ContextoRobot con_update_context = (ContextoRobot) msg.getObject();
					_masterContext.set_contextoRobot1(con_update_context);
					
					//Se notifica del estado al SCADA
					dm.setIdentificador(OntologiaMSG.AUTOM_STATE);
					dm.setObject(!con_update_context.isApagado());
					dm.getParametros().add("RB1");
					_postmaster.sendMessageSCADA(dm);
					
					//Envia el estado
					dm = new DefaultMessage();
					dm.setIdentificador(OntologiaMSG.ROBOT_SET_CONTENT);
					if(con_update_context.getEstadoInterno() == EstateRobots.CAMINOPOSICION_3){
						if(con_update_context.isPastel())
							dm.setObject(1);
						else
							dm.setObject(2);
					}else if(con_update_context.getEstadoInterno() == EstateRobots.DESPLAZARBLISTERCOMPLETO){
						dm.setObject(3);
					}else
						dm.setObject(0);
					
					dm.getParametros().add("RB1");
					_postmaster.sendMessageSCADA(dm);
					
					break;
				}
			}
			
			dm = new DefaultMessage();

		} while (msg != null);
	}
}
