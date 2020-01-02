package StockExchange;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderBook {
	HashMap<Integer, ArrayList<Order>> availableSecurities = new HashMap<>();
	HashMap<Integer, ArrayList<Order>> requestedSecurities = new HashMap<>();
	
	
	public HashMap<Integer, ArrayList<Order>> getAvailableSecurities() {
		return availableSecurities;
	}
	public void setAvailableSecurities(HashMap<Integer, ArrayList<Order>> availableSecurities) {
		this.availableSecurities = availableSecurities;
	}
	public HashMap<Integer, ArrayList<Order>> getRequestedSecurities() {
		return requestedSecurities;
	}
	public void setRequestedSecurities(HashMap<Integer, ArrayList<Order>> requestedSecurities) {
		this.requestedSecurities = requestedSecurities;
	}
	
	
	
	void removeOrderBySI(Order order, Type orderType) {
		if(orderType == Type.purchase)
			requestedSecurities.get(order.getSI()).remove(order);
		else
			availableSecurities.get(order.getSI()).remove(order);
	}
}
