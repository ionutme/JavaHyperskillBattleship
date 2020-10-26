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
        takeOneShot(game, in);
    }

    private static void placeShips(Game game, Scanner in) {
        for (Ship ship : Ship.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):", ship, ship.size);

            placeShip(game, ship, in);

            game.board.print(System.out::print);
        }
    }

    private static void placeShip(Game game, Ship ship, Scanner in) {
        String p1 = in.next();
        String p2 = in.next();

        try {
            game.placeShip(new Coordinates(p1, p2), ship);
        } catch (InvalidShipLocationException |
                InvalidShipLengthException |
                ShipLocationTooCloseException exception) {
            System.out.println(exception.getMessage() + " " + "Try Again:");

            // recursive retry
            placeShip(game, ship, in);
        }
    }

    private static void takeOneShot(Game game, Scanner in) {
        System.out.println("Take a shot!");

        shoot(game, in);
    }

    private static void shoot(Game game, Scanner in) {
        var shotPosition = new Position(in.next());

        boolean hit = false;
        try {
            hit = game.shoot(shotPosition);
        } catch (InvalidCoordinatesException exception) {
            System.out.println(exception.getMessage() + " " + "Try Again:");

            // recursive retry
            shoot(game, in);
        }

        printShot(game.board, hit);
    }

    private static void printShot(Board board, boolean hit) {
        board.printShots(System.out::print);

        System.out.printf("You %s !", hit ? "hit a ship" : "missed");

        board.print(System.out::print);
    }

    private static void printStartOfGame() {
        System.out.println("The game starts!");

        printEmptyBoard();
    }

    private static void printEmptyBoard() {
        new Board().print(System.out::print);
    }
}
