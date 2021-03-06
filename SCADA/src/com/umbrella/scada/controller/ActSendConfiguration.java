package com.umbrella.scada.controller;

import com.umbrella.autocommon.Configuration;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.scada.model.Postmaster;

public class ActSendConfiguration implements Action {
	private Configuration _conf;
	@Override
	public ActionResult execute() {
		DefaultMessage dm = new DefaultMessage();
		dm.setIdentifier(MSGOntology.ACTUALIZARCONFIGURACION);
		dm.setObject(_conf);
		ActionResult ret = ActionResult.EXECUTE_FAIL;
		try {
			Postmaster.getInstance().sendMessage(dm);
			ret = ActionResult.EXECUTE_CORRECT;
		} catch (PropertyException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public boolean insertParam(ActionParams params) {
		boolean ret = false;
		ActionParamsEnum ape = ActionParamsEnum.CONFIGURATION;
		Configuration conf = (Configuration) params.getParam(ape);
		if (conf != null && conf.isCorrect()) {
			_conf = conf;
			ret = true;
		}
		return ret;
	}

}
