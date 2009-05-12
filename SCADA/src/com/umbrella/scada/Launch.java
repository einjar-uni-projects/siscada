package com.umbrella.scada;

import com.umbrella.scada.model.Model;
import com.umbrella.scada.view.screen.MainFrameModel;

public class Launch {
	public static boolean debug = false;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length > 0)
			debug = true;
		new Launch();
	}
	
	public Launch() {
		new Thread(new MainFrameLaunch(),"MainFrameLaunch").start();
		Model.getInstance().test();
	}
	
	
	class MainFrameLaunch implements Runnable{
		@Override
		public void run() {
			MainFrameModel.getInstance().initialize();
		}
	}

}
