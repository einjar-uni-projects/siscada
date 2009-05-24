package com.umbrella.mail.message;

import java.util.Vector;

public class DefaultMessage implements MessageInterface{

	MSGOntology _identifier;
	Object _object;
	Vector<String> _parameters = new Vector<String>();
	
	@Override
	public MSGOntology getIdentifier() {
		// TODO Auto-generated method stub
		return _identifier;
	}
	@Override
	public Object getObject() {
		// TODO Auto-generated method stub
		return _object;
	}
	@Override
	public Vector<String> getParameters() {
		// TODO Auto-generated method stub
		return _parameters;
	}
	@Override
	public void setIdentifier(MSGOntology identifier) {
		// TODO Auto-generated method stub
		this._identifier=identifier;
	}
	@Override
	public void setObject(Object object) {
		// TODO Auto-generated method stub
		this._object=object;
	}
	
	@Override
	public String toString() {
		String ret = _identifier+" ("+_object+") [";
		if(_parameters != null){
			for (String str : _parameters) {
				if(str != null)
					ret += " " +str;
			}
		}
		ret += " ]";
		return ret;
	}
	
	
}
