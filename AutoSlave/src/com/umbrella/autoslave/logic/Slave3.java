package com.umbrella.autoslave.logic;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.Notificable;
import com.umbrella.autoslave.executor.ConveyorBeltExit;
import com.umbrella.autoslave.executor.InstantaneousMachine;
import com.umbrella.autoslave.executor.MoveConveyorBelt;
import com.umbrella.autoslave.executor.PropertiesFile;
import com.umbrella.autoslave.executor.TimeMachine;
import com.umbrella.autoslave.executor.TurnOff;
import com.umbrella.mail.mailbox.ClientMailBox;
import com.umbrella.mail.mailbox.ServerMailBox;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.mail.utils.properties.PropertiesFileHandler;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.Blister;
import com.umbrella.utils.NombreMaquinas;
import com.umbrella.utils.ThreadState;


/*
 * El objetivo de esta clase es llevar el peso de la ejecucion, aqui se crean los hilos q luego se ejecutaran en 
 * paralelo entre ellos
 */
public class Slave3 implements Notificable{

	private  Clock _clock;
	private  MoveConveyorBelt _moverCinta;
	private  ConveyorBeltExit _salBlister;
	private  InstantaneousMachine _calidad;
	private  TimeMachine _selladora;
	private  ClientMailBox _buzon;

	private  Context contexto=Context.getInstance("blister");
	private  Configuration configuracion=Configuration.getInstance();

	private  PropertiesFile pfmodel;

	public  double porcentajeFallos=0.03;
	private boolean _joy = true;

	public Slave3(){
		
		for(int i=0;i<contexto.getEstadoAnterior().length;i++) contexto.setEstadoAnterior(i,false);

		try	{

			TurnOff estado= TurnOff.getInstance();
			//contexto.setState( estado );

			/*
			 * tenemos dos hilos, uno es el reloj y el otro es la ejecucion del automata que debe quedarse bloqueado entre 
			 * los clicks del reloj, para eso el reloj debe hacer un notify a todos los hilos notifyAll()
			 */
			_clock=new Clock();
			_clock.start();
			_clock.setNotificable(this);

			/*
			 * se crean los hilos de ejecucion
			 */
			_moverCinta=new MoveConveyorBelt(configuracion.getVelCintaAut3(),
					configuracion.getPosicionAsociada(NombreMaquinas.CINTA_3));
			_salBlister=new ConveyorBeltExit(configuracion.getPosFinAut3(),
					configuracion.getPosicionAsociada(NombreMaquinas.FIN_3), "blister");
			_calidad=new InstantaneousMachine(configuracion.getPosCalidad(),
					configuracion.getPosicionAsociada(NombreMaquinas.CONTROL_CALIDAD));
			_selladora=new TimeMachine(configuracion.getSelladora(), configuracion.getPosSelladora(),
					configuracion.getPosicionAsociada(NombreMaquinas.SELLADO));

			try {
 				pfmodel = PropertiesFile.getInstance();
 				PropertiesFileHandler.getInstance().LoadValuesOnModel(pfmodel);
 			} catch (PropertyException e1) {
 				// TODO Auto-generated catch block
 				e1.printStackTrace();
 			}
 			PropertiesFileHandler.getInstance().writeFile();
 			_buzon = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._reciveR1Name, ServerMailBox._sendR1Name);
		}catch( Exception e ){
			e.printStackTrace();
		}

	}
	public void execute() {

		
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
					MessageInterface mensaje=null;


					do{
						try {
							mensaje =_buzon.receive();
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(mensaje!=null){
							switch (mensaje.getIdentificador()) {
							case FINCINTALIBRE:							
								contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_3), false);
								break;
							case ACTUALIZARCONFIGURACION: 						
								configuracion=(Configuration)mensaje.getObject();
								contexto.setApagado(false);
								break;
							case START:
								contexto=Context.reset("pastel");
								contexto.setApagado(false);
								break;
							case FININTERFERENCIA:
								contexto.setInterferencia(false);
								break;
							case INTERFERENCIA: 				
								contexto.setInterferencia(true);
								break;
							case PARADA:
								contexto.setParadaCorrecta(true);
								break;
							case PARADAEMERGENCIA:
								contexto.setApagado(true);
								break;
							case PRODUCTORECOGIDO:
								contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_3), false);
								break;
							case RESET:
								if(contexto.isApagado() || contexto.isFallo()){
									contexto=Context.reset("pastel");
									contexto.rellenarCaramelo(configuracion.getCapacidadCaramelo(),configuracion.getCapacidadCaramelo());
									contexto.rellenarCaramelo(configuracion.getCapacidadChocolate(),configuracion.getCapacidadChocolate());
								}
								break;
							case PARADAFALLO:
								contexto.setFallo(true);
								break;
							case BLISTERCOMPLETO:
								contexto.setBlisterListoInicioCinta3(true);
								break;
							}
						}
						
						
					}while(mensaje!=null);

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
								//((TurnOff) estado).transitar();
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


					// envia el mensaje de contexto
 					DefaultMessage mensajeSend=new DefaultMessage();
 					mensajeSend.setIdentificador(OntologiaMSG.ACTUALIZARCONTEXTO);
 					mensajeSend.setObject(contexto);
 		 			_buzon.send(mensajeSend);
				}

			}
			/*
			 * se matan los hilos
			 */
			_moverCinta=null;
			_selladora=null;
			_salBlister=null;
			_calidad=null;

		
	}
	
	/*
	 * Nos dice si algun hilo esta bloqueando al resto, es decir uno de los hilos esta en ejecucion
	 * true= algun hilo esta bloqueando
	 */
	private synchronized boolean hayHiloBloqueante(){
		boolean hay=false;
		if(_calidad.getThreadState().equals(ThreadState.EJECUTANDO)) hay=true;
		else if(_selladora.getThreadState().equals(ThreadState.EJECUTANDO)) hay=true;
		else if(_salBlister.getThreadState().equals(ThreadState.EJECUTANDO)) hay=true;
		return hay;
	}
	
	private synchronized boolean seEnciendeSensor(){
		boolean salida=false;
		/*//no se tienen en cuenta los sensores asociados a maquinas instantaneas
		if(contexto.activaSensor(_calidad.get_posicion())>=0 && 
				!estadoAnterior[configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CALIDAD)]){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CALIDAD), true);
			salida=true;
		}
		*/
		if(contexto.activaSensor(configuracion, _selladora.getPosition())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_SELLADORA))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_SELLADORA), true);
			salida=true;
		}
		if(contexto.activaSensor(configuracion, _salBlister.getPosition())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.FIN_3))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_3), true);
			salida=true;
		}
		// la dispensadora no tiene sensor asociado
		// if(contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		return salida;
	}
	
	private synchronized boolean ejecutandoAlgo(NombreMaquinas nombre){
		boolean salida=false;
		if(nombre.equals(NombreMaquinas.CONTROL_CALIDAD))
			if(_calidad.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.SELLADO))
			if(_selladora.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.FIN_3))
			if(_salBlister.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		return salida;
	}
	
	private synchronized boolean puedoUsar(NombreMaquinas tipo){
		boolean salida=false;
		if(tipo.equals(NombreMaquinas.CONTROL_CALIDAD))
			if(!ejecutandoAlgo(NombreMaquinas.CONTROL_CALIDAD) && 
					contexto.activaSensor(configuracion, _calidad.getPosition())>=0 &&
						!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.CONTROL_CALIDAD)))
				salida=true;
		
		if(tipo.equals(NombreMaquinas.SELLADO))
			if(!ejecutandoAlgo(NombreMaquinas.SELLADO) && 
					contexto.activaSensor(configuracion, _selladora.getPosition())>=0 &&
						!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.SELLADO)))
				salida=true;
		if(tipo.equals(NombreMaquinas.FIN_3))
			if(!ejecutandoAlgo(NombreMaquinas.FIN_3) && 
					contexto.activaSensor(configuracion, _salBlister.getPosition())>=0 &&
						!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.FIN_3))) 
				salida=true;
		return salida;
	}
	
	
	private synchronized void apagarSensores(){
		int num=-1;
		num=contexto.activaSensor(configuracion, _calidad.getPosition());
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CALIDAD), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _selladora.getPosition());
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_SELLADORA), false);
		num=-1;
			num=contexto.activaSensor(configuracion, _salBlister.getPosition());
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_3), false);
	}
	private synchronized boolean tengoEspacio(){
		boolean sal=false;
		double min=configuracion.getSizeCintaAut3()+50;
		for(int i=0;i<contexto.get_listaBlister().size();i++){
			if(contexto.get_listaBlister().get(i).get_posicion()<min) min=contexto.get_listaBlister().get(i).get_posicion();
		}
		if(min>configuracion.getSizeBlister()) sal=true;
		return sal;
	}
	
	public synchronized void guardedJoy() {
		// This guard only loops once for each special event, which may not
		// be the event we're waiting for.
		while (!_joy) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}
	
	@Override
	public void notifyNoSyncJoy() {
		notifyJoy();
	}

	public synchronized void notifyJoy() {
		_joy = true;
		notifyAll();
	}

	public synchronized void pauseJoy() {
		_joy = false;
	}
}