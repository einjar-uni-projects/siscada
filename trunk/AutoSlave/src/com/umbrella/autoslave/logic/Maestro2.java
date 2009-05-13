package com.umbrella.autoslave.logic;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

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
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.mail.modulocomunicacion.MailBox;


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
	private static MailBox _buzon;

	private static String host = "localhost";
	private static int puerto = 9003;
	
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
 			_moverCinta=new MoverCinta(configuracion.getVelCintaAut2(),
 					configuracion.getPosicionAsociada(NombreMaquinas.CINTA_2));
 			_salBlister=new SalidaCinta(configuracion.getPosFinAut2(),
 					configuracion.getPosicionAsociada(NombreMaquinas.FIN_2), "blister");
 			_cortadora=new MaquinaInstantanea(configuracion.getPosCortadora(),
 					configuracion.getPosicionAsociada(NombreMaquinas.CORTADORA));
 			_troqueladora=new MaquinaInstantanea(configuracion.getPosTroqueladora(),
 					configuracion.getPosicionAsociada(NombreMaquinas.TROQUELADORA));
 	
 			
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
 				_buzon=new MailBox(host,puerto,"EntradaMaestro2","SalidaMaestro2");
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
 							contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.FIN_2), false);
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
 		 			actContexto.setIdentificador(OntologiaMSG.ACTUALIZARCONTEXTO.getNombre());
 		 			actContexto.setMaquina(1);
 		 			_buzon.send(actContexto);
 				}
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
		int num=-1;
		num=contexto.activaSensor(configuracion, _cortadora.get_posicion());
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_CORTADORA), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _troqueladora.get_posicion());
		if(num>=0)
			contexto.setDispositivosInternos(configuracion.getPosicionAsociada(NombreMaquinas.SENSOR_TROQUELADORA), false);
		num=-1;
		num=contexto.activaSensor(configuracion, _salBlister.get_posicion());
		if(num>=0)
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
