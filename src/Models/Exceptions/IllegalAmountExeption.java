
public class IllegalAmountExeption extends Thread{
	
	public IllegalAmountExeption();
	
	public IllegalAmountExeption(String text){
		super(text);
	}
	
	public AccountException(String text, Throwable couse){
        super(text, couse);
    }

    public AccountException(Throwable couse){
        super(couse);
    }
}
