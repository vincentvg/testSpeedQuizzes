package be.faros.quizzes.exceptions;


@SuppressWarnings("serial")
//@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
public class IncorrectValueException extends Exception {
	
	public IncorrectValueException(String arg0) {
		super(arg0);
	}
}
