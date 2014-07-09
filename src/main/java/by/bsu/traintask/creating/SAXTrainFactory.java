package by.bsu.traintask.creating;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import by.bsu.traintask.enteties.Train;
import by.bsu.traintask.exceptions.LogicalException;
import by.bsu.traintask.exceptions.TechnicalException;

public class SAXTrainFactory implements TrainFactory {
	private static final SAXParserFactory factory = SAXParserFactory
			.newInstance();;
	private final SAXParser parcer;
	private TrainHandler handler;
	private String path;

	public SAXTrainFactory(String path) throws TechnicalException {
		super();
		this.path = path;
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
		try (InputStream stream = SAXTrainFactory.class
				.getResourceAsStream(path)) {
			parcer.parse(stream, handler);
			return handler.getTrain();
		} catch (IOException e) {
			throw new TechnicalException("Cannot open file", e);
		} catch (SAXException e) {
			throw new TechnicalException("Error while parcing", e);
		}
	}

	public void setPath(String path) {
		this.path = path;
	}

}
