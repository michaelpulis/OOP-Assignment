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
	static HashMap<Integer, ArrayList<Order>> availableSecurities = new HashMap<>();
	static HashMap<Integer, ArrayList<Order>> requestedSecurities = new HashMap<>();
	HashMap<Integer, Security> securities = new HashMap<>();
	
	MatchingEngine engine;
	OrderBook orderBook;
	LoginSystem login;
	
	Scanner sc = new Scanner(System.in);
	boolean signedIn = false;
	ExchangeUser currentUser;
	
	public static void main (String argsp[]) {
		ExchangePlatform Ep = new ExchangePlatform();
	}
	
	public ExchangePlatform() {
			
		login = new LoginSystem();
		engine = new MatchingEngine();
		orderBook = new OrderBook();
		
		debugManualAddSecurity("RS2", "Banking Software", 2.14f, 0, 1);
		
		while(true) {
			ExchangeUser user = login.attemptLogin();	
			if(user == null) {
				System.out.println("Error. Please contact the System Admin? haha this should never occur.");
				break;
			}else if(user instanceof Trader) {
				currentUser = (Trader) user;
				displayTraderScreen();
			}else if(user instanceof Lister) {
				currentUser = (Lister) user;
				displayListerScreen();
			}else if(user instanceof ExchangePlatformOperator)
				displayOperatorScreen();
			
		}
	}
	
	void displayListerScreen() {
		System.out.println("------------------------");
		System.out.println("   LISTER SCREEN 1000   ");
		System.out.println("------------------------");
		System.out.println("1. Add a security");
		System.out.println("2. View Securities");
		System.out.println("3. Cancel Order");
		System.out.println("4. Exit");
		
		while(true) {
			int choice = Utils.getInt();
			
			if(choice == 1) {
				//securities.put(getNewSI(), getSecurityFromInput());
				//debugManualAddSecurity("RS2", "Banking Software", 2.14f, 0, 1);
			}
		}
	}
	
	void displayOperatorScreen() {
		while(true) {
			System.out.println("1. Approve new users");
			System.out.println("2. Exit");
			
			int choice = Utils.getInt();
			int[] indicies = new int [login.users.size()];
			if(choice == 1) {
				System.out.println("Users pending approval:");
				int i = 0, approvedCounter = 0;
				for(ExchangeUser user : login.users) {
					if(user.getApproved())
						indicies[i] = -1;
					else {
						System.out.println((1+approvedCounter)+ ") " + user.getString());
						indicies[approvedCounter++] = i;
					}
					i++;
				}
				
				while(true) {
					choice = Utils.getInt() - 1;
					if(indicies[choice] != -1) {
						login.approveUser(indicies[choice]);
						break;
					}else {
						System.out.println("Not a valid index. Enter valid number:");
					}
				}
			}else if (choice == 2) {
				break;
			}
		}
	}
	
	void displayTraderScreen() {
		
		
		while(true) {
			System.out.println("------------------------");
			System.out.println("  EXCHANGE SYSTEM 1000  ");
			System.out.println("------------------------");
			
			//addOrderToMap(debugManualOrder(2.2f, 10, "Today", 60), availableSecurities);
			//addOrderToMap(debugManualOrder(3.5f, 10, "Today", 60), availableSecurities);
			//addOrderToMap(debugManualOrder(2.3f, 10, "Today", 60), requestedSecurities);
			//addOrderToMap(debugManualOrder(2.8f, 10, "Today", 60), requestedSecurities);
			
			System.out.println("1. Sell");
			System.out.println("2. Purchase");
			System.out.println("3. Exchange Operator Controls");
			System.out.println("4. Print Selling");
			System.out.println("5. Print Buying");
			System.out.println("6. Run Matching");
			System.out.println("7. Ouput Logbook");
			System.out.println("8. Ouput Securites");
			System.out.println("9. Log Out");
			
			
			
			int choice = Utils.getInt();
			if(choice == 1) {
				System.out.println("Choose security to Sell");
				outputKeys(availableSecurities);
				Order inputtedOrder = Order.getOrderFromInput((Trader) currentUser);
				addOrderToMap(inputtedOrder, availableSecurities);
				addTotalSupply(inputtedOrder.getSI(), inputtedOrder.getQuantity());
				engine.fulFillOrders(this);
				
			}else if(choice == 2) {
				System.out.println("Choose security to Purchase");
				outputKeys(requestedSecurities);
				Order inputtedOrder = Order.getOrderFromInput((Trader)currentUser);
				addOrderToMap(inputtedOrder, requestedSecurities);
				engine.fulFillOrders(this);
			}else if(choice == 4) {
				outputMap(availableSecurities);
			}else if(choice == 5) {
				outputMap(requestedSecurities);
			}else if(choice == 6) {
				engine.fulFillOrders(this);
			}else if(choice == 7) {
				orderBook.outputTransactions();
			}else if(choice == 8) {
				outputSecurities();
			}else if(choice == 9) {
				break;
			}
		}
	}
	
	void outputSecurities() {
		for(Map.Entry<Integer, Security> mapEntry : securities.entrySet()) {
			Security security = mapEntry.getValue();
			System.out.println(security.getString());
		}
			
	}
	
	void addTotalSupply(int SI, float quantity) {
		for(int securitySI : availableSecurities.keySet()) {
			if(securitySI == SI) {
				securities.get(SI).setTotalSupply(securities.get(SI).getTotalSupply() + SI);
			}
		}
	}
	
	public void performPurchase(int SI, Order purchaseOrder, Order sellOrder) {
		System.out.println("Order match between the following two orders:");
		System.out.println("BUY :" + purchaseOrder.getString());
		System.out.println("SELL:" + sellOrder.getString());
		
		Transaction temp = new Transaction(sellOrder, purchaseOrder);
		orderBook.addTransaction(temp);
		availableSecurities.get(SI).remove(sellOrder);
		requestedSecurities.get(SI).remove(purchaseOrder);
		
	}
		
	public void addOrder (int SI, float price, float quantity, String date) {
		if(requestedSecurities.containsKey(SI)) {;
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
	
	void addOrderToMap(Order order, HashMap<Integer, ArrayList<Order>> map){
		for(Map.Entry<Integer, ArrayList<Order>> mapEntry : map.entrySet()) {
			int SI = mapEntry.getKey();
			System.out.println(SI + " vs " + order.getSI());
			
			if(SI != order.getSI()) {
				continue;
			}else {
				map.get(SI).add(order);
			}
		}
	}
	
	void outputMap(HashMap<Integer, ArrayList<Order>> map) {
		for(Map.Entry<Integer, ArrayList<Order>> mapEntry : map.entrySet()) {
			System.out.println(securities.get(mapEntry.getKey()).getDescription());
			System.out.println("--------");
			for(Order order : mapEntry.getValue()) {
				System.out.println("\t" + order.getString());
			}
			
		}
	}
	
	// Kumdita methods
	
	void debugManualAddSecurity(String name, String description, float price, float totalSupply, int SI) {
		Security temp = new Security(name, description, price, totalSupply, SI);
		availableSecurities.put(SI, new ArrayList<Order>());
		requestedSecurities.put(SI, new ArrayList<Order>());
		securities.put(SI,  temp);
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
		availableSecurities.put(SI, new ArrayList<Order>());
		requestedSecurities.put(SI, new ArrayList<Order>());
	}
	
	Order debugManualOrder(float price, float quantity, String date, int SI) {
		Trader temp = (Trader) currentUser;
		return new Order(SI, price, quantity, date, temp);
	}
	
	void outputKeys(HashMap<Integer, ArrayList<Order>> map) {
		for(int SI : map.keySet()) {
			System.out.println("SI: "+SI + " -- " + securities.get(SI).getName());
		}
	}
	
	int getNewSI() {
		while(true) {
			int random = (int)(Math.random() * 100);
			if(!availableSecurities.isEmpty())
				for(int SI : availableSecurities.keySet()) {
					if(SI != random)
						return random;
				}
			else
				return random;
		}
	}
}
