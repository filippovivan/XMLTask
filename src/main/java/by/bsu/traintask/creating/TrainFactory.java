package by.bsu.traintask.creating;

import by.bsu.traintask.enteties.Train;

public interface TrainFactory extends AbstractFactory<Train> {
	default boolean isValid(String path) {
		return true; //TODO write validation code
	}
}
