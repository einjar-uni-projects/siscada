package com.umbrella.autoslave.logic;


import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuracion;
import com.umbrella.autocommon.ContextoRobot;
import com.umbrella.autoslave.utils2.EstateRobots;
import com.umbrella.autoslave.utils2.NombreMaquinas;
import com.umbrella.mail.mailbox.ClientMailBox;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.OntologiaMSG;

/*
 * este robot tiene el estado reposo, el estado voy por blister y voy por pastel
 */
public class Robot1 {

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
			_buzon=new ClientMailBox(host,puerto,"EntradaRobot1","SalidaRobot1");
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
						_configuracion=(Configuracion)mensaje.getObject();
						break;
					case ARRANCAR:
						_contexto=_contexto.reset();
						_contexto.setApagado(false);
						break;
					case PARADA:
						_contexto.setParadaCorrecta(true);
						break;
					case PARADAEMERGENCIA:
						_contexto.setApagado(true);
						break;
					case PASTELLISTO:
						_contexto.setPastelListo(true);
						break;
					case RESET:
						_contexto=_contexto.reset();
						break;
					case PARADAFALLO:
						_contexto.setFallo(true);
						break;
					case BLISTERCOMPLETO:
						_contexto.setBlisterCompletoListo(true);
						break;
					}
				}while(mensaje!=null);

				if(_contexto.isParadaCorrecta()){
					//se para directamente porque no se controla
					_contexto.setApagado(true);
				}

				if(!_contexto.isFallo()){
					if(!_contexto.isApagado()){


						_contexto.setDiffTiempo(System.currentTimeMillis()-_contexto.getTiempo());
						if(_contexto.getEstadoInterno().equals(EstateRobots.REPOSO)){
							/*
							 * si recibe un mensaje de recoger blister pues pasa al estado: CAMINOPOSICION_2 y coge el tiempo
							 * si recibe un mensaje de recoger pastel pues pasa al estado: CAMINOPOSICION_1 y coge el tiempo
							 * si recibe un mensaje de moverblistercompleto pues pasa al estado: DESPLAZARBLISTERCOMPLETO y coge el tiempo
							 */
							if(_contexto.isBlisterListo()) _contexto.setEstadoInterno(EstateRobots.CAMINOPOSICION_2);
							if(_contexto.isPastelListo()) _contexto.setEstadoInterno(EstateRobots.CAMINOPOSICION_1);
							if(_contexto.isBlisterCompletoListo()) _contexto.setEstadoInterno(EstateRobots.DESPLAZARBLISTERCOMPLETO);

							_contexto.setTiempo(System.currentTimeMillis());
							_contexto.setDiffTiempo(System.currentTimeMillis()-_contexto.getTiempo());


						}else if(_contexto.getEstadoInterno().equals(EstateRobots.CAMINOPOSICION_1)){
							// controlar interferencias, mejor lo hace el maestro
							if( _contexto.getDiffTiempo() > ((_configuracion.getMoverPastel() -_configuracion.getInterferencia()/2)*1000)){
								_contexto.setEstadoInterno(EstateRobots.SOBREPOSICION_1);
								/*
								 * envia el mensaje de interferencia sobre la cinta 1
								 */
								MessageInterface send=new DefaultMessage();
								send.setIdentificador(OntologiaMSG.INTERFERENCIA);
								send.getParametros().add(NombreMaquinas.ROBOT_1.getDescripcion());
								send.getParametros().add(NombreMaquinas.CINTA_1.getDescripcion());
								_buzon.send(send);
							}


						}else if(_contexto.getEstadoInterno().equals(EstateRobots.SOBREPOSICION_1)){
							//cogo el pastel
							if( (System.currentTimeMillis()-_contexto.getTiempo()) > (_configuracion.getMoverPastel()*1000)){
								_contexto.setEstadoInterno(EstateRobots.CAMINOPOSICION_3);
								_contexto.setPastel(true);
								/*
								 * Envia el mensaje de pastel recogido
								 */
								MessageInterface send=new DefaultMessage();
								send.setIdentificador(OntologiaMSG.PRODUCTORECOGIDO);
								send.getParametros().add(NombreMaquinas.ROBOT_1.getDescripcion());
								send.getParametros().add("pastel");
								_buzon.send(send);
								_contexto.setPastelListo(false);
							}
						}else if(_contexto.getEstadoInterno().equals(EstateRobots.CAMINOPOSICION_2)){
							// controlar interferencias, mejor lo hace el maestro
							if(  _contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() -_configuracion.getInterferencia()/2)*1000)){
								_contexto.setEstadoInterno(EstateRobots.SOBREPOSICION_1);
								/*
								 * envia el mensaje de interferencia sobre la cinta 2
								 */
								MessageInterface send=new DefaultMessage();
								send.setIdentificador(OntologiaMSG.INTERFERENCIA);
								send.getParametros().add(NombreMaquinas.ROBOT_1.getDescripcion());
								send.getParametros().add(NombreMaquinas.CINTA_2.getDescripcion());
								_buzon.send(send);
							}
						}else if(_contexto.getEstadoInterno().equals(EstateRobots.SOBREPOSICION_2)){
							//cogo el blister
							/*
							 * envia el mensaje de blister recogido
							 */
							//cogo el pastel
							if( (System.currentTimeMillis()-_contexto.getTiempo()) > (_configuracion.getMoverBlister()*1000)){
								_contexto.setEstadoInterno(EstateRobots.CAMINOPOSICION_3);
								_contexto.setPastel(false);
								/*
								 * Envia el mensaje de blister recogido
								 */
								MessageInterface send=new DefaultMessage();
								send.setIdentificador(OntologiaMSG.PRODUCTORECOGIDO);
								send.getParametros().add(NombreMaquinas.ROBOT_1.getDescripcion());
								send.getParametros().add("blister");
								_buzon.send(send);
								_contexto.setPastelListo(false);
							}
						}else if(_contexto.getEstadoInterno().equals(EstateRobots.CAMINOPOSICION_3)){
							// controlar interferencias, mejor lo hace el maestro
							if(_contexto.isPastel()){
								if( _contexto.getDiffTiempo() > ((_configuracion.getMoverPastel() +_configuracion.getInterferencia()/2)*1000)){
									/*
									 * envia el mensaje de FIN interferencia sobre la cinta 1
									 */
									MessageInterface send=new DefaultMessage();
									send.setIdentificador(OntologiaMSG.FININTERFERENCIA);
									send.getParametros().add(NombreMaquinas.ROBOT_1.getDescripcion());
									send.getParametros().add(NombreMaquinas.CINTA_1.getDescripcion());
									_buzon.send(send);
								}
								if( _contexto.getDiffTiempo() > (_configuracion.getMoverPastel()*2*1000)){
									_contexto.setEstadoInterno(EstateRobots.SOBREPOSICION_3);
								}
							}else{
								if( _contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() +_configuracion.getInterferencia()/2)*1000)){
									/*
									 * envia el mensaje de FIN interferencia sobre la cinta 2
									 */
									MessageInterface send=new DefaultMessage();
									send.setIdentificador(OntologiaMSG.FININTERFERENCIA);
									send.getParametros().add(NombreMaquinas.ROBOT_1.getDescripcion());
									send.getParametros().add(NombreMaquinas.CINTA_2.getDescripcion());
									_buzon.send(send);
								}
								if( _contexto.getDiffTiempo() > (_configuracion.getMoverBlister()*2)){
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
									MessageInterface send=new DefaultMessage();
									send.setIdentificador(OntologiaMSG.PRODUCTOCOLOCADO);
									send.getParametros().add(NombreMaquinas.ROBOT_1.getDescripcion());
									send.getParametros().add("pastel");
									_buzon.send(send);
									_contexto.setEstadoInterno(EstateRobots.REPOSO);
								}
							}else{
								if( _contexto.getDiffTiempo() > (_configuracion.getMoverBlister()*2)){
									/*
									 * envia el mensaje de pastel colocado
									 */
									MessageInterface send=new DefaultMessage();
									send.setIdentificador(OntologiaMSG.PRODUCTOCOLOCADO);
									send.getParametros().add(NombreMaquinas.ROBOT_1.getDescripcion());
									send.getParametros().add("blister");
									_buzon.send(send);
									_contexto.setEstadoInterno(EstateRobots.REPOSO);
								}
							}

						}else if(_contexto.getEstadoInterno().equals(EstateRobots.DESPLAZARBLISTERCOMPLETO)){
							if( _contexto.getDiffTiempo() > _configuracion.getMoverBlister()){
								/*
								 * envia el mensaje de blister completo colocado en la cinta 3
								 */
								MessageInterface send=new DefaultMessage();
								send.setIdentificador(OntologiaMSG.PRODUCTOCOLOCADO);
								send.getParametros().add(NombreMaquinas.ROBOT_1.getDescripcion());
								send.getParametros().add("blisterCompleto");
								_buzon.send(send);
								_contexto.setEstadoInterno(EstateRobots.REPOSO);
							}
						}
					
					}//si esta apagadp
				}// si hay fallo


				// envia el mensaje de contexto
				DefaultMessage mensajeSend=new DefaultMessage();
				mensajeSend.setIdentificador(OntologiaMSG.ACTUALIZARCONTEXTOROBOT);
				mensajeSend.setObject(_contexto);
				_buzon.send(mensajeSend);
 
			}//si ciclo de reloj
		}//while
	}//main
}//class