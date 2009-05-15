package com.umbrella.mail.utils.properties;


/**
 * 
 * @author Daniel Conde Garcia
 * @version 1.0.0
 */
public class Property {
	public final static char COMENTLINE = '#';
	private final String comment;
	private final String key;
	private String value;
	
	public Property(String comment, String key, String default_value) throws PropertyException{
		if(comment == null || key == null || key.length() == 0 || default_value == null)
			throw new PropertyException("Error de parametros ningún campo puede ser null.\n Si no se desea asignar valor usar cadena vacia.");
		
		StringBuilder fcomment = new StringBuilder(comment.length()+1);
		fcomment.append(COMENTLINE).append(comment);
		this.comment = fcomment.toString();
		this.key = key;
		this.value = default_value;
	}

	/**
	 * Obtiene el valor del property.
	 * @return String con el valor o cadena vacia si no hay valor almacenado.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Inserta el valor al property; 
	 * @param value Cadena con el valor que se desea insertar para el property.
	 */
	public void setValue(String value) {
		if(value == null)
			this.value = "";
		else
			this.value = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 
	 * @return
	 */
	public String getKey() {
		return key;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(comment+"\n");
		sb.append(key);
		sb.append("=");
		sb.append(value+"\n");
		return sb.toString();
	}
}
