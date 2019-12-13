package dstepanets.orderbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Output {

	private static BufferedWriter writer;

	static {
		try {
			writer = new BufferedWriter(new FileWriter(new File("resources/output.csv"), false));
		} catch (IOException e) {
			System.err.println("Error on creating or opening the output.csv file.");
			e.printStackTrace();
			System.exit(-1);
		}

	}

	static void logError(int line) {
		try {
			writer.append("<Invalid command on line " + line + ">");
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Error on writing to the output.csv file.");
			e.printStackTrace();
		}
	}

	static void printNums(int size) {
		try {
			writer.append(String.valueOf(size));
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Error on writing to the output.csv file.");
			e.printStackTrace();
		}
	}

	static void printNums(int price, int size) {
		try {
			writer.append(price + "," + size);
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Error on writing to the output.csv file.");
			e.printStackTrace();
		}
	}

	static void closeFile() {
		try {
			if (writer != null)
				writer.close();
		} catch (IOException e) {
			System.err.println("Couldn't close output.csv file :(");
		}
	}

}
