package StockExchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ExchangePlatform {
	static HashMap<Integer, ArrayList<Order>> availableSecurities;
	static HashMap<Integer, ArrayList<Order>> requestedSecurities;
	MatchingEngine engine;
	OrderBook orderBook;
	Scanner sc = new Scanner(System.in);
	
	public static void main (String argsp[]) {
		ExchangePlatform Ep = new ExchangePlatform();
	}
	
	public ExchangePlatform() {
		
		while(true) {
			System.out.println("------------------------");
			System.out.println("  EXCHANGE SYSTEM 1000  ");
			System.out.println("------------------------");
			System.out.println("1. Purchase");
			System.out.println("2. Sell");
			System.out.println("3. Exchange Operator Controls");
			
			int choice = getInt();
			if(choice == 1) {
				System.out.println("Choose security to purchase");
				
			}
			
		}
	}
	
	public static void addOrder (int SI, float price, float quantity, String date) {
		if(requestedSecurities.containsKey(SI)) {
			requestedSecurities.get(SI).add(new Order(SI, price, quantity, date));
		}else {
			System.out.println("Error, there is NO Security with SI:"+SI);
		}
	}
	
	public static void addAvailability (int SI, float price, float quantity, String date) {
		if(availableSecurities.containsKey(SI)) {
			availableSecurities.get(SI).add(new Order(SI, price, quantity, date));
		}else {
			System.out.println("Error, there is NO Security with SI:"+SI);
		}
	}
	
	int getInt() {
		while(true) {
			try{
				return Integer.parseInt(sc.next());
			}catch (NumberFormatException num){
				System.out.println("Enter a valid number!");
			}
		}
	}
}
