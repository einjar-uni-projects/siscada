package com.umbrella.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.MasterConfiguration;

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
	
	public void openConfiguracionMaestro(String file){
		System.out.println("Abriendo fichero: "+file);
		try {
			_file = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			System.out.println("NO SE ENCUENTRA EL FICHERO CONFIGURACION MAESTRO PARA LEER");
		}
		try {
			_input = new ObjectInputStream(_file);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERROR EN LA LECTURA DEL FICHERO CONFIGURACION MAESTRO");
		}
	}

	public void close() {
		System.out.println("  Cerrando el fichero...\n");
		if (_input != null) {
			try {
				_input.close();
			} catch (IOException e) {
				System.out.println("ERROR AL CERRAR EL FLUJO DE LECTURA");
			}
		}
	}
	
	public Configuration readConfiguration() {
		Configuration conf = null;
		if (_input!=null) {
			try {
				conf = (Configuration)_input.readObject();
			} catch (IOException e) {
				System.out.println("ERROR AL LEER EL FICHERO CONFIGURACION");
			} catch (ClassNotFoundException e) {
				System.out.println("CLASE FICHERO CONFIGURACION NO ENCONTRADA");
			}
		}
		return conf;
	}

	public MasterConfiguration readMasterConfiguration() {
		System.out.println("  Leyendo configuracion del maestro...");
		MasterConfiguration conf = null;
		if (_input!=null) {
			try {
				conf = (MasterConfiguration)_input.readObject();
			} catch (IOException e) {
				System.out.println("ERROR AL LEER EL FICHERO CONFIGURACION MAESTRO");
			} catch (ClassNotFoundException e) {
				System.out.println("CLASE FICHERO CONFIGURACION MAESTRO NO ENCONTRADA");
			}
		}
		return conf;
	}
}
