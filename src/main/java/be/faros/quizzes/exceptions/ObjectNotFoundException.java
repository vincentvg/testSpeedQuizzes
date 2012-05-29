package be.faros.quizzes.exceptions;

@SuppressWarnings("serial")
//@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends Exception {

	public ObjectNotFoundException(String arg0) {
		super(arg0);
	}
}
