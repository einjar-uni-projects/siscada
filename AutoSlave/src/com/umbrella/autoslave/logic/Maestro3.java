package com.umbrella.autoslave.logic;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import com.umbrella.autoslave.executor.Apagado;
import com.umbrella.autoslave.executor.DispensadoraActivada;
import com.umbrella.autoslave.executor.Estado;
import com.umbrella.autoslave.executor.MaquinaInstantanea;
import com.umbrella.autoslave.executor.MaquinaTiempos;
import com.umbrella.autoslave.executor.MoverCinta;
import com.umbrella.autoslave.executor.SalidaCinta;
import com.umbrella.autoslave.message.ActualizarConfiguracion;
import com.umbrella.autoslave.message.ActualizarContexto;
import com.umbrella.autoslave.message.Arrancar;
import com.umbrella.autoslave.message.AvisarUnFallo;
import com.umbrella.autoslave.message.FinCintaLibre;
import com.umbrella.autoslave.message.FinInterferencia;
import com.umbrella.autoslave.message.Interferencia;
import com.umbrella.autoslave.message.Parada;
import com.umbrella.autoslave.message.ParadaEmergencia;
import com.umbrella.autoslave.message.ParadaFallo;
import com.umbrella.autoslave.message.PastelListo;
import com.umbrella.autoslave.message.ProductoRecogido;
import com.umbrella.autoslave.message.RellanarMaquina;
import com.umbrella.autoslave.message.Reset;
import com.umbrella.autoslave.utils2.Blister;
import com.umbrella.autoslave.utils2.EstateThreads;
import com.umbrella.autoslave.utils2.NombreMaquinas;
import com.umbrella.mail.message.Ontologia;
import com.umbrella.mail.modulocomunicacion.MailBox;


/*
 * El objetivo de esta clase es llevar el peso de la ejecucion, aqui se crean los hilos q luego se ejecutaran en 
 * paralelo entre ellos
 */
public class Maestro3 {

	private static Clock _clock;
	private static MoverCinta _moverCinta;
	private static SalidaCinta _salBlister;
	private static MaquinaInstantanea _calidad;
	private static MaquinaTiempos _selladora;
	private static MailBox _buzon;

	private static Contexto contexto=Contexto.getInstance("blister");
	private static Configuracion configuracion=Configuracion.getInstance();

	private static String host = "localhost";
	private static int puerto = 9003;

	public static double porcentajeFallos=0.03;

	public static void main(String[] args) {

		for(int i=0;i<contexto.getEstadoAnterior().length;i++) contexto.setEstadoAnterior(i,false);

		try	{

			Estado estado= Apagado.getInstance();
			contexto.setState( estado );

			/*
			 * tenemos dos hilos, uno es el reloj y el otro es la ejecucion del automata que debe quedarse bloqueado entre 
			 * los clicks del reloj, para eso el reloj debe hacer un notify a todos los hilos notifyAll()
			 */
			_clock=new Clock();
			_clock.start();

			/*
			 * se crean los hilos de ejecucion
			 */
			_moverCinta=new MoverCinta(configuracion.getVelCintaAut3(),
					configuracion.getPosicionAsociada(NombreMaquinas.CINTA_3));
			_salBlister=new SalidaCinta(configuracion.getPosFinAut3(),
					configuracion.getPosicionAsociada(NombreMaquinas.FIN_3), "blister");
			_calidad=new MaquinaInstantanea(configuracion.getPosCalidad(),
					configuracion.getPosicionAsociada(NombreMaquinas.CONTROL_CALIDAD));
			_selladora=new MaquinaTiempos(configuracion.getSelladora(), configuracion.getPosSelladora(),
					configuracion.getPosicionAsociada(NombreMaquinas.SELLADO));

			FinCintaLibre _finCintaLibre=null;
			ActualizarContexto _actualizarContexto=null;
			Arrancar _arrancar=null;
			FinInterferencia _finInterferencia=null;
			Interferencia _interferencia=null;
			Parada _parada = null;
			ParadaEmergencia _paradaEmergencia=null;
			PastelListo _pastelListo=null;
			ProductoRecogido _productoRecogido=null;
			RellanarMaquina _rellanarMaquina=null;
			Reset _reset=null;
			ActualizarConfiguracion _actualizarConfiguracion=null;
			ParadaFallo _paradaFallo=null;
			AvisarUnFallo _avisarUnFallo=null; 

			try {
				_buzon=new MailBox(host,puerto,"EntradaMaestro3","SalidaMaestro3");
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

			long cicloAct=_clock.getClock();
			boolean primeraVez=true;

			Blister blisterAuxiliar=new Blister();

			while(!contexto.isFIN()){
				if(cicloAct<_clock.getClock()){
					cicloAct=_clock.getClock();
					/*
					 * en cada ciclo de reloj, si aun estoy en el ciclo de reloj me quedo aqui
					 */

					/*
					 * se intenta leer si llega algun mensaje que nos saque del estado apagado
					 */
					Object aux=null;
					do{
						aux=_buzon.receive();
						if(aux instanceof FinCintaLibre){
							_finCintaLibre = (FinCintaLibre)aux;
							contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_3), false);
						}else if(aux instanceof ActualizarContexto){
							_actualizarContexto=(ActualizarContexto)aux;
							contexto=_actualizarContexto.getContexto();
						}else if(aux instanceof ActualizarConfiguracion){
							_actualizarConfiguracion=(ActualizarConfiguracion)aux;
							configuracion=_actualizarConfiguracion.getConfiguracion();
						}else if(aux instanceof Arrancar){
							_arrancar=(Arrancar)aux;
							contexto=Contexto.reset("blister");
							contexto.setApagado(false);
						}else if(aux instanceof FinInterferencia){
							_finInterferencia=(FinInterferencia)aux;
							contexto.setInterferencia(false);
						}else if(aux instanceof Interferencia){
							_interferencia=(Interferencia)aux;
							contexto.setInterferencia(true);
						}else if(aux instanceof Parada){
							_parada=(Parada)aux;
							contexto.setParadaCorrecta(true);
						}else if(aux instanceof ParadaEmergencia){
							_paradaEmergencia=(ParadaEmergencia)aux;
							contexto.setApagado(true);
						}else if(aux instanceof PastelListo){
							//este mensaje lo envio yo, no me llega a mi
							_pastelListo=(PastelListo)aux;
						}else if(aux instanceof ProductoRecogido){
							_productoRecogido=(ProductoRecogido)aux;
							// es lo mismo q FinCintaLibre
							contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1), false);
						}else if(aux instanceof Reset){
							_reset=(Reset)aux;
							if(contexto.isApagado() || contexto.isFallo()){
								contexto=Contexto.reset("blister");
							}
						}else if(aux instanceof ParadaFallo){
							_paradaFallo=(ParadaFallo)aux;
							contexto.setFallo(true);
						}

					}while(aux!=null);

					if(contexto.isParadaCorrecta()){
						if(contexto.get_listaBlister().size()==0) contexto.setApagado(true);
					}
					
					if(!contexto.isFallo()){
						if(!contexto.isApagado()){
							/*
							 * se arranca el automata cambiando el estado, 
							 * lo unico q hace es cargar los valores iniciales 
							 */
							if(primeraVez){
								((Apagado) estado).transitar();
								primeraVez=false;
							}else{
								/*
								 * si recibo el mensaje de blister listo y tengo espacio suficiente
								 */
								if(contexto.isBlisterListoInicioCinta3() && tengoEspacio()){
									contexto.get_listaBlister().add(blisterAuxiliar.enCinta3());
									//envio el mensaje de blister colocado, mesa libre
								}
								if(!seEnciendeSensor() && !hayHiloBloqueante() && !contexto.isInterferencia()){
									_moverCinta.run();
								}else{
									seEnciendeSensor();
									if(puedoUsar(NombreMaquinas.CONTROL_CALIDAD) ){
										_calidad.run();
										if(Math.random()<configuracion.getPorcentajeFallos()){
											Vector<Integer> vectorAux=new Vector<Integer>();
											vectorAux.add(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CALIDAD_SENSOR_1));
											vectorAux.add(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CALIDAD_SENSOR_2));
											vectorAux.add(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CALIDAD_SENSOR_3));
											vectorAux.add(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CALIDAD_SENSOR_4));
											int aux2=(int)(Math.random()*4);
											int posAux=vectorAux.get(aux2);
											vectorAux.remove(aux2);
											contexto.setDispositivosInternos(posAux, false);
											if(Math.random()<0.5){
												aux2=(int)(Math.random()*3);
												posAux=vectorAux.get(aux2);
												vectorAux.remove(aux2);
												contexto.setDispositivosInternos(posAux, false);
												if(Math.random()<0.25){
													aux2=(int)(Math.random()*2);
													posAux=vectorAux.get(aux2);
													vectorAux.remove(aux2);
													contexto.setDispositivosInternos(posAux, false);
													if(Math.random()<0.125){
														aux2=(int)(Math.random()*3);
														posAux=vectorAux.get(aux2);
														vectorAux.remove(aux2);
														contexto.setDispositivosInternos(posAux, false);
													}
												}
											}
											for(int i=0;i<vectorAux.size();i++)contexto.setDispositivosInternos(vectorAux.get(i), true);
										}
									}
									if(puedoUsar(NombreMaquinas.SELLADO)){
										_selladora.run();
									}
								}

							}
							/*
							 * Aqui hay q repasar todos los sensores
							 */
							/* esto del estado anterior sirve para saber como estaba el sensor en el estado anterior*/
							for(int i=0;i<contexto.getEstadoAnterior().length;i++) contexto.setEstadoAnterior(i,contexto.getDispositivosInternos(i));
						}else{
							primeraVez=true;
						}

					} // fin del if(!contexto.isfallo)

					_finCintaLibre=null;
					_actualizarContexto=null;
					_arrancar=null;
					_finInterferencia=null;
					_interferencia=null;
					_parada = null;
					_paradaEmergencia=null;
					_pastelListo=null;
					_productoRecogido=null;
					_rellanarMaquina=null;
					_reset=null;
					_actualizarConfiguracion=null;
					_paradaFallo=null;
					_avisarUnFallo=null;


					// envia el mensaje de contexto
					ActualizarContexto actContexto=new ActualizarContexto();
					actContexto.setClick(cicloAct);
					actContexto.setContexto(contexto);
					actContexto.setIdentificador(Ontologia.ACTUALIZARCONTEXTO.getNombre());
					actContexto.setMaquina(1);
					_buzon.send(actContexto);
				}

			}
			/*
			 * se matan los hilos
			 */
			_moverCinta=null;
			_selladora=null;
			_salBlister=null;
			_calidad=null;

		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/*
	 * Nos dice si algun hilo esta bloqueando al resto, es decir uno de los hilos esta en ejecucion
	 * true= algun hilo esta bloqueando
	 */
	private synchronized static boolean hayHiloBloqueante(){
		boolean hay=false;
		if(_calidad.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_selladora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_salBlister.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		return hay;
	}
	
	private synchronized static boolean seEnciendeSensor(){
		boolean salida=false;
		/*//no se tienen en cuenta los sensores asociados a maquinas instantaneas
		if(contexto.activaSensor(_calidad.get_posicion())>=0 && 
				!estadoAnterior[configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CALIDAD)]){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CALIDAD), true);
			salida=true;
		}
		*/
		if(contexto.activaSensor(configuracion, _selladora.get_posicion())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_SELLADORA))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_SELLADORA), true);
			salida=true;
		}
		if(contexto.activaSensor(configuracion, _salBlister.get_posicion())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.FIN_3))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_3), true);
			salida=true;
		}
		// la dispensadora no tiene sensor asociado
		// if(contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		return salida;
	}
	
	private synchronized static boolean ejecutandoAlgo(NombreMaquinas nombre){
		boolean salida=false;
		if(nombre.equals(NombreMaquinas.CONTROL_CALIDAD))
			if(_calidad.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.SELLADO))
			if(_selladora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.FIN_3))
			if(_salBlister.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		return salida;
	}
	
	private synchronized static boolean puedoUsar(NombreMaquinas tipo){
		boolean salida=false;
		if(tipo.equals(NombreMaquinas.CONTROL_CALIDAD))
			if(!ejecutandoAlgo(NombreMaquinas.CONTROL_CALIDAD) && 
					contexto.activaSensor(configuracion, _calidad.get_posicion())>=0 &&
						!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.CONTROL_CALIDAD)))
				salida=true;
		
		if(tipo.equals(NombreMaquinas.SELLADO))
			if(!ejecutandoAlgo(NombreMaquinas.SELLADO) && 
					contexto.activaSensor(configuracion, _selladora.get_posicion())>=0 &&
						!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.SELLADO)))
				salida=true;
		if(tipo.equals(NombreMaquinas.FIN_3))
			if(!ejecutandoAlgo(NombreMaquinas.FIN_3) && 
					contexto.activaSensor(configuracion, _salBlister.get_posicion())>=0 &&
						!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.FIN_3))) 
				salida=true;
		return salida;
	}
	
	
	private synchronized static void apagarSensores(){
		int num=-1;
		num=contexto.activaSensor(configuracion, _calidad.get_posicion());
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CALIDAD), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _selladora.get_posicion());
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_SELLADORA), false);
		num=-1;
			num=contexto.activaSensor(configuracion, _salBlister.get_posicion());
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_3), false);
	}
	private synchronized static boolean tengoEspacio(){
		boolean sal=false;
		double min=configuracion.getSizeCintaAut3()+50;
		for(int i=0;i<contexto.get_listaBlister().size();i++){
			if(contexto.get_listaBlister().get(i).get_posicion()<min) min=contexto.get_listaBlister().get(i).get_posicion();
		}
		if(min>configuracion.getSizeBlister()) sal=true;
		return sal;
	}
}
