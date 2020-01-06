package StockExchange;

public class Trader extends ExchangeUser{
	public Trader(String user, String pass, String name, boolean approved) {
		super(user, pass, name, approved);
	}
	
	public static boolean cancelOrder(Trader trader, Order order, ExchangePlatform ep) {
		return order.getTrader() == trader && cancelOrder(ep, order);			
	}
	
	public static boolean addOrder(ExchangePlatform ep, Order order) {
		// Check that both the lister and the trader have been approved,
		// as well as if the order has been cancelled or not
		if(!order.getCancelled() && ep.securities.get(order.getSI()).getLister().getApproved() && order.getTrader().getApproved()) {
			if(order.getType() == Type.sell) {
				ep.addOrderToMap(order, ep.orderBook.getAvailableSecurities());
				ep.addTotalSupply(order.getSI(), order.getQuantity());
			}else if(order.getType() == Type.purchase) {
				ep.addOrderToMap(order, ep.orderBook.getRequestedSecurities());
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
	