package com.umbrella.autoslave.logic;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.umbrella.autoslave.executor.Apagado;
import com.umbrella.autoslave.executor.DispensadoraActivada;
import com.umbrella.autoslave.executor.Estado;
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
import com.umbrella.autoslave.utils2.EstateThreads;
import com.umbrella.autoslave.utils2.NombreMaquinas;
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.mail.modulocomunicacion.MailBox;


/*
 * El objetivo de esta clase es llevar el peso de la ejecucion, aqui se crean los hilos q luego se ejecutaran en 
 * paralelo entre ellos
 */
public class Maestro1 {
	
	private static Clock _clock;
	private static MoverCinta _moverCinta;
	private static DispensadoraActivada _dispensadora;
	private static SalidaCinta _salPastel;
	private static MaquinaTiempos _chocolate;
	private static MaquinaTiempos _caramelo;
	private static MailBox buzon;
	
	private static Contexto contexto=Contexto.getInstance("pastel");
	private static Configuracion configuracion=Configuracion.getInstance();
	private static MailBox _buzon;
	
	private static String host = "localhost";
	private static int puerto = 9003;
	
	public static void main(String[] args) {
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
 				_buzon=new MailBox(host,puerto,"EntradaMaestro1","SalidaMaestro1");
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
 			
 			for(int i=0;i<16;i++) contexto.setEstadoAnterior(i, false);

 			while(!contexto.isFIN()){

 				if(cicloAct<_clock.getClock()){
 					cicloAct=_clock.getClock();
 					
 					Object aux=null;

 					do{
 						aux=_buzon.receive();
 						if(aux instanceof FinCintaLibre){
 							_finCintaLibre = (FinCintaLibre)aux;
 							contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1), false);
 						}else if(aux instanceof ActualizarContexto){
 							_actualizarContexto=(ActualizarContexto)aux;
 							contexto=_actualizarContexto.getContexto();
 						}else if(aux instanceof ActualizarConfiguracion){
 							_actualizarConfiguracion=(ActualizarConfiguracion)aux;
 							configuracion=_actualizarConfiguracion.getConfiguracion();
 						}else if(aux instanceof Arrancar){
 							_arrancar=(Arrancar)aux;
 							contexto=Contexto.reset("pastel");
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
 						}else if(aux instanceof RellanarMaquina){
 							_rellanarMaquina=(RellanarMaquina)aux;
 							if(_rellanarMaquina.getMaquina().compareTo(NombreMaquinas.DISPENSADORA.getName())==0)
 								_dispensadora.llenarDeposito(_rellanarMaquina.getCantidad());
 							if(_rellanarMaquina.getMaquina().compareTo(NombreMaquinas.CARAMELO.getName())==0)
 								contexto.rellenarCaramelo(_rellanarMaquina.getCantidad(),configuracion.getCapacidadCaramelo());
 							if(_rellanarMaquina.getMaquina().compareTo(NombreMaquinas.CHOCOLATE.getName())==0)
 								contexto.rellenarCaramelo(_rellanarMaquina.getCantidad(),configuracion.getCapacidadChocolate());	
 						}else if(aux instanceof Reset){
 							_reset=(Reset)aux;
 							if(contexto.isApagado() || contexto.isFallo()){
 								contexto=Contexto.reset("pastel");
 								contexto.rellenarCaramelo(configuracion.getCapacidadCaramelo(),configuracion.getCapacidadCaramelo());
 								contexto.rellenarCaramelo(configuracion.getCapacidadChocolate(),configuracion.getCapacidadChocolate());
 							}
 						}else if(aux instanceof ParadaFallo){
 							_paradaFallo=(ParadaFallo)aux;
 							contexto.setFallo(true);
 						}

 					}while(aux!=null);

 					if(contexto.isParadaCorrecta()){
						if(contexto.get_listaPasteles().size()==0) contexto.setApagado(true);
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
 							}

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
 										_avisarUnFallo=new AvisarUnFallo();
 										_avisarUnFallo.setClick(cicloAct);
 										_avisarUnFallo.setMotivo(NombreMaquinas.CHOCOLATE.getName() + "- CAPACIDAD");
 										_buzon.send(_avisarUnFallo);
 										contexto.setFallo(true);
 									}
 								}
 								if(puedoUsar(NombreMaquinas.CARAMELO)){
 									if(contexto.getCapacidadCaramelo()>0){
 										_caramelo.run();
 										contexto.get_listaPasteles().get(contexto.activaSensor(configuracion, _caramelo.get_posicion())).set_caramelo();
 										contexto.decrementarCaramelo();
 									}else{
 										_avisarUnFallo=new AvisarUnFallo();
 										_avisarUnFallo.setClick(cicloAct);
 										_avisarUnFallo.setMotivo(NombreMaquinas.CARAMELO.getName() + "- CAPACIDAD");
 										_buzon.send(_avisarUnFallo);
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
 								_avisarUnFallo=new AvisarUnFallo();
								_avisarUnFallo.setClick(cicloAct);
								_avisarUnFallo.setMotivo(NombreMaquinas.DISPENSADORA.getName() + "- CAPACIDAD");
								_buzon.send(_avisarUnFallo);
								contexto.setFallo(true);
 							}

 						}else{ //el else de:  if(!contexto.apagado){
 							primeraVez=true;
 						}
 						for(int i=0;i<16;i++) contexto.setEstadoAnterior(i, contexto.getDispositivosInternos(i));

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
 		 			actContexto.setIdentificador(OntologiaMSG.ACTUALIZARCONTEXTO.getNombre());
 		 			actContexto.setMaquina(1);
 		 			_buzon.send(actContexto);

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
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CARAMELO), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _chocolate.get_posicion());
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CHOCOLATE), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _salPastel.get_posicion());
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_1), false);
	}
}
