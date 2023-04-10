package mediscreen.noteapi.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 737222464602962113L;

	public EntityNotFoundException(String message) {
		super(message);
	}
}
