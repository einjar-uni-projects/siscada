package com.umbrella.scada.controller;

import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.scada.model.Model;
import com.umbrella.scada.model.Postmaster;

public class ActEmergencyStop implements Action {

	@Override
	public ActionResult execute() {
		Model m = Model.getInstance();
		DefaultMessage dm = new DefaultMessage();
		dm.setIdentifier(MSGOntology.PARADAEMERGENCIA);
		ActionResult ret = ActionResult.EXECUTE_FAIL;
		try {
			Postmaster.getInstance().sendMessage(dm);
			ret = ActionResult.EXECUTE_CORRECT;
			m.set_numGoodPackages(0);
			m.set_numBadPackages(0);
		} catch (PropertyException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		return true;
	}

}
