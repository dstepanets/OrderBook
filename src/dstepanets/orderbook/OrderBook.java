package dstepanets.orderbook;

import java.util.TreeMap;

public class OrderBook {

	/*
	* I opted for TreeMap because it helps to track max bid and min ask easily.
	* It's implemented as a red-black binary tree which allows search, insertion and
	* removal in O(log n) time. And that's not so bad for our purposes.
	* And no additional space required - O(n)
	*/

	private TreeMap<Integer, Integer> bids = new TreeMap<>();
	private TreeMap<Integer, Integer> asks = new TreeMap<>();

/* * * * * * * * * UPDATES * * * * * * * * * */

	/*
	 * The task says that the best bid is always lower than the best ask.
	 * I was told that it's good to validate this condition.
	 * But at the same time, I don't have to execute matching orders.
	 * So, I decided to prevent adding such orders, report them to console,
	 * but not terminate the program.
	 */

	void updateBid(int price, int size) {
//		if the price level is not a spread, update or remove it if size=0
		if (bids.containsKey(price)) {
			if (size == 0) bids.remove(price);
			else bids.replace(price, size);
//		add to bids only if it's lower than the best ask and if size > 0
		} else if (asks.isEmpty() || price < asks.firstKey()) {
			if (size > 0) bids.put(price, size);
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
			if (size > 0) asks.put(price, size);
		} else {
			Output.logError("New ask at line " + Input.getCurrentLine() +
							" is not higher than the best bid.");
		}
	}

/* * * * * * * * * QUERIES * * * * * * * * * */

//	If there are no bids/asks for a query, print zeroes

	void bestBid() {
		if (!bids.isEmpty()) {
			int price = bids.lastKey();
			Output.printNums(price, bids.get(price));
		} else {
			Output.printNums(0, 0);
		}
	}

	void bestAsk() {
		if (!asks.isEmpty()) {
			int price = asks.firstKey();
			Output.printNums(price, asks.get(price));
		} else {
			Output.printNums(0, 0);
		}
	}

	void printSizeAtPrice(int price) {
		int size;
		if (bids.containsKey(price)) {
			size = bids.get(price);
		} else if (asks.containsKey(price)) {
			size = asks.get(price);
//		it's a spread then
		} else {
			size = 0;
		}
		Output.printNums(size);
	}

/* * * * * * * * * ORDERS * * * * * * * * * */

//	iterate subtracting order size from the best bids
//	while size>0 and there are bids available
	void buy(int buySize) {
		while (buySize > 0 && !asks.isEmpty()) {
			int bestAskPrice = asks.firstKey();
			int askSize = asks.get(bestAskPrice);
			askSize -= buySize;
//			update the best ask size if shares left after buying
			if (askSize > 0) {
				asks.replace(bestAskPrice, askSize);
				buySize = 0;
//			otherwise, remove the best ask and start buying from the next best
			} else {
				asks.remove(bestAskPrice);
//			how many shares are left to buy
				buySize = -askSize;
			}
		}
	}

	void sell(int sellSize) {
		while (sellSize > 0 && !bids.isEmpty()) {
			int bestBidPrice = bids.lastKey();
			int bidSize = bids.get(bestBidPrice);
			bidSize -= sellSize;
			if (bidSize > 0) {
				bids.replace(bestBidPrice, bidSize);
				sellSize = 0;
			} else {
				bids.remove(bestBidPrice);
				sellSize = -bidSize;
			}
		}
	}

}
