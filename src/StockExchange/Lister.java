package StockExchange;

import java.util.ArrayList;

public class Lister extends ExchangeUser {
	public Lister(String user, String pass, String name, boolean approved) {
		super(user, pass, name, approved);
	}
	
	 public static boolean cancelOrder(ExchangePlatform ep, Order order, Lister lister) {
		return ep.securities.get(order.getSI()).getLister() == lister && cancelOrder(ep, order);
	}
	 
	public static boolean enlistSecurity(ExchangePlatform ep, Security security) {
		 if(security.getLister().getApproved()) {
			ep.securities.put(security.getSI(), security);
			ep.orderBook.initialiseListForSI(security.getSI());
			
			return true;
		 }else {
			 return false;
		 }
	}
}
