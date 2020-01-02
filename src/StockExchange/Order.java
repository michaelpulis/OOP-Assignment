package StockExchange;

import java.time.LocalDate;
import java.time.LocalTime;



public class Order {
	private int SI;
	private String date;
	private float price;
	private float quantity;
	private Trader trader;
	private Type type;
	private boolean cancelled;
	private int orderID;
	
	public Order(int orderID, int SI, float price, float quantity, String date, Type type, Trader trader) {
		this.orderID = orderID;
		this.SI = SI;
		this.price = price;
		this.date = date;
		this.quantity = quantity;
		this.type = type;
		this.trader = trader;
		this.cancelled = false;
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
		return "OrderID\t"+orderID+ "\tPrice:\t" + price + "\tQuantity:\t" + quantity + "\tDate:\t"+date+"\tTrader:\t"+trader.getName();
	}
	
	public String getStringExcl() {
		return "OrderID\t"+orderID+ "\tPrice:\t" + price + "\tQuantity:\t" + quantity + "\tDate:\t"+date;
	}
	
	public Trader getTrader() {
		return trader;
	}
	
	public int getOrderID() {
		return this.orderID;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public boolean getCancelled() {
		return cancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	static Order getOrderFromInput(int orderID, Trader currentTrader, Type type) {
		System.out.println("Enter price:");
		float price = Utils.getFloat();
		
		System.out.println("Enter quantity:");
		float quantity = Utils.getFloat();
		
		String date = Utils.getTime();
		
		System.out.println("Enter SI of Security to be added:");
		int SI = Utils.getInt();
		
		return new Order(orderID, SI, price, quantity, date, type, currentTrader);
	}
	
	
}
