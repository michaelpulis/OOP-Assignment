package StockExchange;

import java.util.ArrayList;

public class AuditTrail {
	ArrayList<Transaction> transactions;
	
	public AuditTrail (){
		transactions = new ArrayList<>();
	}
	
	void addTransaction(Transaction t) {
		transactions.add(t);
	}
	
	void outputTransactions() {
		int counter = 1;
		for(Transaction transaction : transactions) {
			System.out.println("Transaction: " + counter);
			System.out.println(transaction.getString());
			System.out.println("-----------");
			counter ++;
		}
	}
}
