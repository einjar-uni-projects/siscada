package com.umbrella.autoslave.logic;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Vector;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.Notifiable;
import com.umbrella.autoslave.executor.ConveyorBeltExit;
import com.umbrella.autoslave.executor.InstantaneousMachine;
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
import com.umbrella.utils.Blister;
import com.umbrella.utils.MachineNames;
import com.umbrella.utils.ThreadState;


/*
 * El objetivo de esta clase es llevar el peso de la ejecucion, aqui se crean los hilos q luego se ejecutaran en 
 * paralelo entre ellos
 */
public class Slave3 implements Notifiable{

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
			_clock=Clock.getInstance();
			_clock.start();
			_clock.addNotificable(this);

			/*
			 * se crean los hilos de ejecucion
			 */
			_moverCinta=new MoveConveyorBelt(configuracion.getVelCintaAut3(),
					configuracion.getPosicionAsociada(MachineNames.CINTA_3));
			_salBlister=new ConveyorBeltExit(configuracion.getPosFinAut3(),
					configuracion.getPosicionAsociada(MachineNames.FIN_3), "blister");
			_calidad=new InstantaneousMachine(configuracion.getPosCalidad(),
					configuracion.getPosicionAsociada(MachineNames.CONTROL_CALIDAD));
			_selladora=new TimeMachine(configuracion.getSelladora(), configuracion.getPosSelladora(),
					configuracion.getPosicionAsociada(MachineNames.SELLADO));

			_moverCinta.start();
			_salBlister.start();
			_calidad.start();
			_selladora.start();
			
			try {
 				pfmodel = PropertiesFile.getInstance();
 				PropertiesFileHandler.getInstance().LoadValuesOnModel(pfmodel);
			} catch (PropertyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			PropertiesFileHandler.getInstance().writeFile();
			_buzon = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._reciveAU3Name, ServerMailBox._sendAU3Name, true);
		}catch( Exception e ){
			e.printStackTrace();
		}

	}
	public void execute() {

		boolean primeraVez=true;
		Blister blisterAuxiliar=new Blister();

		while(!contexto.isFIN()){
			pauseJoy();
			guardedJoy();
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
					switch (mensaje.getIdentifier()) {
					case FINCINTALIBRE:							
						contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.FIN_3), false);
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
						contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.FIN_3), false);
						break;
					case RESET:
						if(contexto.isApagado() || contexto.isFallo()){
							contexto=Context.reset("blister");
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
							contexto.addListaBlister(blisterAuxiliar.enCinta3());
							contexto.setBlisterListoInicioCinta3(false);
							//envio el mensaje de blister colocado, mesa libre
						}
						if(!seEnciendeSensor() && !hayHiloBloqueante() && !contexto.isInterferencia()){
							//_moverCinta.run();
							if(_moverCinta != null)
								_moverCinta.notifyNoSyncJoy2();
						}else{
							seEnciendeSensor();
							if(puedoUsar(MachineNames.CONTROL_CALIDAD) ){
								//_calidad.run();
								if(_calidad != null)
									_calidad.notifyNoSyncJoy2();
								if(Math.random()<configuracion.getPorcentajeFallos()){
									Vector<Integer> vectorAux=new Vector<Integer>();
									vectorAux.add(configuracion.getPosicionAsociada(MachineNames.SENSOR_CALIDAD_SENSOR_1));
									vectorAux.add(configuracion.getPosicionAsociada(MachineNames.SENSOR_CALIDAD_SENSOR_2));
									vectorAux.add(configuracion.getPosicionAsociada(MachineNames.SENSOR_CALIDAD_SENSOR_3));
									vectorAux.add(configuracion.getPosicionAsociada(MachineNames.SENSOR_CALIDAD_SENSOR_4));
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
							if(puedoUsar(MachineNames.SELLADO)){
								//_selladora.run();
								if(_selladora != null)
									_selladora.notifyNoSyncJoy2();
							}
						}

					}
					if(puedoUsar(MachineNames.FIN_3)){
						//_salBlister.start();
						if(_salBlister != null)
							_salBlister.notifyNoSyncJoy2();
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
			mensajeSend.setIdentifier(MSGOntology.ACTUALIZARCONTEXTO);
			mensajeSend.setObject(contexto);
			_buzon.send(mensajeSend);
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
		if(_calidad.getThreadState().equals(ThreadState.EJECUTANDO))
			hay=true;
		else if(_selladora.getThreadState().equals(ThreadState.EJECUTANDO))
			hay=true;
		else if(_salBlister.getThreadState().equals(ThreadState.EJECUTANDO))
			hay=true;
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
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(MachineNames.SENSOR_SELLADORA))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.SENSOR_SELLADORA), true);
			salida=true;

		}
		if(contexto.activaSensor(configuracion, _salBlister.getPosition())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(MachineNames.FIN_3))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.FIN_3), true);
			salida=true;
		}
		// la dispensadora no tiene sensor asociado
		// if(contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		return salida;
	}
	
	private synchronized boolean ejecutandoAlgo(MachineNames nombre){
		boolean salida=false;
		if(nombre.equals(MachineNames.CONTROL_CALIDAD))
			if(_calidad.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		if(nombre.equals(MachineNames.SELLADO))
			if(_selladora.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		if(nombre.equals(MachineNames.FIN_3))
			if(_salBlister.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		return salida;
	}
	
	private synchronized boolean puedoUsar(MachineNames tipo){
		boolean salida=false;

		if(tipo.equals(MachineNames.CONTROL_CALIDAD))
			if(!ejecutandoAlgo(MachineNames.CONTROL_CALIDAD) && 
					contexto.activaSensor(configuracion, _calidad.getPosition())>=0)
				if(!contexto.get_listaBlister().get(contexto.activaSensor(configuracion, _calidad.getPosition())).passTest())
					salida=true;
	
		if(tipo.equals(MachineNames.SELLADO))
			if(!ejecutandoAlgo(MachineNames.SELLADO) && 
					contexto.activaSensor(configuracion, _selladora.getPosition())>=0 )
				if(!contexto.get_listaBlister().get(contexto.activaSensor(configuracion, _selladora.getPosition())).is_sellado())
					salida=true;
		if(tipo.equals(MachineNames.FIN_3))
			if(!ejecutandoAlgo(MachineNames.FIN_3) && 
					contexto.activaSensor(configuracion, _salBlister.getPosition())>=0 &&
						!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(MachineNames.FIN_3))) 
				salida=true;
		return salida;
	}
	
	
	private synchronized void apagarSensores(){
		int num=-1;
		num=contexto.activaSensor(configuracion, _calidad.getPosition());
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.SENSOR_CALIDAD), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _selladora.getPosition());
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.SENSOR_SELLADORA), false);
		num=-1;
			num=contexto.activaSensor(configuracion, _salBlister.getPosition());
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.FIN_3), false);
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

	/**
	 * repasa la linkedlist de blisteres y los pone en las posiciones del contador
	 */
	/*private void actualizarContadorAutomata(){
		
		contexto.resetContadorAutomata3();
		LinkedList<Blister> lista=contexto.get_listaBlister();
		for(int i=0;i<lista.size();i++){
			double pos=lista.get(i).get_posicion();
			if( pos<(configuracion.getPosInicioAut3()+configuracion.getSizeBlister()/2) ){
				contexto.incrementarContadorAutomata3(0);
			}else if(pos<(configuracion.getPosCalidad()+configuracion.getSizeBlister()/2)){
				contexto.incrementarContadorAutomata3(1);
			}else if(pos<(configuracion.getPosSelladora()-configuracion.getSizeBlister()/2)){
				contexto.incrementarContadorAutomata3(2);
			}else if(pos<(configuracion.getPosSelladora()+configuracion.getSizeBlister()/2)){
				contexto.incrementarContadorAutomata3(3);
			}else if(pos<(configuracion.getPosFinAut3()-configuracion.getSizeBlister()/2)){
				contexto.incrementarContadorAutomata3(4);
			}else if(pos<(configuracion.getPosFinAut3()+configuracion.getSizeBlister()/2)){
				contexto.incrementarContadorAutomata3(5);
			}
		}
	}*/
	
}
