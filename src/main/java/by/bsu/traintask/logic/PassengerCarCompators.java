package by.bsu.traintask.logic;

import java.util.Comparator;

import org.apache.log4j.Logger;

import by.bsu.traintask.enteties.PassengerCar;

public class PassengerCarCompators {
	private static Logger LOG = Logger.getLogger(PassengerCarCompators.class);

	public static Comparator<PassengerCar> getComfortComparator() {
		LOG.debug("Comfort comparator created");
		return (a, b) -> a.getSeatingCapacity() - b.getSeatingCapacity();
	}

	public static Comparator<PassengerCar> getCapacityComparator() {
		LOG.debug("Capacity comparator created");
		return (a, b) -> a.getSeatingCapacity() - b.getSeatingCapacity();
	}
}
