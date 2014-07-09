package by.bsu.traintask.parcing;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import by.bsu.traintask.enteties.GoodsWagon;
import by.bsu.traintask.enteties.GoodsWagon.GoodsWagonType;
import by.bsu.traintask.enteties.Locomotive;
import by.bsu.traintask.enteties.Locomotive.LocomotiveType;
import by.bsu.traintask.enteties.PassengerCar;
import by.bsu.traintask.enteties.RailroadCar;
import by.bsu.traintask.enteties.Train;
import by.bsu.traintask.enteties.TrainPart;
import by.bsu.traintask.enteties.accessory.Cargo;
import by.bsu.traintask.enteties.accessory.Passenger;
import by.bsu.traintask.enteties.accessory.PassengerCarType;
import by.bsu.traintask.exceptions.LogicalException;

class TrainHandler extends DefaultHandler {
	private static final Logger LOG = Logger.getLogger(TrainHandler.class);
	
	private static final String WEIGHT = "weight";
	private static final String GOODS_TYPE = "goods-type";
	private static final String ENGINE_TYPE = "engine-type";
	private static final String POWER = "power";
	private static final String FULL_NAME = "fullName";
	private static final String PASSENGER_TYPE = "passenger-type";
	private static final String CAPACITY = "capacity";
	private static final String AXIS_PAIRS = "axis-pairs";
	private static final String MASS = "mass";
	private static final String CARGO = "cargo";
	private static final String PASSENGER = "passenger";
	private static final String PASSENGERS_CAR = "passengers-car";
	private static final String GOODS_WAGON = "goods-wagon";
	private static final String ID = "id";
	private static final String LOCOMOTIVE = "locomotive";
	private static final String EMPTY_ELEMENT = "";

	private String currentTag;
	private Train train;
	private TrainPart element;
	private Passenger currentPassenger;
	private Cargo currentCargo;

	public Train getTrain() {
		return train;
	}

	@Override
	public void startDocument() throws SAXException {
		train = new Train();
	}

	@Override
	public void startElement(String namespace, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentTag = qName;
		switch (currentTag) {
		case LOCOMOTIVE:
			element = new Locomotive();
			element.setId(Integer.valueOf(attributes.getValue(ID).substring(1)));
			break;
		case GOODS_WAGON:
			element = new GoodsWagon();
			element.setId(Integer.valueOf(attributes.getValue(ID).substring(1)));
			break;
		case PASSENGERS_CAR:
			element = new PassengerCar();
			element.setId(Integer.valueOf(attributes.getValue(ID).substring(1)));
			break;
		case PASSENGER:
			currentPassenger = new Passenger();
			currentPassenger.setId(Integer.valueOf(attributes.getValue(ID)
					.substring(1)));
			break;
		case CARGO:
			currentCargo = new Cargo();
			currentCargo.setId(Integer.valueOf(attributes.getValue(ID)
					.substring(1)));
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		try {
			if (element instanceof GoodsWagon) {
				goodsWagonCharacters(ch, start, length);
				return;
			}
			if (element instanceof PassengerCar) {
				passengerCarCharacters(ch, start, length);
				return;
			}
			if (element instanceof Locomotive) {
				locomotiveCharacters(ch, start, length);
				return;
			}
		} catch (LogicalException e) {
			LOG.error("Invalid data occured");
		}
	}

	@Override
	public void endElement(String namespace, String localName, String qName)
			throws SAXException {
		currentTag = EMPTY_ELEMENT;
		if (qName.equals(GOODS_WAGON) || qName.equals(PASSENGERS_CAR)) {
			try {
				train.addCar((RailroadCar) element);
			} catch (LogicalException e) {
				LOG.warn("Null car occured");
			}
			element = null;
			return;
		}
		if (qName.equals(LOCOMOTIVE)) {
			train.setLocomotive((Locomotive) element);
			element = null;
			return;
		}
		if (qName.equals(CARGO)) {
			((GoodsWagon) element).addGoods(currentCargo);
			currentCargo = null;
			return;
		}
		if (qName.equals(PASSENGER)) {
			try {
				((PassengerCar) element).addPassenger(currentPassenger);
			} catch (LogicalException e) {
				LOG.warn("Car with too much passengers occured");
			}
			currentPassenger = null;
			return;
		}
	}

	private void passengerCarCharacters(char[] ch, int start, int length)
			throws LogicalException {
		String data = new String(ch, start, length);
		switch (currentTag) {
		case MASS:
			element.setMass(Integer.valueOf(data));
			break;
		case AXIS_PAIRS:
			((RailroadCar) element).setAxelNumber(Integer.valueOf(data));
			break;
		case CAPACITY:
			((PassengerCar) element).setSeatingCapacity(Integer.valueOf(data));
			break;
		case PASSENGER_TYPE:
			((PassengerCar) element).setType(PassengerCarType.valueOf(data));
			break;
		case FULL_NAME:
			currentPassenger.setFullName(data);
			break;
		}
	}

	private void locomotiveCharacters(char[] ch, int start, int length)
			throws LogicalException {
		String data = new String(ch, start, length);
		switch (currentTag) {
		case MASS:
			element.setMass(Integer.valueOf(data));
			break;
		case POWER:
			((Locomotive) element).setEnginePower(Integer.valueOf(data));
			break;
		case ENGINE_TYPE:
			((Locomotive) element).setType(LocomotiveType.valueOf(data));
			break;
		}
	}

	private void goodsWagonCharacters(char[] ch, int start, int length)
			throws LogicalException {
		String data = new String(ch, start, length);
		switch (currentTag) {
		case MASS:
			element.setMass(Integer.valueOf(data));
			break;
		case AXIS_PAIRS:
			((RailroadCar) element).setAxelNumber(Integer.valueOf(data));
			break;
		case CAPACITY:
			((GoodsWagon) element).setCapacity(Integer.valueOf(data));
			break;
		case GOODS_TYPE:
			((GoodsWagon) element).setType(GoodsWagonType.valueOf(data));
			break;
		case WEIGHT:
			currentCargo.setWeight(Integer.valueOf(data));
			break;
		}
	}

}
