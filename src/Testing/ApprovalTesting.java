package Testing;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import StockExchange.ExchangePlatform;
import StockExchange.ExchangePlatformOperator;
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
		ep.login.addUser(lister1);
		boolean listAttepmpt = Lister.enlistSecurity(ep, security1);
		assertEquals(false, listAttepmpt);
	}
	
	@Test
	void approvedListerLosingApproval() {		
		//Attempting to enlist a security with an unapproved lister
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		
		ExchangePlatformOperator epo = new ExchangePlatformOperator("Ben", "Pass", "GoldWasser");
		ep.login.addUser(epo);
		
		
		ep.login.addUser(lister1);
		boolean listAttepmpt = Lister.enlistSecurity(ep, security1);
		assertEquals(true, listAttepmpt);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		
		ep.login.addUser(trader1);
		
		Order order = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.purchase, trader1);
		Trader.addOrder(ep,  order);
		epo.cancelUser(ep, trader1);
		
		assertEquals(false, Trader.cancelOrder(trader1, order, ep));
	}
	
	@Test
	void approvedTraderLosingApproval() {		
		//Attempting to enlist a security with an unapproved lister
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		
		ExchangePlatformOperator epo = new ExchangePlatformOperator("Ben", "Pass", "GoldWasser");
		ep.login.addUser(epo);
		
		ep.login.addUser(lister1);
		
		Lister.enlistSecurity(ep, security1);
		
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", false);
//		assertEquals(true, listAttepmpt);
		ep.login.addUser(trader1);
		
		Order order = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.purchase, trader1);
		
		epo.cancelUser(ep, lister1);
		
		assertEquals(false, Lister.cancelOrder(ep, order, lister1));	
	}
	
	@Test
	void approvedLister() {
		//Attempting to enlist a security with an approved lister
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1); 
		ep.login.addUser(lister1);
		boolean listAttepmpt = Lister.enlistSecurity(ep, security1);
		assertEquals(true, listAttepmpt);	
	}
	
@Test
	void approveLister() {		
		//Attempting to enlist a security with an unapproved lister
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", false);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ExchangePlatformOperator epo = new ExchangePlatformOperator("Ben", "Pass", "GoldWasser");
		
		ep.login.addUser(epo);
		
		ep.login.addUser(lister1);
		
		//Approving Lister
		epo.approveUser(ep, lister1);
		
		boolean listAttepmpt = Lister.enlistSecurity(ep, security1);
		assertEquals(true, listAttepmpt);
	}
	@Test
	void unapprovedTrader() {
		//Attempting to add an order with an unapproved trader
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		Lister.enlistSecurity(ep, security1);
		ep.login.addUser(lister1);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", false);
		
		ep.login.addUser(trader1);
		
		Order order = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.purchase, trader1);
		
		boolean orderAttempt = Trader.addOrder(ep, order);
		
		assertEquals(false, orderAttempt);	
	}
	
	@Test
	void approvedTrader() {
		//Attempting to add an order with an approved trader
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		ep.login.addUser(lister1);
		//s
		
		Lister.enlistSecurity(ep, security1);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", true);
		ep.login.addUser(trader1);
		
		Order order = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.purchase, trader1);
		
		boolean orderAttempt = Trader.addOrder(ep, order);
		assertEquals(true, orderAttempt);	
	}
	
	@Test
	void approveTrader() {
		//Attempting to add an order with an unapproved trader, after approving it
		ExchangePlatform ep = new ExchangePlatform();
		Lister lister1 = new Lister("Damon", "Albarn", "Damon Albarn", true);
		Security security1 = new Security("The Now Now", "Newest Album", 2.3f, ep.getNewSI(), lister1);
		
		ExchangePlatformOperator epo = new ExchangePlatformOperator("Ben", "Pass", "GoldWasser");
		
		Lister.enlistSecurity(ep, security1);
		ep.login.addUser(epo);
		ep.login.addUser(lister1);
		
		Trader trader1 = new Trader("Thomas", "Bangalter", "Thomas Bangalter", false);
		ep.login.addUser(trader1);
		System.out.println("wazzaa");
		//Approving Trader
		epo.approveUser(ep, trader1);
				
		Order order = new Order(ep.getNewOrder(), security1.getSI(), 2.3f, 10, Utils.getTime(), Type.purchase, trader1);
		
		boolean orderAttempt = Trader.addOrder(ep, order);
		
		assertEquals(true, orderAttempt);	
	}

}
