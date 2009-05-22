package com.umbrella.autoslave;


import com.umbrella.autoslave.logic.Robot2;
import com.umbrella.mail.utils.properties.PropertyException;

public class LaunchRobot2 {
	public static boolean debug = false;
	
	/**
	 * @param args
	 * @throws PropertyException 
	 */
	public static void main(String[] args) throws Exception {
		if(args.length > 0)
			debug = true;
		
		Robot2 robot=new Robot2();
		robot.execute();
	}
}
