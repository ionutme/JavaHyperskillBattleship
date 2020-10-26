package battleship.exceptions;

public class InvalidCoordinatesException extends RuntimeException {
    public InvalidCoordinatesException() {
        super("Error! You entered the wrong coordinates!");
    }
}
