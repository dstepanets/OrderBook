package dstepanets.orderbook;

public class Main {

    public static void main(String[] args) {

        String inputFilePath;
        if (args.length > 0) {
            inputFilePath = args[0];
        } else {
            inputFilePath = "resources/input.csv";
        }

        OrderBook orderBook = new OrderBook();
        Input input = new Input(orderBook);
        input.parseInputFile(inputFilePath);

        Output.closeFile();


    }
}
