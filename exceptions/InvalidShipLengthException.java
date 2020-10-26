package battleship.exceptions;

import battleship.ShipType;

public class InvalidShipLengthException extends RuntimeException {
    public InvalidShipLengthException(ShipType shipType) {
        super(String.format("Error! Wrong length of the %s!", shipType));
    }
}

