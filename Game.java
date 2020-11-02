package battleship;

import battleship.exceptions.InvalidCoordinatesException;
import battleship.exceptions.InvalidShipLengthException;
import battleship.exceptions.ShipLocationTooCloseException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class GameStatus {
    boolean isOver;
    boolean isShipSank;
    boolean isHit;

    GameStatus(boolean isOver, boolean isShipSank, boolean isHit) {
        this.isOver = isOver;
        this.isShipSank = isShipSank;
        this.isHit = isHit;
    }
}

public class Game {
    final Board board;
    final Fleet fleet;

    public Game() {
        this.board = new Board();
        this.fleet = new Fleet();
    }

    public void placeShip(Coordinates coordinates, ShipType shipType) {
        validate(coordinates, shipType);

        addShip(coordinates, shipType);
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

    private void addShip(Coordinates coordinates, ShipType shipType) {
        this.fleet.add(new Ship(coordinates, shipType));
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
            if (!this.board.canMark(position)) {
                return false;
            }
        }

        return true;
    }

    public boolean isOver() {
        return fleet.isSank();
    }

    public GameStatus shoot(Position shotPosition) {
        boolean hit = shootOnBoard(shotPosition);
        boolean isShipSank = shootInShips(shotPosition);

        return new GameStatus(isOver(), isShipSank, hit);
    }

    private boolean shootOnBoard(Position shotPosition) {
        if (!this.board.isValidPosition(shotPosition)) {
            throw new InvalidCoordinatesException();
        }

        return this.board.shoot(shotPosition);
    }

    private boolean shootInShips(Position shotPosition) {
        List<Ship> aliveShips = fleet.getAliveShips();
        int countPrevAliveShips = fleet.countAliveShips();

        boolean hit = false;
        for (Ship ship : aliveShips) {
            boolean didHit = ship.shoot(shotPosition);
            if (didHit) {
                hit = true;
            }
        }

        //System.out.println("-----------COMPARE " + countPrevAliveShips + " WITH " + fleet.countAliveShips() + "-------------");

        return hit && countPrevAliveShips != fleet.countAliveShips();
    }
}
