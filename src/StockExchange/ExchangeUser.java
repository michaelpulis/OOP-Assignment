package StockExchange;

public class ExchangeUser {
	private Tuple loginData;
	private String name;
	private boolean approved;
	
	public ExchangeUser() {}
	
	public ExchangeUser(String user, String pass, String name, boolean approved) {
		loginData = new Tuple(user, pass);
		this.approved = approved;
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
	
	public String getString() {
		return "Username:\t" + getUsername() + "\tPassword:\t"+getPassword()+"\tName\t"+name;
	}
	
	public boolean cancelOrder(ExchangePlatform ep, int SI, Order order) {
		
		// Checking if order is a cancel order or a purchase oder
		if(order.getType() == Type.purchase) {
			
		}else if(order.getType() == Type.sell){
			
		}
		
		return false;
	}
		
}
