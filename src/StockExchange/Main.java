package StockExchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
	
	
	public static void main(String args[]) {
		Main m = new Main();
	}
	
	public Main() {
		ExchangePlatform ep = new ExchangePlatform();
		
		
		while(true) {
			ExchangeUser user = ep.login.attemptLogin(ep);	
			if(user == null) {
				System.out.println("Exiting Exchange Platform...");
				break;
			}else if(user instanceof Trader) {
				ep.currentUser = (Trader) user;
				displayTraderScreen(ep);
			}else if(user instanceof Lister) {
				ep.currentUser = (Lister) user;
				displayListerScreen(ep);
			}else if(user instanceof ExchangePlatformOperator)
				displayOperatorScreen(ep);
			
		}
	}

	void displayListerScreen(ExchangePlatform ep) {
		while(true) {
			System.out.println("------------------------");
			System.out.println("   LISTER SCREEN 1000   ");
			System.out.println("------------------------");
			System.out.println("1. Add a security");
			System.out.println("2. View Securities");
			System.out.println("3. Cancel Order");
			System.out.println("4. Exit");
			int choice = Utils.getInt();
			
			if(choice == 1) {
				int newSI = ep.getNewSI();
				Lister.enlistSecurity(ep, Security.getSecurityFromInput(newSI, (Lister) ep.currentUser));
			}else if(choice == 2){
				ep.outputSecurities();
			}else if(choice ==3) {
				ep.cancelOrderRelatedTo((Lister)ep.currentUser);
			}else if(choice == 4) {
				break;
			}
		}
	}
	
	void displayOperatorScreen(ExchangePlatform ep) {
		while(true) {
			System.out.println("1. Approve new users");
			System.out.println("2. Exit");
			
			int choice = Utils.getInt();
			int[] indicies = new int [ep.login.users.size()];
			if(choice == 1) {
				System.out.println("Users pending approval:");
				System.out.println("Enter -1 to quit adding");
				int i = 0, approvedCounter = 0;
				for(ExchangeUser user : ep.login.users) {
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
					
					if(choice == -1) {
						System.out.println("Exiting...");
						break;
					}else if(indicies[choice] != -1) {
						ep.login.approveUser(indicies[choice]);
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
	
	void displayTraderScreen(ExchangePlatform ep) {
		while(true) {
			System.out.println("------------------------");
			System.out.println("  EXCHANGE SYSTEM 1000  ");
			System.out.println("------------------------");
			
			System.out.println("1. Sell");
			System.out.println("2. Purchase");
			System.out.println("3. Exchange Operator Controls");
			System.out.println("4. Print Selling");
			System.out.println("5. Print Buying");
			System.out.println("6. Run Matching");
			System.out.println("7. Ouput Logbook");
			System.out.println("8. Ouput Securites");
			System.out.println("9. Cancel Order");
			System.out.println("10. Log Out");
			
			int choice = Utils.getInt();
			if(choice == 1) {
				System.out.println("Available Securities to Sell:");
				ep.outputSecurities();
				Order inputtedOrder = Order.getOrderFromInput(ep.orderCounter++, (Trader) ep.currentUser, Type.sell);
				Trader.addOrder(ep, inputtedOrder);
				ep.engine.fulFillOrders(ep);
				
			}else if(choice == 2) {
				System.out.println("Available Securities to Purchase:");
				ep.outputSecurities();
				Order inputtedOrder = Order.getOrderFromInput(ep.orderCounter++, (Trader)ep.currentUser, Type.purchase);
				Trader.addOrder(ep, inputtedOrder);
				ep.engine.fulFillOrders(ep);
			}else if(choice == 4) {
				outputMap(ep, ep.availableSecurities);
			}else if(choice == 5) {
				outputMap(ep, ep.requestedSecurities);
			}else if(choice == 6) {
				ep.engine.fulFillOrders(ep);
			}else if(choice == 7) {
				ep.auditTrail.outputTransactions();
			}else if(choice == 8) {
				ep.outputSecurities();
			}else if(choice == 9) {
				ep.cancelOrderRelatedTo((Trader) ep.currentUser);
			}else if(choice == 10) {
				break;
			}
		}
	}
	

	void outputMap(ExchangePlatform ep, HashMap<Integer, ArrayList<Order>> map) {
		for(Map.Entry<Integer, ArrayList<Order>> mapEntry : map.entrySet()) {
			System.out.println(ep.securities.get(mapEntry.getKey()).getString());
			System.out.println("--------");
			for(Order order : mapEntry.getValue()) {
				System.out.println("\t" + order.getString());
			}
			
		}
	}
		
	
	void outputKeys(ExchangePlatform ep, HashMap<Integer, ArrayList<Order>> map) {
		for(int SI : map.keySet()) {
			System.out.println("SI: "+SI + " -- " + ep.securities.get(SI).getName());
		}
	}
}
