package StockExchange;

public class Trader extends ExchangeUser{
	public Trader(String user, String pass, String name, boolean approved) {
		super(user, pass, name, approved);
	}
	
	public static boolean cancelOrder(Trader trader, Order order, ExchangePlatform ep) {
		if(order.getTrader() == trader) {
			if(order.getType() == Type.purchase) {
				ep.requestedSecurities.get(order.getSI()).remove(order);
			}else if(order.getType() == Type.sell) {
				ep.availableSecurities.get(order.getSI()).remove(order);
			}
		
			order.setCancelled(true);
			return true;
		}else 
			return false;
	}
	
	public static boolean addOrder(ExchangePlatform ep, Order order) {
		if(ep.securities.get(order.getSI()).getLister().getApproved() && order.getTrader().getApproved()) {
			if(order.getType() == Type.sell) {
				ep.addOrderToMap(order, ep.availableSecurities);
				ep.addTotalSupply(order.getSI(), order.getQuantity());
			}else if(order.getType() == Type.purchase) {
				ep.addOrderToMap(order, ep.requestedSecurities);
			}
			
			return true;
		}else if(!ep.securities.get(order.getSI()).getLister().getApproved()){
			System.out.println("Lister has not been approved for trading.");
			return false;
		}else {
			System.out.println("Trader has not been approved for trading.");
			return false;
		}
	}
	
}
	