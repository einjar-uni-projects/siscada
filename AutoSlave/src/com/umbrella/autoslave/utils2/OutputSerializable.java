package com.umbrella.autoslave.utils2;

import java.io.*;

import com.umbrella.autocommon.Configuracion;
import com.umbrella.autoslave.logic.ConfiguracionMaestro;
import com.umbrella.autoslave.logic.Contexto;
import com.umbrella.autoslave.logic.ContextoMaestro;
import com.umbrella.autoslave.logic.ContextoRobot;

public class OutputSerializable {
	private FileOutputStream _file;
	private ObjectOutputStream _output;
	
	public OutputSerializable(Object tipo) {
		if (tipo instanceof Configuracion) {
			try {
				this.openConfiguracion();
			}
			catch(IOException ioe) {
				System.out.println("ERROR AL ESCRIBIR EL FICHERO DE CONFIGURACION");
			} 
		}
		else if (tipo instanceof ConfiguracionMaestro) {
			try {
				this.openConfiguracionMaestro();
			}
			catch(IOException ioe) {
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
		_file = new FileOutputStream("configuracion.ser");
		_output = new ObjectOutputStream(_file);
		return true;
	}
	
	public boolean openConfiguracionMaestro() throws IOException {
		_file = new FileOutputStream("configuracionMaestro.ser");
		_output = new ObjectOutputStream(_file);
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
