package by.bsu.traintask.enteties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import by.bsu.traintask.exceptions.LogicalException;

public class Train {
	private Locomotive locomotive;
	private List<RailroadCar> cars;

	public Train() {
		cars = new ArrayList<>();
	}

	public Train(Locomotive locomotive, List<? extends RailroadCar> cars) {
		super();
		this.locomotive = locomotive;
		this.cars = new ArrayList<>();
		this.cars.addAll(cars);
	}

	public Locomotive getLocomotive() {
		return locomotive;
	}

	public void setLocomotive(Locomotive locomotive) {
		this.locomotive = locomotive;
	}

	public boolean addCars(List<? extends RailroadCar> cars) {
		return this.cars.addAll(cars);
	}

	public boolean addCar(RailroadCar car) throws LogicalException {
		if (car != null) {
			return this.cars.add(car);
		} else {
			throw new LogicalException("Null car not allowed.");
		}
	}

	public Iterator<RailroadCar> carsIterator() {
		return cars.iterator();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(locomotive + "\n");
		for (RailroadCar car : cars) {
			builder.append(car + "\n");
		}
		return builder.toString();
	}
}
