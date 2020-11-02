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

    private static void placeShips(Game game, Scanner in) {
        for (ShipType shipType : ShipType.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):", shipType, shipType.size);

            placeShip(game, shipType, in);

            game.board.print(System.out::print);
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

    private static void startShooting(Game game, Scanner in) {
        System.out.println("Take a shot!");

        while (!game.isOver()) {
            GameStatus shot = shoot(game, in);

            printShot(game.board, shot);
        }

        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private static GameStatus shoot(Game game, Scanner in) {
        var shotPosition = new Position(in.next());

        try {
            return game.shoot(shotPosition);
        } catch (InvalidCoordinatesException exception) {
            System.out.println(exception.getMessage() + " " + "Try Again:");

            // recursive retry
            return shoot(game, in);
        }
    }

    private static void printShot(Board board, GameStatus status) {
        board.printShots(System.out::print);

        if (status.isShipSank) {
            System.out.println("You sank a ship! Specify a new target:");
        } else {
            System.out.printf("You %s Try again:", status.isHit ? "hit a ship!" : "missed.");
        }
    }

    private static void printStartOfGame() {
        System.out.println("The game starts!");

        printEmptyBoard();
    }

    private static void printEmptyBoard() {
        new Board().print(System.out::print);
    }
}
