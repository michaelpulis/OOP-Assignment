
public class Security {
	private String description;
	private float price;
	private static float totalSupply;
	private boolean listed;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public static float getTotalSupply() {
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
	
}
