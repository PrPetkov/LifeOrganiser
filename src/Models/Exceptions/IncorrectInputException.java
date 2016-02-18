package models.exceptions;

public class IncorrectInputException extends Exception {
	
	private final static String MESSAGE = "You cannot input empty field for name!";
	
	public IncorrectInputException() {
		super(MESSAGE);
	}
	
	public IncorrectInputException(String text) {
		super(text);
	}
}
