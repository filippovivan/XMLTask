package by.bsu.traintask.creating;

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

public class TrainHandler extends DefaultHandler {
	private static final Logger LOG = Logger.getLogger(TrainHandler.class);
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
		case "locomotive":
			element = new Locomotive();
			element.setId(Integer.valueOf(attributes.getValue("id").substring(1)));
			break;
		case "goods-wagon":
			element = new GoodsWagon();
			element.setId(Integer.valueOf(attributes.getValue("id").substring(1)));
			break;
		case "passengers-car":
			element = new PassengerCar();
			element.setId(Integer.valueOf(attributes.getValue("id").substring(1)));
			break;
		case "passenger":
			currentPassenger = new Passenger();
			currentPassenger.setId(Integer.valueOf(attributes.getValue("id").substring(1)));
			break;
		case "cargo":
			currentCargo = new Cargo();
			currentCargo.setId(Integer.valueOf(attributes.getValue("id").substring(1)));
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
		if (qName.equals("goods-wagon")
				|| qName.equals("passengers-car")) {
			try {
				train.addCar((RailroadCar) element);
			} catch (LogicalException e) {
				LOG.warn("Null car occured");
			}
			element = null;
			return;
		}
		if (qName.equals("locomotive")) {
			train.setLocomotive((Locomotive) element);
			element = null;
			return;
		}
		if (qName.equals("cargo")) {
			((GoodsWagon) element).addGoods(currentCargo);
			currentCargo = null;
			return;
		}
		if (qName.equals("passenger")) {
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
		case "mass":
			element.setMass(Integer.valueOf(data));
			break;
		case "axis-pairs":
			((RailroadCar) element).setAxelNumber(Integer.valueOf(data));
			break;
		case "capacity":
			((PassengerCar) element).setSeatingCapacity(Integer.valueOf(data));
			break;
		case "passenger-type":
			((PassengerCar) element).setType(PassengerCarType.valueOf(data));
			break;
		case "fullName":
			currentPassenger.setFullName(data);
			break;
		}
	}

	private void locomotiveCharacters(char[] ch, int start, int length)
			throws LogicalException {
		String data = new String(ch, start, length);
		switch (currentTag) {
		case "mass":
			element.setMass(Integer.valueOf(data));
			break;
		case "power":
			((Locomotive) element).setEnginePower(Integer.valueOf(data));
			break;
		case "engine-type":
			((Locomotive) element).setType(LocomotiveType.valueOf(data));
			break;
		}
	}

	private void goodsWagonCharacters(char[] ch, int start, int length)
			throws LogicalException {
		String data = new String(ch, start, length);
		switch (currentTag) {
		case "mass":
			element.setMass(Integer.valueOf(data));
			break;
		case "axis-pairs":
			((RailroadCar) element).setAxelNumber(Integer.valueOf(data));
			break;
		case "capacity":
			((GoodsWagon) element).setCapacity(Integer.valueOf(data));
			break;
		case "goods-type":
			((GoodsWagon) element).setType(GoodsWagonType.valueOf(data));
			break;
		case "weight":
			currentCargo.setWeight(Integer.valueOf(data));
			break;
		}
	}

}
