package Models.tasks;

public class PaymentEvent extends Event{

	//LocalDate 
	private double amount;
	private boolean isIncome;
	private boolean isPaid;
	private boolean isOverdue;
	
	public PaymentEvent(String eventTitle,String description, Date, double amount, boolean isIncome, boolean isPaid) {
        super(eventTitle, description);
        this.isIncome(isIncome);
        this.isOverdue = false;
        this.isPaid = isPaid;
        //this.date = date;
        try{
        	this.setAmount(amount);
        }catch(IllegalAmountExeption e){
        	e.getMessage
        }
    }


    public double getAmmount() {
        return this.amount;
    }
    
    private void setIsIncome(boolean IsIncome){
    	this.isIncome = isIncome;
    }
    
    public boolean getIsIncome(){
    	return this.isIncome;
    }

    private void setAmount(Double amount) {
        if(amount != null && (amount >= 0){
        	this.ammount = ammount;
        }else{
        	throw new IllegalAmountExeption("Amoun of your payment must be positive !");	
        }
    }
    
    public boolean getIsOverdue(){
    	return this.isOverdue;
    }
    
    public boolean getIsPaid(){
    	return this.isPaid;
    }
    
}
