package com.umbrella.autoslave;

import com.umbrella.autoslave.logic.Slave3;
import com.umbrella.mail.utils.properties.PropertyException;

public class LaunchSlave3 {
	public static boolean debug = false;
	
	/**
	 * @param args
	 * @throws PropertyException 
	 */
	public static void main(String[] args) throws Exception {
		if(args.length > 0)
			debug = true;
		
		Slave3 slave=new Slave3();
		slave.execute();
	}
}
