package StockExchange;

import java.util.ArrayList;

public class Lister extends ExchangeUser {
	public Lister(String user, String pass, String name, boolean approved) {
		super(user, pass, name, approved);
	}
	
	 public static boolean cancelOrder(ExchangePlatform ep, Order order, Lister lister) {
		if(ep.securities.get(order.getSI()).getLister() == lister) {
			if(order.getType() == Type.purchase) {
				ep.orderBook.removeOrderBySI(order, Type.purchase);
			}else if(order.getType() == Type.sell) {
				ep.orderBook.removeOrderBySI(order, Type.sell);
			}
		
			order.setCancelled(true);
			return true;
		}else {
			return false;
		}
		
	}

	 public static boolean enlistSecurity(ExchangePlatform ep, Security security) {
		 if(security.getLister().getApproved()) {
			ep.securities.put(security.getSI(), security);
			ep.availableSecurities.put(security.getSI(), new ArrayList<Order>());
			ep.requestedSecurities.put(security.getSI(), new ArrayList<Order>());
			return true;
		 }else {
			 return false;
		 }
	}
}
