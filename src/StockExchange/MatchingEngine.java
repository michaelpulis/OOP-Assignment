package StockExchange;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class MatchingEngine {

	public void fulFillOrders(ExchangePlatform exchangePlatform) {
		// Check if there are any orders
		for(Map.Entry<Integer, ArrayList<Order>> entry: exchangePlatform.requestedSecurities.entrySet()) {
			
			int SI = entry.getKey();
			ArrayList<Order> purchaseOrders = entry.getValue();
			
			// For each security, check if there are listings that have corresponding traders
			for(Order purchaseOrder : purchaseOrders) {
				// In the event that there are no listings for this security
				if(exchangePlatform.availableSecurities.get(entry.getKey()).size() == 0){
					continue;
				}else {
					float minimum = 0;
					boolean initial = true;
					Order listingToPurchase = null;
					
					for(Order sellOrder : exchangePlatform.availableSecurities.get(entry.getKey())) {
						//Attempt to find the best suited listing-trader pairing for this particular trader
						float priceDifference = purchaseOrder.getPrice()-sellOrder.getPrice();
						if(priceDifference >= 0 && (initial || minimum > priceDifference) && purchaseOrder.getQuantity() == sellOrder.getQuantity()){
							if(initial)
								initial = false;
							
							minimum = priceDifference;
							listingToPurchase = sellOrder;
						}
					}
					
					if(listingToPurchase != null) {
						exchangePlatform.performPurchase(entry.getKey(), purchaseOrder, listingToPurchase);
						break;
					}
				}
			}
		}
	}
}
