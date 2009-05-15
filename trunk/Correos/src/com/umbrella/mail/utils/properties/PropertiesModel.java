package com.umbrella.mail.utils.properties;

import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author Daniel Conde Garcia
 * @version 1.0.0
 */
public interface PropertiesModel{
	
	/**
	 * @return
	 */
	public Iterator<String> getMapKeys();
	
	/**
	 * @param map
	 */
	public void setMapValues(Map<String, String> map);
	
	/**
	 * @param key
	 * @param Value
	 */
	public void setKeyValue(String key, String value);
	
	/**
	 * @return
	 * @throws PropertyException
	 */
	public Map<String, Property> getDefaultProperties() throws PropertyException;
	
	
	/**
	 * @param key
	 * @return
	 */
	public String getKeyComment(String key);
}
