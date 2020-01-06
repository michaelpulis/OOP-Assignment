package StockExchange;
import java.util.ArrayList;
import java.util.Map;



public class MatchingEngine {

	public static int fulFillOrders(ExchangePlatform exchangePlatform) {
		int matchCounter = 0;
		boolean foundMatch = false;
		ArrayList<Transaction> transactions = new ArrayList<>();
		
		do {
			
			// Check if there are any orders
			for(Map.Entry<Integer, ArrayList<Order>> entry: exchangePlatform.orderBook.getRequestedSecurities().entrySet()) {
				
				ArrayList<Order> purchaseOrders = entry.getValue();
				
				// For each security, check if there are listings that have corresponding traders
				for(Order purchaseOrder : purchaseOrders) {
					// In the event that there are no listings for this security
					if(exchangePlatform.orderBook.getAvailableSecurities().get(entry.getKey()).size() == 0){
						continue;
					}else {
						float minimum = 0;
						boolean initial = true;
						Order listingToPurchase = null;
						
						for(Order sellOrder : exchangePlatform.orderBook.getAvailableSecurities().get(entry.getKey())) {
							//Attempt to find the best suited listing-trader pairing for this particular trader
							float priceDifference = purchaseOrder.getPrice()-sellOrder.getPrice();
							if(sellOrder.getTrader() != purchaseOrder.getTrader() && !sellOrder.isEarmarked() && priceDifference >= 0 && (initial || minimum > priceDifference) && purchaseOrder.getQuantity() == sellOrder.getQuantity()){
								if(initial)
									initial = false;
								
								minimum = priceDifference;
								listingToPurchase = sellOrder;
							}
						}
						
						if(listingToPurchase != null) {
							foundMatch = true;
							matchCounter ++;
							listingToPurchase.setEarmarked(true);
							transactions.add(new Transaction(listingToPurchase, purchaseOrder));
							break;
						}else {
							foundMatch = false;
						}
					}
				}
			}
		}while (foundMatch);
		
		
		for(Transaction t : transactions) {
			exchangePlatform.performPurchase(t.getSellOrder().getSI(), t.getPurchaseOrder(), t.getSellOrder());
		}
		
		return matchCounter;
	}
}
