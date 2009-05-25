package com.umbrella.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.MasterConfiguration;


public class OutputSerializable {
	private FileOutputStream _file;
	private ObjectOutputStream _output;
	
	public OutputSerializable(Object tipo) {
		if (tipo instanceof Configuration) {
			try {
				this.openConfiguracion();
			}
			catch(IOException ioe) {
				System.out.println("ERROR AL ESCRIBIR EL FICHERO DE CONFIGURACION");
			} 
		}
		else if (tipo instanceof MasterConfiguration) {
			try {
				this.openConfiguracionMaestro();
			}
			catch(Exception e) {
				System.out.println("ERROR AL ESCRIBIR EL FICHERO DE CONFIGURACION MAESTRO");
			} 
		}
/*		else if (tipo instanceof Contexto) {
			this.writeContexto(tipo);
		}
		else if (tipo instanceof ContextoMaestro) {
			this.writeContextoMaestro(tipo);
		}
		else if (tipo instanceof ContextoRobot) {
			this.writeContextoRobot(tipo);
		}*/
	}
	
	public boolean openConfiguracion() throws IOException {
		_file = new FileOutputStream(".\\configuracion.ser");
		_output = new ObjectOutputStream(_file);
		return true;
	}
	
	public boolean openConfiguracionMaestro() {
		try {
			_file = new FileOutputStream(".\\configuracionMaestro.ser");
		}
		catch (FileNotFoundException fnfe) {
			// TODO: handle exception
			System.out.println("Fichero no encontrado");
		}
		try {
			_output = new ObjectOutputStream(_file);
		}
		catch (IOException ioe) {
			// TODO: handle exception
			System.out.println("Error de apertura de fichero");
		}
		return true;
	}
	
	public boolean write(Object tipo) {
		if (_output != null) {
			try {
				_output.writeObject(tipo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR AL ESCRIBIR CONFIGURACION");
			}
		}
		return true;

	}

/*	public boolean writeContexto(Object tipo) {
		return true;

	}
	public boolean writeContextoMaestro(Object tipo) {
		return true;

	}
	public boolean writeContextoRobot(Object tipo) {
		return true;

	}
*/
	public boolean close() {
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
