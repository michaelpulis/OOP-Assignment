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

class CancelTesting {

	@Test
	void traderCancelOrder() {

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);

		Lister.enlistSecurity(ep, security1);
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		assertEquals(true, Trader.cancelOrder(trader1, order1, ep));
	}
	
	@Test
	void traderCancelCancelledOrder() {

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);

		Lister.enlistSecurity(ep, security1);
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		assertEquals(true, Trader.cancelOrder(trader1, order1, ep));
	}
	
	@Test
	void incorrectTraderCancellingOrder() {

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);

		Lister.enlistSecurity(ep, security1);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Trader trader2 = new Trader("Guy", "Manuel", "Guy Manuel", true);
		ep.login.addUser(trader2);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		
		
		assertEquals(false, Trader.cancelOrder(trader2, order1, ep));
	}

	
	@Test
	void cancelOrderUnauthorisedTrader() {
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);

		Lister.enlistSecurity(ep, security1);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", false);
		ep.login.addUser(trader1);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		
		
		assertEquals(false, Trader.cancelOrder(trader1, order1, ep));
	}
	
	@Test
	void listerCancelOrder() {

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);

		Lister.enlistSecurity(ep, security1);
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		assertEquals(true, Lister.cancelOrder(ep, order1, lister1));
	}
	
	@Test
	void unauthorisedListerCancelOrder() {

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", false);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);

		Lister.enlistSecurity(ep, security1);
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		assertThrows(NullPointerException.class,
				()->{
					Lister.cancelOrder(ep, order1, lister1);	
				});
	}
	
	@Test
	void listerCancelCancelledOrder() {

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);

		Lister.enlistSecurity(ep, security1);
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		Lister.cancelOrder(ep, order1, lister1);
		
		assertEquals(false, Lister.cancelOrder(ep, order1, lister1));
	}
	
	@Test
	void incorrectListerCancelOrder() {

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);
		
		Lister lister2 = new Lister("Kanye", "West", "Kanye West", true);
		ep.login.addUser(lister2);

		Lister.enlistSecurity(ep, security1);
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		assertEquals(false, Lister.cancelOrder(ep, order1, lister2));
	}
	
	@Test
	void exchangePlaformCancelOrder() {

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);

		Lister.enlistSecurity(ep, security1);
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		assertEquals(true, ep.cancelOrder(order1));
	}
	
	@Test
	void exchangePlaformCancelCancelledOrder() {

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);

		Lister.enlistSecurity(ep, security1);
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		Lister.cancelOrder(ep, order1, lister1);
		assertEquals(false, ep.cancelOrder(order1));
	}
}
