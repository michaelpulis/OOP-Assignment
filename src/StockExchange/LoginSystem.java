package StockExchange;

import java.util.ArrayList;
import java.util.Scanner;

public class LoginSystem {
	
	public ArrayList<ExchangeUser> users = new ArrayList<>();
	
	public ExchangeUser attemptLogin(ExchangePlatform ep) {
		boolean signedIn = false;
		Scanner sc = new Scanner(System.in);
		
		while(!signedIn) {
			System.out.println("1. Log In");
			System.out.println("2. Sign Up");
			System.out.println("3. Load Test Scenario");
			System.out.println("4. Exit");
			
			
			int choice = Utils.getInt();
			
			if(choice == 1) {
				System.out.println("Enter your username:");
				String userName = sc.next().trim();
				System.out.println("Enter your password");
				String password = sc.next().trim();
				
				if(userName.equals("admin") && password.equals("password")) {
					return new ExchangePlatformOperator();
				}
				
				//checking if user name exists
				for(ExchangeUser user : users) {
					if(user.getApproved() && user.getUsername().equals(userName) && user.getPassword().equals(password)) {
						System.out.println("Account created for" + user.getName());
						return user;
					}
				}
				
				// check if user has to be approved
				//checking if username exists
				for(ExchangeUser user : users) {
					if(!user.getApproved() && user.getUsername().equals(userName) && user.getPassword().equals(password)) {
						System.out.println("User has not been approved");
					}
				}
				
				System.out.println("User does not exist!");
			}else if (choice == 2) {	 
				System.out.println("Enter your username:");
				String userName = sc.next().trim();
				System.out.println("Enter your password");
				String password = sc.next().trim();
				
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
					while(true) {
						System.out.println("Choose account type:");
						System.out.println("1. Trader");
						System.out.println("2. Lister");
						
						choice = Utils.getInt();
						
						if(choice == 1) {
							Trader temp = new Trader(userName, password, name, false);
							users.add(temp);
							break;
						}else if(choice == 2) {
							Lister temp = new Lister(userName, password, name, false);
							System.out.println("Lister added");
							users.add(temp);
							break;
						}else {
							System.out.println("Enter valid response");
						}
					}
					
					
					
					break;
				}
			}else if(choice == 3) {
				//Creating a few suppliers
				Lister lister1 = new Lister("Damon", "pass", "Damon Albarn", true);
				Lister lister2 = new Lister("Kanye", "pass", "Kanye West", true);
				
				Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
				Security security2 = new Security("Plastic Beach", "A Great Album", 1.1f, ep.getNewSI(), lister1);
				
				Security security3 = new Security("Late Registration", "The second album", 3.f, ep.getNewSI(), lister2);
				Security security4 = new Security("Kids See Ghosts", "Collaborative Album", 5.f, ep.getNewSI(), lister2);
				
				Lister.enlistSecurity(ep, security1);
				Lister.enlistSecurity(ep, security2);
				Lister.enlistSecurity(ep, security3);
				Lister.enlistSecurity(ep, security4);
				
				//Creating a few Traders
				Trader trader1 = new Trader("Thomas", "password", "Thomas Bangalter", true);
				Trader trader2 = new Trader("Guy", "password", "Guy Manuel", true);
				
				
				users.add(lister1);
				users.add(lister2);
				users.add(trader1);
				users.add(trader2);
				
				Order order1 = new Order(ep.orderCounter++, security1.getSI(), 2.2f, 10, Utils.getTime(), Type.sell, trader1);
				Order order2 = new Order(ep.orderCounter++, security2.getSI(), 1.3f, 10, Utils.getTime(), Type.purchase, trader1);
				Order order3 = new Order(ep.orderCounter++, security1.getSI(), 2.4f, 5, Utils.getTime(), Type.sell, trader2);
				Order order4 = new Order(ep.orderCounter++, security2.getSI(), 1f, 5, Utils.getTime(), Type.purchase, trader2);
				
				Order order5 = new Order(ep.orderCounter++, security3.getSI(), 3.2f, 10, Utils.getTime(), Type.purchase, trader1);
				Order order6 = new Order(ep.orderCounter++, security4.getSI(), 5.1f, 10, Utils.getTime(), Type.sell, trader1);
				Order order7 = new Order(ep.orderCounter++, security3.getSI(), 2.9f, 5, Utils.getTime(), Type.sell, trader2);
				Order order8 = new Order(ep.orderCounter++, security4.getSI(), 4.9f, 5, Utils.getTime(), Type.purchase, trader2);
				
				Trader.addOrder(ep, order1);
				Trader.addOrder(ep, order2);
				Trader.addOrder(ep, order3);
				Trader.addOrder(ep, order4);
				Trader.addOrder(ep, order5);
				Trader.addOrder(ep, order6);
				Trader.addOrder(ep, order7);
				Trader.addOrder(ep, order8);
				
				System.out.println("Scenario Loaded!");
				
				
			}else if(choice == 4) {
				return null;
			}
		}
		return null;
	}
	
	boolean approveUser(int user) {
		// Arbitrary accepting logic
		users.get(user).setApproved(true);
	
		
		return true;
	}
}
