package com.umbrella.autoslave;


import com.umbrella.autoslave.logic.Slave2;
import com.umbrella.mail.utils.properties.PropertyException;

public class LaunchSlave2 {
	public static boolean debug = false;
	
	/**
	 * @param args
	 * @throws PropertyException 
	 */
	public static void main(String[] args) throws Exception {
		if(args.length > 0)
			debug = true;
		
		Slave2 slave=new Slave2();
		slave.execute();
	}
}
