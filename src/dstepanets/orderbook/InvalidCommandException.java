package dstepanets.orderbook;

//	The only purpose of it is to distinguish input file validation errors
//	from possible IO and other standard exceptions
public class InvalidCommandException extends Exception {
	public InvalidCommandException(){
		super("Invalid input command");
	}
}
