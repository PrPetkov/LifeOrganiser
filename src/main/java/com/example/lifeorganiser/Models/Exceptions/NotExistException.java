package models.exceptions;

public class NotExistException extends Exception {
	
	private static String message= "Item does not exist!";
	
	public NotExistException() {
		super(message);
	}
	
	public NotExistException(String message) {
		super(message);
	}
	
	
	
}
