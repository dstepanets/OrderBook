package dstepanets.orderbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Input {

	private OrderBook orderBook;
	private int currentLine;

	public Input(OrderBook orderBook) {
		this.orderBook = orderBook;
		currentLine = 0;
	}

	void parseInputFile(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String ln;
			while ((ln = br.readLine()) != null) {
				currentLine++;
				String[] command = ln.split(",");
				switch (command[0]) {
					case "u":
						parseUpdate(command);
						break;
					case "q":
						parseQuery(command);
						break;
					case "o":
						parseOrder(command);
						break;
					default:
						Output.logError(currentLine);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Input file not found or can't be read.");
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseUpdate(String[] command) {
		try {
			if (command.length == 4) {
				int price = Integer.parseInt(command[1]);
				int size = Integer.parseInt(command[2]);
				if (price > 0 && size >= 0) {
					if (command[3].toLowerCase().equals("bid")) {
						orderBook.updateBid(price, size);
					} else if (command[3].toLowerCase().equals("ask")) {
						orderBook.updateAsk(price, size);
					} else throw new InvalidCommandException();
				} else throw new InvalidCommandException();
			} else throw new InvalidCommandException();
		} catch (NumberFormatException | InvalidCommandException e) {
			Output.logError(currentLine);
		}
	}

	private void parseQuery(String[] command) {
		try {
			if (command.length == 2) {
				if (command[1].toLowerCase().equals("best_bid")) {
					orderBook.bestBid();
				} else if (command[1].toLowerCase().equals("best_ask")) {
					orderBook.bestAsk();
				} else throw new InvalidCommandException();
			} else if (command.length == 3) {
				int price = Integer.parseInt(command[2]);
				if (command[1].toLowerCase().equals("size") && price > 0) {
					orderBook.printSizeAtPrice(price);
				} else throw new InvalidCommandException();
			} else throw new InvalidCommandException();
		} catch (NumberFormatException | InvalidCommandException e) {
			Output.logError(currentLine);
		}
	}

	private void parseOrder(String[] command) {
		try {
			if (command.length == 3) {
				int size = Integer.parseInt(command[2]);
				if (size > 0) {
					if (command[1].toLowerCase().equals("buy")) {
						orderBook.buy(size);
					} else if (command[1].toLowerCase().equals("sell")) {
						orderBook.sell(size);
					} else throw new InvalidCommandException();
				} else throw new InvalidCommandException();
			} else throw new InvalidCommandException();
		} catch (NumberFormatException | InvalidCommandException e) {
			Output.logError(currentLine);
		}
	}
}
