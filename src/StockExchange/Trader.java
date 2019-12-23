package StockExchange;

public class Trader extends ExchangeUser{
	public void raise(int SI, float price, float quantity, String date) {
		ExchangePlatform.addOrder(SI, price, quantity, date);
	}
}
