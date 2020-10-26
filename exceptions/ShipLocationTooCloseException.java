package battleship.exceptions;

public class ShipLocationTooCloseException extends RuntimeException {
    public ShipLocationTooCloseException() {
        super("Error! You placed it too close to another one.");
    }
}
