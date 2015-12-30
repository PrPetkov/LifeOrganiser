package appObjects.accounts;


public abstract class Account extends User implements IAccount {
	
	private String accountName;
	private String accountPassword;
	
	Account(String acountName, String accountPassword) {
		setAccountName(acountName);
		getAccountName(accountPassword);
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getAccountName() {
		return this.accountName;
	}
	
	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
	
	public String getAccountPassword() {
		return this.accountPassword;
	}
}
