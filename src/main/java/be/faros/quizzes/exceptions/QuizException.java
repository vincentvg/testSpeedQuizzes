package be.faros.quizzes.exceptions;


@SuppressWarnings("serial")
//@ResponseStatus(value=HttpStatus.CONFLICT)
public class QuizException extends Exception {

	public QuizException(String arg0) {
		super(arg0);
	}

}
