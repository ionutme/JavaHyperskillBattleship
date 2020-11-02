package battleship;

import battleship.exceptions.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var in = new Scanner(System.in);

        var game = new Game();

        printEmptyBoard();
        placeShips(game, in);
        printStartOfGame();
        startShooting(game, in);
    }

    //region PLACE SHIPS

    private static void placeShips(Game game, Scanner in) {
        for (ShipType shipType : ShipType.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):", shipType, shipType.size);

            placeShip(game, shipType, in);

            game.printBoard(System.out::print);
        }
    }

    private static void placeShip(Game game, ShipType shipType, Scanner in) {
        String p1 = in.next();
        String p2 = in.next();

        try {
            game.placeShip(new Coordinates(p1, p2), shipType);
        } catch (InvalidShipLocationException |
                InvalidShipLengthException |
                ShipLocationTooCloseException exception) {
            System.out.println(exception.getMessage() + " " + "Try Again:");

            // recursive retry
            placeShip(game, shipType, in);
        }
    }

    //endregion

    //region SHOOT

    private static void startShooting(Game game, Scanner in) {
        System.out.println("Take a shot!");

        while (!game.isOver()) {
            ShotStatus shot = shoot(game, in);

            printStatus(game, shot);
        }

        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private static ShotStatus shoot(Game game, Scanner in) {
        var shotPosition = new Position(in.next());

        try {
            return game.shoot(shotPosition);
        } catch (InvalidCoordinatesException exception) {
            System.out.println(exception.getMessage() + " " + "Try Again:");

            // recursive retry
            return shoot(game, in);
        }
    }

    //endregion

    //region PRINT

    private static void printStartOfGame() {
        System.out.println("The game starts!");

        printEmptyBoard();
    }

    private static void printEmptyBoard() {
        new Board().print(System.out::print);
    }

    private static void printStatus(Game game, ShotStatus status) {
        game.printShots(System.out::print);

        printUserActionMessage(status);
    }

    private static void printUserActionMessage(ShotStatus status) {
        String userActionMessage;

        if (status.isShipSank) {
            userActionMessage = "You sank a ship! Specify a new target:";
        } else if (status.isHit) {
            userActionMessage = "You hit a ship! Try again:";
        } else {
            userActionMessage = "You missed. Try again:";
        }

        System.out.println(userActionMessage);
    }

    //endregion
}
