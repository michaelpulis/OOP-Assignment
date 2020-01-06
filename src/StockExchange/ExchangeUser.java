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
	
	protected void setApproved(boolean b) {
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
	
	public static boolean cancelOrder(ExchangePlatform ep, Order order) {
		if(!order.getCancelled() && ep.securities.get(order.getSI()).getLister().getApproved() && order.getTrader().getApproved()) {
			if(order.getType() == Type.purchase) {
				ep.orderBook.removeOrder(order, Type.purchase);
			}else if(order.getType() == Type.sell) {
				ep.orderBook.removeOrder(order, Type.sell);
				//reduce total supply
				ep.securities.get(order.getSI()).setTotalSupply(ep.securities.get(order.getSI()).getTotalSupply() - order.getQuantity());
			}
			
			order.setCancelled(true);
			return true;
		}else
			return false;
		
	}
		
}
