package com.umbrella.autoslave.logic;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuracion;
import com.umbrella.autocommon.Contexto;
import com.umbrella.autoslave.executor.Apagado;
import com.umbrella.autoslave.executor.MaquinaInstantanea;
import com.umbrella.autoslave.executor.MoverCinta;
import com.umbrella.autoslave.executor.SalidaCinta;
import com.umbrella.mail.mailbox.ClientMailBox;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.utils.Blister;
import com.umbrella.utils.EstateThreads;
import com.umbrella.utils.NombreMaquinas;


/*
 * El objetivo de esta clase es llevar el peso de la ejecucion, aqui se crean los hilos q luego se ejecutaran en 
 * paralelo entre ellos
 */
public class Maestro2 {
	
	private static Clock _clock;
	private static MoverCinta _moverCinta;
	private static SalidaCinta _salBlister;
	private static MaquinaInstantanea _cortadora;
	private static MaquinaInstantanea _troqueladora;
	
	private static Contexto contexto=Contexto.getInstance("blister");
	private static Configuracion configuracion=Configuracion.getInstance();
	private static ClientMailBox _buzon;

	private static String host = "localhost";
	private static int puerto = 9003;
	
	public static void main(String[] args) {
		
		for(int i=0;i<contexto.getEstadoAnterior().length;i++) contexto.setEstadoAnterior(i,false);
		
		try	{
			
			Apagado estado= Apagado.getInstance();
 			//contexto.setState( estado );
 			
 			/*
 			 * tenemos dos hilos, uno es el reloj y el otro es la ejecucion del automata que debe quedarse bloqueado entre 
 			 * los clicks del reloj, para eso el reloj debe hacer un notify a todos los hilos notifyAll()
 			 */
 			_clock=new Clock();
 			_clock.start();
 	
 			/*
 			 * se crean los hilos de ejecucion
 			 */
 			_moverCinta=new MoverCinta(configuracion.getVelCintaAut2(),
 					configuracion.getPosicionAsociada(NombreMaquinas.CINTA_2));
 			_salBlister=new SalidaCinta(configuracion.getPosFinAut2(),
 					configuracion.getPosicionAsociada(NombreMaquinas.FIN_2), "blister");
 			_cortadora=new MaquinaInstantanea(configuracion.getPosCortadora(),
 					configuracion.getPosicionAsociada(NombreMaquinas.CORTADORA));
 			_troqueladora=new MaquinaInstantanea(configuracion.getPosTroqueladora(),
 					configuracion.getPosicionAsociada(NombreMaquinas.TROQUELADORA));
 	
 			
 			try {
 				_buzon=new ClientMailBox(host,puerto,"EntradaMaestro2","SalidaMaestro2");
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
 			
 			while(!contexto.isFIN()){
 				if(cicloAct<_clock.getClock()){
 					cicloAct=_clock.getClock();
 					/*
 					 * en cada ciclo de reloj, si aun estoy en el ciclo de reloj me quedo aqui
 					 */
 				
 					MessageInterface mensaje=null;
 					
 					do{
 						mensaje=_buzon.receive();
 						switch (mensaje.getIdentificador()) {
						case FINCINTALIBRE:							
 							contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_2), false);
 							break;
						case ACTUALIZARCONFIGURACION: 						
 							configuracion=(Configuracion)mensaje.getObject();
 							break;
						case ARRANCAR:
 							contexto=Contexto.reset("pastel");
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
 							contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_2), false);
 							break;
 						case RESET:
 							if(contexto.isApagado() || contexto.isFallo()){
 								contexto=Contexto.reset("pastel");
 								contexto.rellenarCaramelo(configuracion.getCapacidadCaramelo(),configuracion.getCapacidadCaramelo());
 								contexto.rellenarCaramelo(configuracion.getCapacidadChocolate(),configuracion.getCapacidadChocolate());
 							}
 							break;
 						case PARADAFALLO:
 							contexto.setFallo(true);
 							break;
 						}
 					}while(mensaje!=null);

 					if(contexto.isParadaCorrecta()){
						//se para directamente porque no se controla
 						contexto.setApagado(true);
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
 								hayEspacio();
 								if(!seEnciendeSensor() && !hayHiloBloqueante() && !contexto.isInterferencia()){
 									_moverCinta.run();
 								}else{
 									seEnciendeSensor();

 									if(puedoUsar(NombreMaquinas.CORTADORA) ){
 										_cortadora.run();
 										contexto.get_listaBlister().get(contexto.activaSensor(configuracion, _cortadora.get_posicion())).set_cortado(true);
 									}
 									if(puedoUsar(NombreMaquinas.TROQUELADORA) ){
 										_troqueladora.run();
 										contexto.get_listaBlister().get(contexto.activaSensor(configuracion, _troqueladora.get_posicion())).set_troquelado(true);
 									}
 								}
 							}
 							/* esto del estado anterior sirve para saber como estaba el sensor en el estado anterior*/
 							for(int i=0;i<contexto.getEstadoAnterior().length;i++) contexto.setEstadoAnterior(i,contexto.getDispositivosInternos(i));
 							apagarSensores();
 						}

 					} // fin del if(!contexto.isfallo)


 		 			// envia el mensaje de contexto
 					DefaultMessage mensajeSend=new DefaultMessage();
 					mensajeSend.setIdentificador(OntologiaMSG.ACTUALIZARCONTEXTO);
 					mensajeSend.setObject(contexto);
 		 			_buzon.send(mensajeSend);
 		 			
 				}//fin del ciclo de reloj
 			}
 			/*
 			 * se matan los hilos
 			 */
 			_moverCinta=null;
 			_troqueladora=null;
 			_salBlister=null;
 			_cortadora=null;
 			
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
		if(_cortadora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_troqueladora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_salBlister.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		return hay;
	}
	
	private synchronized static boolean seEnciendeSensor(){
		boolean salida=false;
		
		if(contexto.activaSensor(configuracion, _salBlister.get_posicion())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.FIN_2))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_2), true);
			salida=true;
		}
		// la dispensadora no tiene sensor asociado
		// if(contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		return salida;
	}
	
	private synchronized static boolean ejecutandoAlgo(NombreMaquinas nombre){
		boolean salida=false;
		if(nombre.equals(NombreMaquinas.TROQUELADORA))
			if(_troqueladora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.CORTADORA))
			if(_cortadora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.FIN_2))
			if(_salBlister.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		return salida;
	}
	
	private synchronized static boolean puedoUsar(NombreMaquinas tipo){
		boolean salida=false;
		if(tipo.equals(NombreMaquinas.CORTADORA))
			if(!ejecutandoAlgo(NombreMaquinas.CORTADORA) && 
					contexto.activaSensor(configuracion, _cortadora.get_posicion())>=0){
				if(!contexto.get_listaBlister().get(contexto.activaSensor(configuracion, _cortadora.get_posicion())).is_cortado())
					salida=true;
			}
				
		
		if(tipo.equals(NombreMaquinas.TROQUELADORA))
			if(!ejecutandoAlgo(NombreMaquinas.TROQUELADORA) && 
					contexto.activaSensor(configuracion, _troqueladora.get_posicion())>=0){
				if(!contexto.get_listaBlister().get(contexto.activaSensor(configuracion, _troqueladora.get_posicion())).is_troquelado())
					salida=true;
			}
				
		if(tipo.equals(NombreMaquinas.FIN_2))
			if(!ejecutandoAlgo(NombreMaquinas.FIN_2) && 
					contexto.activaSensor(configuracion, _salBlister.get_posicion())>=0 &&
						!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.FIN_2))) 
				salida=true;
		return salida;
	}
	
	
	private synchronized static void apagarSensores(){
		//TODO hay q cambiarlo, ahora hay q mirar el blister para ver sus atributos y ver si ha pasado por el sensor
		
		int num=-1;
		num=contexto.activaSensor(configuracion, _cortadora.get_posicion());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CORTADORA), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _troqueladora.get_posicion());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_TROQUELADORA), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _salBlister.get_posicion());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_2), false);
	}
	
	private synchronized static void hayEspacio(){
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
			contexto.get_listaBlister().add(new Blister());
	}
}
