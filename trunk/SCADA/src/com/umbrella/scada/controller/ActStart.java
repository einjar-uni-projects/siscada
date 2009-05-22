package com.umbrella.scada.controller;

import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.OntologiaMSG;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.scada.model.Postmaster;

public class ActStart implements Action {

	@Override
	public ActionResult execute() {
		DefaultMessage dm = new DefaultMessage();
		dm.setIdentificador(OntologiaMSG.START);
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
