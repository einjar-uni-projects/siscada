package com.umbrella.scada.observer;

public enum TransferBufferKeys {
	NULL(null), GEN_CLOCK_TIME(Integer.class), GEN_SENSOR_ERROR(Double.class), AU1_CONVEYOR_BELT_SIZE(
			Double.class), AU2_CONVEYOR_BELT_SIZE(Double.class), AU3_CONVEYOR_BELT_SIZE(
			Double.class), AU1_CAKE_DEPOT(Integer.class), AU1_CHOCOLAT_DEPOT(
			Integer.class), AU1_CARAMEL_DEPOT(Integer.class), AU1_CONVEYOR_BELT_SPEED(
			Double.class), AU2_CONVEYOR_BELT_SPEED(Double.class), AU3_CONVEYOR_BELT_SPEED(
			Double.class), AU1_CHOCOLATE_VALVE_DELAY(Integer.class), AU1_CARAMEL_VALVE_DELAY(
			Integer.class), AU2_VACUUM_SEALING_MACHINE(Integer.class), RB1_BLISTER_DELAY(
			Integer.class), RB1_CAKE_DELAY(Integer.class), RB2_BLISTER_DELAY(
			Integer.class), GEN_ROBOT_INTERFERENCE(Integer.class), GEN_CAKE_SIZE(
			Double.class), GEN_BLISTER_SIZE(Double.class), AU1_CAKES_POS1(
			Integer.class), AU1_CAKES_POS2(Integer.class), AU1_CAKES_POS3(
			Integer.class), AU1_CAKES_POS4(Integer.class), AU1_CAKES_POS5(
			Integer.class), AU1_CAKES_POS6(Integer.class), AU1_CAKES_POS7(
			Integer.class), AU2_BLISTERS_POS1(Integer.class), AU2_BLISTERS_POS2(
			Integer.class), AU2_BLISTERS_POS3(Integer.class), AU2_BLISTERS_POS4(
			Integer.class), AU2_BLISTERS_POS5(Integer.class), AU3_PACKAGE_POS1(
			Integer.class), AU3_PACKAGE_POS2(Integer.class), AU3_PACKAGE_POS3(
			Integer.class), AU3_PACKAGE_POS4(Integer.class), AU3_PACKAGE_POS5(
			Integer.class), TABLE_CONTENT(Integer.class), AU1_STATE(
			Boolean.class), AU1_MOVE(Boolean.class), AU2_STATE(Boolean.class), AU2_MOVE(
			Boolean.class), AU3_STATE(Boolean.class), AU3_MOVE(Boolean.class), RB1_STATE(
			Boolean.class), RB2_STATE(Boolean.class), GEN_IP(String.class), GEN_PORT(
			Integer.class), RB1_CONTENT(Integer.class), RB2_CONTENT(
			Integer.class), GOOD_PACKAGES(Integer.class), BAD_PACKAGES(
			Integer.class), GOOD_PACKAGES_TOTAL(Integer.class), BAD_PACKAGES_TOTAL(
			Integer.class);

	private final Class _class;

	private TransferBufferKeys(Class the_class) {
		_class = the_class;

	}

	public Class get_class() {
		return _class;
	}

}
