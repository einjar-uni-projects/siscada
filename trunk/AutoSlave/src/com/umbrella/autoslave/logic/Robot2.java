package com.umbrella.autoslave.logic;


import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.ContextoRobot;
import com.umbrella.mail.mailbox.ClientMailBox;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.utils.EstateRobots;
import com.umbrella.utils.NombreMaquinas;


/*
 * este robot tiene el estado reposo, el estado voy por blister y voy por pastel
 */
public class Robot2 {

	private static Configuration _configuracion= Configuration.getInstance();
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
		
		_buzon=new ClientMailBox(host,puerto,"EntradaRobot2","SalidaRobot2");
			
		_contexto.setEstadoInterno(EstateRobots.REPOSO);
		long cicloAct=_clock.getClock();
		
		while(!_contexto.isFIN()){
			if(cicloAct<_clock.getClock()){
				cicloAct=_clock.getClock();
				
				MessageInterface mensaje=new DefaultMessage();
				do{
					try {
						mensaje=_buzon.receive();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					switch (mensaje.getIdentificador()) {
					case ACTUALIZARCONTEXTO:							
						_contexto=(ContextoRobot)mensaje.getObject();
						break;
					case ACTUALIZARCONFIGURACION: 						
						_configuracion=(Configuration)mensaje.getObject();
						break;
					case START:
						_contexto=_contexto.reset();
						_contexto.setApagado(false);
						break;
					case PARADA:
						_contexto.setParadaCorrecta(true);
						break;
					case PARADAEMERGENCIA:
						_contexto.setApagado(true);
						break;
					case BLISTERNOVALIDO:
						_contexto.setValido(false);//blister que no pasa el control de calidad
						break;
					case RESET:
						_contexto=_contexto.reset();
						break;
					case PARADAFALLO:
						_contexto.setFallo(true);
						break;
					case BLISTERVALIDO:
						_contexto.setValido(true);//blister que pasa el control de calidad
						break;
					}
				}while(mensaje!=null);

				if(_contexto.isParadaCorrecta()){
					//se para directamente porque no se controla
					_contexto.setApagado(true);
				}
				
				if(!_contexto.isFallo()){
					if(!_contexto.isApagado()){
						//posicion 1 es la cinta 3
						//posicion 2 es la caja de validos
						//posicion 3 es la caja de no validos
						_contexto.setDiffTiempo(System.currentTimeMillis()-_contexto.getTiempo());
						if(_contexto.getEstadoInterno().equals(EstateRobots.REPOSO)){
							_contexto.setEstadoInterno(EstateRobots.CAMINOPOSICION_1);
						 	_contexto.setTiempo(System.currentTimeMillis());
						 	_contexto.setDiffTiempo(System.currentTimeMillis()-_contexto.getTiempo());
						}
						//va hacia la cinta3
						else if(_contexto.getEstadoInterno().equals(EstateRobots.CAMINOPOSICION_1)){//OK
							// controlar interferencias, mejor lo hace el maestro
							if( _contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() -_configuracion.getInterferencia()/2)*1000)){
								_contexto.setEstadoInterno(EstateRobots.SOBREPOSICION_1);
								/*
								 * envia el mensaje de interferencia sobre la cinta 3
								 */
								MessageInterface send=new DefaultMessage();
								send.setIdentificador(OntologiaMSG.INTERFERENCIA);
								send.getParametros().add(NombreMaquinas.ROBOT_2.getDescripcion());
								send.getParametros().add(NombreMaquinas.CINTA_3.getDescripcion());
								_buzon.send(send);
							}
						}
						else if(_contexto.getEstadoInterno().equals(EstateRobots.SOBREPOSICION_1)){//OK
							//cojo el Blister
							if( (System.currentTimeMillis()-_contexto.getTiempo()) > (_configuracion.getMoverBlister()*1000)){
								if(_contexto.isValido()){
									_contexto.setEstadoInterno(EstateRobots.CAMINOPOSICION_2);
								}else{
									_contexto.setEstadoInterno(EstateRobots.CAMINOPOSICION_3);
								}
								/*
								 * enviar mensaje de FIN de cinta 3 libre
								 */
								MessageInterface send=new DefaultMessage();
								send.setIdentificador(OntologiaMSG.PRODUCTORECOGIDO);
								send.getParametros().add(NombreMaquinas.ROBOT_1.getDescripcion());
								send.getParametros().add("blisterCompleto");
								_buzon.send(send);
								_contexto.setPastelListo(false);
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
								MessageInterface send=new DefaultMessage();
								send.setIdentificador(OntologiaMSG.BLISTERALMACENADO);
								send.getParametros().add("true");
								_buzon.send(send);
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
								MessageInterface send=new DefaultMessage();
								send.setIdentificador(OntologiaMSG.BLISTERALMACENADO);
								send.getParametros().add("false");
								_buzon.send(send);
								_contexto.setEstadoInterno(EstateRobots.REPOSO);
							}
						}
					}
				}
			}
		}
	}
}
