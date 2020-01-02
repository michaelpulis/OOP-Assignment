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
	
	HashMap<Integer, Security> securities = new HashMap<>();
	public int orderCounter = 0;
	
	MatchingEngine engine;
	AuditTrail auditTrail;
	OrderBook orderBook;
	public LoginSystem login;
	
	Scanner sc = new Scanner(System.in);
	boolean signedIn = false;
	ExchangeUser currentUser;
	
	public static void main (String argsp[]) {
		ExchangePlatform Ep = new ExchangePlatform();
	}
	
	public ExchangePlatform() {
			
		login = new LoginSystem();
		engine = new MatchingEngine();
		auditTrail = new AuditTrail();
		orderBook = new OrderBook();
	}
	
	
	void cancelOrder(Order order) {
		if(order.getType() == Type.purchase) {
			orderBook.removeOrderBySI(order, Type.purchase);
		}else if(order.getType() == Type.sell) {
			orderBook.removeOrderBySI(order, Type.sell);
			availableSecurities.get(order.getSI()).remove(order);
		}
		order.setCancelled(true);
	}
	
	
	
	
	
	void outputSecurities() {
		for(Map.Entry<Integer, Security> mapEntry : securities.entrySet()) {
			Security security = mapEntry.getValue();
			System.out.println(security.getString());
		}
			
	}
	
	protected void addTotalSupply(int SI, float quantity) {
		securities.get(SI).setTotalSupply(securities.get(SI).getTotalSupply() + quantity);
	}
	
	public void performPurchase(int SI, Order purchaseOrder, Order sellOrder) {
		System.out.println("Order fulfilled between the following:");
		System.out.println("BUY :" + purchaseOrder.getString());
		System.out.println("SELL:" + sellOrder.getString());
		
		Transaction temp = new Transaction(sellOrder, purchaseOrder);
		
		auditTrail.addTransaction(temp);
		availableSecurities.get(SI).remove(sellOrder);
		requestedSecurities.get(SI).remove(purchaseOrder);
		
		//Since total supply has decreased, decrease the total supply
		securities.get(SI).setTotalSupply(securities.get(SI).getTotalSupply() - purchaseOrder.getQuantity());
	}
	
	void addOrderToMap(Order order, HashMap<Integer, ArrayList<Order>> map){
		boolean found = false;
		for(Map.Entry<Integer, ArrayList<Order>> mapEntry : map.entrySet()) {
			int SI = mapEntry.getKey();
			
			if(SI != order.getSI()) {
				continue;
			}else {
				found = true;
				map.get(SI).add(order);
				break;
			}
		}
	}
	
	void debugManualAddSecurity(String name, String description, float price, float totalSupply, int SI) {
		Security temp = new Security(name, description, price, SI, null);
		availableSecurities.put(SI, new ArrayList<Order>());
		requestedSecurities.put(SI, new ArrayList<Order>());
		securities.put(SI,  temp);
	}
	
	void cancelOrderRelatedTo(Lister lister) {
		//List purchase orders.
		System.out.println("The following are securities listed by " + lister.getName());
		System.out.println("Select an order to cancel from the following:");
		
		System.out.println("Purchase Orders:");
		for(Map.Entry<Integer, ArrayList<Order>> mapEntry : requestedSecurities.entrySet()) {
			if(securities.get(mapEntry.getKey()).getLister() == lister) {
				System.out.println("\tSECURITIES: "+securities.get(mapEntry.getKey()).getString());
				System.out.println("\t-------------------------------------------------------");
				ArrayList<Order> purchaseOrders = mapEntry.getValue();
				for(Order order : purchaseOrders) {
					System.out.println("\t\tORDERS: "+order.getString());
				}
			}
			System.out.println();
		}
			
		//List sell orders
		System.out.println("Sell Orders:");
		for(Map.Entry<Integer, ArrayList<Order>> mapEntry : availableSecurities.entrySet()) {
			if(securities.get(mapEntry.getKey()).getLister() == lister) {
				System.out.println("\tSECURITY: "+securities.get(mapEntry.getKey()).getString());
				System.out.println("\t-------------------------------------------------------");
				ArrayList<Order> sellOrders = mapEntry.getValue();
				for(Order order : sellOrders) {
					System.out.println("\t\tORDER: "+order.getString());
				}
			}
			System.out.println();
		}
		
		int inputtedSI, inputtedOrderID;
		System.out.println("Enter SI:");
		inputtedSI = Utils.getInt();
		System.out.println("Enter order number: ");
		inputtedOrderID = Utils.getInt();
		
		if(!requestedSecurities.containsKey(inputtedSI)) {
			System.out.println("Invalid indicies entered.");
		}else {
			//Check which Order is to be deleted
			
			for(Order order: availableSecurities.get(inputtedSI)) {
				if(order.getOrderID() == inputtedOrderID) 
					if(Lister.cancelOrder(this,  order, lister)) 
						System.out.println("Order Cancelled");
					else
						System.out.println("Current user does not have permissions over order requested!");
					
					
				
				System.out.println();
			}
			
			for(Order order: requestedSecurities.get(inputtedSI)) {
				if(order.getOrderID() == inputtedOrderID)
					if(Lister.cancelOrder(this, order, lister))
						System.out.println("Order Cancelled");
					else
						System.out.println("Current user does not have permissions over order requested!");
					
				
				System.out.println();
			}
		}
	}
	
	void cancelOrderRelatedTo(Trader trader) {
		//List purchase orders.
		System.out.println("Select an order to cancel from the following:");
		
		System.out.println("Purchase Orders:");
		for(Map.Entry<Integer, ArrayList<Order>> mapEntry : requestedSecurities.entrySet()) {
			
			System.out.println("\t"+securities.get(mapEntry.getKey()).getString());
			System.out.println("\t-------------------------------------------------------");
			ArrayList<Order> purchaseOrders = mapEntry.getValue();
			for(Order order : purchaseOrders) {
				if(order.getTrader() == trader) {
					System.out.println("\t\tORDER: "+order.getString());
				}
			}
			
			System.out.println();
		}
			
		//List sell orders
		System.out.println("Sell Orders:");
		for(Map.Entry<Integer, ArrayList<Order>> mapEntry : availableSecurities.entrySet()) {
			System.out.println("\tSECURITY: "+securities.get(mapEntry.getKey()).getString());
			System.out.println("\t-------------------------------------------------------");
			ArrayList<Order> purchaseOrders = mapEntry.getValue();
			for(Order order : purchaseOrders) {
				if(order.getTrader() == trader) {
					System.out.println("\t\tORDER: "+order.getString());
				}
			}
			System.out.println();
		}
		
		int inputtedSI, inputtedOrderID;
		System.out.println("Enter SI:");
		inputtedSI = Utils.getInt();
		System.out.println("Enter order number: ");
		inputtedOrderID = Utils.getInt();
		
		if(!requestedSecurities.containsKey(inputtedSI)) {
			System.out.println("Invalid indicies entered.");
		}else {
		
				//Check which Order is to be deleted
			for(Order order: availableSecurities.get(inputtedSI)) {
				if(order.getOrderID() == inputtedOrderID) {
					if(Trader.cancelOrder(trader, order, this)) {
						System.out.println("Order cancelled");
					}else
						System.out.println("Current user does not have permissions over order requested!");
					
				}
			}
			
			for(Order order: requestedSecurities.get(inputtedSI)) {
				if(order.getOrderID() == inputtedOrderID) {
					if(Trader.cancelOrder(trader, order, this)) {
						System.out.println("Order cancelled");
					}else 
						System.out.println("Current user does not have permissions over order requested!");
				}	
			}
		}
	}
	
	void addSecurityFromInput() {
		System.out.println("Enter name:");
		String name = sc.next();
		
		System.out.println("Enter description:");
		String description = sc.next();

		System.out.println("Enter price:");
		float price = Utils.getFloat();
		
		int SI = getNewSI();
		
		Lister.enlistSecurity(this, new Security(name, description, price, SI , (Lister) currentUser));
	}
	
	
	
	public int getNewSI() {
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
