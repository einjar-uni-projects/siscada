package com.umbrella.automaster.logic;

import com.umbrella.mail.message.MessageInterface;

public class MessageInterpreter {
	
	public enum MachineEnum{
		SCADA, AU1, AU2, AU3, RB1, RB2;
	}
	
	public static void executueMessageBehaviour(MessageInterface message, MachineEnum machineEnum){
		switch (message.getIdentificador()) {
		case ARRANCAR:
			
			break;

		default:
			break;
		}
		
	}

}
