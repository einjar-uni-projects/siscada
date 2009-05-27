package com.umbrella.scada.view.screen;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JPanel;

/**
 * Clase encargada de cargar las imágenes de la interfaz del sistema y prepararlas para imprimir
 * Ofrece los métodos necesarios para obtener estas imágenes de cara a dibujarlas
 * @author Umbrella.Soft
 * @version 1.0
 */
public class ImageLoader {
	
	/*
	 * Imágenes almacenadas
	 */
	private Image _expendedora;
	private Image _expendedoraChoc;
	private Image _expendedoraCaram;
	private Image _estados;
	private Image _estadosRojo;
	private Image _estadosAmarillo;
	private Image _estadosVerde;
	private Image _masa;
	private Image _masaChoc;
	private Image _masaCaram;
	private Image _cinta1;
	private Image _cinta2;
	private Image _blister1Tarta;
	private Image _blister2Tarta;
	private Image _blister3Tarta;
	private Image _blister4Tarta;
	private Image _blisterBruto;
	private Image _blisterCortado;
	private Image _blisterEstampado;
	private Image _robot;
	private Image _robotTarta;
	private Image _robotBlister;
	private Image _robotTartaDef;
	private Image _robotTartaCompleta;
	private Image _cortadora;
	private Image _estampadora;
	private Image _calidadfg;
	private Image _calidadbk;
	private Image _selladora;
	private Image _table;
	
	/*
	 * Valor auxiliar para el MediaTracker encargado de esperar por la carga de las imágenes
	 */
	private int _auxNumIncremental;
	
	/**
	 * Crea una instancia del cargador de imágenes e inicia la carga de las imágenes del sistema
	 * @param panel Panel que utilizará las imagenes
	 */
	public ImageLoader(JPanel panel){
		MediaTracker tracker = new MediaTracker(panel);
		Toolkit toolkit = panel.getToolkit();
		
		_expendedora = toolkit.getImage("resources/expendedora.png");
		_expendedoraChoc = toolkit.getImage("resources/expendedoraChocolate.png");
		_expendedoraCaram = toolkit.getImage("resources/expendedoraCaramelo.png");
		_estados = toolkit.getImage("resources/estados.png");
		_estadosRojo = toolkit.getImage("resources/estadosRojo.png");
		_estadosAmarillo = toolkit.getImage("resources/estadosAmarillo.png");
		_estadosVerde = toolkit.getImage("resources/estadosVerde.png");
		_masa = toolkit.getImage("resources/masa.png");
		_masaChoc = toolkit.getImage("resources/masaChoc.png");
		_masaCaram = toolkit.getImage("resources/masaCaramelo.png");
		_cinta1 = toolkit.getImage("resources/cinta1.png");
		_cinta2 = toolkit.getImage("resources/cinta2.png");
		_blister1Tarta = toolkit.getImage("resources/blister1Tarta.png");
		_blister2Tarta = toolkit.getImage("resources/blister2Tarta.png");
		_blister3Tarta = toolkit.getImage("resources/blister3Tarta.png");
		_blister4Tarta = toolkit.getImage("resources/blister4Tarta.png");
		_blisterBruto = toolkit.getImage("resources/blisterBruto.png");
		_blisterCortado = toolkit.getImage("resources/blisterCortado.png");
		_blisterEstampado = toolkit.getImage("resources/blisterEstampado.png");
		_robot = toolkit.getImage("resources/robot.png");
		_robotTarta = toolkit.getImage("resources/robotTarta.png");
		_robotBlister = toolkit.getImage("resources/robotBlister.png");
		_robotTartaDef = toolkit.getImage("resources/robotTartaDef.png");
		_robotTartaCompleta = toolkit.getImage("resources/robotTartaCompleta.png");
		_cortadora = toolkit.getImage("resources/cortadora.png");
		_estampadora = toolkit.getImage("resources/estampadora.png");
		_calidadfg = toolkit.getImage("resources/calidad2.png");
		_calidadbk = toolkit.getImage("resources/calidad1.png");
		_selladora = toolkit.getImage("resources/selladora.png");
		_table = toolkit.getImage("resources/mesa.png");
		
		tracker.addImage(_expendedora, getNextNumIncr());
		tracker.addImage(_expendedoraChoc, getNextNumIncr());
		tracker.addImage(_expendedoraCaram, getNextNumIncr());
		tracker.addImage(_estados, getNextNumIncr());
		tracker.addImage(_estadosRojo, getNextNumIncr());
		tracker.addImage(_estadosAmarillo, getNextNumIncr());
		tracker.addImage(_estadosVerde, getNextNumIncr());
		tracker.addImage(_masa, getNextNumIncr());
		tracker.addImage(_masaChoc, getNextNumIncr());
		tracker.addImage(_masaCaram, getNextNumIncr());
		tracker.addImage(_cinta1, getNextNumIncr());
		tracker.addImage(_cinta2, getNextNumIncr());
		tracker.addImage(_blister1Tarta, getNextNumIncr());
		tracker.addImage(_blister2Tarta, getNextNumIncr());
		tracker.addImage(_blister3Tarta, getNextNumIncr());
		tracker.addImage(_blister4Tarta, getNextNumIncr());
		tracker.addImage(_blisterBruto, getNextNumIncr());
		tracker.addImage(_blisterCortado, getNextNumIncr());
		tracker.addImage(_blisterEstampado, getNextNumIncr());
		tracker.addImage(_robot, getNextNumIncr());
		tracker.addImage(_robotTarta, getNextNumIncr());
		tracker.addImage(_robotBlister, getNextNumIncr());
		tracker.addImage(_robotTartaCompleta, getNextNumIncr());
		tracker.addImage(_robotTartaDef, getNextNumIncr());
		tracker.addImage(_cortadora, getNextNumIncr());
		tracker.addImage(_estampadora, getNextNumIncr());
		tracker.addImage(_calidadbk, getNextNumIncr());
		tracker.addImage(_calidadfg, getNextNumIncr());
		tracker.addImage(_selladora, getNextNumIncr());
		tracker.addImage(_table, getNextNumIncr());
		
		try {
			tracker.waitForAll();
			if(tracker.isErrorAny())
				System.err.println("Error al cargar los elementos de imagen");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Incrementa el contador y devuelve el nuevo valor, se utiliza para la carga de las imágenes
	 * @return nuevo valor del contador auxiliar
	 */
	private int getNextNumIncr(){
		return ++_auxNumIncremental;
	}

	/**
	 * Obtiene la imagen de la máquina expendedora
	 * @return imagen de la máquina expendedora
	 */
	public Image get_expendedora() {
		return _expendedora;
	}

	/**
	 * Obtiene la imagen del semaforo de estados apagado
	 * @return imagen del semaforo de estados apagado
	 */
	public Image get_estados() {
		return _estados;
	}

	/**
	 * Obtiene la imagen del semaforo de estados en rojo
	 * @return imagen del semaforo de estados en rojo
	 */
	public Image get_estadosRojo() {
		return _estadosRojo;
	}

	/**
	 * Obtiene la imagen del semaforo de estados en amarillo
	 * @return imagen del semaforo de estados en amarillo
	 */
	public Image get_estadosAmarillo() {
		return _estadosAmarillo;
	}

	/**
	 * Obtiene la imagen del semaforo de estados en verde
	 * @return imagen del semaforo de estados en verde
	 */
	public Image get_estadosVerde() {
		return _estadosVerde;
	}

	/**
	 * Obtiene la imagen de la masa
	 * @return imagen de la masa
	 */
	public Image get_masa() {
		return _masa;
	}

	/**
	 * Obtiene la imagen de la masa con chocolate
	 * @return imagen de la masa con chocolate
	 */
	public Image get_masaChoc() {
		return _masaChoc;
	}

	/**
	 * Obtiene la imagen de la máquina expendedora de chocolate
	 * @return imagen de la máquina expendedora de chocolate
	 */
	public Image get_expendedoraChoc() {
		return _expendedoraChoc;
	}

	/**
	 * Obtiene la imagen de la máquina expendedora de caramelo
	 * @return imagen de la máquina expendedora de caramelo
	 */
	public Image get_expendedoraCaram() {
		return _expendedoraCaram;
	}

	/**
	 * Obtiene la imagen de la cinta en la posición 1
	 * @return imagen de la cinta en la posición 1
	 */
	public Image get_cinta1() {
		return _cinta1;
	}

	/**
	 * Obtiene la imagen de la cinta en la posición 2
	 * @return imagen de la cinta en la posición 2
	 */
	public Image get_cinta2() {
		return _cinta2;
	}

	/**
	 * Obtiene la imagen de la masa con chocolate y caramelo
	 * @return imagen de la masa con chocolate y caramelo
	 */
	public Image get_masaCaram() {
		return _masaCaram;
	}

	/**
	 * Obtiene la imagen de un blister con 1 tarta
	 * @return imagen de un blister con 1 tarta
	 */
	public Image get_blister1Tarta() {
		return _blister1Tarta;
	}

	/**
	 * Obtiene la imagen de un blister con 2 tartas
	 * @return imagen de un blister con 2 tartas
	 */
	public Image get_blister2Tarta() {
		return _blister2Tarta;
	}

	/**
	 * Obtiene la imagen de un blister con 3 tartas
	 * @return imagen de un blister con 3 tartas
	 */
	public Image get_blister3Tarta() {
		return _blister3Tarta;
	}

	/**
	 * Obtiene la imagen de un blister con 4 tartas
	 * @return imagen de un blister con 4 tartas
	 */
	public Image get_blister4Tarta() {
		return _blister4Tarta;
	}

	/**
	 * Obtiene la imagen de un blister en bruto
	 * @return imagen de un blister en bruto
	 */
	public Image get_blisterBruto() {
		return _blisterBruto;
	}

	/**
	 * Obtiene la imagen de un blister cortado
	 * @return imagen de un blister cortado
	 */
	public Image get_blisterCortado() {
		return _blisterCortado;
	}

	/**
	 * Obtiene la imagen de un blister estampado
	 * @return imagen de un blister estampado
	 */
	public Image get_blisterEstampado() {
		return _blisterEstampado;
	}

	/**
	 * Obtiene la imagen de un robot
	 * @return imagen de un robot
	 */
	public Image get_robot() {
		return _robot;
	}

	/**
	 * Obtiene la imagen de un robot trasladando una tarta
	 * @return imagen de un robot trasladando una tarta
	 */
	public Image get_robotTarta() {
		return _robotTarta;
	}

	/**
	 * Obtiene la imagen de un robot trasladando un blister
	 * @return imagen de un robot trasladando un blister
	 */
	public Image get_robotBlister() {
		return _robotBlister;
	}

	/**
	 * Obtiene la imagen de un robot trasladando un blister con pasteles
	 * @return imagen de un robot trasladando un blister con pasteles
	 */
	public Image get_robotTartaDef() {
		return _robotTartaDef;
	}

	/**
	 * Obtiene la imagen de la cortadora
	 * @return imagen de la cortadora
	 */
	public Image get_cortadora() {
		return _cortadora;
	}

	/**
	 * Obtiene la imagen de la estampadora
	 * @return imagen de la estampadora
	 */
	public Image get_estampadora() {
		return _estampadora;
	}

	/**
	 * Obtiene la imagen de la verificadora de calidad superior
	 * @return imagen de la verificadora de calidad
	 */
	public Image get_calidadfg() {
		return _calidadfg;
	}
	
	/**
	 * Obtiene la imagen de la verificadora de calidad de fondo
	 * @return imagen de la verificadora de calidad
	 */
	public Image get_calidadbk() {
		return _calidadbk;
	}

	/**
	 * Obtiene la imagen de la selladora
	 * @return imagen de la selladora
	 */
	public Image get_selladora() {
		return _selladora;
	}

	/**
	 * Obtiene la imagen de la mesa
	 * @return imagen de la mesa
	 */
	public Image get_table() {
		return _table;
	}

	public Image get_robotTartaCompleta() {
		return _robotTartaCompleta;
	}
	
}
