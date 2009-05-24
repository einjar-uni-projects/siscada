package com.umbrella.autocommon;

public interface Notifiable {
	public enum NotificableSignal{
		CLOCK_SIGNAL;
	}
	
	/*Debe invocar a un metodo sincronizado*/
	public void notifyNoSyncJoy(NotificableSignal signal);
	public void notifyNoSyncJoy2(String machine);

}
