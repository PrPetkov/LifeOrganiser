package appObjects.accounts;


public abstract class CreditAccount extends Account {
	
	private String creditAccountName;
	
	CreditAccount(String creditAccountName) {
		setCreditAccountName(creditAccountName);
	}
	
	// getters and setters
	public String getCreditAccountName() {
		return this.creditAccountName;
	}
	
	public void setCreditAccountName(String creditAccountName) {
		this.creditAccountName = creditAccountName;
	}
	
}
