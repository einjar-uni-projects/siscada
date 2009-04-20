package com.umbrella.scada.view.screen;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import com.umbrella.scada.view.localization.LocalizationResources;
import com.umbrella.scada.view.localization.LocalizatorIDs;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.JButton;

public class MainFrame {

	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="194,58"
	private JPanel jContentPane = null;
	private JPanel _rightPanel = null;
	private JMenuBar _menuBar = null;
	private JMenu _fileMenu = null;
	
	private LocalizationResources _languageResources = LocalizationResources.getInstance();
	private String _selectedLanguage = LocalizationResources.SPANISHLOCALE;
	private JMenu _menuOptions = null;
	private JPanel _footerPanel = null;
	private JLabel _footerInfo = null;
	private JButton _initButton = null;
	private JButton _pauseButton = null;
	private JButton _stopButton = null;

	public MainFrame(){
		getJFrame();
		jFrame.setVisible(true);
	}
	
	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(639, 284));
			jFrame.setJMenuBar(get_menuBar());
			jFrame.setContentPane(getJContentPane());
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
			jContentPane.add(get_rightPanel(), BorderLayout.EAST);
			jContentPane.add(get_footerPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes _rightPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel get_rightPanel() {
		if (_rightPanel == null) {
			_rightPanel = new JPanel();
			_rightPanel.setLayout(new GridBagLayout());
		}
		return _rightPanel;
	}

	/**
	 * This method initializes _menuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar get_menuBar() {
		if (_menuBar == null) {
			_menuBar = new JMenuBar();
			_menuBar.add(get_fileMenu());
			_menuBar.add(get_menuOptions());
		}
		return _menuBar;
	}

	/**
	 * This method initializes _fileMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu get_fileMenu() {
		if (_fileMenu == null) {
			_fileMenu = new JMenu();
			_fileMenu.setName("FileMenu");
			_fileMenu.setText(_languageResources.getLocal(LocalizatorIDs.MENUBAR_FILE, _selectedLanguage));
		}
		return _fileMenu;
	}

	/**
	 * This method initializes _menuOptions	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu get_menuOptions() {
		if (_menuOptions == null) {
			_menuOptions = new JMenu();
			_menuOptions.setName("OptiosMenu");
			_menuOptions.setText(_languageResources.getLocal(LocalizatorIDs.MENUBAR_OPTIONS, _selectedLanguage));
		}
		return _menuOptions;
	}

	/**
	 * This method initializes _footerPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel get_footerPanel() {
		if (_footerPanel == null) {
			_footerPanel = new JPanel();
			_footerPanel.setLayout(new FlowLayout());
			_footerPanel.add(get_footerInfo(), null);
			_footerPanel.add(get_initButton(), null);
			_footerPanel.add(get_pauseButton(), null);
			_footerPanel.add(get_stopButton(), null);
		}
		return _footerPanel;
	}

	/**
	 * This method initializes _footerInfo	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel get_footerInfo() {
		if (_footerInfo == null) {
			_footerInfo = new JLabel();
			_footerInfo.setText("JLabel");
		}
		return _footerInfo;
	}

	/**
	 * This method initializes _initButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton get_initButton() {
		if (_initButton == null) {
			_initButton = new JButton();
		}
		return _initButton;
	}

	/**
	 * This method initializes _pauseButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton get_pauseButton() {
		if (_pauseButton == null) {
			_pauseButton = new JButton();
		}
		return _pauseButton;
	}

	/**
	 * This method initializes _stopButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton get_stopButton() {
		if (_stopButton == null) {
			_stopButton = new JButton();
		}
		return _stopButton;
	}

}
