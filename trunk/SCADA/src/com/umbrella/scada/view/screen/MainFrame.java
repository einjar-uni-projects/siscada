package com.umbrella.scada.view.screen;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class MainFrame {

	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="194,58"
	private JPanel jContentPane = null;  //  @jve:decl-index=0:visual-constraint="220,90"

	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(240, 144));
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setSize(new Dimension(133, 80));
		}
		return jContentPane;
	}

}
