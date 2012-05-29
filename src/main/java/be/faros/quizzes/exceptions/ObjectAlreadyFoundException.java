package be.faros.quizzes.exceptions;


@SuppressWarnings("serial")
//@ResponseStatus(value=HttpStatus.CONFLICT)
public class ObjectAlreadyFoundException extends Exception {

	

	public ObjectAlreadyFoundException(String message) {
		super(message);
	}

	
}
