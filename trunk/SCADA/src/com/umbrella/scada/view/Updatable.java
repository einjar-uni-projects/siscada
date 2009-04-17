package com.umbrella.scada.view;

import com.umbrella.scada.observer.TransferBuffer;

public interface Updatable {
	
	public void update(TransferBuffer buffer);

}
