package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import StockExchange.ExchangePlatform;
import StockExchange.Lister;
import StockExchange.MatchingEngine;
import StockExchange.Order;
import StockExchange.Security;
import StockExchange.Trader;
import StockExchange.Type;
import StockExchange.Utils;

class MatchingTesting {

	@Test
	void attemptMatch() {
		System.out.println("attemptMatch");

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
		Order order2 = new Order(ep.getNewOrder(), security1.getSI(), 2.4f, 10, Utils.getTime(), Type.purchase, trader2);
		
		Trader.addOrder(ep, order1);
		Trader.addOrder(ep, order2);
		
		
		int matchCount = MatchingEngine.fulFillOrders(ep); 
		System.out.println(matchCount);
		
		assertEquals(matchCount, 1);
	}
	
	@Test
	void attemptMatchMultipleBuyWithOneSell() {
		System.out.println("attemptMatchMultipleBuyWithOneSell");

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);
		//s
		
		Lister.enlistSecurity(ep, security1);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Trader trader2 =	 new Trader("Guy", "Manuel", "Guy Manuel", true);
		ep.login.addUser(trader2);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		Order order2 = new Order(ep.getNewOrder(), security1.getSI(), 2.4f, 10, Utils.getTime(), Type.purchase, trader2);
		Order order3 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.purchase, trader2);
		
		Trader.addOrder(ep, order1);
		Trader.addOrder(ep, order2);
		Trader.addOrder(ep, order3);
		
		
		int matchCount = MatchingEngine.fulFillOrders(ep); 
		System.out.println(matchCount);
		
		assertEquals(matchCount, 1);
	}
	
	
	@Test
	void attemptMatchMultipleSellWithOneBuy() {
		System.out.println("attemptMatchMultipleSellWithOneBuy");

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);
		//s
		
		Lister.enlistSecurity(ep, security1);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Trader trader2 =	 new Trader("Guy", "Manuel", "Guy Manuel", true);
		ep.login.addUser(trader2);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		Order order2 = new Order(ep.getNewOrder(), security1.getSI(), 2.2f, 10, Utils.getTime(), Type.sell, trader1);
		Order order3 = new Order(ep.getNewOrder(), security1.getSI(), 2.4f, 10, Utils.getTime(), Type.purchase, trader2);
		
		Trader.addOrder(ep, order1);
		Trader.addOrder(ep, order2);
		Trader.addOrder(ep, order3);
		
		
		int matchCount = MatchingEngine.fulFillOrders(ep); 
		System.out.println(matchCount);
		
		assertEquals(matchCount, 2);
	}
	
	@Test
	void attemptMatchMultipleSellWithMultipleBuy() {
		System.out.println("attemptMatchMultipleSellWithMultipleBuy");

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);
		//s
		
		Lister.enlistSecurity(ep, security1);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Trader trader2 =	 new Trader("Guy", "Manuel", "Guy Manuel", true);
		ep.login.addUser(trader2);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		Order order2 = new Order(ep.getNewOrder(), security1.getSI(), 2.2f, 10, Utils.getTime(), Type.sell, trader1);
		Order order3 = new Order(ep.getNewOrder(), security1.getSI(), 2.4f, 10, Utils.getTime(), Type.purchase, trader2);
		Order order4 = new Order(ep.getNewOrder(), security1.getSI(), 2.4f, 10, Utils.getTime(), Type.purchase, trader2);
		
		Trader.addOrder(ep, order1);
		Trader.addOrder(ep, order2);
		Trader.addOrder(ep, order3);
		Trader.addOrder(ep, order4);
		
		
		int matchCount = MatchingEngine.fulFillOrders(ep);
		
		assertEquals(matchCount, 2);
	}

	@Test
	void attemptMatchDifferentSecurities() {
		System.out.println("attemptMatchDifferentSecurities");

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		Security security2 = new Security("Plastic Beach", "Fan Favourite", 2.f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);
		
		Lister.enlistSecurity(ep, security1);
		Lister.enlistSecurity(ep, security2);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Trader trader2 = new Trader("Guy", "Manuel", "Guy Manuel", true);
		ep.login.addUser(trader2);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		Order order2 = new Order(ep.getNewOrder(), security2.getSI(), 2.4f, 10, Utils.getTime(), Type.purchase, trader2);
		
		Trader.addOrder(ep, order1);
		Trader.addOrder(ep, order2);
		
		
		int matchCount = MatchingEngine.fulFillOrders(ep); 
		System.out.println(matchCount);
		
		assertEquals(0, matchCount);
	}
	
	@Test
	void attemtMatchInvalidPrices() {
		System.out.println("attemtMatchInvalidPrices");

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
		Order order2 = new Order(ep.getNewOrder(), security1.getSI(), 2.1f, 10, Utils.getTime(), Type.purchase, trader2);
		
		Trader.addOrder(ep, order1);
		Trader.addOrder(ep, order2);
		
		
		int matchCount = MatchingEngine.fulFillOrders(ep); 
		System.out.println(matchCount);
		
		assertEquals(matchCount, 0);
	}
	
	
	@Test
	void attemptMatchMultipleSellWithMultipleBuyMultipleSecurities() {
		System.out.println("attemptMatchMultipleSellWithMultipleBuy");

		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		Security security2 = new Security("Plastic Beach", "Fan Favourite", 2.f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);
		
		Lister.enlistSecurity(ep, security1);
		Lister.enlistSecurity(ep, security2);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Trader trader2 = new Trader("Guy", "Manuel", "Guy Manuel", true);
		ep.login.addUser(trader2);
		
		Order order1 = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.sell, trader1);
		Order order2 = new Order(ep.getNewOrder(), security1.getSI(), 2.2f, 10, Utils.getTime(), Type.sell, trader1);
		Order order3 = new Order(ep.getNewOrder(), security1.getSI(), 2.4f, 10, Utils.getTime(), Type.purchase, trader2);
		Order order4 = new Order(ep.getNewOrder(), security1.getSI(), 2.4f, 10, Utils.getTime(), Type.purchase, trader2);
		
		Order order5 = new Order(ep.getNewOrder(), security2.getSI(), 1.9f, 10, Utils.getTime(), Type.sell, trader1);
		Order order6 = new Order(ep.getNewOrder(), security2.getSI(), 2.f, 10, Utils.getTime(), Type.sell, trader1);
		Order order7 = new Order(ep.getNewOrder(), security2.getSI(), 1.9f, 10, Utils.getTime(), Type.purchase, trader2);
		Order order8 = new Order(ep.getNewOrder(), security2.getSI(), 2.1f, 10, Utils.getTime(), Type.purchase, trader2);
		
		Trader.addOrder(ep, order1);
		Trader.addOrder(ep, order2);
		Trader.addOrder(ep, order3);
		Trader.addOrder(ep, order4);
		Trader.addOrder(ep, order5);
		Trader.addOrder(ep, order6);
		Trader.addOrder(ep, order7);
		Trader.addOrder(ep, order8);
		
		
		int matchCount = MatchingEngine.fulFillOrders(ep);
		
		assertEquals(4, matchCount);
	}
}
