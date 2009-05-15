package com.umbrella.scada.model;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.umbrella.mail.utils.properties.PropertiesModel;
import com.umbrella.mail.utils.properties.Property;
import com.umbrella.mail.utils.properties.PropertyException;


/**
 * 
 * @author Daniel Conde Garcia
 * @version 1.0
 */
public final class PropertiesFile implements PropertiesModel {
	
	private final Map< String, Property> properties;
	
	private final String prekey = ("com.umbrella.scada.model.");
	
	/* SINGLETON PATTERN */
	private static PropertiesFile INSTANCE = null;

	/**
	 * Gets the unique instance.
	 * @return: PropertiesFile the instance.
	 * @throws PropertyException 
	 */
	public static PropertiesFile getInstance() throws PropertyException {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}

	/* Synchronized builder to prevent multithreads problems */
	private synchronized static void createInstance() throws PropertyException {
		if (INSTANCE == null) {
			INSTANCE = new PropertiesFile();
		}
	}

	/* Private constructor to control the creation */
	private PropertiesFile() throws PropertyException {
		properties = getDefaultProperties();
	}
	/* END SINGLETON PATTERN */

	
	public String getMasterAutIP(){
		return properties.get(prekey+"master_aut_IP").getValue();
	}
	
	
	
	
	public int getMasterAutPort(){
		int ret = -1;
		try{
			ret = Integer.parseInt(properties.get(prekey+"master_aut_port").getValue());
		}catch(NumberFormatException nfe){
			
		}
		return ret;
	}

	@Override
	public Iterator<String>  getMapKeys() {
		return properties.keySet().iterator();
	}

	@Override
	public void setMapValues(Map<String, String> map) {
		Set<String> set = map.keySet();
		for (String key : set) {
			setKeyValue(key,map.get(key));
		}
	}

	@Override
	public Map<String, Property> getDefaultProperties() throws PropertyException {
		Map< String, Property> properties = new Hashtable<String, Property>();
		
		Property master_aut_IP = new Property(" IP del automataMaestro,",prekey+"master_aut_IP","localhost");
		Property master_aut_port = new Property(" Puerto de comunicacion para los buzones",prekey+"master_aut_port","9003");

		
		properties.put(master_aut_IP.getKey(), master_aut_IP);
		properties.put(master_aut_port.getKey(), master_aut_port);
		return properties;
	}

	@Override
	public void setKeyValue(String key, String value) {
		Property p = properties.get(key);
		if(p != null)
			p.setValue(value);
		
	}

	@Override
	public String getKeyComment(String key) {
		String ret = null;
		Property p = properties.get(key);
		if(p != null)
			ret = p.getComment();
		return ret;
	}
}