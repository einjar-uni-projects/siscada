package com.umbrella.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.MasterConfiguration;


public class OutputSerializable {
	private FileOutputStream _file;
	private ObjectOutputStream _output;
	
	public boolean openConfiguracion() throws IOException {
		File f = new File("configuracion.ser");
		_file = new FileOutputStream(f);
		_output = new ObjectOutputStream(_file);
		return true;
	}
	
	public boolean openConfiguracionMaestro(String file) {
		System.out.println("Abriendo fichero: "+file);
		
		File f = new File(file);
		if(!f.exists()){
			System.err.println("Se necesita crear configuracionMaestro.ser");
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		try {
			_file = new FileOutputStream(f);	
		}
		catch (FileNotFoundException fnfe) {
			System.err.println("Fichero no encontrado");
		}
		
		try {
			_output = new ObjectOutputStream(_file);
		}
		catch (IOException ioe) {
			System.out.println("Error de apertura de fichero");
		}
		return true;
	}
	
	public boolean write(Object tipo) {
		System.out.println("  Guardando la configuracion del maestro...");
		if (_output != null) {
			try {
				_output.writeObject(tipo);
			} catch (IOException e) {
				
				System.out.println("ERROR AL ESCRIBIR CONFIGURACION");
			}
		}
		return true;

	}
	
	public boolean close() {
		System.out.println("  Cerrando el fichero...\n");
		if (_output != null) {
			try {
				_output.close();
			} catch (IOException e) {
				System.out.println("ERROR AL CERRAR EL FLUJO DE ESCRITURA");
			}
		}
		return true;
	}
}
