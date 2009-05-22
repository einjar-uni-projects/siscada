package com.umbrella.autoslave;


import com.umbrella.autoslave.logic.Robot1;
import com.umbrella.mail.utils.properties.PropertyException;

public class LaunchRobot1 {
	public static boolean debug = false;
	
	/**
	 * @param args
	 * @throws PropertyException 
	 */
	public static void main(String[] args) throws Exception {
		if(args.length > 0)
			debug = true;
		
		Robot1 robot=new Robot1();
		robot.execute();
	}
}
