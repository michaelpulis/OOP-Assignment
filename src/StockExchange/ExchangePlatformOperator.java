package StockExchange;

public class ExchangePlatformOperator extends ExchangeUser {
	public ExchangePlatformOperator (String user, String pass, String name) {
		super(user, pass, name, true);
	}
	
	public boolean approveUser(ExchangePlatform ep, ExchangeUser user) {
		// Arbitrary accepting logic
		if(ep.login.getUserIndex(user) != -1) {
			user.setApproved(true);
			return true;
		}else
			return false;
	}
	
	public boolean cancelUser(ExchangePlatform ep, ExchangeUser user) {
		// Arbitrary accepting logic
		if(ep.login.getUserIndex(user) != -1) {
			user.setApproved(false);
			return true;
		}else
			return false;
	}
	
	public boolean approveUser(ExchangePlatform ep, ExchangeUser user, boolean bool) {
		// Arbitrary accepting logic
		return bool;
	}
	
}
