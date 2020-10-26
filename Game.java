package battleship;

import battleship.exceptions.InvalidCoordinatesException;
import battleship.exceptions.InvalidShipLengthException;
import battleship.exceptions.ShipLocationTooCloseException;

public class Game {
    final Board board;

    public Game() {
        this.board = new Board();
    }

    public void placeShip(Coordinates coordinates, ShipType shipType) {
        validate(coordinates, shipType);

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

    private void validate(Coordinates coordinates, ShipType ship) {
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

    public boolean shoot(Position shotPosition) {
        if (!board.isValidPosition(shotPosition)) {
            throw new InvalidCoordinatesException();
        }

        return board.shoot(shotPosition);
    }
}
