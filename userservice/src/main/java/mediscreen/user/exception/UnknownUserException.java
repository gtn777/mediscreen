package mediscreen.user.exception;

public class UnknownUserException extends RuntimeException {

	private static final long serialVersionUID = 4759598242516248165L;

	public UnknownUserException(String message) {
		super(message);
	}

}