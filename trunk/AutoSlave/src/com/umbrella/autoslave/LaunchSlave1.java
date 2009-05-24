package com.umbrella.autoslave;

import com.umbrella.autoslave.logic.Slave1;
import com.umbrella.mail.utils.properties.PropertyException;

public class LaunchSlave1 {
	public static boolean debug = false;
	
	/**
	 * @param args
	 * @throws PropertyException 
	 */
	public static void main(String[] args) throws Exception {
		if(args.length > 0)
			debug = true;
		
		Slave1 slave=new Slave1();
		slave.execute();
	}
}
