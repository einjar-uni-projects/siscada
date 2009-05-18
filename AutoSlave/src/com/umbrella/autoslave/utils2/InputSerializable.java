package com.umbrella.autoslave.utils2;

import java.io.*;

import com.umbrella.autocommon.Configuracion;
import com.umbrella.autoslave.logic.ConfiguracionMaestro;

public class InputSerializable {
	private FileInputStream _file;
	private ObjectInputStream _input;
	
	public void openConfiguracion()  {
		try {
			_file = new FileInputStream("configuracion.ser");
		} catch (FileNotFoundException e) {
			System.out.println("NO SE ENCUENTRA EL FICHERO CONFIGURACION PARA LEER");
		}
		try {
			_input = new ObjectInputStream(_file);
		} catch (IOException e) {
			System.out.println("ERROR EN LA LECTURA DEL FICHERO CONFIGURACION");
		}
	}
	
	public void openConfiguracionMaestro() {
		try {
			_file = new FileInputStream("configuracionMaestro.ser");
		} catch (FileNotFoundException e) {
			System.out.println("NO SE ENCUENTRA EL FICHERO CONFIGURACION MAESTRO PARA LEER");
		}
		try {
			_input = new ObjectInputStream(_file);
		} catch (IOException e) {
			System.out.println("ERROR EN LA LECTURA DEL FICHERO CONFIGURACION MAESTRO");
		}
	}

	public void close() {
		if (_input != null) {
			try {
				_input.close();
			} catch (IOException e) {
				System.out.println("ERROR AL CERRAR EL FLUJO DE LECTURA");
			}
		}
	}
	
	public Configuracion readConfiguration() {
		Configuracion conf = null;
		if (_input!=null) {
			try {
				conf = (Configuracion)_input.readObject();
			} catch (IOException e) {
				System.out.println("ERROR AL LEER EL FICHERO CONFIGURACION");
			} catch (ClassNotFoundException e) {
				System.out.println("CLASE FICHERO CONFIGURACION NO ENCONTRADA");
			}
		}
		return conf;
	}

	public ConfiguracionMaestro readMasterConfiguration() {
		ConfiguracionMaestro conf = null;
		if (_input!=null) {
			try {
				conf = (ConfiguracionMaestro)_input.readObject();
			} catch (IOException e) {
				System.out.println("ERROR AL LEER EL FICHERO CONFIGURACION MAESTRO");
			} catch (ClassNotFoundException e) {
				System.out.println("CLASE FICHERO CONFIGURACION MAESTRO NO ENCONTRADA");
			}
		}
		return conf;
	}
}
