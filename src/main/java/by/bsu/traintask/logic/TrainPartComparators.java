package by.bsu.traintask.logic;

import java.util.Comparator;

import by.bsu.traintask.enteties.TrainPart;

public class TrainPartComparators {
	public static Comparator<TrainPart> getMassComparator() {
		return (a, b) -> a.getMass() - b.getMass();
	}

	public static Comparator<TrainPart> getIdComparator() {
		return (a, b) -> a.getId() - b.getId();
	}
}
