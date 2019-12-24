package StockExchange;

import java.time.LocalDate;
import java.time.LocalTime;

public class Order {
	private int SI;
	private String date;
	private float price;
	private float quantity;
	private Trader trader;
	
	public Order(int SI, float price, float quantity, String date, Trader trader) {
		this.SI = SI;
		this.price = price;
		this.date = date;
		this.quantity = quantity;
		this.trader = trader;
	}

	public int getSI() {
		return SI;
	}

	public void setSI(int sI) {
		SI = sI;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
	public String getString() {
		return "Price:\t" + price + "\tQuantity:\t" + quantity + "\tDate:\t"+date+"\tTrader:\t"+trader.getName();
	}
	
	public String getStringExcl() {
		return "Price:\t" + price + "\tQuantity:\t" + quantity + "\tDate:\t"+date;
	}
	
	public Trader getTrader() {
		return trader;
	}
	
	static Order getOrderFromInput(Trader currentTrader) {
		System.out.println("Enter price:");
		float price = Utils.getFloat();
		
		System.out.println("Enter quantity:");
		float quantity = Utils.getFloat();
		
		String date = Utils.getTime();
		
		System.out.println("Enter SI of Security to be added:");
		int SI = Utils.getInt();
		
		return new Order(SI, price, quantity, date, currentTrader);
	}
	
}
