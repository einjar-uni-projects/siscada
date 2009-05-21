package com.umbrella.mail.utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author Daniel Conde Garcia
 * @version 1.0.0
 */
public class PropertiesFileHandler {
	private final static String CONFIG_FOLDER = "./lib/";
	private final static String CONFIG_FILE = "./config.properties";
	private File file;
	private final Properties pc = new Properties();

	/* SINGLETON PATTERN */
	private static PropertiesFileHandler INSTANCE = null;

	/**
	 * Gets the unique instance.
	 * 
	 * @return: PropertiesFileHandler the instance.
	 */
	public static PropertiesFileHandler getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}

	/* Synchronized builder to prevent multithreads problems */
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PropertiesFileHandler();
		}
	}

	/* Private constructor to control the creation */
	private PropertiesFileHandler() {
		existFile();
		loadFile();
	}

	/* END SINGLETON PATTERN */

	private void existFile() {
		File g = new File(CONFIG_FOLDER);
		/*TODO*/ System.out.println(g.getAbsolutePath());/**/
		/*TODO*/ System.out.println(g.exists());/**/
		if(g.exists())
			file= new File(CONFIG_FOLDER+CONFIG_FILE);
		else
			file= new File(CONFIG_FILE);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private synchronized void loadFile() {
		try {
			//TODO System.out.println(file.getAbsolutePath());
			FileInputStream f = new FileInputStream(file.getAbsolutePath());
			pc.load(f);
			f.close();
			String list = pc.toString().replaceAll(",", "\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void LoadValuesOnModel(PropertiesModel model) throws PropertyException {
		Map<String, Property> def = model.getDefaultProperties();
		Iterator<String> it = model.getMapKeys();
		String key;
		Property p;
		while (it.hasNext()) {
			key = it.next();
			if (pc.containsKey(key)) {
				model.setKeyValue(key, pc.getProperty(key));
			} else {
				p = def.get(key);
				pc.setProperty(key, obtainValue(p));
				model.setKeyValue(key, pc.getProperty(key));
			}
		}
	}
	
	private String obtainValue(Property value) {
		// TODO pedir valor por interfaz grafica
		return value.getValue();
	}

	public synchronized void writeFile() {
		try {
			FileOutputStream w = new FileOutputStream(file.getAbsolutePath());
			pc.store(w, null);
			w.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
