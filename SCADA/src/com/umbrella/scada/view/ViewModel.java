package com.umbrella.scada.view;

import com.umbrella.scada.observer.TransferBuffer;
import com.umbrella.scada.observer.TransferBufferKeys;

public class ViewModel implements Updatable {

	@Override
	public void update(TransferBuffer buffer) {
		TransferBufferKeys[] keys = TransferBufferKeys.values();
		Object o;
		for (int i = 0; i < keys.length; i++) {
			o = buffer.getElement(keys[i]);
			if (o != null) {
				updateValue(keys[i], keys[i].get_class(), o);
			}
		}
	}

	private synchronized void updateValue(TransferBufferKeys key, Class object_class, Object o) {
		switch (key) {
		case AU1_CAKE_DEPOT:
			
			break;
		case AU1_CARAMEL_VALVE_DELAY:

			break;
		case AU1_CHOCOLATE_VALVE_DELAY:

			break;
		case AU1_CONVEYOR_BELT_SIZE:

			break;
		case AU1_CONVEYOR_BELT_SPEED:

			break;
		case AU2_CONVEYOR_BELT_SIZE:

			break;
		case AU2_CONVEYOR_BELT_SPEED:

			break;
		case AU2_VACUUM_SEALING_MACHINE:

			break;
		case AU3_CONVEYOR_BELT_SIZE:

			break;
		case AU3_CONVEYOR_BELT_SPEED:

			break;
		case GEN_BLISTER_SIZE:

			break;
		case GEN_CAKE_SIZE:

			break;
		case GEN_CLOCK_TIME:

			break;
		case GEN_ROBOT_INTERFERENCE:

			break;
		case GEN_SENSOR_ERROR:

			break;
		case RB1_BLISTER_DELAY:

			break;
		case RB1_CAKE_DELAY:

			break;
		case RB2_BLISTER_DELAY:

			break;

		default:
			break;
		}

	}

}
