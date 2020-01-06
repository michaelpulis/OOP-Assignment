package StockExchange;

public class ExchangePlatformOperator extends ExchangeUser {
	public ExchangePlatformOperator (String user, String pass, String name) {
		super(user, pass, name, true);
	}
	
	public boolean approveUser(ExchangePlatform ep, ExchangeUser user) {
		// Arbitrary accepting logic
		if(true) {
			return true;
		}else
			return false;
		
	}
	
}
