package com.umbrella.autoslave.logic;


import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.umbrella.autoslave.message.ActualizarConfiguracion;
import com.umbrella.autoslave.message.ActualizarContexto;
import com.umbrella.autoslave.message.ActualizarContextoRobot;
import com.umbrella.autoslave.message.Arrancar;
import com.umbrella.autoslave.message.AvisarUnFallo;
import com.umbrella.autoslave.message.BlisterCompleto;
import com.umbrella.autoslave.message.BlisterListo;
import com.umbrella.autoslave.message.FinCintaLibre;
import com.umbrella.autoslave.message.FinInterferencia;
import com.umbrella.autoslave.message.Interferencia;
import com.umbrella.autoslave.message.Parada;
import com.umbrella.autoslave.message.ParadaEmergencia;
import com.umbrella.autoslave.message.ParadaFallo;
import com.umbrella.autoslave.message.PastelListo;
import com.umbrella.autoslave.message.ProductoColocado;
import com.umbrella.autoslave.message.ProductoRecogido;
import com.umbrella.autoslave.message.RellanarMaquina;
import com.umbrella.autoslave.message.Reset;
import com.umbrella.autoslave.utils2.EstateRobots;
import com.umbrella.autoslave.utils2.NombreMaquinas;
import com.umbrella.autoslave.utils2.Ontologia;
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
		
		ActualizarContextoRobot _actualizarContextoRobot=null;
		Arrancar _arrancar=null;
		FinInterferencia _finInterferencia=null;
		Interferencia _interferencia=null;
		Parada _parada = null;
		ParadaEmergencia _paradaEmergencia=null;
		PastelListo _pastelListo=null;
		BlisterListo _blisterListo=null;
		BlisterCompleto _blisterCompleto=null;
		ProductoRecogido _productoRecogido=null;
		Reset _reset=null;
		ActualizarConfiguracion _actualizarConfiguracion=null;
		ParadaFallo _paradaFallo=null;
		ProductoColocado _productoColocado=null;
			
		_contexto.setEstadoInterno(EstateRobots.REPOSO);
		long cicloAct=_clock.getClock();
		
		while(!_contexto.isFIN()){
			if(cicloAct<_clock.getClock()){
				cicloAct=_clock.getClock();
				
				Object aux=null;

				do{
					try {
						aux=_buzon.receive();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(aux instanceof ActualizarContexto){
						_actualizarContextoRobot=(ActualizarContextoRobot)aux;
						_contexto=_actualizarContextoRobot.getContextoRobot();
					}else if(aux instanceof ActualizarConfiguracion){
						_actualizarConfiguracion=(ActualizarConfiguracion)aux;
						_configuracion=_actualizarConfiguracion.getConfiguracion();
					}else if(aux instanceof Arrancar){
						_arrancar=(Arrancar)aux;
						_contexto=_contexto.reset();
						_contexto.setApagado(false);
					}else if(aux instanceof Parada){
						_parada=(Parada)aux;
						_contexto.setParadaCorrecta(true);
					}else if(aux instanceof ParadaEmergencia){
						_paradaEmergencia=(ParadaEmergencia)aux;
						_contexto.setApagado(true);
					}else if(aux instanceof PastelListo){						
						_pastelListo=(PastelListo)aux;
						_contexto.setPastelListo(true);
					}else if(aux instanceof Reset){
						_reset=(Reset)aux;
						if(_contexto.isApagado() || _contexto.isFallo()){
							_contexto=_contexto.reset();
						}
					}else if(aux instanceof ParadaFallo){
						_paradaFallo=(ParadaFallo)aux;
						_contexto.setFallo(true);
					}else if(aux instanceof BlisterCompleto){
						_blisterCompleto=(BlisterCompleto)aux;
						_contexto.setBlisterCompletoListo(true);
					}

				}while(aux!=null);

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
							if(_contexto.isPastelListo()) _contexto.setEstadoInterno(EstateRobots.DESPLAZARBLISTERCOMPLETO);
							
							_contexto.setTiempo(System.currentTimeMillis());
							_contexto.setDiffTiempo(System.currentTimeMillis()-_contexto.getTiempo());
							
							
						}else if(_contexto.getEstadoInterno().equals(EstateRobots.CAMINOPOSICION_1)){
							// controlar interferencias, mejor lo hace el maestro
							if( _contexto.getDiffTiempo() > ((_configuracion.getMoverPastel() -_configuracion.getInterferencia()/2)*1000)){
								_contexto.setEstadoInterno(EstateRobots.SOBREPOSICION_1);
								/*
								 * envia el mensaje de interferencia sobre la cinta 1
								 */
								_interferencia=new Interferencia();
								_interferencia.setClick(cicloAct);
								_interferencia.setRobot(NombreMaquinas.ROBOT_1.getDescriptor());
								_interferencia.setCinta(NombreMaquinas.CINTA_1.getDescriptor());
								_buzon.send(_interferencia);
							}

							
						}else if(_contexto.getEstadoInterno().equals(EstateRobots.SOBREPOSICION_1)){
							//cogo el pastel
							if( (System.currentTimeMillis()-_contexto.getTiempo()) > (_configuracion.getMoverPastel()*1000)){
								_contexto.setEstadoInterno(EstateRobots.CAMINOPOSICION_3);
								_contexto.setPastel(true);
								/*
								 * Envia el mensaje de pastel recogido
								 */
								_productoRecogido=new ProductoRecogido();
								_productoRecogido.setClick(cicloAct);
								_productoRecogido.setRobot(NombreMaquinas.ROBOT_1.getDescriptor());
								_productoRecogido.setTipo("pastel");
								_buzon.send(_productoRecogido);
								 _contexto.setPastelListo(false);
							}
						}else if(_contexto.getEstadoInterno().equals(EstateRobots.CAMINOPOSICION_2)){
							// controlar interferencias, mejor lo hace el maestro
							if(  _contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() -_configuracion.getInterferencia()/2)*1000)){
								_contexto.setEstadoInterno(EstateRobots.SOBREPOSICION_1);
								/*
								 * envia el mensaje de interferencia sobre la cinta 2
								 */
								_interferencia=new Interferencia();
								_interferencia.setClick(cicloAct);
								_interferencia.setRobot(NombreMaquinas.ROBOT_1.getDescriptor());
								_interferencia.setCinta(NombreMaquinas.CINTA_2.getDescriptor());
								_buzon.send(_interferencia);
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
								_productoRecogido=new ProductoRecogido();
								_productoRecogido.setClick(cicloAct);
								_productoRecogido.setRobot(NombreMaquinas.ROBOT_1.getDescriptor());
								_productoRecogido.setTipo("blister");
								_buzon.send(_productoRecogido);
								 _contexto.setBlisterListo(false);
							}
						}else if(_contexto.getEstadoInterno().equals(EstateRobots.CAMINOPOSICION_3)){
							// controlar interferencias, mejor lo hace el maestro
							if(_contexto.isPastel()){
								if( _contexto.getDiffTiempo() > ((_configuracion.getMoverPastel() +_configuracion.getInterferencia()/2)*1000)){
									/*
									 * envia el mensaje de FIN interferencia sobre la cinta 1
									 */
									_finInterferencia=new FinInterferencia();
									_finInterferencia.setClick(cicloAct);
									_finInterferencia.setRobot(NombreMaquinas.ROBOT_1.getDescriptor());
									_finInterferencia.setCinta(NombreMaquinas.CINTA_1.getDescriptor());
									_buzon.send(_finInterferencia);
								}
								if( _contexto.getDiffTiempo() > (_configuracion.getMoverPastel()*2*1000)){
									_contexto.setEstadoInterno(EstateRobots.SOBREPOSICION_3);
								}
							}else{
								if( _contexto.getDiffTiempo() > ((_configuracion.getMoverBlister() +_configuracion.getInterferencia()/2)*1000)){
									/*
									 * envia el mensaje de FIN interferencia sobre la cinta 2
									 */
									_finInterferencia=new FinInterferencia();
									_finInterferencia.setClick(cicloAct);
									_finInterferencia.setRobot(NombreMaquinas.ROBOT_1.getDescriptor());
									_finInterferencia.setCinta(NombreMaquinas.CINTA_2.getDescriptor());
									_buzon.send(_finInterferencia);

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
									_productoColocado=new ProductoColocado();
									_productoColocado.setClick(cicloAct);
									_productoColocado.setRobot(NombreMaquinas.ROBOT_1.getDescriptor());
									_productoColocado.setProducto("pastel");
									_buzon.send(_productoColocado);
									_contexto.setEstadoInterno(EstateRobots.REPOSO);
								}
							}else{
								if( _contexto.getDiffTiempo() > (_configuracion.getMoverBlister()*2)){
									/*
									 * envia el mensaje de pastel colocado
									 */
									_productoColocado=new ProductoColocado();
									_productoColocado.setClick(cicloAct);
									_productoColocado.setRobot(NombreMaquinas.ROBOT_1.getDescriptor());
									_productoColocado.setProducto("blister");
									_buzon.send(_productoColocado);
									_contexto.setEstadoInterno(EstateRobots.REPOSO);
								}
							}

						}else if(_contexto.getEstadoInterno().equals(EstateRobots.DESPLAZARBLISTERCOMPLETO)){
							if( _contexto.getDiffTiempo() > _configuracion.getMoverBlister()){
								/*
								 * envia el mensaje de blister completo colocado en la cinta 3
								 */
								_productoColocado=new ProductoColocado();
								_productoColocado.setClick(cicloAct);
								_productoColocado.setRobot(NombreMaquinas.ROBOT_1.getDescriptor());
								_productoColocado.setProducto("blisterCompleto");
								_buzon.send(_productoColocado);
								_contexto.setEstadoInterno(EstateRobots.REPOSO);
							}
						}
					}

				}
				
				_blisterListo=null;
				_arrancar=null;
				_finInterferencia=null;
				_interferencia=null;
				_parada = null;
				_paradaEmergencia=null;
				_pastelListo=null;
				_productoRecogido=null;
				_reset=null;
				_actualizarConfiguracion=null;
				_paradaFallo=null;
				_blisterCompleto=null;
				_productoColocado=null;

				// envia el mensaje de contexto
				ActualizarContextoRobot actContexto=new ActualizarContextoRobot();
				actContexto.setClick(cicloAct);
				actContexto.setContextoRobot(_contexto);
				actContexto.setIdentificador(Ontologia.ACTUALIZARCONTEXTOROBOT.getNombre());
				actContexto.setMaquina(1);
				_buzon.send(actContexto);
				
			}
		}
	}

}
