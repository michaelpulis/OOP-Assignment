package StockExchange;

public class Security {
	private String name, description;
	private float price;
	private static float totalSupply;
	private boolean listed;
	private int SI;
	
	public Security(String name, String description, float price, float totalSupply, int SI) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.totalSupply = totalSupply;
		this.SI = SI;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getTotalSupply() {
		return totalSupply;
	}
	public static void setTotalSupply(float totalSupply) {
		Security.totalSupply = totalSupply;
	}
	
	public boolean isListed() {
		return listed;
	}
	
	public void setListed(boolean param) {
		listed = param;
	}
	
	public void setSI(int SI) {
		this.SI = SI;
	}
	
	public int getSI() {
		return this.SI;
	}
	
	public String getString() {
		return "Name:\t" + name + "\tDesc:\t"+description + "\tPrice:\t" + price + "\tTotal Supply:\t" + totalSupply + "\tSI:\t"+SI;
	}
	
}
