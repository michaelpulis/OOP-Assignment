package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import StockExchange.ExchangePlatform;
import StockExchange.Lister;
import StockExchange.Order;
import StockExchange.Security;
import StockExchange.Trader;
import StockExchange.Type;
import StockExchange.Utils;

class AddingTesting {

	@Test
	void addTrader() {
		ExchangePlatform ep = new ExchangePlatform();
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		assertEquals(true, ep.login.addUser(trader1));
	}
	
	@Test
	void addDuplicateTrader() {
		ExchangePlatform ep = new ExchangePlatform();
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		assertEquals(false, ep.login.addUser(trader1));
	}
	
	@Test
	void addingPurchaseOrder() {
		//Attempting to add an order with an approved trader
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);
		
		Lister.enlistSecurity(ep, security1);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Order order = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.purchase, trader1);
		
		boolean orderAttempt = Trader.addOrder(ep, order);
		
		assertEquals(orderAttempt, true);	
	}
	
	@Test
	void addingSellOrder() {
		//Attempting to add an order with an approved trader
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);
		
		Lister.enlistSecurity(ep, security1);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Order order = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		
		boolean orderAttempt = Trader.addOrder(ep, order);
		
		assertEquals(orderAttempt, true);	
	}
	
	@Test
	void addListing() {
		//Attempting to add an order with an approved trader
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);
		
		assertEquals(true, Lister.enlistSecurity(ep, security1));
	}
	
	@Test
	void addingLister() {
		//Attempting to add an order with an approved trader
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
			
		assertEquals(true, ep.login.addUser(lister1));
	}
	
	@Test
	void addingDuplicateLister() {
		//Attempting to add an order with an approved trader
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		ep.login.addUser(lister1);
		
		assertEquals(false, ep.login.addUser(lister1));
	}

}
