
public class IllegalAmountExeption extends Thread{
	
	public IllegalAmountExeption();
	
	public IllegalAmountExeption(String text){
		super(text);
	}
	
	public IllegalAmountExeption(String text, Throwable couse){
        super(text, couse);
    }

    public IllegalAmountExeption(Throwable couse){
        super(couse);
    }

}
