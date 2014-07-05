package by.bsu.traintask.launching;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import by.bsu.traintask.enteties.Train;
import by.bsu.traintask.exceptions.TechnicalException;
import by.bsu.traintask.factories.JSONTrainFactory;

public class Launcher {
	private static final String TRAIN_JSON_PATH = "resourses/jsontrain.json";
	private static final Logger LOG = Logger.getLogger(Launcher.class);

	static {
		new DOMConfigurator().doConfigure("resourses/logconfig.xml",
				LogManager.getLoggerRepository());
	}

	public static void main(String[] args) {
		JSONTrainFactory parcer = new JSONTrainFactory(TRAIN_JSON_PATH);
		try {
			Train train = parcer.createInstance();
			LOG.info(train);
		} catch (TechnicalException e) {
			LOG.error(e);
		}
	}
}
