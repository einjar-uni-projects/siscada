package com.umbrella.scada.view.screen;

import java.awt.BorderLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import com.umbrella.scada.view.localization.LocalizationResources;

public class ReportViewer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextArea reportArea = null;
	private JScrollPane scrollPanel = null;
	private JToolBar toolBar = null;
	private JButton fileButton = null;
	/**
	 * This is the default constructor
	 */
	public ReportViewer() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 450);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("ReportsViewer");
		setReportAreaText();
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
			jContentPane.add(getScrollPanel(), BorderLayout.CENTER);
			jContentPane.add(getToolBar(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes reportArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getReportArea() {
		if (reportArea == null) {
			reportArea = new JTextArea();
		}
		return reportArea;
	}

	/**
	 * This method initializes scrollPanel	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollPanel() {
		if (scrollPanel == null) {
			scrollPanel = new JScrollPane();
			scrollPanel.setViewportView(getReportArea());
		}
		return scrollPanel;
	}

	/**
	 * This method initializes toolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.add(getFileButton());
		}
		return toolBar;
	}

	/**
	 * This method initializes fileButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getFileButton() {
		if (fileButton == null) {
			fileButton = new JButton();
			fileButton.setText("Save");
			fileButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					openFileDialog();
				}
			});
		}
		return fileButton;
	}
	
	private void openFileDialog(){
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showSaveDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	try {
				PrintWriter pw = new PrintWriter(new FileWriter(chooser.getSelectedFile()));
				pw.println(reportArea.getText());
				pw.close();
			} catch (IOException e) {
				System.err.println("Imposible guardar fichero");
			}
	    }
	}
	
	private void setReportAreaText() {
		MainFrameModel model = MainFrameModel.getInstance();
		String text;
		if(model.get_selectedLanguage() == LocalizationResources.LanguageIDs.SPANISHLOCALE)
			text = "INFORME NÚMERO DE PRODUCTOS\n\tParciales:\n\t\tCorrectos: "+model.get_goodPackages()+"\n\t\tIncorrectos: "+model.get_badPackages()+"\n\tTotales:\n\t\tCorrectos: "+model.get_goodPackagesTotal()+"\n\t\tIncorrectos: "+model.get_badPackagesTotal()+"\n\nINFORMES DE OPERACIONES\n\tArranques: "+model.get_numStarts()+"\n\tParadas: "+model.get_numStops()+"\n\tParadas de emergencia: "+model.get_numEmergencyStops();
		else
			text = "NUMBER OF PRODUCTS REPORT\n\tParcial:\n\t\tCorrect: "+model.get_goodPackages()+"\n\t\tWrong: "+model.get_badPackages()+"\n\tTotal:\n\t\tCorrect: "+model.get_goodPackagesTotal()+"\n\t\tWrong: "+model.get_badPackagesTotal()+"\n\nOPERATION REPORTS\n\tStarts: "+model.get_numStarts()+"\n\tStops: "+model.get_numStops()+"\n\tEmergency Stops: "+model.get_numEmergencyStops();
		reportArea.setText(text);
	}

}
