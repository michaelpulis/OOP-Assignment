package StockExchange;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Utils {
	private static Scanner sc = new Scanner(System.in);
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
	
	public static String getString() {
		return sc.next().trim();
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
	
	public static String getTime() {
		 return LocalDate.now().toString() + " - " + LocalTime.now().toString();
	}
	
	
}
