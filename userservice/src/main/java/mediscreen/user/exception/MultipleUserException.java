package mediscreen.user.exception;

public class MultipleUserException extends RuntimeException {

	private static final long serialVersionUID = 3725797746858575466L;

	public MultipleUserException(String message) {
		super(message);
	}
}
