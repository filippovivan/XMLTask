package by.bsu.traintask.creating;

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

public class DOMTrainFactory implements TrainFactory {

	private static final String AXIS_PAIRS = "axis-pairs";
	private static final String MASS = "mass";
	private static final String ID = "id";
	private static final String ENGINE_TYPE = "engine-type";
	private static final String POWER = "power";
	private static final String GOODS_TYPE = "goods-type";
	private static final String GOODS_CAPACITY = "capacity";
	private static final String PASSENGER_TYPE = "passenger-type";
	private static final String PASSENGER_CAPACITY = GOODS_CAPACITY;
	private static final String PASSENGERS_CAR = "passengers-car";
	private static final String GOODS_WAGON = "goods-wagon";
	private static final String LOCOMOTIVE = "locomotive";
	private String path;
	private DocumentBuilderFactory factory;

	public DOMTrainFactory(String path) {
		super();
		this.path = path;
		factory = DocumentBuilderFactory.newInstance();
	}

	@Override
	public Train createInstance() throws TechnicalException, LogicalException {
		try (InputStream resource = DOMTrainFactory.class
				.getResourceAsStream(path);) {
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.parse(resource);
			Train train = new Train();
			NodeList locomotiveNode = document.getElementsByTagName(LOCOMOTIVE);
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
		NodeList goodsWagonNodes = document.getElementsByTagName(GOODS_WAGON);
		for (int i = 0; i < goodsWagonNodes.getLength(); i++) {
			Node wagonNode = goodsWagonNodes.item(i);
			if (wagonNode.getNodeType() == Node.ELEMENT_NODE) {
				cars.add(parceGoodsWagon((Element) wagonNode));
			}
		}
		NodeList passengerCarsNodes = document
				.getElementsByTagName(PASSENGERS_CAR);
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
		int capacity = Integer.parseInt(carElement
				.getElementsByTagName(PASSENGER_CAPACITY).item(0)
				.getTextContent());
		PassengerCarType type = PassengerCarType.valueOf(carElement
				.getElementsByTagName(PASSENGER_TYPE).item(0).getTextContent());
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
		int capacity = Integer.parseInt(wagonElement
				.getElementsByTagName(GOODS_CAPACITY).item(0).getTextContent());
		GoodsWagon.GoodsWagonType type = GoodsWagon.GoodsWagonType
				.valueOf(wagonElement.getElementsByTagName(GOODS_TYPE).item(0)
						.getTextContent());
		wagon.setCapacity(capacity);
		wagon.setType(type);
		// TODO Goods
		return wagon;
	}

	private Locomotive parceLocomotive(Element locomotiveElement)
			throws LogicalException {
		Locomotive locomotive = new Locomotive();
		parceTrainPartInfo(locomotive, locomotiveElement);
		int power = Integer.parseInt(locomotiveElement
				.getElementsByTagName(POWER).item(0).getTextContent());
		LocomotiveType type = LocomotiveType.valueOf(locomotiveElement
				.getElementsByTagName(ENGINE_TYPE).item(0).getTextContent());
		locomotive.setEnginePower(power);
		locomotive.setType(type);
		return locomotive;
	}

	private void parceTrainPartInfo(TrainPart car, Element carElement)
			throws LogicalException {
		int id = Integer.parseInt(carElement.getAttribute(ID).substring(1));
		int mass = Integer.parseInt(carElement.getElementsByTagName(MASS)
				.item(0).getTextContent());
		car.setId(id);
		car.setMass(mass);
	}

	private void parceCarInfo(RailroadCar car, Element carElement)
			throws LogicalException {
		int axis = Integer.parseInt(carElement.getElementsByTagName(AXIS_PAIRS)
				.item(0).getTextContent());
		car.setAxelNumber(axis);
	}

	public void setPath(String path) {
		this.path = path;
	}
}
