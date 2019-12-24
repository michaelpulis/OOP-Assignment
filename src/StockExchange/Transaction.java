package StockExchange;

public class Transaction {
	Order sellOrder;
	Order purchaseOrder;
	String time;
	
	public Transaction(Order sellOrder, Order purchaseOrder) {
		this.sellOrder = sellOrder;
		this.purchaseOrder = purchaseOrder;
		this.time = Utils.getTime();
	}
	
	String getString() {
		return "Seller:\t" +sellOrder.getTrader().getName() + " sold " + sellOrder.getStringExcl() + "\n"
		   +   "Buyer:\t" + purchaseOrder.getTrader().getName() + " sold " + purchaseOrder.getStringExcl();
	}
}
