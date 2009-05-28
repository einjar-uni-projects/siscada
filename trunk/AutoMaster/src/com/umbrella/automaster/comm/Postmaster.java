package com.umbrella.automaster.comm;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import com.umbrella.autocommon.Configuration;
import com.umbrella.autocommon.MasterConfiguration;
import com.umbrella.autocommon.MasterContext;
import com.umbrella.automaster.LaunchAutMaster;
import com.umbrella.automaster.logic.Maestro;
import com.umbrella.automaster.model.PropertiesFile;
import com.umbrella.mail.mailbox.ClientMailBox;
import com.umbrella.mail.mailbox.ServerMailBox;
import com.umbrella.mail.message.DefaultMessage;
import com.umbrella.mail.message.MSGOntology;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.utils.properties.PropertiesFileHandler;
import com.umbrella.mail.utils.properties.PropertyException;

public class Postmaster {

	private final ClientMailBox _SCADAmessageConsulter;
	private final ClientMailBox _RB1messageConsulter;
	private final ClientMailBox _RB2messageConsulter;
	private final ClientMailBox _AU1messageConsulter;
	private final ClientMailBox _AU2messageConsulter;
	private final ClientMailBox _AU3AmessageConsulter;
	private static Postmaster instance = null;
	private boolean _activated;
	private boolean _isSistemStarted = false;
	private Object _mutex = new Object();

	// El constructor privado no permite que se genere un constructor por
	// defecto
	// (con mismo modificador de acceso que la definicion de la clase)
	private Postmaster() throws PropertyException, RemoteException,
			MalformedURLException {
		PropertiesFile pfmodel = PropertiesFile.getInstance();
		PropertiesFileHandler.getInstance().LoadValuesOnModel(pfmodel);
		PropertiesFileHandler.getInstance().writeFile();

		ServerMailBox smb = new ServerMailBox(pfmodel.getMasterAutPort());

		ClientMailBox clientMailBox;
		clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel
				.getMasterAutPort(), ServerMailBox._sendSCADAName,
				ServerMailBox._reciveSCADAName, false);
		_SCADAmessageConsulter = clientMailBox;

		clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel
				.getMasterAutPort(), ServerMailBox._sendR1Name,
				ServerMailBox._reciveR1Name, false);
		_RB1messageConsulter = clientMailBox;

		clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel
				.getMasterAutPort(), ServerMailBox._sendR2Name,
				ServerMailBox._reciveR2Name, false);
		_RB2messageConsulter = clientMailBox;

		clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel
				.getMasterAutPort(), ServerMailBox._sendAU1Name,
				ServerMailBox._reciveAU1Name, false);
		_AU1messageConsulter = clientMailBox;

		clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel
				.getMasterAutPort(), ServerMailBox._sendAU2Name,
				ServerMailBox._reciveAU2Name, false);
		_AU2messageConsulter = clientMailBox;

		clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel
				.getMasterAutPort(), ServerMailBox._sendAU3Name,
				ServerMailBox._reciveAU3Name, false);
		_AU3AmessageConsulter = clientMailBox;
	}

	/**
	 * Obtiene la instancia única del objeto, la primera invocación realiza la
	 * creación del mismo.
	 * 
	 * @return la instancia única de Postmaster
	 * @throws PropertyException
	 * @throws MalformedURLException
	 * @throws RemoteException
	 */
	public static Postmaster getInstance() throws PropertyException,
			RemoteException, MalformedURLException {
		if (instance == null) {
			return getSyncInstance();
		}
		return instance;
	}

	private static synchronized Postmaster getSyncInstance()
			throws PropertyException, RemoteException, MalformedURLException {
		if (instance == null)
			instance = new Postmaster();
		return instance;
	}

	private boolean send(MessageInterface message, ClientMailBox cmb,
			boolean debug, String str) {
		boolean ret = false;
		if (debug)
			System.out.println("Send: " + message);
		if (cmb.getState()) {
			ret = cmb.send(message);
		} else {
			System.out.println("Comunicación con " + str + " interrumpida");
		}
		return ret;
	}

	public boolean sendMessageSCADA(MessageInterface message) {
		return send(message, _SCADAmessageConsulter,
				LaunchAutMaster.debugSCADA, "SCADA");
	}

	public boolean sendMessageAU1(MessageInterface message) {
		return send(message, _AU1messageConsulter, LaunchAutMaster.debugSlav1,
				"AU1");
	}

	public boolean sendMessageAU2(MessageInterface message) {
		return send(message, _AU2messageConsulter, LaunchAutMaster.debugSlav2,
				"AU2");
	}

	public boolean sendMessageAU3(MessageInterface message) {
		return send(message, _AU3AmessageConsulter, LaunchAutMaster.debugSlav3,
				"AU3");
	}

	public boolean sendMessageRB1(MessageInterface message) {
		return send(message, _RB1messageConsulter, LaunchAutMaster.debugRobo1,
				"RB1");
	}

	public boolean sendMessageRB2(MessageInterface message) {
		return send(message, _RB2messageConsulter, LaunchAutMaster.debugRobo2,
				"RB2");
	}

	public MessageInterface reciveMessageSCADA() {
		MessageInterface ret = null;
		if (_isSistemStarted && checkActivated()) {
			try {
				ret = _SCADAmessageConsulter.receive();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				ret = _SCADAmessageConsulter.receive();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	private boolean is_activated() {
		boolean ret;
		synchronized (_mutex) {
			ret = _activated;
		}
		return ret;
	}

	private void set_activated(boolean _activated) {
		synchronized (_mutex) {
			this._activated = _activated;
		}
	}

	private boolean checkActivated() {
		boolean ret = false;
		if (_isSistemStarted) {
			boolean not_down = true;
			not_down &= _AU1messageConsulter.getState();
			not_down &= _AU2messageConsulter.getState();
			not_down &= _AU3AmessageConsulter.getState();
			not_down &= _RB1messageConsulter.getState();
			not_down &= _RB2messageConsulter.getState();
			not_down &= _SCADAmessageConsulter.getState();

			if (is_activated()) {
				if (not_down) {
					ret = true;
				} else {
					set_activated(false);
					System.out
							.println("Detectada caida de comunicación parando el sistema");

					DefaultMessage dm = new DefaultMessage();
					dm.setIdentifier(MSGOntology.PARADAEMERGENCIA);
					sendMessageAU1(dm);
					sendMessageAU2(dm);
					sendMessageAU3(dm);
					sendMessageRB1(dm);
					sendMessageRB2(dm);
					sendMessageSCADA(dm);
				}
			} else {
				if (not_down) {
					set_activated(true);
					System.out
							.println("Restaurada comunicación arrancando el sistema");
					DefaultMessage dm = new DefaultMessage();
					/*dm.setIdentifier(MSGOntology.ARRANCARDESDEEMERGENCIA);
					sendMessageAU1(dm);
					sendMessageAU2(dm);
					sendMessageAU3(dm);
					sendMessageRB1(dm);
					sendMessageRB2(dm);
					sendMessageSCADA(dm);*/

					//Actualiza los contextos
					MasterContext mc = MasterContext.getInstance();
					dm = new DefaultMessage();
					dm.setIdentifier(MSGOntology.ACTUALIZARCONTEXTO);
					dm.setObject(mc.get_contextoAut1());
					sendMessageAU1(dm);
					dm = new DefaultMessage();
					dm.setIdentifier(MSGOntology.ACTUALIZARCONTEXTO);
					dm.setObject(mc.get_contextoAut2());
					sendMessageAU2(dm);
					dm = new DefaultMessage();
					dm.setIdentifier(MSGOntology.ACTUALIZARCONTEXTO);
					dm.setObject(mc.get_contextoAut3());
					sendMessageAU3(dm);
					dm = new DefaultMessage();
					dm.setIdentifier(MSGOntology.ACTUALIZARCONTEXTO);
					dm.setObject(mc.get_contextoRobot1());
					sendMessageRB1(dm);
					dm = new DefaultMessage();
					dm.setIdentifier(MSGOntology.ACTUALIZARCONTEXTO);
					dm.setObject(mc.get_contextoRobot2());
					sendMessageRB2(dm);
					
					dm = new DefaultMessage();
					Configuration conf = Maestro.getInstance()
							.getConfiguration();
					dm.setIdentifier(MSGOntology.ACTUALIZARCONFIGURACION);
					dm.setObject(conf);
					sendMessageAU1(dm);
					sendMessageAU2(dm);
					sendMessageAU3(dm);
					sendMessageRB1(dm);
					sendMessageRB2(dm);
					sendMessageSCADA(dm);

					ret = true;
				}
			}
		} else
			ret = true;
		return ret;
	}

	public MessageInterface reciveMessageAU1() {
		MessageInterface ret = null;
		if (checkActivated()) {
			try {
				ret = _AU1messageConsulter.receive();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	public MessageInterface reciveMessageAU2() {
		MessageInterface ret = null;
		if (checkActivated()) {
			try {
				ret = _AU2messageConsulter.receive();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	public MessageInterface reciveMessageAU3() {
		MessageInterface ret = null;
		if (checkActivated()) {
			try {
				ret = _AU3AmessageConsulter.receive();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	public MessageInterface reciveMessageRB1() {
		MessageInterface ret = null;
		if (checkActivated()) {
			try {
				ret = _RB1messageConsulter.receive();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	public MessageInterface reciveMessageRB2() {
		MessageInterface ret = null;
		if (checkActivated()) {
			try {
				ret = _RB2messageConsulter.receive();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	public boolean getStateMessageSCADA() {
		return _SCADAmessageConsulter.getState();
	}

	public boolean getStateMessageAU1() {
		return _AU1messageConsulter.getState();
	}

	public boolean getStateMessageAU2() {
		return _AU2messageConsulter.getState();
	}

	public boolean getStateMessageAU3() {
		return _AU3AmessageConsulter.getState();
	}

	public boolean getStateMessageRB1() {
		return _RB1messageConsulter.getState();
	}

	public boolean getStateMessageRB2() {
		return _RB2messageConsulter.getState();
	}

	public synchronized void isSistemStarted(boolean b) {
		_isSistemStarted = b;
	}

}
