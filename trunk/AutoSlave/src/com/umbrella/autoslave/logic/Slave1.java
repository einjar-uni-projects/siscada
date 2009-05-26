package com.umbrella.autoslave.logic;

import java.rmi.RemoteException;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.Notifiable;
import com.umbrella.autoslave.executor.ActivatedDispenser;
import com.umbrella.autoslave.executor.ConveyorBeltExit;
import com.umbrella.autoslave.executor.MoveConveyorBelt;
import com.umbrella.autoslave.executor.PropertiesFile;
import com.umbrella.autoslave.executor.TimeMachine;
import com.umbrella.autoslave.executor.TurnOff;
import com.umbrella.mail.mailbox.ClientMailBox;
import com.umbrella.mail.mailbox.ServerMailBox;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.utils.properties.PropertiesFileHandler;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.utils.MachineNames;
import com.umbrella.utils.ThreadState;


/*
 * El objetivo de esta clase es llevar el peso de la ejecucion, aqui se crean los hilos q luego se ejecutaran en 
 * paralelo entre ellos
 */
public class Slave1 implements Notifiable {
	
	private  Clock _clock;
	private  MoveConveyorBelt _moverCinta;
	private  ActivatedDispenser _dispensadora;
	private  ConveyorBeltExit _salPastel;
	private  TimeMachine _chocolate;
	private  TimeMachine _caramelo;
	
	private  Context contexto=Context.getInstance("pastel");
	private  Configuration configuracion=Configuration.getInstance();
	private static ClientMailBox _buzon;
	PropertiesFile pfmodel;
	private boolean _joy = true;

//TODO propiom quitar antes de entregar
	private boolean debug=false;
	/**
	 * @param args
	 */
	public Slave1(){
		try	{

			TurnOff estado= TurnOff.getInstance();
			//contexto.setState( estado );

			/*
			 * tenemos dos hilos, uno es el reloj y el otro es la ejecucion del automata que debe quedarse bloqueado entre 
			 * los clicks del reloj, para eso el reloj debe hacer un notify a todos los hilos notifyAll()
			 */
			_clock=Clock.getInstance();
			_clock.start();
			_clock.addNotificable(this);
			/*
			 * se crean los hilos de ejecucion
			 */
			_moverCinta=new MoveConveyorBelt(configuracion.getVelCinta(),
					configuracion.getPosicionAsociada(MachineNames.CINTA_1));
			_dispensadora=(ActivatedDispenser)ActivatedDispenser.getInstance(configuracion.getPosBizc(),
					configuracion.getPosicionAsociada(MachineNames.DISPENSADORA));
			_salPastel=new ConveyorBeltExit(configuracion.getPosFinAut1(),
					configuracion.getPosicionAsociada(MachineNames.FIN_2), "pastel");
			_caramelo=new TimeMachine(configuracion.getValvCaram(), configuracion.getPosCaram(),
					configuracion.getPosicionAsociada(MachineNames.CARAMELO));
			_chocolate=new TimeMachine(configuracion.getValvChoc(), configuracion.getPosChoc(),
					configuracion.getPosicionAsociada(MachineNames.CHOCOLATE));

			contexto.rellenarCaramelo(configuracion.getCapacidadCaramelo(),configuracion.getCapacidadCaramelo());
			contexto.rellenarChocolate(configuracion.getCapacidadChocolate(),configuracion.getCapacidadChocolate());

			_moverCinta.start();
			_salPastel.start();
			_caramelo.start();
			_chocolate.start();
			
			try {
				pfmodel = PropertiesFile.getInstance();
				PropertiesFileHandler.getInstance().LoadValuesOnModel(pfmodel);
			} catch (PropertyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			PropertiesFileHandler.getInstance().writeFile();
			_buzon = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._reciveAU1Name, ServerMailBox._sendAU1Name, true);
			
			for(int i=0;i<16;i++) contexto.setEstadoAnterior(i, false);
		}catch( Exception e ){
 			e.printStackTrace();
 		}
	}
	
	public void execute(){
		while(!contexto.isFIN()){
			pauseJoy();
			guardedJoy();

			MessageInterface mensaje=null;
			do{
				try {
					mensaje=_buzon.receive();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(mensaje!=null){
					System.out.println(mensaje.getIdentifier());
					switch (mensaje.getIdentifier()) {
					case FINCINTALIBRE:							
						contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.FIN_1), false);
						break;
					case ACTUALIZARCONFIGURACION: 						
						configuracion=(Configuration)mensaje.getObject();
						contexto.setNumPasteles(configuracion.getCapacidadPasteles());
						contexto.setRemainderCakes(configuracion.getCapacidadPasteles());
						contexto.rellenarCaramelo(configuracion.getCapacidadCaramelo(),configuracion.getCapacidadCaramelo());
						contexto.rellenarCaramelo(configuracion.getCapacidadChocolate(),configuracion.getCapacidadChocolate());
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
						contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.FIN_1), false);
						break;
					case RELLENARMAQUINA:
						String maquina=mensaje.getParameters().get(0);
						int cantidad=Integer.parseInt(mensaje.getParameters().get(1));
						if(maquina.compareTo(MachineNames.DISPENSADORA.getName())==0)
							_dispensadora.fillDeposit(cantidad);
						if(maquina.compareTo(MachineNames.CARAMELO.getName())==0)
							contexto.rellenarCaramelo(cantidad,configuracion.getCapacidadCaramelo());
						if(maquina.compareTo(MachineNames.CHOCOLATE.getName())==0)
							contexto.rellenarCaramelo(cantidad,configuracion.getCapacidadChocolate());
						break;
					case RESET:
						if(contexto.isApagado() || contexto.isFallo()){
							contexto=Context.reset("pastel");
							contexto.setNumPasteles(configuracion.getCapacidadPasteles());
							contexto.setRemainderCakes(configuracion.getCapacidadPasteles());
							contexto.rellenarCaramelo(configuracion.getCapacidadCaramelo(),configuracion.getCapacidadCaramelo());
							contexto.rellenarCaramelo(configuracion.getCapacidadChocolate(),configuracion.getCapacidadChocolate());
						}
						break;
					case PARADAFALLO:
						contexto.setFallo(true);
						break;
					}
				}
			}while(mensaje!=null);

			if(contexto.isParadaCorrecta()){
				if(contexto.get_listaPasteles().size()==0)
					contexto.setApagado(true);
			}

			if(!contexto.isFallo()){
if(debug) System.out.println("llega 1  - SLAVE 1");
				if(!contexto.isApagado()){
if(debug) System.out.println("llega 2 - SLAVE 1");
					/*
					 * se coordina y ejecutan los hilos de: movercinta, DispensadoraAutomatica, MaquinaCaramelo, MaquinaChocolate, 
					 * 		moverCinta, salidaPastel
					 */
					if(!seEnciendeSensor() && !hayHiloBloqueante() && !contexto.isInterferencia()){
if(debug) System.out.println("moverCinta  - SLAVE 1");
						//_moverCinta.start();
						if(_moverCinta != null)
							_moverCinta.notifyNoSyncJoy2();
						else
							System.out.println("_moverCinta = null - SLAVE 1");							
					}else{
						//TODO q datos tengo
						System.out.println("seEnciendeSensor=" +seEnciendeSensor());
						System.out.println("hayHiloBloqueante()=" +hayHiloBloqueante());
						System.out.println("contexto.isInterferencia()=" +contexto.isInterferencia());
						
						
						
						if(puedoUsar(MachineNames.CHOCOLATE)){
if(debug) System.out.println("ejecuta la maquina de chocolate - CHOCOLATE, cantidad = " + contexto.getCapacidadChocolate());
							if(contexto.getCapacidadChocolate()>0){
								//_chocolate.start();
								if(_chocolate != null)
									_chocolate.notifyNoSyncJoy2();
								contexto.get_listaPasteles().get(contexto.activaSensor(configuracion, _chocolate.getPosition())).set_chocolate();
								contexto.decrementarChocolate();
							}else{
								DefaultMessage mensajeSend= new DefaultMessage();
								mensajeSend.setIdentifier(MSGOntology.AVISARUNFALLO);
								mensajeSend.getParameters().add(MachineNames.CHOCOLATE.getName()); 									
								_buzon.send(mensajeSend);
								contexto.setFallo(true);
							}
						}
						if(puedoUsar(MachineNames.CARAMELO)){
							if(contexto.getCapacidadCaramelo()>0){
								//_caramelo.start();
								if(_caramelo != null)
									_caramelo.notifyNoSyncJoy2();
								contexto.get_listaPasteles().get(contexto.activaSensor(configuracion, _caramelo.getPosition())).set_caramelo();
								contexto.decrementarCaramelo();
							}else{
								DefaultMessage mensajeSend= new DefaultMessage();
								mensajeSend.setIdentifier(MSGOntology.AVISARUNFALLO);
								mensajeSend.getParameters().add(MachineNames.CARAMELO.getName()); 									
								_buzon.send(mensajeSend);
								contexto.setFallo(true);
							}
						}
						if(puedoUsar(MachineNames.FIN_1)){
							//_salPastel.start();
							if(_salPastel != null)
								_salPastel.notifyNoSyncJoy2();
						}
					}
					//no me importa si la cinta se mueve o no, si puede la dispensadora echa un pastel
					// se pone dentro del while del ciclo de reloj porq solo pone un pastel por click
					if(!contexto.isParadaCorrecta()){
if(debug) System.out.println("si no es parada correcta - SLAVE 1");			
						if(_dispensadora != null)
							_dispensadora.notifyNoSyncJoy2();
						//_dispensadora.start();
					}
					if(_dispensadora.getRemainderCakes()==0){
if(debug) System.out.println("si tengo 0 pasteles restantes - SLAVE 1");						
						DefaultMessage mensajeSend= new DefaultMessage();
						mensajeSend.setIdentifier(MSGOntology.AVISARUNFALLO);
						mensajeSend.getParameters().add(MachineNames.DISPENSADORA.getName()); 									
						_buzon.send(mensajeSend);
						contexto.setFallo(true);
					}


					for(int i=0;i<16;i++) contexto.setEstadoAnterior(i, contexto.getDispositivosInternos(i));
					apagarSensores();
					
				//	actualizarContadorAutomata();
					
if(debug) System.out.println("llega 5 - SLAVE 1");
					// envia el mensaje de contexto
					DefaultMessage mensajeSend=new DefaultMessage();
					mensajeSend.setIdentifier(MSGOntology.ACTUALIZARCONTEXTO);
					mensajeSend.setObject(contexto);
					_buzon.send(mensajeSend);
				}// del if (!contexto.isApagado)
			} // fin del if(!contexto.isfallo)
		}// fin del while(!esFin)
		/*
		 * se matan los hilos
		 */
		_moverCinta=null;
		_dispensadora=null;
		_caramelo=null;
		_chocolate=null;
		_salPastel=null;
	}
	
	
	/**
	 * Nos dice si algun hilo esta bloqueando al resto, es decir uno de los hilos esta en ejecucion
	 * true= algun hilo esta bloqueando
	 */
	private synchronized  boolean hayHiloBloqueante(){
		boolean hay=false;
		//if(_dispensadora.getThreadState().equals(ThreadState.EJECUTANDO)) hay=true;
		if(_caramelo.getThreadState().equals(ThreadState.EJECUTANDO)) hay=true;
		else if(_chocolate.getThreadState().equals(ThreadState.EJECUTANDO)) hay=true;
		else if(_salPastel.getThreadState().equals(ThreadState.EJECUTANDO)) hay=true;
		return hay;
	}
	
	/**
	 * @return
	 */
	private synchronized  boolean seEnciendeSensor(){
		boolean salida=false;
		
		if(contexto.activaSensor(configuracion, _caramelo.getPosition())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(MachineNames.SENSOR_CARAMELO))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.SENSOR_CARAMELO), true);
			salida=true;
		}else{
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.SENSOR_CARAMELO), false);
		}
		/*if(contexto.activaSensor(configuracion, _chocolate.getPosition())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(MachineNames.SENSOR_CHOCOLATE))){*/
		if(contexto.activaSensor(configuracion, _chocolate.getPosition())>=0 && 
				!contexto.get_listaPasteles().get(contexto.activaSensor(configuracion, _chocolate.getPosition())).is_chocolate()){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.SENSOR_CHOCOLATE), true);
			salida=true;
		}else{
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.SENSOR_CHOCOLATE), false);
		}
		if(contexto.activaSensor(configuracion, _salPastel.getPosition())>=0){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.FIN_1), true);
			salida=true;
		}else{
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.FIN_1), false);
		}
		// la dispensadora no tiene sensor asociado
		// if(contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		return salida;
	}
	
	/**
	 * @param nombre
	 * @return
	 */
	private synchronized  boolean ejecutandoAlgo(MachineNames nombre){
		boolean salida=false;
		/*
		if(nombre.equals(MachineNames.DISPENSADORA)) 
			if(_dispensadora.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
			*/
		if(nombre.equals(MachineNames.CHOCOLATE))
			if(_chocolate.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		if(nombre.equals(MachineNames.CARAMELO))
			if(_caramelo.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		if(nombre.equals(MachineNames.FIN_1))
			if(_salPastel.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		return salida;
	}
	
	/**
	 * @param tipo
	 * @return
	 */
	private synchronized  boolean puedoUsar(MachineNames tipo){
		boolean salida=false;
		/*
		if(tipo.equals(NombreMaquinas.DISPENSADORA))
			if(!ejecutandoAlgo(NombreMaquinas.DISPENSADORA) && contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		*/
		if(tipo.equals(MachineNames.CHOCOLATE)){
			if(!ejecutandoAlgo(MachineNames.CHOCOLATE) && 
					contexto.activaSensor(configuracion, _chocolate.getPosition())>=0 &&
						!contexto.get_listaPasteles().get(contexto.activaSensor(configuracion, _chocolate.getPosition())).is_chocolate())
							salida=true;
		}
		/*&&
		!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(MachineNames.CHOCOLATE))) salida=true;*/
		if(tipo.equals(MachineNames.CARAMELO)){
			if(!ejecutandoAlgo(MachineNames.CARAMELO) && 
					contexto.activaSensor(configuracion, _caramelo.getPosition())>=0 &&
						!contexto.get_listaPasteles().get(contexto.activaSensor(configuracion, _caramelo.getPosition())).is_caramelo()) 
				salida=true;
		}
		if(tipo.equals(MachineNames.FIN_1)){
			if(!ejecutandoAlgo(MachineNames.FIN_1) && 
					contexto.activaSensor(configuracion, _salPastel.getPosition())>=0 &&
						!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(MachineNames.FIN_1))) 
				salida=true;
		}
		return salida;
	}
	/**
	 * 
	 */
	private synchronized  void apagarSensores(){
		int num=-1;
		num=contexto.activaSensor(configuracion, _caramelo.getPosition());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.SENSOR_CARAMELO), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _chocolate.getPosition());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.SENSOR_CHOCOLATE), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _salPastel.getPosition());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.FIN_1), false);
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
	public void notifyNoSyncJoy(NotificableSignal signal) {
		notifyJoy();
	}

	public synchronized void notifyJoy() {
		_joy = true;
		notifyAll();
	}

	public synchronized void pauseJoy() {
		_joy = false;
	}
	
	public synchronized void guardedJoy2() {
	}
	
	@Override
	public void notifyNoSyncJoy2() {

	}

	public synchronized void notifyJoy2() {
	}

	public synchronized void pauseJoy2() {
	}
}
