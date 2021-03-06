package com.umbrella.autoslave.logic;

import java.rmi.RemoteException;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.Context;
import com.umbrella.autocommon.Notifiable;
import com.umbrella.autoslave.executor.ConveyorBeltExit;
import com.umbrella.autoslave.executor.InstantaneousMachine;
import com.umbrella.autoslave.executor.MoveConveyorBelt;
import com.umbrella.autoslave.executor.PropertiesFile;
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
public class Slave2 implements Notifiable {

	private  Clock _clock;
	private  MoveConveyorBelt _moverCinta;
	private  ConveyorBeltExit _salBlister;
	private  InstantaneousMachine _cortadora;
	private  InstantaneousMachine _troqueladora;

	private  Context contexto=Context.getInstance("blister");
	private  Configuration configuracion=Configuration.getInstance();
	private  ClientMailBox _buzon;

	private  PropertiesFile pfmodel;
	private boolean _joy = true;

	public Slave2(){
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
			_moverCinta=new MoveConveyorBelt(configuracion.getVelCintaAut2(),
					configuracion.getPosicionAsociada(MachineNames.CINTA_2));
			_salBlister=new ConveyorBeltExit(configuracion.getPosFinAut2(),
					configuracion.getPosicionAsociada(MachineNames.FIN_2), "blister");
			_cortadora=new InstantaneousMachine(configuracion.getPosCortadora(),
					configuracion.getPosicionAsociada(MachineNames.CORTADORA));
			_troqueladora=new InstantaneousMachine(configuracion.getPosTroqueladora(),
					configuracion.getPosicionAsociada(MachineNames.TROQUELADORA));
			
			_moverCinta.start();
			_salBlister.start();
			_cortadora.start();
			_troqueladora.start();
			
			try {
				pfmodel = PropertiesFile.getInstance();
				PropertiesFileHandler.getInstance().LoadValuesOnModel(pfmodel);
			} catch (PropertyException e1) {
				
				e1.printStackTrace();
			}
			PropertiesFileHandler.getInstance().writeFile();
			_buzon = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._reciveAU2Name, ServerMailBox._sendAU2Name, true);

			
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	public void execute(){
		boolean primeraVez=true;
		while(!contexto.isFIN()){

			pauseJoy();
			guardedJoy();
			/*
			 * en cada ciclo de reloj, si aun estoy en el ciclo de reloj me quedo aqui
			 */
			MessageInterface mensaje=null;

			do{
				try {
					mensaje=_buzon.receive();
				} catch (RemoteException e) {

					e.printStackTrace();
				}
				if(mensaje!=null){
					switch (mensaje.getIdentifier()) {
					case FINCINTALIBRE:							
						contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.FIN_2), false);
						break;
					case ACTUALIZARCONFIGURACION: 						
						configuracion=(Configuration)mensaje.getObject();
						contexto.setApagado(false);
						contexto.setParadaCorrecta(false);
						break;
					case START:
						contexto=Context.reset("blister");
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
						contexto.setMoviendoCinta(false);
						break;
					case PRODUCTORECOGIDO:
						//contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.FIN_2), false);
						deleteLastBlister();
						break;
					case RESET:
						if(contexto.isApagado() || contexto.isFallo()){
							contexto=Context.reset("blister");
						}
						break;
					case PARADAFALLO:
						contexto.setFallo(true);
						break;
					case ACTUALIZARCONTEXTO:
						contexto.absorverContexto((Context) mensaje.getObject());
						contexto.setApagado(false);
						contexto.setParadaCorrecta(false);
						break;
					}
				}
			}while(mensaje!=null);

			if(contexto.isParadaCorrecta()){
				//se para directamente porque no se controla
				contexto.setApagado(true);
				contexto.setMoviendoCinta(false);
				
				// envia el mensaje de contexto
				DefaultMessage mensajeSend=new DefaultMessage();
				mensajeSend.setIdentifier(MSGOntology.ACTUALIZARCONTEXTO);
				mensajeSend.setObject(contexto);
				_buzon.send(mensajeSend);
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
						hayEspacio();
						if(!seEnciendeSensor() && !hayHiloBloqueante() && !contexto.isInterferencia()){
							if(_moverCinta != null){
								_moverCinta.notifyNoSyncJoy2();
								contexto.setMoviendoCinta(true);
							}
						}else{
							contexto.setMoviendoCinta(false);
							seEnciendeSensor();

							if(puedoUsar(MachineNames.CORTADORA) ){
								//_cortadora.run();
								if(_cortadora != null)
									_cortadora.notifyNoSyncJoy2();
								contexto.get_listaBlister().get(contexto.activaSensor(configuracion, _cortadora.getPosition())).set_cortado(true);
							}
							if(puedoUsar(MachineNames.TROQUELADORA) ){
								//_troqueladora.run();
								if(_troqueladora != null)
									_troqueladora.notifyNoSyncJoy2();
								contexto.get_listaBlister().get(contexto.activaSensor(configuracion, _troqueladora.getPosition())).set_troquelado(true);
							}
						}
					}

					if(puedoUsar(MachineNames.FIN_2)){
						if(_salBlister != null)
							_salBlister.notifyNoSyncJoy2();
					}
					/* esto del estado anterior sirve para saber como estaba el sensor en el estado anterior*/
					for(int i=0;i<contexto.getEstadoAnterior().length;i++)
						contexto.setEstadoAnterior(i,contexto.getDispositivosInternos(i));
					apagarSensores();
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
		_troqueladora=null;
		_salBlister=null;
		_cortadora=null;	
	}
	
	/*
	 * Nos dice si algun hilo esta bloqueando al resto, es decir uno de los hilos esta en ejecucion
	 * true= algun hilo esta bloqueando
	 */
	private synchronized boolean hayHiloBloqueante(){
		boolean hay=false;
		if(_cortadora.getThreadState().equals(ThreadState.EJECUTANDO))
			hay=true;
		else if(_troqueladora.getThreadState().equals(ThreadState.EJECUTANDO))
			hay=true;
		else if(_salBlister.getThreadState().equals(ThreadState.EJECUTANDO))
			hay=true;
		return hay;
	}
	
	private synchronized boolean seEnciendeSensor(){
		boolean salida=false;
		
		if(contexto.activaSensor(configuracion, _salBlister.getPosition())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(MachineNames.FIN_2))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.FIN_2), true);
			salida=true;
		}
		// la dispensadora no tiene sensor asociado
		// if(contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		return salida;
	}
	
	private synchronized boolean ejecutandoAlgo(MachineNames nombre){
		boolean salida=false;
		if(nombre.equals(MachineNames.TROQUELADORA))
			if(_troqueladora.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		if(nombre.equals(MachineNames.CORTADORA))
			if(_cortadora.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		if(nombre.equals(MachineNames.FIN_2))
			if(_salBlister.getThreadState().equals(ThreadState.EJECUTANDO)) salida=true;
		return salida;
	}
	
	private synchronized  boolean puedoUsar(MachineNames tipo){
		boolean salida=false;
		if(tipo.equals(MachineNames.CORTADORA))
			if(!ejecutandoAlgo(MachineNames.CORTADORA) && 
					contexto.activaSensor(configuracion, _cortadora.getPosition())>=0){
				if(!contexto.get_listaBlister().get(contexto.activaSensor(configuracion, _cortadora.getPosition())).is_cortado())
					salida=true;
			}
				
		
		if(tipo.equals(MachineNames.TROQUELADORA))
			if(!ejecutandoAlgo(MachineNames.TROQUELADORA) && 
					contexto.activaSensor(configuracion, _troqueladora.getPosition())>=0){
				if(!contexto.get_listaBlister().get(contexto.activaSensor(configuracion, _troqueladora.getPosition())).is_troquelado())
					salida=true;
			}
				
		if(tipo.equals(MachineNames.FIN_2))
			if(!ejecutandoAlgo(MachineNames.FIN_2) && 
					contexto.activaSensor(configuracion, _salBlister.getPosition())>=0 &&
						!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(MachineNames.FIN_2))) 
				salida=true;
		return salida;
	}
	
	
	private synchronized  void apagarSensores(){
		//TODO hay q cambiarlo, ahora hay q mirar el blister para ver sus atributos y ver si ha pasado por el sensor
		
		int num=-1;
		num=contexto.activaSensor(configuracion, _cortadora.getPosition());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.SENSOR_CORTADORA), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _troqueladora.getPosition());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.SENSOR_TROQUELADORA), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _salBlister.getPosition());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(MachineNames.FIN_2), false);
	}
	
	private synchronized  void hayEspacio(){
		/*
		 * comprueba q la posicion del ultimo blister en la cinta es mayor q el tama�o de blister mas 1/2 del blister
		 * (el 1/2) es porque cogemos la posicion intermedia del blister
		 */
		double min=configuracion.getSizeCinta()+20;
		for(int i=0;i<contexto.get_listaBlister().size();i++){
			if(contexto.get_listaBlister().get(i).get_posicion()<min){
				min=contexto.get_listaBlister().get(i).get_posicion();
			}
		}
		if(min>(configuracion.getSizeBlister() + configuracion.getSizeBlister()/2)) 
			contexto.addListaBlister(new Blister());
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

	private void deleteLastBlister(){
		boolean eliminado = false;
		for(int i=0; !eliminado && i<contexto.get_listaBlister().size();i++){
			if(contexto.get_listaBlister().get(i).get_posicion()>=(configuracion.getPosFinAut2()-configuracion.getErrorSensor())){
				contexto.get_listaBlister().remove(i);
				eliminado = true;
			}
		}
	}
	
	

}
