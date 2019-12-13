package dstepanets.orderbook;

import java.util.TreeMap;

public class OrderBook {

	private TreeMap<Integer, Integer> bids = new TreeMap<>();
	private TreeMap<Integer, Integer> asks = new TreeMap<>();

/* * * * * * * * * UPDATES * * * * * * * * * */

	void updateBid(int price, int size) {
		if (bids.containsKey(price)) {
			bids.replace(price, size);
		} else {
			bids.put(price, size);
		}
	}

	void updateAsk(int price, int size) {
		if (asks.containsKey(price)) {
			asks.replace(price, size);
		} else {
			asks.put(price, size);
		}
	}

/* * * * * * * * * QUERIES * * * * * * * * * */

	void bestBid() {
		int price = bids.lastKey();
		Output.printNums(price, bids.get(price));
	}

	void bestAsk() {
		int price = asks.firstKey();
		Output.printNums(price, asks.get(price));
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
		while (size > 0) {
			int bestAskKey = asks.firstKey();
			int askSize = asks.get(bestAskKey);
			askSize -= size;
			if (askSize > 0) {
				asks.replace(bestAskKey, askSize);
				size = 0;
			} else if (askSize == 0) {
				asks.remove(bestAskKey);
				size = 0;
			} else {
				asks.remove(bestAskKey);
				size = -askSize;
			}
		}
	}

	void sell(int size) {
		while (size > 0) {
			int bestBidKey = bids.lastKey();
			int bidSize = bids.get(bestBidKey);
			bidSize -= size;
			if (bidSize > 0) {
				bids.replace(bestBidKey, bidSize);
				size = 0;
			} else if (bidSize == 0) {
				bids.remove(bestBidKey);
				size = 0;
			} else {
				bids.remove(bestBidKey);
				size = -bidSize;
			}
		}
	}


}
