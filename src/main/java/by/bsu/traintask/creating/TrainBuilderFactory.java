package by.bsu.traintask.creating;

import by.bsu.traintask.exceptions.TechnicalException;

public enum TrainBuilderFactory {
	DOM, SAX, STAX;
	public static TrainBuilder create(TrainBuilderFactory type)
			throws TechnicalException {
		switch (type) {
		case DOM:
			return new DOMTrainBuilder();
		case SAX:
			return new SAXTrainBuilder();
		case STAX:
			return new StAXTrainBuilder();
		default:
			throw new TechnicalException("Unknown parcer type");
		}
	}
}
