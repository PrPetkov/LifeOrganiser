package models.exceptions;

public class InsufficientAmountOfClientException extends Exception{
	
	private static String message = "Not enough money";
	
	public InsufficientAmountOfClientException() {
		super(message);
	}
	
	public InsufficientAmountOfClientException(String message) {
		super(message);
	}
}
