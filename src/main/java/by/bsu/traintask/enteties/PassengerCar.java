package by.bsu.traintask.enteties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import by.bsu.traintask.enteties.accessory.Passenger;
import by.bsu.traintask.enteties.accessory.PassengerCarType;
import by.bsu.traintask.exceptions.LogicalException;

public class PassengerCar extends RailroadCar {

	private static final String FULL_CAR_EXCEPTION_TEXT = "Can't add passenger to full car.";

	private PassengerCarType type;
	private int seatingCapacity;
	private List<Passenger> passengers;

	public PassengerCar() {
		passengers = new ArrayList<>();
	}

	public boolean addPassenger(Passenger passengers) throws LogicalException {
		if (this.passengers.size() < seatingCapacity) {
			return this.passengers.add(passengers);
		} else {
			throw new LogicalException(FULL_CAR_EXCEPTION_TEXT);
		}
	}

	public Iterator<Passenger> passengersIterator() {
		return passengers.iterator();
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(int seatingCapacity) throws LogicalException {
		if (seatingCapacity >= 0) {
			this.seatingCapacity = seatingCapacity;
		} else {
			throw new LogicalException("Capacity must be positive.");
		}
	}

	public PassengerCarType getType() {
		return type;
	}

	public void setType(PassengerCarType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Passenger car " + " " + type + " ¹"
				+ String.valueOf(getId()) + ".");
		if (!passengers.isEmpty()) {
			builder.append(" Passengers:");
			for (Passenger passenger : passengers) {
				builder.append("\n" + passenger);
			}
		}
		return builder.toString();
	}
}
