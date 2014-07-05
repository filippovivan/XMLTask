package by.bsu.traintask.factories;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import by.bsu.traintask.enteties.GoodsWagon;
import by.bsu.traintask.enteties.Locomotive;
import by.bsu.traintask.enteties.PassengerCar;
import by.bsu.traintask.enteties.RailroadCar;
import by.bsu.traintask.enteties.Train;
import by.bsu.traintask.enteties.TrainPart;
import by.bsu.traintask.enteties.accessory.PassengerCarType;
import by.bsu.traintask.exceptions.LogicalException;
import by.bsu.traintask.exceptions.TechnicalException;

public class JSONTrainFactory implements AbstractFactory<Train> {
	private static final String PACCENGER_CAR_TYPE = "seatingtype";
	private static final String PASSENGER_CAR_CAPACITY = "seatingcapacity";
	private static final String GOODS_WAGON_CAPACITY = "capacity";
	private static final String GOODS_WAGON_TYPE = "goodswagontype";
	private static final String AXIS = "axis";
	private static final String MASS = "mass";
	private static final String ID = "id";
	private static final String GOODS = "goods";
	private static final String PASSENGER = "passenger";
	private static final String CAR_TYPE = "cartype";
	private static final String ENGINE_TYPE = "enginetype";
	private static final String ENGINE_POWER = "engine";
	private static final String CAR = "car";
	private static final String LOCOMOTIVE = "locomotive";
	private static final Logger LOG = Logger.getLogger(JSONTrainFactory.class);

	private String path;

	public JSONTrainFactory(String path) {
		super();
		this.path = path;
	}

	@Override
	public Train createInstance() throws TechnicalException {
		StringBuilder builder = new StringBuilder();
		Scanner scanner = null;
		Train train = null;
		try {
			scanner = new Scanner(new File(path));
			while (scanner.hasNext()) {
				builder.append(scanner.next());
			}

			String fullText = builder.toString();
			JSONParser parser = new JSONParser();
			JSONObject fullObject = (JSONObject) parser.parse(fullText);
			JSONObject trainString = (JSONObject) fullObject.get("train");
			JSONObject trainPart;
			List<RailroadCar> cars = new ArrayList<>();
			int i = 0;
			while ((trainPart = (JSONObject) trainString.get(CAR + i)) != null) {
				try {
					cars.add(parceCar(trainPart));
					LOG.debug("Car added");
				} catch (LogicalException e) {
					LOG.error("Invalid car fields values. Car wasn't added.");
				}
				i++;
			}
			Locomotive locomotive = null;
			try {
				locomotive = parcelocomotive((JSONObject) trainString
						.get(LOCOMOTIVE));
			} catch (LogicalException e) {
				LOG.error("Invalid locomotive description.");
			}
			train = new Train(locomotive, cars);
		} catch (FileNotFoundException e) {
			LOG.error(e);
			throw new TechnicalException(e);
		} catch (ParseException e) {
			LOG.error(e);
			throw new TechnicalException("Invalid JSON file.", e);
		} finally {
			scanner.close();
		}
		LOG.info("Train successfully parced.");
		return train;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private RailroadCar parceCar(JSONObject trainPart)
			throws TechnicalException, LogicalException {
		String type = (String) trainPart.get(CAR_TYPE);
		switch (type) {
		case PASSENGER:
			return parcePassengerCar(trainPart);
		case GOODS:
			return parceGoodsWagon(trainPart);
		default:
			throw new TechnicalException("Invalid car type.");
		}
	}

	private void parceAbstractInfo(TrainPart wagon, JSONObject carString)
			throws LogicalException {
		long id = (long) carString.get(ID);
		wagon.setId((int) id);
		long mass = (long) carString.get(MASS);
		wagon.setMass((int) mass);
	}

	private void parceCarInfo(RailroadCar wagon, JSONObject carString)
			throws LogicalException {
		long count = (long) carString.get(AXIS);
		wagon.setAxelNumber((int) count);
	}

	private GoodsWagon parceGoodsWagon(JSONObject wagonString)
			throws LogicalException {
		GoodsWagon wagon = new GoodsWagon();
		parceAbstractInfo(wagon, wagonString);
		parceCarInfo(wagon, wagonString);
		wagon.setType(GoodsWagon.GoodsWagonType.valueOf((String) wagonString
				.get(GOODS_WAGON_TYPE)));
		long capacity = (long) wagonString.get(GOODS_WAGON_CAPACITY);
		wagon.setCapacity((int) capacity);
		return wagon;
	}

	private PassengerCar parcePassengerCar(JSONObject carString)
			throws LogicalException {
		PassengerCar car = new PassengerCar();
		parceAbstractInfo(car, carString);
		parceCarInfo(car, carString);
		long capacity = (long) carString.get(PASSENGER_CAR_CAPACITY);
		car.setSeatingCapacity((int) capacity);
		car.setType(PassengerCarType.valueOf((String) carString
				.get(PACCENGER_CAR_TYPE)));
		return car;
	}

	private Locomotive parcelocomotive(JSONObject jsonObject)
			throws LogicalException {
		Locomotive locomotive = new Locomotive();
		parceAbstractInfo(locomotive, jsonObject);
		long power = (long) jsonObject.get(ENGINE_POWER);
		locomotive.setEnginePower((int) power);
		locomotive.setType(Locomotive.LocomotiveType
				.valueOf((String) jsonObject.get(ENGINE_TYPE)));
		return locomotive;
	}

}
