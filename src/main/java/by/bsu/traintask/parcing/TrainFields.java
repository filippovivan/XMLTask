package by.bsu.traintask.parcing;

enum TrainFields {
	TRAIN("train"), EMPTY_ELEMENT(""), AXIS_PAIRS("axis-pairs"), MASS("mass"), ID(
			"id"), POWER("power"), ENGINE_TYPE("engine-type"), GOODS_TYPE(
			"goods-type"), GOODS_CAPACITY("capacity"), PASSENGER_TYPE(
			"passenger-type"), PASSENGER_CAPACITY("capacity"), PASSENGERS_CAR(
			"passengers-car"), GOODS_WAGON("goods-wagon"), LOCOMOTIVE(
			"locomotive"), CARGO("cargo"), PASSENGER("passenger"), WEIGHT(
			"weight"), FULL_NAME("full-name");

	private final String value;

	private TrainFields(String value) {
		this.value = value;
	}

	public final String getValue() {
		return value;
	}
}
