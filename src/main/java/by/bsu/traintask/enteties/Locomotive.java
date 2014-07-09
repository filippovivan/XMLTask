package by.bsu.traintask.enteties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import by.bsu.traintask.exceptions.LogicalException;

public class Locomotive extends TrainPart {
	public static enum LocomotiveType {
		ELECTRIC, DIESEL
	}

	private LocomotiveType type;
	private int enginePower;
	private List<String> drivers;

	public Locomotive() {
		drivers = new ArrayList<>();
	}

	public boolean addDriver(String driver) {
		return this.drivers.add(driver);
	}

	public Iterator<String> driversIterator() {
		return drivers.iterator();
	}

	public LocomotiveType getType() {
		return type;
	}

	public void setType(LocomotiveType type) {
		this.type = type;
	}

	public int getEnginePower() {
		return enginePower;
	}

	public void setEnginePower(int enginePower) throws LogicalException {
		if (enginePower > 0) {
			this.enginePower = enginePower;
		} else {
			throw new LogicalException("Engine power must be positive.");
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Locomotive " + " " + type + " " + enginePower + " ¹"
				+ String.valueOf(getId()) + ".");
		if (!drivers.isEmpty()) {
			builder.append(" Drivers:");
			for (String driver : drivers) {
				builder.append("\n" + driver);
			}
		}
		return builder.toString();
	}

}
