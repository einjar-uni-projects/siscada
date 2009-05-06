package com.umbrella.scada;

import com.umbrella.scada.view.screen.MainFrameModel;

public class Launch {
	public static boolean debug = false;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length > 0)
			debug = true;
		MainFrameModel.getInstance().initialize();
	}

}
