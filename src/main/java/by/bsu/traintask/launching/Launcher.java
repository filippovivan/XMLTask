package by.bsu.traintask.launching;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.xml.sax.SAXException;

import by.bsu.traintask.creating.DOMTrainFactory;
import by.bsu.traintask.creating.TrainFactory;
import by.bsu.traintask.creating.TrainHandler;
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
		/*TrainFactory factory = new DOMTrainFactory("/xml/train.xml");
		Train train = factory.createInstance();
		LOG.info(train);*/
		SAXParserFactory factory = SAXParserFactory.newInstance(); 
		SAXParser parser = factory.newSAXParser(); 
		TrainHandler handler = new TrainHandler();
		parser.parse(new File("src/main/resources/xml/train.xml"), handler);
		LOG.info(handler.getTrain());
	}
}
