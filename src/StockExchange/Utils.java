package StockExchange;

import java.util.Scanner;

public class Utils {
	static Scanner sc = new Scanner(System.in);
	// Utilities
	public static int getInt() {
		while(true) {
			try{
				return Integer.parseInt(sc.next());
			}catch (NumberFormatException num){
				System.out.println("Enter a valid integer!");
			}
		}
	}
	
	public static float getFloat() {
		while(true) {
			try{
				return Float.parseFloat(sc.next());
			}catch (NumberFormatException num){
				System.out.println("Enter a valid number!");
			}
		}
	}
	
	
}
