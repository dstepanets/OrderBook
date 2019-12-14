package dstepanets.orderbook;

public class Main {

    /*
    * Get input file path from the first command line argument
    * if none provided, use the default file
    */

    public static void main(String[] args) {

        String inputFilePath;
        if (args.length > 0) {
            inputFilePath = args[0];
        } else {
            inputFilePath = "./resources/input.csv";
        }

        OrderBook orderBook = new OrderBook();
        Input input = new Input(orderBook);
        input.parseInputFile(inputFilePath);

        Output.closeFile();

    }
}
