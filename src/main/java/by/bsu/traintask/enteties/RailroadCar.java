package by.bsu.traintask.enteties;

import by.bsu.traintask.exceptions.LogicalException;

public abstract class RailroadCar extends TrainPart {
	private int axelNumber;

	public int getAxelPairsNumber() {
		return axelNumber;
	}

	public void setAxelNumber(int axelPairsNumber) throws LogicalException {
		if (axelPairsNumber > 2) {
			this.axelNumber = axelPairsNumber;
		} else {
			throw new LogicalException("Incorrect number of axis.");
		}
	}
}
