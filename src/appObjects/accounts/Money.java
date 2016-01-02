
public class Money {

	private String currency; // TODO currency
	private BigDecimal ammount;

	Account account;

	Account addMoney(double addedAmmount) {
		if (addedAmmount <= 0) {
			System.out.println("Must add positive ammount.");
			return this.ammount;
		}
		return this.ammount += addedAmmount;
	}

	Account removeMoney(double removedAmmount) {
		if (this.ammount < removedAmmount) {
			System.out.println("Insufficient money for operation.");
			return this.ammount;
		}
		return this.ammount -= removedAmmount;
	}

	// getters and setters
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmmount() {
		return this.ammount;
	}

	public void setAmmount(BigDecimal ammount) {
		this.ammount = ammount;
	}
}
