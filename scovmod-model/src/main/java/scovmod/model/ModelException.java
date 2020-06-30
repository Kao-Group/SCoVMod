package scovmod.model;

public class ModelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	
	public ModelException(String arg0) {
		super(arg0);
	}


	public ModelException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
