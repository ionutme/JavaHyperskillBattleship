package battleship;

import battleship.exceptions.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var game = new Game();

        game.board.print(System.out::print);

        placeShips(game);
    }

    private static void placeShips(Game game) {
        var in = new Scanner(System.in);

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
        } catch (InvalidShipLocationException | InvalidShipLengthException | ShipLocationTooCloseException exception) {
            System.out.println(exception.getMessage() + " " + "Try Again:");

            // recursive retry
            placeShip(game, ship, in);
        }
    }
}
