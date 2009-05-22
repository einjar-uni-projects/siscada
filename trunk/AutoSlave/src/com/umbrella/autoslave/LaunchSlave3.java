package com.umbrella.autoslave;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

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
