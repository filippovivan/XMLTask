package by.bsu.traintask.exceptions;

@SuppressWarnings("serial")
public class TechnicalException extends Exception {

	public TechnicalException(String message, Throwable cause) {
		super(message, cause);
	}

	public TechnicalException(String message) {
		super(message);
	}

	public TechnicalException(Throwable cause) {
		super(cause);
	}

}
