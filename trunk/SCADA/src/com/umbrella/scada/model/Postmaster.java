package com.umbrella.scada.model;

import java.util.LinkedList;

import com.umbrella.mail.mailbox.ClientMailBox;
import com.umbrella.mail.mailbox.ServerMailBox;
import com.umbrella.mail.message.MessageInterface;
import com.umbrella.mail.utils.properties.PropertiesFileHandler;
import com.umbrella.mail.utils.properties.PropertyException;
import com.umbrella.scada.controller.ActionFactory;
import com.umbrella.scada.controller.ActionFactoryProvider;
import com.umbrella.scada.controller.ActionKey;
import com.umbrella.scada.controller.ActionParams;
import com.umbrella.scada.controller.ActionParamsEnum;

public class Postmaster extends Thread {
	
	private final ClientMailBox _clientMailBox;
	private final LinkedList<MessageInterface> llmi;
	private static Postmaster instance = null;
	private boolean _no_end;
	
	// El constructor privado no permite que se genere un constructor por defecto
	// (con mismo modificador de acceso que la definicion de la clase) 
	private Postmaster() throws PropertyException  {
		PropertiesFile pfmodel = PropertiesFile.getInstance();
		PropertiesFileHandler.getInstance().LoadValuesOnModel(pfmodel);
		PropertiesFileHandler.getInstance().writeFile();
		_clientMailBox = new ClientMailBox(pfmodel.getMasterAutIP(), pfmodel.getMasterAutPort(), ServerMailBox._reciveSCADAName, ServerMailBox._sendSCADAName, true);
		llmi = new LinkedList<MessageInterface>();
		_no_end = true;
	}

	/**
	 * Obtiene la instancia única del objeto, la primera invocación
	 * realiza la creación del mismo.
	 * @return la instancia única de Postmaster
	 * @throws PropertyException 
	 * @throws PropertyException 
	 */
	public static Postmaster getInstance() throws PropertyException {
		Postmaster ret;
		if(instance == null){
			return getSyncInstance();
		}
		return instance;
	}

	private static synchronized Postmaster getSyncInstance() throws PropertyException {
		if(instance == null)
			instance = new Postmaster();
		return instance;
	}
	
	public boolean sendMessage(MessageInterface message){
		return _clientMailBox.send(message);
	}
	
	@Override
	public void run() {
		ActionFactory af = ActionFactoryProvider.getInstance();
		ActionParams params;
		ActionParamsEnum ape;
		
		while(_no_end){
			try {
				params = null;
				MessageInterface msg = _clientMailBox.receiveBlocking();
				System.out.println(msg.getIdentifier());
				switch (msg.getIdentifier()) {
					case AUTOM_STATE: //TODO esto cambia todo
						params = new ActionParams();
						ape = ActionParamsEnum.STATE;
						params.setParam(ape, ape.getEnclosedClass(), msg.getObject());
						ape = ActionParamsEnum.MACHINE;
						params.setParam(ape, ape.getEnclosedClass(), msg.getParameters().get(0));
						af.executeAction(ActionKey.UPDATE_STATE, params);
						break;
					case CAKE_DEPOT:
						params = new ActionParams();
						ape = ActionParamsEnum.CAKE_DEPOT;
						params.setParam(ape,ape.getEnclosedClass(),msg.getObject());
						af.executeAction(ActionKey.UPDATE_CAKE_DEPOT, params);
						break;
					case AU1_CAKES_POS:
						params = new ActionParams();
						ape = ActionParamsEnum.AU1_CAKES_POS;
						params.setParam(ape,ape.getEnclosedClass(),msg.getObject());
						af.executeAction(ActionKey.AU1_CAKES_POS, params);
						break;
					case AU2_BLISTER_POS:
						params = new ActionParams();
						ape = ActionParamsEnum.AU2_BLISTER_POS;
						params.setParam(ape,ape.getEnclosedClass(),msg.getObject());
						af.executeAction(ActionKey.AU2_BLISTER_POS, params);
						break;
					case AU3_PACKAGE_POS:
						params = new ActionParams();
						ape = ActionParamsEnum.AU3_PACKAGE_POS;
						params.setParam(ape,ape.getEnclosedClass(),msg.getObject());
						af.executeAction(ActionKey.AU3_PACKAGE_POS, params);
						break;
					case TABLE_CONTENT:
						params = new ActionParams();
						ape = ActionParamsEnum.TABLE_CONTENT;
						params.setParam(ape,ape.getEnclosedClass(),msg.getObject());
						af.executeAction(ActionKey.TABLE_CONTENT, params);
						break;
					case ROBOT_SET_CONTENT:
						params = new ActionParams();
						ape = ActionParamsEnum.ROBOT_CONTENT;
						params.setParam(ape,ape.getEnclosedClass(),msg.getObject());
						ape = ActionParamsEnum.MACHINE;
						params.setParam(ape, ape.getEnclosedClass(), msg.getParameters().get(0));
						af.executeAction(ActionKey.UPDATE_ROBOT_CONTENT, params);
						break;
					case ACTUALIZARCONFIGURACION:
						params = new ActionParams();
						ape = ActionParamsEnum.CONFIGURATION;
						params.setParam(ape,ape.getEnclosedClass(),msg.getObject());
						af.executeAction(ActionKey.UPDATE_CONFIGURATION, params);
						break;
					case CONVEYOR_BELT_MOVE:
						params = new ActionParams();
						ape = ActionParamsEnum.CONVEYOR_BELT_MOVE;
						params.setParam(ape,ape.getEnclosedClass(),msg.getObject());
						ape = ActionParamsEnum.MACHINE;
						params.setParam(ape, ape.getEnclosedClass(), msg.getParameters().get(0));
						af.executeAction(ActionKey.CONVEYOR_BELT_MOVE, params);
						break;
					case NUM_GOOD_PACKAGES:
						params = new ActionParams();
						ape = ActionParamsEnum.NUMBER_PACKAGES;
						params.setParam(ape,ape.getEnclosedClass(),msg.getObject());
						ape = ActionParamsEnum.GOOD_PACKAGES;
						params.setParam(ape,ape.getEnclosedClass(), true);
						ape = ActionParamsEnum.TOTAL_PACKAGES;
						if(msg.getParameters().get(0).equalsIgnoreCase("TOTAL"))
							params.setParam(ape,ape.getEnclosedClass(), true);
						else
							params.setParam(ape,ape.getEnclosedClass(), false);
						af.executeAction(ActionKey.NUMBER_PACKAGES, params);
						break;
					case NUM_BAD_PACKAGES:
						params = new ActionParams();
						ape = ActionParamsEnum.NUMBER_PACKAGES;
						params.setParam(ape,ape.getEnclosedClass(),msg.getObject());
						ape = ActionParamsEnum.GOOD_PACKAGES;
						params.setParam(ape,ape.getEnclosedClass(), false);
						ape = ActionParamsEnum.TOTAL_PACKAGES;
						if(msg.getParameters().get(0).equalsIgnoreCase("TOTAL"))
							params.setParam(ape,ape.getEnclosedClass(), true);
						else
							params.setParam(ape,ape.getEnclosedClass(), false);
						af.executeAction(ActionKey.NUMBER_PACKAGES, params);
						break;
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
