package battleship;

import battleship.exceptions.InvalidShipLengthException;
import battleship.exceptions.ShipLocationTooCloseException;

public class Game {
    final Board board;

    public Game() {
        this.board = new Board();
    }

    public void placeShip(Coordinates coordinates, Ship ship) {
        validate(coordinates, ship);

        placeShip(coordinates);
    }

    /**
     * For tests purpose only!
     * @param coordinates Indicates 2 positions on the board.
     *                    Ex: F3, F7.
     */
    public void placeShip(Coordinates coordinates) {
        for (Position position : coordinates) {
            this.board.mark(position);
        }
    }

    private void validate(Coordinates coordinates, Ship ship) {
        if (coordinates.getDistance() != ship.size) {
            throw new InvalidShipLengthException(ship);
        }

        if (!canPlaceShip(coordinates)) {
            throw new ShipLocationTooCloseException();
        }
    }

    public boolean canPlaceShip(Coordinates coordinates) {
        for (Position position : coordinates) {
            if (!board.canMark(position)) {
                return false;
            }
        }

        return true;
    }
}
