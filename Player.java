package battleship;

import battleship.exceptions.InvalidCoordinatesException;
import battleship.exceptions.InvalidShipLengthException;
import battleship.exceptions.ShipLocationTooCloseException;

import java.util.function.Consumer;

public class Player {
    final String name;
    private final Board board;
    private final Board shotsBoard;
    final Fleet fleet;

    public Player(String name) {
        this.name = name;
        this.board = new Board();
        this.shotsBoard = new Board();
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

    public void markShotsBoard(Position position, Shot shot) {
        boolean isHit = shot == Shot.Hit ||
                        shot == Shot.Sank;

        shotsBoard.markShot(position, isHit);
    }

    public Shot shoot(Position shotPosition) {
        shootOnBoard(shotPosition);

        return shootInShips(shotPosition);
    }

    private boolean shootOnBoard(Position shotPosition) {
        if (!board.isValidPosition(shotPosition)) {
            throw new InvalidCoordinatesException();
        }

        return board.shoot(shotPosition);
    }

    private Shot shootInShips(Position shotPosition) {
        return fleet.shoot(shotPosition);
    }

    //endregion shoot

    //region PRINT

    public void printBoard(Consumer<String> printFunc) {
        board.print(printFunc);
    }

    public void printShots(Consumer<String> printFunc) {
        shotsBoard.printShots(printFunc);
    }

    //endregion

    //region STATE

    public boolean isDead() {
        return this.fleet.isSank();
    }

    //endregion
}
