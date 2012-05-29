package be.faros.quizzes.exceptions;


@SuppressWarnings("serial")
//@ResponseStatus(value=HttpStatus.CONFLICT)
public class UserException extends Exception {

	public UserException(String arg0) {
		super(arg0);		
	}
}
