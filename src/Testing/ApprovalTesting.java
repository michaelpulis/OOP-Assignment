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


class ApprovalTesting {
	//Approval Testing
	@Test
	void unapprovedLister() {		
		//Attempting to enlist a security with an unapproved lister
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", false);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.users.add(lister1);
		boolean listAttepmpt = Lister.enlistSecurity(ep, security1);
		assertEquals(listAttepmpt, false);
	}
	
	@Test
	void approvedLister() {
		//Attempting to enlist a security with an approved lister
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1); 
		ep.login.users.add(lister1);
		boolean listAttepmpt = Lister.enlistSecurity(ep, security1);
		assertEquals(listAttepmpt, true);	
	}

	@Test
	void unapprovedTrader() {
		//Attempting to add an order with an unapproved trader
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		Lister.enlistSecurity(ep, security1);
		ep.login.users.add(lister1);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", false);
		ep.login.users.add(trader1);
		
		Order order = new Order(ep.orderCounter++, security1.getSI(), 2.3f, 10, Utils.getTime(), Type.purchase, trader1);
		
		boolean orderAttempt = Trader.addOrder(ep, order);
		
		assertEquals(orderAttempt, false);	
	}
	
	@Test
	void approvedTrader() {
		//Attempting to add an order with an approved trader
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.users.add(lister1);
		//s
		
		Lister.enlistSecurity(ep, security1);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.users.add(trader1);
		
		Order order = new Order(ep.orderCounter++, security1.getSI(), 2.3f, 10, Utils.getTime(), Type.purchase, trader1);
		
		boolean orderAttempt = Trader.addOrder(ep, order);
		
		assertEquals(orderAttempt, true);	
	}

}
