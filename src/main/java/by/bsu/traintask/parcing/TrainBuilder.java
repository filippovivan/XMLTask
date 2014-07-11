package by.bsu.traintask.parcing;

import by.bsu.traintask.enteties.Train;

public abstract class TrainBuilder implements AbstractBuilder<Train> {
	public static final String POWER = "power";
	public static final String WEIGHT = "weight";
	public static final String CARGO = "cargo";
	public static final String GOODS = "goods";
	public static final String GOODS_TYPE = "goods-type";
	public static final String FULL_NAME = "fullName";
	public static final String PASSENGER = "passenger";
	public static final String PASSENGERS = "passengers";
	public static final String PASSENGER_TYPE = "passenger-type";
	public static final String CAPACITY = "capacity";
	public static final String AXIS_PAIRS = "axis-pairs";
	public static final String MASS = "mass";
	public static final String ID = "id";
	public static final String LOCOMOTIVE = "locomotive";
	public static final String PASSENGERS_CAR = "passengers-car";
	public static final String GOODS_WAGON = "goods-wagon";
	public static final String ENGINE_TYPE = "engine-type";
	private String path;

	public boolean isValid(String path) {
		return true; // TODO write validation code
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
