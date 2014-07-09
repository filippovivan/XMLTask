package by.bsu.traintask.launching;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.xml.sax.SAXException;

import by.bsu.traintask.creating.SAXTrainFactory;
import by.bsu.traintask.creating.TrainFactory;
import by.bsu.traintask.enteties.Train;
import by.bsu.traintask.exceptions.LogicalException;
import by.bsu.traintask.exceptions.TechnicalException;

public class Launcher {

	static {
		try (InputStream config = Launcher.class
				.getResourceAsStream("/logconfig.properties")) {
			PropertyConfigurator.configure(config);
		} catch (IOException e) {
		}
	}
	private static final Logger LOG = Logger.getLogger(Launcher.class);

	public static void main(String[] args) throws TechnicalException,
			LogicalException, ParserConfigurationException, SAXException, IOException {
		TrainFactory factory = new SAXTrainFactory("/xml/train.xml");
		Train train = factory.createInstance();
		LOG.info(train);
		
	}
}
