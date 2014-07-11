package by.bsu.traintask.launching;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import by.bsu.traintask.enteties.Train;
import by.bsu.traintask.exceptions.LogicalException;
import by.bsu.traintask.exceptions.TechnicalException;
import by.bsu.traintask.parcing.TrainBuilder;
import by.bsu.traintask.parcing.TrainBuilderFactory;

public class Launcher {

	static {
		try (InputStream config = Launcher.class
				.getResourceAsStream("/logconfig.properties")) {
			PropertyConfigurator.configure(config);
		} catch (IOException e) {
		}
	}
	private static final Logger LOG = Logger.getLogger(Launcher.class);

	public static void main(String[] args) {
		try {
			TrainBuilder builder = TrainBuilderFactory
					.create(TrainBuilderFactory.DOM);
			builder.setPath("/xml/train.xml");
			Train train = builder.createInstance();
			LOG.info(train);
		} catch (TechnicalException e) {
			LOG.error(e);
		} catch (LogicalException e) {
			LOG.error(e);
		}
	}
}
