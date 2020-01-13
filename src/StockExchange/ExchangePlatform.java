package StockExchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExchangePlatform {
	
	HashMap<Integer, Security> securities = new HashMap<>();
	private int orderCounter = 0, siCounter = 0;
	
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
	
	
	public boolean cancelOrder(Order order) {
		if(!order.getCancelled()) {
			if(order.getType() == Type.purchase) {
				orderBook.removeOrder(order, Type.purchase);
			}else if(order.getType() == Type.sell) {
				orderBook.removeOrder(order, Type.sell);
				securities.get(order.getSI()).setTotalSupply(securities.get(order.getSI()).getTotalSupply() - order.getQuantity());
			}
			order.setCancelled(true);
			return true;
		}else
			return false;			
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
		System.out.println("Order fulfilled between the following (SI = " + sellOrder.getSI()+ "):");
		System.out.println("BUY :" + purchaseOrder.getString());
		System.out.println("SELL:" + sellOrder.getString());
		
		Transaction temp = new Transaction(sellOrder, purchaseOrder);
		
		auditTrail.addTransaction(temp);
		
		orderBook.removeOrder(purchaseOrder, Type.purchase);
		orderBook.removeOrder(sellOrder, Type.sell);
		//availableSecurities.get(SI).remove(sellOrder);
		//requestedSecurities.get(SI).remove(purchaseOrder);
		
		//Since total supply has decreased, decrease the total supply
		securities.get(SI).setTotalSupply(securities.get(SI).getTotalSupply() - purchaseOrder.getQuantity());
		System.out.println();
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
	
	void cancelOrderRelatedTo(Lister lister) {
		//List purchase orders.
		System.out.println("The following are securities listed by " + lister.getName());
		System.out.println("Select an order to cancel from the following:");
		
		System.out.println("Purchase Orders:");
		for(Map.Entry<Integer, ArrayList<Order>> mapEntry : orderBook.getAvailableSecurities().entrySet()) {
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
		for(Map.Entry<Integer, ArrayList<Order>> mapEntry : orderBook.getAvailableSecurities().entrySet()) {
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
		
		if(!orderBook.getRequestedSecurities().containsKey(inputtedSI)) {
			System.out.println("Invalid indicies entered.");
		}else {
			//Check which Order is to be deleted
			
			for(Order order: orderBook.getAvailableSecurities().get(inputtedSI)) {
				if(order.getOrderID() == inputtedOrderID) 
					if(Lister.cancelOrder(this,  order, lister)) 
						System.out.println("Order Cancelled");
					else
						System.out.println("Current user does not have permissions over order requested!");
					
				break;
					
				
				
			}
			
			for(Order order: orderBook.getRequestedSecurities().get(inputtedSI)) {
				if(order.getOrderID() == inputtedOrderID)
					if(Lister.cancelOrder(this, order, lister))
						System.out.println("Order Cancelled");
					else
						System.out.println("Current user does not have permissions over order requested!");
					
				
				break;
			}
		}
	}
	
	void cancelOrderRelatedTo(Trader trader) {
		//List purchase orders.
		System.out.println("Select an order to cancel from the following:");
		
		System.out.println("Purchase Orders:");
		for(Map.Entry<Integer, ArrayList<Order>> mapEntry : orderBook.getRequestedSecurities().entrySet()) {
			
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
		for(Map.Entry<Integer, ArrayList<Order>> mapEntry : orderBook.getAvailableSecurities().entrySet()) {
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
		
		if(!orderBook.getRequestedSecurities().containsKey(inputtedSI)) {
			System.out.println("Invalid indicies entered.");
		}else {
		
				//Check which Order is to be deleted
			for(Order order: orderBook.getAvailableSecurities().get(inputtedSI)) {
				if(order.getOrderID() == inputtedOrderID) {
					if(Trader.cancelOrder(trader, order, this)) {
						System.out.println("Order cancelled");
						break;
					}else
						System.out.println("Current user does not have permissions over order requested!");
						break;
					
				}
			}
			
			for(Order order: orderBook.getRequestedSecurities().get(inputtedSI)) {
				if(order.getOrderID() == inputtedOrderID) {
					if(Trader.cancelOrder(trader, order, this)) {
						System.out.println("Order cancelled");
						break;
					}else 
						System.out.println("Current user does not have permissions over order requested!");
						break;
				}	
			}
		}
	}	
	
	public int getNewSI() {
		return siCounter ++;
	}
	
	public int getNewOrder() {
		return orderCounter ++;
	}
}
