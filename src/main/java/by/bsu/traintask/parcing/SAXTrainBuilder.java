package by.bsu.traintask.parcing;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import by.bsu.traintask.enteties.Train;
import by.bsu.traintask.exceptions.LogicalException;
import by.bsu.traintask.exceptions.TechnicalException;

public class SAXTrainBuilder extends TrainBuilder {
	private static final SAXParserFactory factory = SAXParserFactory
			.newInstance();;
	private final SAXParser parcer;
	private TrainHandler handler;
	public SAXTrainBuilder() throws TechnicalException {
		super();
		try {
			parcer = factory.newSAXParser();
			handler = new TrainHandler();
		} catch (ParserConfigurationException | SAXException e) {
			throw new TechnicalException("Error occured while creating parcer",
					e);
		}
	}

	@Override
	public Train createInstance() throws TechnicalException, LogicalException {
		try (InputStream stream = SAXTrainBuilder.class
				.getResourceAsStream(getPath())) {
			parcer.parse(stream, handler);
			return handler.getTrain();
		} catch (IOException e) {
			throw new TechnicalException("Cannot open file", e);
		} catch (SAXException e) {
			throw new TechnicalException("Error while parcing", e);
		}
	}

}
