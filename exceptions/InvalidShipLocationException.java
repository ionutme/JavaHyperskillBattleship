package battleship.exceptions;

public class InvalidShipLocationException extends RuntimeException {
    public InvalidShipLocationException() {
        super("Error! Wrong ship location!");
    }
}
