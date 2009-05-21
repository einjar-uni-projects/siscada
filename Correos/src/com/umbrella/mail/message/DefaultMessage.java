package com.umbrella.mail.message;

import java.util.Vector;

public class DefaultMessage implements MessageInterface{

	OntologiaMSG _identificador;
	Object _objeto;
	Vector<String> _parametros = new Vector<String>();
	
	@Override
	public OntologiaMSG getIdentificador() {
		// TODO Auto-generated method stub
		return _identificador;
	}
	@Override
	public Object getObject() {
		// TODO Auto-generated method stub
		return _objeto;
	}
	@Override
	public Vector<String> getParametros() {
		// TODO Auto-generated method stub
		return _parametros;
	}
	@Override
	public void setIdentificador(OntologiaMSG identificador) {
		// TODO Auto-generated method stub
		this._identificador=identificador;
	}
	@Override
	public void setObject(Object objeto) {
		// TODO Auto-generated method stub
		this._objeto=objeto;
	}
	
	@Override
	public String toString() {
		String ret = _identificador+" ("+_objeto+") [";
		if(_parametros != null){
			for (String str : _parametros) {
				if(str != null)
					ret += " " +str;
			}
		}
		ret += " ]";
		return ret;
	}
	
	
}
