package battleship.exceptions;

import battleship.Ship;

public class InvalidShipLengthException extends RuntimeException {
    public InvalidShipLengthException(Ship ship) {
        super(String.format("Error! Wrong length of the %s!", ship));
    }
}
