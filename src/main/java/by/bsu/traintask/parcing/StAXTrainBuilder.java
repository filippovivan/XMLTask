package by.bsu.traintask.parcing;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.bsu.traintask.enteties.GoodsWagon;
import by.bsu.traintask.enteties.Locomotive;
import by.bsu.traintask.enteties.GoodsWagon.GoodsWagonType;
import by.bsu.traintask.enteties.Locomotive.LocomotiveType;
import by.bsu.traintask.enteties.accessory.Cargo;
import by.bsu.traintask.enteties.accessory.Passenger;
import by.bsu.traintask.enteties.accessory.PassengerCarType;
import by.bsu.traintask.enteties.PassengerCar;
import by.bsu.traintask.enteties.Train;
import by.bsu.traintask.exceptions.LogicalException;
import by.bsu.traintask.exceptions.TechnicalException;

public class StAXTrainBuilder extends TrainBuilder {

	private static final String ENGINE_TYPE = "engine-type";
	private static final String POWER = "power";
	private static final String WEIGHT = "weight";
	private static final String CARGO = "cargo";
	private static final String GOODS = "goods";
	private static final String GOODS_TYPE = "goods-type";
	private static final String FULL_NAME = "fullName";
	private static final String PASSENGER = "passenger";
	private static final String PASSENGERS = "passengers";
	private static final String PASSENGER_TYPE = "passenger-type";
	private static final String CAPACITY = "capacity";
	private static final String AXIS_PAIRS = "axis-pairs";
	private static final String MASS = "mass";
	private static final String ID = "id";
	private static final String LOCOMOTIVE = "locomotive";
	private static final String PASSENGERS_CAR = "passengers-car";
	private static final String GOODS_WAGON = "goods-wagon";
	private XMLInputFactory inputFactory;

	public StAXTrainBuilder() {
		super();
		inputFactory = XMLInputFactory.newInstance();
	}

	@Override
	public Train createInstance() throws TechnicalException, LogicalException {
		Train train = new Train();
		try (InputStream file = StAXTrainBuilder.class
				.getResourceAsStream(getPath())) {
			XMLStreamReader reader = inputFactory.createXMLStreamReader(file);
			while (reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					String name = reader.getLocalName();
					switch (name) {
					case GOODS_WAGON:
						train.addCar(buildGoodsWagon(reader));
						break;
					case PASSENGERS_CAR:
						train.addCar(buildPassengersCar(reader));
						break;
					case LOCOMOTIVE:
						train.setLocomotive(buildLocomotive(reader));
						break;
					}
				}
			}
			return train;
		} catch (IOException e) {
			throw new TechnicalException("Can't access file", e);
		} catch (XMLStreamException e) {
			throw new TechnicalException("Can't create XMLStream", e);
		}
	}

	private PassengerCar buildPassengersCar(XMLStreamReader reader)
			throws XMLStreamException, TechnicalException,
			NumberFormatException, LogicalException {
		PassengerCar car = new PassengerCar();
		car.setId(Integer.valueOf(reader.getAttributeValue(null, ID)
				.substring(1)));
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				fillPassengersCar(reader, car);
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (name.equals(PASSENGERS_CAR)) {
					return car;
				}
				break;
			}
		}
		throw new TechnicalException("Unknown tag");
	}

	private void fillPassengersCar(XMLStreamReader reader, PassengerCar car)
			throws NumberFormatException, LogicalException, XMLStreamException {
		String name;
		name = reader.getLocalName();
		switch (name) {
		case MASS:
			car.setMass(Integer.valueOf(getContent(reader)));
			break;
		case AXIS_PAIRS:
			car.setAxelNumber(Integer.valueOf(getContent(reader)));
			break;
		case CAPACITY:
			car.setSeatingCapacity(Integer.valueOf(getContent(reader)));
			break;
		case PASSENGER_TYPE:
			car.setType(PassengerCarType.valueOf(getContent(reader)));
			break;
		case PASSENGERS:
			parcePassengers(reader, car);
			break;
		}
	}

	private void parcePassengers(XMLStreamReader reader, PassengerCar car)
			throws XMLStreamException, LogicalException {
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case (XMLStreamConstants.START_ELEMENT):
				name = reader.getLocalName();
				if (name.equals(PASSENGER)) {
					Passenger person = new Passenger();
					person.setId(Integer.valueOf(reader.getAttributeValue(null,
							ID).substring(1)));
					parcePassengerName(reader, person);
					car.addPassenger(person);
					break;
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (name.equals(PASSENGERS)) {
					return;
				}
			}
		}
	}

	private void parcePassengerName(XMLStreamReader reader, Passenger person)
			throws XMLStreamException {
		while (reader.hasNext()) {
			int nodeType = reader.next();
			if (nodeType == XMLStreamConstants.START_ELEMENT) {
				String tempName = reader.getLocalName();
				if (tempName.equals(FULL_NAME)) {
					person.setFullName(getContent(reader));
					return;
				}
			}
		}
	}

	private GoodsWagon buildGoodsWagon(XMLStreamReader reader)
			throws XMLStreamException, TechnicalException,
			NumberFormatException, LogicalException {
		GoodsWagon car = new GoodsWagon();
		car.setId(Integer.valueOf(reader.getAttributeValue(null, ID)
				.substring(1)));
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				fillGoodsWagon(reader, car);
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (name.equals(GOODS_WAGON)) {
					return car;
				}
				break;
			}
		}
		throw new TechnicalException("Unknown tag");
	}

	private void fillGoodsWagon(XMLStreamReader reader, GoodsWagon car)
			throws NumberFormatException, LogicalException, XMLStreamException {
		String name;
		name = reader.getLocalName();
		switch (name) {
		case MASS:
			car.setMass(Integer.valueOf(getContent(reader)));
			break;
		case AXIS_PAIRS:
			car.setAxelNumber(Integer.valueOf(getContent(reader)));
			break;
		case CAPACITY:
			car.setCapacity(Integer.valueOf(getContent(reader)));
			break;
		case GOODS_TYPE:
			car.setType(GoodsWagonType.valueOf(getContent(reader)));
			break;
		case GOODS:
			parceGoods(reader, car);
			break;
		}
	}

	private void parceGoods(XMLStreamReader reader, GoodsWagon car)
			throws XMLStreamException {
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case (XMLStreamConstants.START_ELEMENT):
				name = reader.getLocalName();
				if (name.equals(CARGO)) {
					Cargo cargo = new Cargo();
					cargo.setId(Integer.valueOf(reader.getAttributeValue(null,
							ID).substring(1)));
					parceCargoWeight(reader, cargo);
					car.addGoods(cargo);
					break;
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (name.equals(GOODS)) {
					return;
				}
			}
		}
	}

	private void parceCargoWeight(XMLStreamReader reader, Cargo cargo)
			throws XMLStreamException {
		while (reader.hasNext()) {
			int nodeType = reader.next();
			if (nodeType == XMLStreamConstants.START_ELEMENT) {
				String tempName = reader.getLocalName();
				if (tempName.equals(WEIGHT)) {
					cargo.setWeight(Integer.valueOf(getContent(reader)));
					return;
				}
			}
		}
	}

	private Locomotive buildLocomotive(XMLStreamReader reader)
			throws XMLStreamException, NumberFormatException, LogicalException,
			TechnicalException {
		Locomotive locomotive = new Locomotive();
		locomotive.setId(Integer.valueOf(reader.getAttributeValue(null, ID)
				.substring(1)));
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				fillLocomotive(reader, locomotive);
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (name.equals(LOCOMOTIVE)) {
					return locomotive;
				}
				break;
			}
		}
		throw new TechnicalException("Unknown tag");
	}

	private void fillLocomotive(XMLStreamReader reader, Locomotive locomotive)
			throws LogicalException, XMLStreamException {
		String name;
		name = reader.getLocalName();
		switch (name) {
		case MASS:
			locomotive.setMass(Integer.valueOf(getContent(reader)));
			break;
		case POWER:
			locomotive.setEnginePower(Integer.valueOf(getContent(reader)));
			break;
		case ENGINE_TYPE:
			locomotive.setType(LocomotiveType.valueOf(getContent(reader)));
			break;
		}
	}

	private String getContent(XMLStreamReader reader) throws XMLStreamException {
		String text = null;
		if (reader.hasNext()) {
			reader.next();
			text = reader.getText();
		}
		return text;
	}

}
