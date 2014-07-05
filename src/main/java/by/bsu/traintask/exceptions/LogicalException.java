package by.bsu.traintask.exceptions;

@SuppressWarnings("serial")
public class LogicalException extends Exception {

	public LogicalException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogicalException(String message) {
		super(message);
	}

	public LogicalException(Throwable cause) {
		super(cause);
	}

}
