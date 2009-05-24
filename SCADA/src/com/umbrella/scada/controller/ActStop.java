package com.umbrella.scada.controller;

import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.scada.model.Postmaster;

public class ActStop implements Action {

	@Override
	public ActionResult execute() {
		DefaultMessage dm = new DefaultMessage();
		dm.setIdentifier(MSGOntology.PARADA);
		ActionResult ret = ActionResult.EXECUTE_FAIL;
		try {
			Postmaster.getInstance().sendMessage(dm);
			ret = ActionResult.EXECUTE_CORRECT;
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		// TODO Auto-generated method stub
		return true;
	}

}
