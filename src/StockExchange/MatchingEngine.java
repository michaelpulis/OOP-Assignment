package StockExchange;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchingEngine {

	public void fulFillOrders() {
		// Check if there are any orders
		for(Map.Entry<Integer, ArrayList<Order>> entry: ExchangePlatform.requestedSecurities.entrySet()) {
			
			int SI = entry.getKey();
			ArrayList<Order> orders = entry.getValue();
			System.out.println("Checking for " + SI);
			
			// For each security, check if there are listings that have corresponding traders
			for(Order order : orders) {
				// In the event that there are no listings for this security
				if(ExchangePlatform.availableSecurities.get(SI).size() == 0){
					continue;
				}else {
					float minimum = 0;
					boolean initial = true;
					int matchIndex = -1;
					
					for(Order listing : ExchangePlatform.availableSecurities.get(SI)) {
						//Attempt to find the best suited listing-trader pairing for this particular trader
						float priceDifference = (int)(Math.abs(order.getPrice()-listing.getPrice()));
						if(initial || minimum > priceDifference){
							if(initial)
								initial = false;
							
							minimum = priceDifference;
							matchIndex = ExchangePlatform.availableSecurities.get(SI).indexOf(listing);
						}
					}
					
					System.out.println("Best match is: " + minimum);
					//TODO Order book add log
					
					
					
				}
			}
			
		}
	}
}
