package StockExchange;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class MatchingEngine {

	public static void fulFillOrders() {
		// Check if there are any orders
		for(Map.Entry<Security, ArrayList<Order>> entry: ExchangePlatform.requestedSecurities.entrySet()) {
			
			int SI = entry.getKey().getSI();
			ArrayList<Order> orders = entry.getValue();
			System.out.println("Checking for " + SI);
			
			// For each security, check if there are listings that have corresponding traders
			for(Order order : orders) {
				// In the event that there are no listings for this security
				if(ExchangePlatform.availableSecurities.get(entry.getKey()).size() == 0){
					continue;
				}else {
					float minimum = 0;
					boolean initial = true;
					int matchIndex = -1;
					
					for(Order listing : ExchangePlatform.availableSecurities.get(entry.getKey())) {
						//Attempt to find the best suited listing-trader pairing for this particular trader
						float priceDifference = (float)(Math.abs(order.getPrice()-listing.getPrice()));
						if(initial || minimum > priceDifference){
							if(initial)
								initial = false;
							
							minimum = priceDifference;
							matchIndex = ExchangePlatform.availableSecurities.get(entry.getKey()).indexOf(listing);
						}
					}
					
					System.out.println("Best match is: " + minimum);
					//TODO Order book add log
					
					
					
				}
			}
			
		}
	}
}
