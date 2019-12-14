package dstepanets.orderbook;

import java.util.TreeMap;

public class OrderBook {

//	TODO temp public!!!!!!!!!!!!!11
	public TreeMap<Integer, Integer> bids = new TreeMap<>();
	public TreeMap<Integer, Integer> asks = new TreeMap<>();

/* * * * * * * * * UPDATES * * * * * * * * * */

	/*
	 * The task says that the best bid is always lower than the best ask.
	 * I was told that it's good to validate this condition.
	 * But at the same time, I don't have to execute matching orders.
	 * So, I decided to prevent adding such orders, report them to console,
	 * but not terminate the program.
	 */

	void updateBid(int price, int size) {
		if (bids.containsKey(price)) {
			if (size == 0) bids.remove(price);
			else bids.replace(price, size);
		} else if (asks.isEmpty() || price < asks.firstKey()) {
			bids.put(price, size);
		} else {
			Output.logError("New bid at line " + Input.getCurrentLine() +
							" is not lower than the best ask.");
		}
	}

	void updateAsk(int price, int size) {
		if (asks.containsKey(price)) {
			if (size == 0) asks.remove(price);
			else asks.replace(price, size);
		} else if (bids.isEmpty() || price > bids.lastKey()) {
			asks.put(price, size);
		} else {
			Output.logError("New ask at line " + Input.getCurrentLine() +
							" is not higher than the best bid.");
		}
	}

/* * * * * * * * * QUERIES * * * * * * * * * */

//	If there is no order for a query, I print zeroes

	void bestBid() {
		int price = 0, size = 0;
		if (!bids.isEmpty()) {
			price = bids.lastKey();
			size = bids.get(price);
		}
		Output.printNums(price, size);
	}

	void bestAsk() {
		int price = 0, size = 0;
		if (!asks.isEmpty()) {
			price = asks.firstKey();
			size = asks.get(price);
		}
		Output.printNums(price, size);
	}

	void printSizeAtPrice(int price) {
		int size;
		if (bids.containsKey(price)) {
			size = bids.get(price);
		} else if (asks.containsKey(price)) {
			size = asks.get(price);
		} else {
			size = 0;
		}
		Output.printNums(size);
	}

/* * * * * * * * * ORDERS * * * * * * * * * */

	void buy(int size) {
		while (size > 0 && !asks.isEmpty()) {
			int bestAskKey = asks.firstKey();
			int askSize = asks.get(bestAskKey);
			askSize -= size;
			if (askSize > 0) {
				asks.replace(bestAskKey, askSize);
				size = 0;
			} else {
				asks.remove(bestAskKey);
				size = -askSize;
			}
		}
	}

	void sell(int size) {
		while (size > 0 && !bids.isEmpty()) {
			int bestBidKey = bids.lastKey();
			int bidSize = bids.get(bestBidKey);
			bidSize -= size;
			if (bidSize > 0) {
				bids.replace(bestBidKey, bidSize);
				size = 0;
			} else {
				bids.remove(bestBidKey);
				size = -bidSize;
			}
		}
	}

}
