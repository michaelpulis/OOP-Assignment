package StockExchange;

public class Security {
	private String name, description;
	private float price;
	private float totalSupply;
	private boolean listed;
	private int SI;
	private Lister lister;
	
	public Security(String name, String description, float price, int SI, Lister lister) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.totalSupply = 0;
		this.SI = SI;
		this.lister = lister;
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
	public void setTotalSupply(float totalSupply) {
		this.totalSupply = totalSupply;
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

	public Lister getLister() {
		return this.lister;
	}
	
	public static Security getSecurityFromInput(int SI, Lister lister) {
		System.out.println("Enter Security name:");
		String name = Utils.getString();
		
		System.out.println("Enter Security description:");
		String description = Utils.getString();
		
		System.out.println("Enter Security recommended price:");
		float price = Utils.getFloat();
		
		return new Security(name, description, price, SI, lister);
	}
}
