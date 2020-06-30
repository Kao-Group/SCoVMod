package scovmod.model;

public class BadInputDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadInputDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadInputDataException(String arg0) {
		super(arg0);

	}
}
