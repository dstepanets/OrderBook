package dstepanets.orderbook;

public class InvalidCommandException extends Exception {
	public InvalidCommandException(){
		super("Invalid input command");
	}
}
