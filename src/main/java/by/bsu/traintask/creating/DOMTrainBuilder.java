package by.bsu.traintask.creating;

import static by.bsu.traintask.creating.TrainFields.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.bsu.traintask.enteties.GoodsWagon;
import by.bsu.traintask.enteties.Locomotive;
import by.bsu.traintask.enteties.Locomotive.LocomotiveType;
import by.bsu.traintask.enteties.PassengerCar;
import by.bsu.traintask.enteties.RailroadCar;
import by.bsu.traintask.enteties.Train;
import by.bsu.traintask.enteties.TrainPart;
import by.bsu.traintask.enteties.accessory.PassengerCarType;
import by.bsu.traintask.exceptions.LogicalException;
import by.bsu.traintask.exceptions.TechnicalException;

public class DOMTrainBuilder extends TrainBuilder {

	private DocumentBuilderFactory factory;

	public DOMTrainBuilder() {
		super();
		factory = DocumentBuilderFactory.newInstance();
	}

	@Override
	public Train createInstance() throws TechnicalException, LogicalException {
		try (InputStream resource = DOMTrainBuilder.class
				.getResourceAsStream(getPath());) {
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.parse(resource);
			Train train = new Train();
			NodeList locomotiveNode = document.getElementsByTagName(LOCOMOTIVE
					.getValue());
			if (locomotiveNode.getLength() > 0
					&& locomotiveNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
				train.setLocomotive(parceLocomotive((Element) locomotiveNode
						.item(0)));
			}
			train.addCars(parceCars(document));
			return train;
		} catch (ParserConfigurationException e) {
			throw new LogicalException("Can't parce xml", e);
		} catch (SAXException e) {
			throw new TechnicalException("Can't parce xml", e);
		} catch (IOException e) {
			throw new TechnicalException("Can't open xml", e);
		}
	}

	private ArrayList<RailroadCar> parceCars(Document document)
			throws LogicalException {
		ArrayList<RailroadCar> cars = new ArrayList<>();
		NodeList goodsWagonNodes = document.getElementsByTagName(GOODS_WAGON
				.getValue());
		for (int i = 0; i < goodsWagonNodes.getLength(); i++) {
			Node wagonNode = goodsWagonNodes.item(i);
			if (wagonNode.getNodeType() == Node.ELEMENT_NODE) {
				cars.add(parceGoodsWagon((Element) wagonNode));
			}
		}
		NodeList passengerCarsNodes = document
				.getElementsByTagName(PASSENGERS_CAR.getValue());
		for (int i = 0; i < passengerCarsNodes.getLength(); i++) {
			Node carNode = passengerCarsNodes.item(i);
			if (carNode.getNodeType() == Node.ELEMENT_NODE) {
				cars.add(parcePassengerCar((Element) carNode));
			}
		}
		return cars;
	}

	private PassengerCar parcePassengerCar(Element carElement)
			throws LogicalException {
		PassengerCar car = new PassengerCar();
		parceTrainPartInfo(car, carElement);
		parceCarInfo(car, carElement);
		int capacity = Integer.parseInt(getContent(carElement,
				PASSENGER_CAPACITY));
		PassengerCarType type = PassengerCarType.valueOf(getContent(carElement,
				PASSENGER_TYPE));
		car.setSeatingCapacity(capacity);
		car.setType(type);
		// TODO Passengers
		return car;
	}

	private GoodsWagon parceGoodsWagon(Element wagonElement)
			throws LogicalException {
		GoodsWagon wagon = new GoodsWagon();
		parceTrainPartInfo(wagon, wagonElement);
		parceCarInfo(wagon, wagonElement);
		int capacity = Integer
				.parseInt(getContent(wagonElement, GOODS_CAPACITY));
		GoodsWagon.GoodsWagonType type = GoodsWagon.GoodsWagonType
				.valueOf(getContent(wagonElement, GOODS_TYPE));
		wagon.setCapacity(capacity);
		wagon.setType(type);
		// TODO Goods
		return wagon;
	}

	private Locomotive parceLocomotive(Element locomotiveElement)
			throws LogicalException {
		Locomotive locomotive = new Locomotive();
		parceTrainPartInfo(locomotive, locomotiveElement);
		int power = Integer.parseInt(getContent(locomotiveElement, POWER));
		LocomotiveType type = LocomotiveType.valueOf(getContent(
				locomotiveElement, ENGINE_TYPE));
		locomotive.setEnginePower(power);
		locomotive.setType(type);
		return locomotive;
	}

	private void parceTrainPartInfo(TrainPart car, Element carElement)
			throws LogicalException {
		int id = Integer.parseInt(carElement.getAttribute(ID.getValue())
				.substring(1));
		int mass = Integer.parseInt(getContent(carElement, MASS));
		car.setId(id);
		car.setMass(mass);
	}

	private void parceCarInfo(RailroadCar car, Element carElement)
			throws LogicalException {
		int axis = Integer.parseInt(getContent(carElement, AXIS_PAIRS));
		car.setAxelNumber(axis);
	}

	private String getContent(Element element, TrainFields type) {
		return element.getElementsByTagName(type.getValue()).item(0)
				.getTextContent();
	}
}