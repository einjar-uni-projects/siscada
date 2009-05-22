package com.umbrella.scada.view.screen;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.umbrella.scada.controller.ActionFactory;
import com.umbrella.scada.controller.ActionFactoryProvider;
import com.umbrella.scada.controller.ActionKey;
import com.umbrella.scada.view.localization.LocalizationResources;
import com.umbrella.scada.view.localization.LocalizatorIDs;
import com.umbrella.scada.view.localization.LocalizationResources.LanguageIDs;
import com.umbrella.scada.view.screen.attributePanels.AttributePanel;
import com.umbrella.scada.view.screen.attributePanels.Automata1AttributePanel;
import com.umbrella.scada.view.screen.attributePanels.Automata2AttributePanel;
import com.umbrella.scada.view.screen.attributePanels.Automata3AttributePanel;
import com.umbrella.scada.view.screen.attributePanels.Robot1AttributePanel;
import com.umbrella.scada.view.screen.attributePanels.Robot2AttributePanel;
import com.umbrella.scada.view.screen.attributePanels.VoidAttributePanel;

public class MainFrame implements UpdatableInterface{

	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="194,58"
	private JPanel jContentPane = null;
	private JPanel _rightPanel = null;
	private JMenuBar _menuBar = null;
	private JMenu _scadaMenu = null;	
	private JMenu _menuOptions = null;
	private JPanel _footerPanel = null;
	private JLabel _footerInfo = null;
	private JButton _initButton = null;
	private JButton _pauseButton = null;
	private JButton _stopButton = null;
	private MainPanel _mainPanel = null;
	private JMenu _language = null;
	
	private static MainFrame _instance;  //  @jve:decl-index=0:
	private MainFrameModel _model = MainFrameModel.getInstance();
	private LocalizationResources _languageResources = LocalizationResources.getInstance();  //  @jve:decl-index=0:
	private JMenuItem _spanishLanguage = null;
	private JMenuItem _englishLanguage = null;
	private CardLayout _rightLayout;  //  @jve:decl-index=0:
	private AttributePanel[] _attributePanels;
	private ActionFactory _actionFactory = ActionFactoryProvider.getInstance();  //  @jve:decl-index=0:
	private JMenuItem _menuReports = null;
	private JMenuItem _menuExit = null;
	
	private long _time1;
	private long _time2;
	
	private AttributePanel _actualAttributePanel;
	
	private MainFrame(){
		getJFrame();
		updateLanguage();
		runThread();
		jFrame.setVisible(true);
	}
	
	public static MainFrame getInstance(){
		if(_instance == null)
			_instance = new MainFrame();
		return _instance;
	}

	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(939, 684));
			jFrame.setTitle("SCADA Interfaz de usuario");
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			jContentPane.add(get_mainPanel(), BorderLayout.CENTER);
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
			_rightLayout = new CardLayout();	
			_rightPanel.setLayout(_rightLayout);
			_rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			_attributePanels = new AttributePanel[6];
			_attributePanels[0] = new VoidAttributePanel();
			_attributePanels[1] = new Automata1AttributePanel();
			_attributePanels[2] = new Automata2AttributePanel();
			_attributePanels[3] = new Automata3AttributePanel();
			_attributePanels[4] = new Robot1AttributePanel();
			_attributePanels[5] = new Robot2AttributePanel();
			
			_rightPanel.add(_attributePanels[0], "0");
			_rightPanel.add(_attributePanels[1], "1");
			_rightPanel.add(_attributePanels[2], "2");
			_rightPanel.add(_attributePanels[3], "3");
			_rightPanel.add(_attributePanels[4], "4");
			_rightPanel.add(_attributePanels[5], "5");
			
			_rightLayout.show(_rightPanel, "0");
			_rightPanel.setPreferredSize(new Dimension(200, 600));
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
		if (_scadaMenu == null) {
			_scadaMenu = new JMenu();
			_scadaMenu.setName("ScadaMenu");
			_scadaMenu.setText("SCADA");
			_scadaMenu.add(get_menuReports());
			_scadaMenu.add(get_menuExit());
		}
		return _scadaMenu;
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
			_menuOptions.setText("Options");
			_menuOptions.add(get_language());
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
			_footerInfo.setText("Estado: Ok");
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
			_initButton.setText("Init");
			_initButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					_mainPanel.startAutRob();
					_actionFactory.executeAction(ActionKey.START, null);
				}
			});
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
			_pauseButton.setText("Pause");
			_pauseButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

				}
			});
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
			_stopButton.setText("Stop");
			_stopButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					_mainPanel.stopAutRob();
					_actionFactory.executeAction(ActionKey.STOP, null);
				}
			});
		}
		return _stopButton;
	}

	/**
	 * This method initializes _mainPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private MainPanel get_mainPanel() {
		if (_mainPanel == null) {
			_mainPanel = new MainPanel(_model);
		}
		return _mainPanel;
	}

	/**
	 * This method initializes _language	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenu get_language() {
		if (_language == null) {
			_language = new JMenu();
			_language.setText("Language");
			_language.add(get_spanishLanguage());
			_language.add(get_englishLanguage());
		}
		return _language;
	}

	/**
	 * This method initializes _spanishLanguage	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem get_spanishLanguage() {
		if (_spanishLanguage == null) {
			_spanishLanguage = new JMenuItem();
			_spanishLanguage.setText("Spanish");
			_spanishLanguage.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					_model.set_selectedLanguage(LanguageIDs.SPANISHLOCALE);
				}
			});
		}
		return _spanishLanguage;
	}

	/**
	 * This method initializes _englishLanguage	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem get_englishLanguage() {
		if (_englishLanguage == null) {
			_englishLanguage = new JMenuItem();
			_englishLanguage.setText("English");
			_englishLanguage.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					_model.set_selectedLanguage(LanguageIDs.ENGLISHLOCALE);
				}
			});
		}
		return _englishLanguage;
	}
	
	public void updateLanguage(){
		_englishLanguage.setText(_languageResources.getLocal(LocalizatorIDs.MENUBAR_EN_LANGUAGE, _model.get_selectedLanguage()));
		_spanishLanguage.setText(_languageResources.getLocal(LocalizatorIDs.MENUBAR_ES_LANGUAGE, _model.get_selectedLanguage()));
		_language.setText(_languageResources.getLocal(LocalizatorIDs.MENUBAR_LANGUAGE, _model.get_selectedLanguage()));
		_stopButton.setText(_languageResources.getLocal(LocalizatorIDs.BUTTON_STOP, _model.get_selectedLanguage()));
		_pauseButton.setText(_languageResources.getLocal(LocalizatorIDs.BUTTON_PAUSE, _model.get_selectedLanguage()));
		_initButton.setText(_languageResources.getLocal(LocalizatorIDs.BUTTON_INIT, _model.get_selectedLanguage()));
		_menuOptions.setText(_languageResources.getLocal(LocalizatorIDs.MENUBAR_OPTIONS, _model.get_selectedLanguage()));
		_scadaMenu.setText(_languageResources.getLocal(LocalizatorIDs.MENUBAR_SCADA, _model.get_selectedLanguage()));
		_menuReports.setText(_languageResources.getLocal(LocalizatorIDs.MENUBAR_REPORTS, _model.get_selectedLanguage()));
		_menuExit.setText(_languageResources.getLocal(LocalizatorIDs.MENUBAR_EXIT, _model.get_selectedLanguage()));
		for(AttributePanel panel : _attributePanels){
			panel.updateLanguage();
		}
	}
	
	public void updateData(){
		
	}
	
	void changeRightCard(int card){
		_actualAttributePanel = _attributePanels[card];
		_actualAttributePanel.refreshData();
		_rightLayout.show(_rightPanel, ""+card);
		Dimension d = _actualAttributePanel.getPreferredSize();
		d.height+=10;
		d.width+=10;
		_rightPanel.setPreferredSize(d);
	}

	/**
	 * This method initializes _menuInforms	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem get_menuReports() {
		if (_menuReports == null) {
			_menuReports = new JMenuItem();
			_menuReports.setText("View Report");
			_menuReports.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new ReportViewer().setVisible(true);
				}
			});
		}
		return _menuReports;
	}

	/**
	 * This method initializes _menuExit	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem get_menuExit() {
		if (_menuExit == null) {
			_menuExit = new JMenuItem();
			_menuExit.setText("Exit");
			_menuExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jFrame.dispose();
				}
			});
		}
		return _menuExit;
	}
	
	private void runThread() {
		new Thread(new RepaintThr(), "ThreadRepaint").start();
	}
	
	private class RepaintThr implements Runnable{
		public void run() {
			while(true){
				_time1 = System.currentTimeMillis();
				_mainPanel.repaint();
				if(_actualAttributePanel != null)
					_actualAttributePanel.refreshData();
				_time2 = System.currentTimeMillis();
				long sleep = 200-(_time2-_time1);
				if(sleep > 0)
					try {
						Thread.sleep(sleep);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}

}
