package com.umbrella.scada;

import com.umbrella.scada.view.screen.MainFrame;
import com.umbrella.scada.view.screen.MainFrameModel;

public class Launch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainFrameModel.getInstance().initialize();
	}

}
