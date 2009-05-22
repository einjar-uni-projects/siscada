package com.umbrella.autoslave.logic;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.Notificable;
import com.umbrella.autoslave.executor.ActivatedDispenser;
import com.umbrella.autoslave.executor.ConveyorBeltExit;
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
import com.umbrella.utils.NombreMaquinas;
import com.umbrella.utils.ThreadState;


/*
 * El objetivo de esta clase es llevar el peso de la ejecucion, aqui se crean los hilos q luego se ejecutaran en 
 * paralelo entre ellos
 */
public class Slave1 implements Notificable {
	
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
			_clock=new Clock();
			_clock.start();
			_clock.setNotificable(this);
			/*
			 * se crean los hilos de ejecucion
			 */
			_moverCinta=new MoveConveyorBelt(configuracion.getVelCinta(),
					configuracion.getPosicionAsociada(NombreMaquinas.CINTA_1));
			_dispensadora=(ActivatedDispenser)ActivatedDispenser.getInstance(configuracion.getPosBizc(),
					configuracion.getPosicionAsociada(NombreMaquinas.DISPENSADORA));
			
			_salPastel=new ConveyorBeltExit(configuracion.getPosFinAut1(),
					configuracion.getPosicionAsociada(NombreMaquinas.FIN_2), "pastel");
			_caramelo=new TimeMachine(configuracion.getValvCaram(), configuracion.getPosCaram(),
					configuracion.getPosicionAsociada(NombreMaquinas.CARAMELO));
			_chocolate=new TimeMachine(configuracion.getValvChoc(), configuracion.getPosChoc(),
					configuracion.getPosicionAsociada(NombreMaquinas.CHOCOLATE));

			contexto.rellenarCaramelo(configuracion.getCapacidadCaramelo(),configuracion.getCapacidadCaramelo());
			contexto.rellenarCaramelo(configuracion.getCapacidadChocolate(),configuracion.getCapacidadChocolate());

			try {
				pfmodel = PropertiesFile.getInstance();
				PropertiesFileHandler.getInstance().LoadValuesOnModel(pfmodel);
			} catch (PropertyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			PropertiesFileHandler.getInstance().writeFile();
			_buzon = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._reciveR1Name, ServerMailBox._sendR1Name);
			
			for(int i=0;i<16;i++) contexto.setEstadoAnterior(i, false);
		}catch( Exception e ){
 			e.printStackTrace();
 		}
	}
	
	public void execute(){
		while(!contexto.isFIN()){
			System.out.println("antes de dormir");
			pauseJoy();
			guardedJoy();
			System.out.println("despues de dormir");

			MessageInterface mensaje=null;
			do{
				try {
					System.out.println("antes de recibir");
					mensaje=_buzon.receive();
					System.out.println("despues de recibir: "+mensaje);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(mensaje!=null){
					System.out.println(mensaje.getIdentificador());
					switch (mensaje.getIdentificador()) {
					case FINCINTALIBRE:							
						contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1), false);
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
						contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1), false);
						break;
					case RELLENARMAQUINA:
						String maquina=mensaje.getParametros().get(0);
						int cantidad=Integer.parseInt(mensaje.getParametros().get(1));
						if(maquina.compareTo(NombreMaquinas.DISPENSADORA.getName())==0)
							_dispensadora.fillDeposit(cantidad);
						if(maquina.compareTo(NombreMaquinas.CARAMELO.getName())==0)
							contexto.rellenarCaramelo(cantidad,configuracion.getCapacidadCaramelo());
						if(maquina.compareTo(NombreMaquinas.CHOCOLATE.getName())==0)
							contexto.rellenarCaramelo(cantidad,configuracion.getCapacidadChocolate());
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
					}
				}
			}while(mensaje!=null);

			if(contexto.isParadaCorrecta()){
				if(contexto.get_listaPasteles().size()==0) contexto.setApagado(true);
			}

			if(!contexto.isFallo()){
				if(!contexto.isApagado()){


					/*
					 * se coordina y ejecutan los hilos de: movercinta, DispensadoraAutomatica, MaquinaCaramelo, MaquinaChocolate, 
					 * 		moverCinta, salidaPastel
					 */

					if(!seEnciendeSensor() && !hayHiloBloqueante() && !contexto.isInterferencia()){
						_moverCinta.run();
					}else{
						if(puedoUsar(NombreMaquinas.CHOCOLATE)){
							if(contexto.getCapacidadChocolate()>0){
								_chocolate.run();
								contexto.get_listaPasteles().get(contexto.activaSensor(configuracion, _chocolate.getPosition())).set_chocolate();
								contexto.decrementarChocolate();
							}else{
								DefaultMessage mensajeSend= new DefaultMessage();
								mensajeSend.setIdentificador(OntologiaMSG.AVISARUNFALLO);
								mensajeSend.getParametros().add(NombreMaquinas.CHOCOLATE.getName()); 									
								_buzon.send(mensajeSend);
								contexto.setFallo(true);
							}
						}
						if(puedoUsar(NombreMaquinas.CARAMELO)){
							if(contexto.getCapacidadCaramelo()>0){
								_caramelo.run();
								contexto.get_listaPasteles().get(contexto.activaSensor(configuracion, _caramelo.getPosition())).set_caramelo();
								contexto.decrementarCaramelo();
							}else{
								DefaultMessage mensajeSend= new DefaultMessage();
								mensajeSend.setIdentificador(OntologiaMSG.AVISARUNFALLO);
								mensajeSend.getParametros().add(NombreMaquinas.CARAMELO.getName()); 									
								_buzon.send(mensajeSend);
								contexto.setFallo(true);
							}
						}
						if(puedoUsar(NombreMaquinas.FIN_1)) _salPastel.run();
					}
					//no me importa si la cinta se mueve o no, si puede la dispensadora echa un pastel
					// se pone dentro del while del ciclo de reloj porq solo pone un pastel por click
					if(!contexto.isParadaCorrecta())
						_dispensadora.run();
					if(_dispensadora.getRemainderCakes()==0){
						DefaultMessage mensajeSend= new DefaultMessage();
						mensajeSend.setIdentificador(OntologiaMSG.AVISARUNFALLO);
						mensajeSend.getParametros().add(NombreMaquinas.DISPENSADORA.getName()); 									
						_buzon.send(mensajeSend);
						contexto.setFallo(true);
					}

				}
				for(int i=0;i<16;i++) contexto.setEstadoAnterior(i, contexto.getDispositivosInternos(i));
				apagarSensores();
			} // fin del if(!contexto.isfallo)

			// envia el mensaje de contexto
			DefaultMessage mensajeSend=new DefaultMessage();
			mensajeSend.setIdentificador(OntologiaMSG.ACTUALIZARCONTEXTO);
			mensajeSend.setObject(contexto);
			_buzon.send(mensajeSend);
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
		if(_dispensadora.getThreadState().equals(ThreadState.EJECUTANDO)) hay=true;
		else if(_caramelo.getThreadState().equals(ThreadState.EJECUTANDO)) hay=true;
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
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CARAMELO))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CARAMELO), true);
			salida=true;
		}
		if(contexto.activaSensor(configuracion, _chocolate.getPosition())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CHOCOLATE))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CHOCOLATE), true);
			salida=true;
		}
		if(contexto.activaSensor(configuracion, _salPastel.getPosition())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1), true);
			salida=true;
		}
		// la dispensadora no tiene sensor asociado
		// if(contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		return salida;
	}
	
	/**
	 * @param nombre
	 * @return
	 */
	private synchronized  boolean ejecutandoAlgo(NombreMaquinas nombre){
		boolean salida=false;
		if(nombre.equals(NombreMaquinas.DISPENSADORA))
			if(_dispensadora.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.CHOCOLATE))
			if(_chocolate.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.CARAMELO))
			if(_caramelo.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.FIN_1))
			if(_salPastel.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		return salida;
	}
	
	/**
	 * @param tipo
	 * @return
	 */
	private synchronized  boolean puedoUsar(NombreMaquinas tipo){
		boolean salida=false;
		/*
		if(tipo.equals(NombreMaquinas.DISPENSADORA))
			if(!ejecutandoAlgo(NombreMaquinas.DISPENSADORA) && contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		*/
		if(tipo.equals(NombreMaquinas.CHOCOLATE))
			if(!ejecutandoAlgo(NombreMaquinas.CHOCOLATE) && 
					contexto.activaSensor(configuracion, _chocolate.getPosition())>=0 &&
					!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.CHOCOLATE))) salida=true;
		if(tipo.equals(NombreMaquinas.CARAMELO))
			if(!ejecutandoAlgo(NombreMaquinas.CARAMELO) && 
					contexto.activaSensor(configuracion, _caramelo.getPosition())>=0 &&
					!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.CARAMELO))) salida=true;
		if(tipo.equals(NombreMaquinas.FIN_1))
			if(!ejecutandoAlgo(NombreMaquinas.FIN_1) && 
					contexto.activaSensor(configuracion, _salPastel.getPosition())>=0 &&
					!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1))) salida=true;
		return salida;
	}
	/**
	 * 
	 */
	private synchronized  void apagarSensores(){
		int num=-1;
		num=contexto.activaSensor(configuracion, _caramelo.getPosition());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CARAMELO), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _chocolate.getPosition());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CHOCOLATE), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _salPastel.getPosition());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1), false);
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