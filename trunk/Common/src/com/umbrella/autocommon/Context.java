package com.umbrella.autocommon;

/*
 * Author: pablo Jos� Izquierdo Escudero
 * Fecha: 19/04/2009
 * 
 * Objetivo: contiene la informacion del estado del sistema, es toda informacion dinamica y que cambia o puede cambiar en tiempo de ejecucion
 */

import java.io.Serializable;
import java.util.LinkedList;

import com.umbrella.utils.Blister;
import com.umbrella.utils.Cake;


public class Context implements Serializable{

	/*
	 * tipo=pastel o blister
	 */
	private String tipo;
	
	private LinkedList<Cake> _listaPasteles;
	private LinkedList<Blister> _listaBlister;
	
	private boolean apagado=true;
	private boolean paradaCorrecta=false; //sirve para hacer bien la parada
	
	
	/*
	 * indica los sensores que estan activos, o los dispositivos activados, es el estado interno del automata 
	 */
	private boolean [] dispositivosInternos= new boolean[16];

	/*
	 * apagado deja el automata apagado pero esto lo deja en standby
	 * FIN acaba la ejecucion completamente
	 */
	private boolean FIN=false;

	private boolean[] estadoAnterior=new boolean[16];
	
	
	private boolean interferencia=false;
	
	//Configuracion configuracion=Configuracion.getInstance();
	
	private int pastelesRestantes = 50;
	private boolean blisterListoInicioCinta3=false;
	
	private int capacidadCaramelo = 50;
	private int capacidadChocolate = 50;
	
	private static Context INSTANCE = null;
	
	private boolean fallo=false;
	
	/*
	 * numero de pasteles en la cinta, no sirve para nada tecnicamente solo da informacion
	 */
	private int numPasteles=50;
	
	private int contadorAutomata1[]= new int[7];
	private int contadorAutomata2[]= new int[5];
	private int contadorAutomata3[]= new int[6];
	
    private Context(String tipo) {
    	
    	for(int i=0;i<dispositivosInternos.length;i++){
    		dispositivosInternos[i]=false;
    		estadoAnterior[i]=false;
    	}
    	this.tipo=tipo;
    	if(tipo.equalsIgnoreCase("pastel"))
    		_listaPasteles=new LinkedList<Cake>();
    	else if(tipo.equalsIgnoreCase("blister"))
    		_listaBlister=new LinkedList<Blister>();
    	else
    		System.err.println("ese valor no es valido, solo se admite 'pastel' o 'blister'.");
    	
    	for(int i=0;i<contadorAutomata1.length;i++){
    		contadorAutomata1[i]=0;
    	}
    }
 
    /*
     *  creador sincronizado para protegerse de posibles problemas  multi-hilo
     *  otra prueba para evitar instanciaci�n m�ltiple
     */ 
    
    private synchronized static void createInstance(String tipo) {
        if (INSTANCE == null) { 
            INSTANCE = new Context(tipo);
        }
    }
    
    public static Context getInstance(String tipo) {
        if (INSTANCE == null) createInstance(tipo);
        return INSTANCE;
    }
    
    public static Context getInstance() {
        return INSTANCE;
    }
    
    /*
 	public void setState( Estado state ){
 		this.estado = state;
 	}
 
 	public Estado getState(){
 		return estado;
 	}
	*/
	public synchronized boolean[] getEstadoAnterior() {
		return estadoAnterior;
	}
	
	public boolean getEstadoAnterior (int pos){
		return estadoAnterior[pos];
	}
	
	public synchronized void setEstadoAnterior(int pos, boolean valor) {
		estadoAnterior[pos] = valor;
	}
	
	public synchronized boolean isFIN() {
		return FIN;
	}

	private synchronized int getNumPasteles() {
		return numPasteles;
	}

	private synchronized void setNumPasteles(int numPasteles) {
		this.numPasteles=numPasteles;
	}
	
	public synchronized void decrementarNumPasteles() {
		this.numPasteles--;
	}
 	
	public synchronized void incrementarNumPasteles() {
		this.numPasteles++;
	}
	
	public synchronized void setDispositivosInternos(int pos, boolean valor){
		dispositivosInternos[pos]=valor;
	}
	public synchronized boolean[] getDispositivosInternos(){
		return dispositivosInternos;
	}
	public synchronized boolean getDispositivosInternos(int pos){
		return dispositivosInternos[pos];
	}

	/*
	 * devuelve el numero de pastel q activa el sensor q tiene la posicion pasada,
	 *  -1 si no hay coincidencia
	 */
	public synchronized int activaSensor(Configuration configuracion, double posicion){
		//false = pasteles, true = blister
		int sal=-1;	
		if(tipo.equalsIgnoreCase("blister")){
			for(int i=0;i<_listaBlister.size();i++){
				if(_listaBlister.get(i).get_posicion()<(posicion+configuracion.getSizeBlister()/2) || 
					_listaBlister.get(i).get_posicion()>(posicion-configuracion.getErrorSensor()/2) )
					sal=i;
			}
		}else{
			for(int i=0;i<_listaPasteles.size();i++){
				if(_listaPasteles.get(i).get_posicion()<(posicion+configuracion.getSizeBizcocho()/2) || 
					_listaPasteles.get(i).get_posicion()>(posicion-configuracion.getSizeBizcocho()/2) )
					sal=i;
			}
		}
		return sal;
	}
	public synchronized String getTipo() {
		return tipo;
	}

	public synchronized LinkedList<Cake> get_listaPasteles() {
		return _listaPasteles;
	}

	public synchronized void addListaPastel(Cake cake){
		_listaPasteles.add(cake);
	}
	public synchronized void removeListaPastel(int cake){
		_listaPasteles.remove(cake);
	}
	
	public synchronized LinkedList<Blister> get_listaBlister() {
		return _listaBlister;
	}
	
	public synchronized void addListaBlister(Blister blister){
		_listaBlister.add(blister);
	}

	public synchronized void removeListaBlister(int blister){
		_listaBlister.remove(blister);
	}
	public synchronized boolean isInterferencia() {
		return interferencia;
	}

	public synchronized void setInterferencia(boolean interferencia) {
		this.interferencia = interferencia;
	}

	public synchronized int getPastelesRestantes() {
		return pastelesRestantes;
	}

	public synchronized void setRemainderCakes(int pastelesRestantes) {
		this.pastelesRestantes = pastelesRestantes;
	}

	public synchronized boolean isBlisterListoInicioCinta3() {
		return blisterListoInicioCinta3;
	}

	public synchronized void setBlisterListoInicioCinta3(
			boolean blisterListoInicioCinta3) {
		this.blisterListoInicioCinta3 = blisterListoInicioCinta3;
	}

	public synchronized boolean isFallo() {
		return fallo;
	}

	public synchronized void setFallo(boolean fallo) {
		this.fallo = fallo;
	}

	public synchronized boolean isApagado() {
		return apagado;
	}

	public synchronized void setApagado(boolean apagado) {
		this.apagado = apagado;
	}
	
	public synchronized static Context reset(String tipo) {
		INSTANCE = new Context(tipo);
		return INSTANCE;
	}

	public synchronized boolean isParadaCorrecta() {
		return paradaCorrecta;
	}

	public synchronized void setParadaCorrecta(boolean paradaCorrecta) {
		this.paradaCorrecta = paradaCorrecta;
	}
	
	
	public synchronized int getCapacidadCaramelo() {
		return capacidadCaramelo;
	}

	public synchronized int getCapacidadChocolate() {
		return capacidadChocolate;
	}

	public synchronized void rellenarCaramelo(int cantidad, int max){
		capacidadCaramelo+=cantidad;
		if(capacidadCaramelo>max) capacidadCaramelo=max;
	}
	public synchronized void decrementarCaramelo(){
		capacidadCaramelo--;
	}
	public synchronized void rellenarChocolate(int cantidad, int max){
		capacidadChocolate+=cantidad;
		if(capacidadChocolate>max) capacidadChocolate=max;
	}
	public synchronized void decrementarChocolate(){
		capacidadChocolate--;
	}
	
	public void incrementarContadorAutomata1(int pos){
		contadorAutomata1[pos]++;
	}
	public void decrementarContadorAutomata1(int pos){
		contadorAutomata1[pos]--;
	}
	public void valorContadorAutomata1(int pos, int valor){
		contadorAutomata1[pos]=valor;
	}
	public void resetContadorAutomata1(){
		for(int i=0;i<contadorAutomata1.length;i++) contadorAutomata1[i]=0;
	}
	public synchronized int[] getContadorAutomata1(){
		return contadorAutomata1;
	}

	public void incrementarContadorAutomata2(int pos){
		contadorAutomata2[pos]++;
	}
	public void decrementarContadorAutomata2(int pos){
		contadorAutomata2[pos]--;
	}
	public void valorContadorAutomata2(int pos, int valor){
		contadorAutomata2[pos]=valor;
	}
	public void resetContadorAutomata2(){
		for(int i=0;i<contadorAutomata2.length;i++) contadorAutomata2[i]=0;
	}
	public synchronized int[] getContadorAutomata2(){
		return contadorAutomata2;
	}

	public void incrementarContadorAutomata3(int pos){
		contadorAutomata3[pos]++;
	}
	public void decrementarContadorAutomata3(int pos){
		contadorAutomata3[pos]--;
	}
	public void valorContadorAutomata3(int pos, int valor){
		contadorAutomata3[pos]=valor;
	}
	public void resetContadorAutomata3(){
		for(int i=0;i<contadorAutomata3.length;i++) contadorAutomata3[i]=0;
	}
	public synchronized int[] getContadorAutomata3(){
		return contadorAutomata3;
	}
}
