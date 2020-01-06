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
		   +   "Buyer:\t" + purchaseOrder.getTrader().getName() + " purchased " + purchaseOrder.getStringExcl();
	}

	public Order getSellOrder() {
		return sellOrder;
	}

	public void setSellOrder(Order sellOrder) {
		this.sellOrder = sellOrder;
	}

	public Order getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(Order purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
}
