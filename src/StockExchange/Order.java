package StockExchange;

public class Order {
	private int SI;
	private String date;
	private float price;
	private float quantity;
	
	public Order(int SI, float price, float quantity, String date) {
		this.SI = SI;
		this.price = price;
		this.date = date;
		this.quantity = quantity;
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
	
}
