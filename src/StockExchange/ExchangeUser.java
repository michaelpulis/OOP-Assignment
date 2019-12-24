package StockExchange;

public class ExchangeUser {
	private Tuple loginData;
	private String name;
	private boolean approved;
	
	void setLoginDetails(String user, String pass, String name) {
		loginData = new Tuple(user, pass);
		this.setName(name);
	}
	
	void setApproved(boolean b) {
		this.approved = b;
	}
	
	boolean getApproved() {
		return this.approved;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsername() {
		return loginData.user;
	}
	
	public String getPassword() {
		return loginData.password;
	}
		
}
