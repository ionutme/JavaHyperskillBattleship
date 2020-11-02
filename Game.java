package battleship;

import battleship.exceptions.InvalidCoordinatesException;
import battleship.exceptions.InvalidShipLengthException;
import battleship.exceptions.ShipLocationTooCloseException;

import java.util.function.Consumer;

public class Game {
    private final Board board;
    final Fleet fleet;

    public Game() {
        this.board = new Board();
        this.fleet = new Fleet();
    }

    //region PLACE SHIPS

    public void placeShip(Coordinates coordinates, ShipType shipType) {
        validate(coordinates, shipType);

        placeShipInFleet(coordinates, shipType);
        placeShipOnBoard(coordinates);
    }

    /**
     * It's -public- for test purpose only!
     * @param coordinates Indicates 2 positions on the board.
     *                    Ex: F3, F7.
     */
    public void placeShipOnBoard(Coordinates coordinates) {
        for (Position position : coordinates) {
            this.board.mark(position);
        }
    }

    private void placeShipInFleet(Coordinates coordinates, ShipType shipType) {
        this.fleet.add(new Ship(coordinates, shipType));
    }

    private void validate(Coordinates coordinates, ShipType ship) {
        if (coordinates.getDistance() != ship.size) {
            throw new InvalidShipLengthException(ship);
        }

        if (!canPlaceShipOnBoard(coordinates)) {
            throw new ShipLocationTooCloseException();
        }
    }

    public boolean canPlaceShipOnBoard(Coordinates coordinates) {
        for (Position position : coordinates) {
            if (!this.board.canMark(position)) {
                return false;
            }
        }

        return true;
    }

    //endregion

    //region SHOOT

    public ShotStatus shoot(Position shotPosition) {
        boolean hit = shootOnBoard(shotPosition);
        boolean isShipSank = shootInShips(shotPosition);

        return new ShotStatus(hit, isShipSank);
    }

    private boolean shootOnBoard(Position shotPosition) {
        if (!this.board.isValidPosition(shotPosition)) {
            throw new InvalidCoordinatesException();
        }

        return this.board.shoot(shotPosition);
    }

    private boolean shootInShips(Position shotPosition) {
        return this.fleet.shoot(shotPosition);
    }

    //endregion shoot

    //region PRINT

    public void printBoard(Consumer<String> printFunc) {
        this.board.print(printFunc);
    }

    public void printShots(Consumer<String> printFunc) {
        this.board.printShots(printFunc);
    }

    //endregion

    public boolean isOver() {
        return this.fleet.isSank();
    }
}
