package com.umbrella.autoslave.logic;


import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.umbrella.autoslave.utils2.EstateRobots;
import com.umbrella.mail.modulocomunicacion.MailBox;

/*
 * este robot tiene el estado reposo, el estado voy por blister y voy por pastel
 */
public class Robot1 {

	private static Configuracion _configuracion= Configuracion.getInstance();
	private static ContextoRobot _contexto= ContextoRobot.getInstance();
	
	private static Clock _clock;
	private static MailBox _buzon;
	
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
			_buzon=new MailBox(host,puerto,"EntradaRobot1","SalidaRobot1");
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
				
				_contexto.setDiffTiempo(System.currentTimeMillis()-_contexto.getTiempo());
				if(_contexto.getEstadoInterno().equals(EstateRobots.REPOSO)){
					/*
					 * si recibe un mensaje de recoger blister pues pasa al estado: CAMINOPOSICION_2 y coge el tiempo
					 * si recibe un mensaje de recoger pastel pues pasa al estado: CAMINOPOSICION_1 y coge el tiempo
					 * si recibe un mensaje de moverblistercompleto pues pasa al estado: DESPLAZARBLISTERCOMPLETO y coge el tiempo
					 */
				 	_contexto.setTiempo(System.currentTimeMillis());
				 	_contexto.setDiffTiempo(System.currentTimeMillis()-_contexto.getTiempo());
				}else if(_contexto.getEstadoInterno().equals(EstateRobots.CAMINOPOSICION_1)){
					// controlar interferencias, mejor lo hace el maestro
					if( _contexto.getDiffTiempo() > ((_configuracion.getMoverPastel() -_configuracion.getInterferencia()/2)*1000)){
						_contexto.setEstadoInterno(EstateRobots.SOBREPOSICION_1);
						/*
						 * envia el mensaje de interferencia sobre la cinta 1
						 */
					}
					
				}else if(_contexto.getEstadoInterno().equals(EstateRobots.SOBREPOSICION_1)){
					//cogo el pastel
					if( (System.currentTimeMillis()-_contexto.getTiempo()) > (_configuracion.getMoverPastel()*1000)){
						_contexto.setEstadoInterno(EstateRobots.CAMINOPOSICION_3);
						_contexto.setPastel(true);
						/*
						 * Envia el mensaje de pastel recogido
						 */
					}
				}else if(_contexto.getEstadoInterno().equals(EstateRobots.CAMINOPOSICION_2)){
					// controlar interferencias, mejor lo hace el maestro
					if(  _contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() -_configuracion.getInterferencia()/2)*1000)){
						_contexto.setEstadoInterno(EstateRobots.SOBREPOSICION_1);
						/*
						 * envia el mensaje de interferencia sobre la cinta 1
						 */
					}
				}else if(_contexto.getEstadoInterno().equals(EstateRobots.SOBREPOSICION_2)){
					//cogo el blister
					/*
					 * envia el mensaje de blister recogido
					 */
					_contexto.setEstadoInterno(EstateRobots.CAMINOPOSICION_3);
					_contexto.setPastel(false);
				}else if(_contexto.getEstadoInterno().equals(EstateRobots.CAMINOPOSICION_3)){
					// controlar interferencias, mejor lo hace el maestro
					if(_contexto.isPastel()){
						if( _contexto.getDiffTiempo() > ((_configuracion.getMoverPastel() +_configuracion.getInterferencia()/2)*1000)){
							/*
							 * envia el mensaje de FIN interferencia sobre la cinta 1
							 */
						}
						if( _contexto.getDiffTiempo() > ((_configuracion.getMoverPastel()*2 - _configuracion.getInterferencia()/2)*1000)){
							_contexto.setEstadoInterno(EstateRobots.SOBREPOSICION_3);
						}
					}else{
						if( _contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() +_configuracion.getInterferencia()/2)*1000)){
							/*
							 * envia el mensaje de FIN interferencia sobre la cinta 2
							 */
							
						}
						if( _contexto.getDiffTiempo() > ((_configuracion.getMoverBlister()*2 - _configuracion.getInterferencia()/2)*1000)){
							_contexto.setEstadoInterno(EstateRobots.SOBREPOSICION_3);
						}
					}
				}else if(_contexto.getEstadoInterno().equals(EstateRobots.SOBREPOSICION_3)){
					//dejo el pastel o blister
					if(_contexto.isPastel()){
						if( _contexto.getDiffTiempo() > (_configuracion.getMoverPastel()*2)){
							/*
							 * envia el mensaje de pastel colocado
							 */
							_contexto.setEstadoInterno(EstateRobots.REPOSO);
						}
					}else{
						if( _contexto.getDiffTiempo() > (_configuracion.getMoverBlister()*2)){
							/*
							 * envia el mensaje de pastel colocado
							 */
							_contexto.setEstadoInterno(EstateRobots.REPOSO);
						}
					}
					
				}else if(_contexto.getEstadoInterno().equals(EstateRobots.DESPLAZARBLISTERCOMPLETO)){
					if( _contexto.getDiffTiempo() > _configuracion.getMoverBlister()){
						/*
						 * envia el mensaje de blister completo colocado en la cinta 3
						 */
						_contexto.setEstadoInterno(EstateRobots.REPOSO);
					}
				}
			}
			
		}

	}

}
