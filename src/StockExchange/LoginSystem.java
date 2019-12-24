package StockExchange;

import java.util.ArrayList;
import java.util.Scanner;

public class LoginSystem {
	ArrayList<ExchangeUser> users = new ArrayList<>();
	public ExchangeUser attemptLogin() {
		boolean signedIn = false;
		Scanner sc = new Scanner(System.in);
		
		while(!signedIn) {
			System.out.println("1. Log In");
			System.out.println("2. Sign Up");
			System.out.println("3. Exit");
			
			
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
					}else {
						System.out.println("User does not exist");
					}
				}
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
							Trader temp = new Trader();
							temp.setLoginDetails(userName, password, name, false);
							System.out.println("Trader added");
							users.add(temp);
							break;
						}else if(choice == 2) {
							Lister temp = new Lister();
							temp.setLoginDetails(userName, password, name, false);
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
