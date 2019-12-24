package StockExchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Date;
import java.time.LocalTime;
import java.time.LocalDate;

public class ExchangePlatform {
	static HashMap<Security, ArrayList<Order>> availableSecurities = new HashMap<>();
	static HashMap<Security, ArrayList<Order>> requestedSecurities = new HashMap<>();
	ArrayList<ExchangeUser> users = new ArrayList<>();
	MatchingEngine engine;
	OrderBook orderBook;
	Scanner sc = new Scanner(System.in);
	boolean signedIn = false;
	ExchangeUser currentUser;
	
	public static void main (String argsp[]) {
		ExchangePlatform Ep = new ExchangePlatform();
	}
	
	public ExchangePlatform() {
		
		while(true) {
			System.out.println("------------------------");
			System.out.println("  EXCHANGE SYSTEM 1000  ");
			System.out.println("------------------------");
			
			if(!signedIn) {

				while(!signedIn) {
					System.out.println("1. Log In");
					System.out.println("2. Sign Up");
					
					int choice = Utils.getInt();
					
					System.out.println("Enter your username:");
					String userName = sc.next().trim();
					System.out.println("Enter your password");
					String password = sc.next().trim();
					
					if(choice == 1) {
						//checking if username exists
						for(ExchangeUser user : users) {
							if(user.getName().equals(userName) && user.getPassword().equals(password)) {
								this.currentUser = user;
								signedIn = true;
								break;
							}else {
								System.out.println("Invalid login details.");
							}
						}
					}else if (choice == 2) {	 
						while(true) {
							System.out.println("Enter your name");
							String name = sc.next();
							
							//checking if username exists
							for(ExchangeUser user : users) {
								if(user.getName().equals(userName)) {
									System.out.println("User already exists!");
									continue;
								}
							}
							ExchangeUser temp = new ExchangeUser();
							temp.setLoginDetails(userName, password, name);
							System.out.println("Adding user");
							users.add(temp);
							break;
						}
					}
					
					
					
					
					
				}
				
				
				
				
			}else {
				debugManualAddSecurity("RS2", "Banking Software", 2.14f, 1000, 60);
				addOrderToMap(debugManualOrder(2.2f, 10, "Today", 60), availableSecurities);
				addOrderToMap(debugManualOrder(3.5f, 10, "Today", 60), availableSecurities);
				addOrderToMap(debugManualOrder(2.3f, 10, "Today", 60), requestedSecurities);
				addOrderToMap(debugManualOrder(2.8f, 10, "Today", 60), requestedSecurities);
				
				System.out.println("1. Sell");
				System.out.println("2. Purchase");
				System.out.println("3. Exchange Operator Controls");
				System.out.println("4. Print Selling");
				System.out.println("5. Print Buying");
				System.out.println("6. Run Matching");
				
				int choice = Utils.getInt();
				if(choice == 1) {
					System.out.println("Choose security to Sell");
					outputKeys(availableSecurities);
					Order inputtedOrder = Order.getOrderFromInput((Trader) currentUser);
					addOrderToMap(inputtedOrder, availableSecurities);
					outputMap(availableSecurities);
					engine.fulFillOrders();
					
				}else if(choice == 2) {
					System.out.println("Choose security to Purchase");
					outputKeys(requestedSecurities);
					Order inputtedOrder = Order.getOrderFromInput((Trader) currentUser);
					addOrderToMap(inputtedOrder, requestedSecurities);
					outputMap(requestedSecurities);
					engine.fulFillOrders();
				}else if(choice == 4) {
					outputMap(availableSecurities);
				}else if(choice == 5) {
					outputMap(requestedSecurities);
				}else if(choice == 6) {
					MatchingEngine.fulFillOrders();
				}
			}
			
			
			
		}
	}
	
		
	public void addOrder (int SI, float price, float quantity, String date) {
		if(requestedSecurities.containsKey(SI)) {
			requestedSecurities.get(SI).add(new Order(SI, price, quantity, date, (Trader) currentUser));
		}else {
			System.out.println("Error, there is NO Security with SI:"+SI);
		}
	}
	
	public void addAvailability (int SI, float price, float quantity, String date) {
		if(availableSecurities.containsKey(SI)) {
			availableSecurities.get(SI).add(new Order(SI, price, quantity, date, (Trader) currentUser));
		}else {
			System.out.println("Error, there is NO Security with SI:"+SI);
		}
	}
	
	void addOrderToMap(Order order, HashMap<Security, ArrayList<Order>> map){
		for(Map.Entry<Security, ArrayList<Order>> mapEntry : map.entrySet()) {
			Security tempSec = mapEntry.getKey();
			if(tempSec.getSI() != order.getSI()) {
				continue;
			}else {
				map.get(tempSec).add(order);
			}
		}
	}
	
	void outputMap(HashMap<Security, ArrayList<Order>> map) {
		for(Map.Entry<Security, ArrayList<Order>> mapEntry : map.entrySet()) {
			System.out.println(mapEntry.getKey().getString());
			System.out.println("--------");
			for(Order order : mapEntry.getValue()) {
				System.out.println("\t" + order.getString());
			}
			
		}
	}
	
	// Kumdita methods
	
	void debugManualAddSecurity(String name, String description, float price, float totalSupply, int SI) {
		Security temp = new Security(name, description, price, totalSupply, SI);
		availableSecurities.put(temp, new ArrayList<Order>());
		requestedSecurities.put(temp, new ArrayList<Order>());
	}
	
	void addSecurityFromInput() {
		System.out.println("Enter name:");
		String name = sc.next();
		
		System.out.println("Enter description:");
		String description = sc.next();

		System.out.println("Enter price:");
		float price = Utils.getFloat();
		
		System.out.println("Enter total supply:");
		float totalSupply = Utils.getFloat();
		
		int SI = getNewSI();
		
		Security temp = new Security(name, description, price, totalSupply, SI);
		availableSecurities.put(temp, new ArrayList<Order>());
		requestedSecurities.put(temp, new ArrayList<Order>());
	}
	
	Order debugManualOrder(float price, float quantity, String date, int SI) {
		return new Order(SI, price, quantity, date, (Trader) currentUser);
	}
	
	
	
	void outputKeys(HashMap<Security, ArrayList<Order>> map) {
		for(Security security : map.keySet()) {
			System.out.println("SI: "+security.getSI() + " -- " + security.getName());
		}
	}
	
	
	int getNewSI() {
		while(true) {
			int random = (int)(Math.random() * 100);
			if(!availableSecurities.isEmpty())
				for(Security security : availableSecurities.keySet()) {
					if(security.getSI() != random)
						return random;
				}
			else
				return random;
		}
	}
}
