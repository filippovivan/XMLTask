package by.bsu.traintask.creating;

import by.bsu.traintask.enteties.Train;

public abstract class TrainBuilder implements AbstractBuilder<Train> {
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
