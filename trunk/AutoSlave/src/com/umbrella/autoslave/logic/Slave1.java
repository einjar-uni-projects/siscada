package com.umbrella.autoslave.logic;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Clock;
import com.umbrella.autocommon.Configuracion;
import com.umbrella.autocommon.Contexto;
import com.umbrella.autoslave.executor.Apagado;
import com.umbrella.autoslave.executor.DispensadoraActivada;
import com.umbrella.autoslave.executor.MaquinaTiempos;
import com.umbrella.autoslave.executor.MoverCinta;
import com.umbrella.autoslave.executor.SalidaCinta;
import com.umbrella.mail.mailbox.ClientMailBox;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.utils.EstateThreads;
import com.umbrella.utils.NombreMaquinas;


/*
 * El objetivo de esta clase es llevar el peso de la ejecucion, aqui se crean los hilos q luego se ejecutaran en 
 * paralelo entre ellos
 */
public class Slave1 {
	
	private static Clock _clock;
	private static MoverCinta _moverCinta;
	private static DispensadoraActivada _dispensadora;
	private static SalidaCinta _salPastel;
	private static MaquinaTiempos _chocolate;
	private static MaquinaTiempos _caramelo;
	
	private static Contexto contexto=Contexto.getInstance("pastel");
	private static Configuracion configuracion=Configuracion.getInstance();
	private static ClientMailBox _buzon;
	
	private static String host = "localhost";
	private static int puerto = 9003;
	
	public static void main(String[] args) {
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
 			_moverCinta=new MoverCinta(configuracion.getVelCinta(),
 					configuracion.getPosicionAsociada(NombreMaquinas.CINTA_1));
 			_dispensadora=(DispensadoraActivada)DispensadoraActivada.getInstance(configuracion.getPosBizc(),
 					configuracion.getPosicionAsociada(NombreMaquinas.DISPENSADORA));
 			_salPastel=new SalidaCinta(configuracion.getPosFinAut1(),
 					configuracion.getPosicionAsociada(NombreMaquinas.FIN_2), "pastel");
 			_caramelo=new MaquinaTiempos(configuracion.getValvCaram(), configuracion.getPosCaram(),
 					configuracion.getPosicionAsociada(NombreMaquinas.CARAMELO));
 			_chocolate=new MaquinaTiempos(configuracion.getValvChoc(), configuracion.getPosChoc(),
 					configuracion.getPosicionAsociada(NombreMaquinas.CHOCOLATE));

 			contexto.rellenarCaramelo(configuracion.getCapacidadCaramelo(),configuracion.getCapacidadCaramelo());
 			contexto.rellenarCaramelo(configuracion.getCapacidadChocolate(),configuracion.getCapacidadChocolate());
 			
 			try {
 				_buzon=new ClientMailBox(host,puerto,"EntradaMaestro1","SalidaMaestro1");
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
 			
 			for(int i=0;i<16;i++) contexto.setEstadoAnterior(i, false);

 			while(!contexto.isFIN()){

 				if(cicloAct<_clock.getClock()){
 					cicloAct=_clock.getClock();
 					
 					MessageInterface mensaje=null;

 					do{
 						mensaje=_buzon.receive();
 						switch (mensaje.getIdentificador()) {
						case FINCINTALIBRE:							
 							contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1), false);
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
 							contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1), false);
 							break;
 						case RELLENARMAQUINA:
 							String maquina=mensaje.getParametros().get(0);
 							int cantidad=Integer.parseInt(mensaje.getParametros().get(1));
 							if(maquina.compareTo(NombreMaquinas.DISPENSADORA.getName())==0)
 								_dispensadora.llenarDeposito(cantidad);
 							if(maquina.compareTo(NombreMaquinas.CARAMELO.getName())==0)
 								contexto.rellenarCaramelo(cantidad,configuracion.getCapacidadCaramelo());
 							if(maquina.compareTo(NombreMaquinas.CHOCOLATE.getName())==0)
 								contexto.rellenarCaramelo(cantidad,configuracion.getCapacidadChocolate());
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
 										contexto.get_listaPasteles().get(contexto.activaSensor(configuracion, _chocolate.get_posicion())).set_chocolate();
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
 										contexto.get_listaPasteles().get(contexto.activaSensor(configuracion, _caramelo.get_posicion())).set_caramelo();
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
 							if(_dispensadora.get_PastelesRestantes()==0){
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

 				}//fin del if de cada ciclo de reloj
 			}// fin del while(!esFin)
 			/*
 			 * se matan los hilos
 			 */
 			_moverCinta=null;
 			_dispensadora=null;
 			_caramelo=null;
 			_chocolate=null;
 			_salPastel=null;
 			
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
		if(_dispensadora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_caramelo.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_chocolate.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		else if(_salPastel.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) hay=true;
		return hay;
	}
	
	private synchronized static boolean seEnciendeSensor(){
		boolean salida=false;
		
		if(contexto.activaSensor(configuracion, _caramelo.get_posicion())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CARAMELO))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CARAMELO), true);
			salida=true;
		}
		if(contexto.activaSensor(configuracion, _chocolate.get_posicion())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CHOCOLATE))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CHOCOLATE), true);
			salida=true;
		}
		if(contexto.activaSensor(configuracion, _salPastel.get_posicion())>=0 && 
				!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1))){
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1), true);
			salida=true;
		}
		// la dispensadora no tiene sensor asociado
		// if(contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		return salida;
	}
	
	private synchronized static boolean ejecutandoAlgo(NombreMaquinas nombre){
		boolean salida=false;
		if(nombre.equals(NombreMaquinas.DISPENSADORA))
			if(_dispensadora.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.CHOCOLATE))
			if(_chocolate.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.CARAMELO))
			if(_caramelo.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		if(nombre.equals(NombreMaquinas.FIN_1))
			if(_salPastel.get_estadoHilo().equals(EstateThreads.EJECUTANDO)) salida=true;
		return salida;
	}
	
	private synchronized static boolean puedoUsar(NombreMaquinas tipo){
		boolean salida=false;
		/*
		if(tipo.equals(NombreMaquinas.DISPENSADORA))
			if(!ejecutandoAlgo(NombreMaquinas.DISPENSADORA) && contexto.activaSensor(_dispensadora.get_posicion())>=0) salida=true;
		*/
		if(tipo.equals(NombreMaquinas.CHOCOLATE))
			if(!ejecutandoAlgo(NombreMaquinas.CHOCOLATE) && 
					contexto.activaSensor(configuracion, _chocolate.get_posicion())>=0 &&
					!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.CHOCOLATE))) salida=true;
		if(tipo.equals(NombreMaquinas.CARAMELO))
			if(!ejecutandoAlgo(NombreMaquinas.CARAMELO) && 
					contexto.activaSensor(configuracion, _caramelo.get_posicion())>=0 &&
					!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.CARAMELO))) salida=true;
		if(tipo.equals(NombreMaquinas.FIN_1))
			if(!ejecutandoAlgo(NombreMaquinas.FIN_1) && 
					contexto.activaSensor(configuracion, _salPastel.get_posicion())>=0 &&
					!contexto.getEstadoAnterior(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1))) salida=true;
		return salida;
	}
	private synchronized static void apagarSensores(){
		int num=-1;
		num=contexto.activaSensor(configuracion, _caramelo.get_posicion());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CARAMELO), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _chocolate.get_posicion());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CHOCOLATE), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _salPastel.get_posicion());
		if(num<0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1), false);
	}
}
