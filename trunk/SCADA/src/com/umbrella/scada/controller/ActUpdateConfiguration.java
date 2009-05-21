package com.umbrella.scada.controller;

import com.umbrella.autocommon.Configuration;
import com.umbrella.scada.model.Model;

public class ActUpdateConfiguration implements Action {
	private Configuration _conf;
	@Override
	public ActionResult execute() {		
		ActionResult ret = ActionResult.EXECUTE_CORRECT;
		Model m = Model.getInstance();
		m.set_au1CakeDepot(_conf.getCapacidadPasteles());
		m.set_au1CaramelValveDelay(_conf.getValvCaram());
		m.set_au1ChocolateValveDelay(_conf.getValvChoc());
		m.set_au1ConveyorBeltSize(_conf.getSizeCintaAut1());
		m.set_au1ConveyorBeltSpeed(_conf.getVelCintaAut1());
		
		m.set_au2ConveyorBeltSize(_conf.getSizeCintaAut2());
		m.set_au2ConveyorBeltSpeed(_conf.getVelCintaAut2());
		
		m.set_au3ConveyorBeltSize(_conf.getSizeCintaAut3());
		m.set_au3ConveyorBeltSpeed(_conf.getVelCintaAut3());
		
		m.set_genBlisterSize(_conf.getSizeBlister());
		m.set_genCakeSize(_conf.getSizeBizcocho());
		m.set_genClockTime(_conf.get_tiempoReloj());
		m.set_genRobotInterference(_conf.getInterferencia());
		m.set_genSensorError(_conf.getErrorSensor());
		m.set_rb1BlisterDelay(_conf.getMoverBlister());
		m.set_rb2BlisterDelay(_conf.getMoverBlister());
		m.set_rb1CakeDelay(_conf.getMoverPastel());		
		
		if(ret == ActionResult.EXECUTE_CORRECT)
			m.notifyChanges();
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
