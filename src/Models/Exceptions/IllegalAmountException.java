package models.exceptions;

public class IllegalAmountException extends Exception {
	
	public IllegalAmountException() {
		
	}
	
	public IllegalAmountException(String text){
		super(text);
	}
	
	public IllegalAmountException(String text, Throwable couse){
        super(text, couse);
    }

    public IllegalAmountException(Throwable couse){
        super(couse);
    }

}
