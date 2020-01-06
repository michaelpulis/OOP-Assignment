package StockExchange;

import java.util.ArrayList;

public class AuditTrail {
	private ArrayList<Transaction> transactions;
	
	public AuditTrail (){
		transactions = new ArrayList<>();
	}
	
	protected void addTransaction(Transaction t) {
		transactions.add(t);
	}
	
	public void outputTransactions() {
		int counter = 1;
		for(Transaction transaction : transactions) {
			System.out.println("Transaction: " + counter + " from SI " + transaction.getPurchaseOrder().getSI());
			System.out.println(transaction.getString());
			System.out.println("-----------");
			counter ++;
		}
	}
}
