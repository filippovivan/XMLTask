package by.bsu.traintask.logic;

import java.util.Comparator;

import by.bsu.traintask.enteties.GoodsWagon;

public class GoodsWagonComparators {
	public static Comparator<GoodsWagon> getCapacityComparator() {
		return (a, b) -> a.getCapacity() - b.getCapacity();
	}
}
