package com.umbrella.autoslave.logic;


import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.umbrella.autoslave.utils2.EstateRobots;
import com.umbrella.mail.mailbox.ClientMailBox;

/*
 * este robot tiene el estado reposo, el estado voy por blister y voy por pastel
 */
public class Robot2 {

	private static Configuracion _configuracion= Configuracion.getInstance();
	private static ContextoRobot _contexto= ContextoRobot.getInstance();
	
	private static Clock _clock;
	private static ClientMailBox _buzon;

	private static String host = "localhost";
	private static int puerto = 9003;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		_clock=new Clock();
		_clock.start();
		try {
			_buzon=new ClientMailBox(host,puerto,"EntradaRobot2","SalidaRobot2");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		_contexto.setEstadoInterno(EstateRobots.REPOSO);
		long cicloAct=_clock.getClock();
		
		while(!_contexto.isFIN()){
			if(cicloAct<_clock.getClock()){
				cicloAct=_clock.getClock();
				//posicion 1 es la cinta 3
				//posicion 2 es la caja de validos
				//posicion 3 es la caja de no validos
				_contexto.setDiffTiempo(System.currentTimeMillis()-_contexto.getTiempo());
				if(_contexto.getEstadoInterno().equals(EstateRobots.REPOSO)){
					/*
					 * si recibe un mensaje de recoger blister valido: CAMINOPOSICION_2 y coge el tiempo
					 * si recibe un mensaje de recoger blister no valido: CAMINOPOSICION_3 y coge el tiempo
					 */
					
					/*
					if(<Mensaje de blister valido>){
						_contexto.setValido(true);
					}
					if(<Mensaje de blister no valido>){
						_contexto.setValido(false);
					}
					*/
					_contexto.setEstadoInterno(EstateRobots.CAMINOPOSICION_1);
				 	_contexto.setTiempo(System.currentTimeMillis());
				 	_contexto.setDiffTiempo(System.currentTimeMillis()-_contexto.getTiempo());
				}else if(_contexto.getEstadoInterno().equals(EstateRobots.CAMINOPOSICION_1)){
					// controlar interferencias, mejor lo hace el maestro
					if( _contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() -_configuracion.getInterferencia()/2)*1000)){
						_contexto.setEstadoInterno(EstateRobots.SOBREPOSICION_1);
						/*
						 * envia el mensaje de interferencia sobre la cinta 3
						 */
					}
				}else if(_contexto.getEstadoInterno().equals(EstateRobots.SOBREPOSICION_1)){
					//cogo el Blister
					if( (System.currentTimeMillis()-_contexto.getTiempo()) > (_configuracion.getMoverBlister()*1000)){
						if(_contexto.isValido()){
							_contexto.setEstadoInterno(EstateRobots.CAMINOPOSICION_2);
						}else{
							_contexto.setEstadoInterno(EstateRobots.CAMINOPOSICION_3);
						}
						/*
						 * enviar mensaje de FIN de cinta 3 libre
						 */
						_contexto.setPastel(false);
					}
				}else if(_contexto.getEstadoInterno().equals(EstateRobots.CAMINOPOSICION_2)){
					if(!_contexto.isPastel() && (_contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() +_configuracion.getInterferencia()/2)*1000))){
						_contexto.setPastel(true);
					}
					
				}else if(_contexto.getEstadoInterno().equals(EstateRobots.SOBREPOSICION_2)){
					if( (System.currentTimeMillis()-_contexto.getTiempo()) > (_configuracion.getMoverBlister()*2*1000)){
						/*
						 * Enviar mensaje de pastel valido depositado
						 */
						_contexto.setEstadoInterno(EstateRobots.REPOSO);
					}
					
				}else if(_contexto.getEstadoInterno().equals(EstateRobots.CAMINOPOSICION_3)){
					if(!_contexto.isPastel() && (_contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() +_configuracion.getInterferencia()/2)*1000))){
						_contexto.setPastel(true);
					}
					
				}else if(_contexto.getEstadoInterno().equals(EstateRobots.SOBREPOSICION_3)){
					if( (System.currentTimeMillis()-_contexto.getTiempo()) > (_configuracion.getMoverBlister()*2*1000)){
						/*
						 * Enviar mensaje de pastel NO valido depositado
						 */
						_contexto.setEstadoInterno(EstateRobots.REPOSO);
					}
					
				}
			}
			
		}

	}

}
