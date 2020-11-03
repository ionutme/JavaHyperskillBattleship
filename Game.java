package battleship;

import battleship.exceptions.InvalidCoordinatesException;
import battleship.exceptions.InvalidShipLengthException;
import battleship.exceptions.InvalidShipLocationException;
import battleship.exceptions.ShipLocationTooCloseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Game {
    final Player playerOne;
    final Player playerTwo;
    private Player currentPlayer;
    private final InputStream input;
    private final Scanner scanner;

    public Game(InputStream input) {
        this.input = input;
        this.scanner = new Scanner(this.input);

        this.playerOne = new Player("1");
        this.playerTwo = new Player("2");
        this.currentPlayer = playerOne;
    }

    public void start() {
        placeShips();

        startShooting();
    }

    //region PLACE SHIPS

    private void placeShips() {
        placeShips(this.playerOne);

        printPressEnterAndPassToAnotherPlayer();

        placeShips(this.playerTwo);
    }

    private void placeShips(Player player) {
        printPlaceShips(player);

        for (ShipType shipType : ShipType.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):", shipType, shipType.size);

            placeShip(player, shipType);

            player.printBoard(System.out::print);
        }
    }

    private void placeShip(Player player, ShipType shipType) {
        String p1 = this.scanner.next();
        String p2 = this.scanner.next();

        try {
            player.placeShip(new Coordinates(p1, p2), shipType);
        } catch (InvalidShipLocationException |
                 InvalidShipLengthException |
                 ShipLocationTooCloseException exception) {
            System.out.println(exception.getMessage() + " " + "Try Again:");

            // recursive retry
            placeShip(player, shipType);
        }
    }

    //endregion

    //region SHOOT

    private void startShooting() {
        while (!isGameOver()) {
            printPressEnterAndPassToAnotherPlayer();

            printBoards();
            printItIsYourTurn();

            //VIEW SHOTS ON BOARD ALSO !!!!!!!!!!!!

            var shotPosition = new Position(this.scanner.next());
            Shot shot = shoot(getNextPlayer(), shotPosition);
            currentPlayer.markShotsBoard(shotPosition, shot);
            printShotResult(shot);

            currentPlayer = getNextPlayer();
        }

        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private Shot shoot(Player player, Position shotPosition) {
        try {
            return player.shoot(shotPosition);
        } catch (InvalidCoordinatesException exception) {
            System.out.println(exception.getMessage() + " " + "Try Again:");

            // recursive retry
            return shoot(player, shotPosition);
        }
    }

    //endregion

    //region PRINT

    private static void printPlaceShips(Player player) {
        System.out.printf("Player %s, place your ships on the game field\n", player.name);

        new Board().print(System.out::print);
    }

    private void printBoards() {
        currentPlayer.printShots(System.out::print);

        System.out.println("---------------------");

        currentPlayer.printBoard(System.out::print);

        System.out.println();
    }

    private void printShotResult(Shot shot) {
        String result;

        switch (shot) {
            case Hit:
                result = "You hit a ship!";
                break;
            case Sank:
                result = "You sank a ship!";
                break;
            case Miss:
                result = "You missed!";
                break;
            default:
                result = "something unexpected has happened!";
                break;
        }

        System.out.println(result);
    }

    private void printItIsYourTurn() {
        System.out.printf("Player %s, it's your turn:\n", this.currentPlayer.name);
    }

    private void printPressEnterAndPassToAnotherPlayer() {
        System.out.println("Press Enter and pass the move to another player");

        waitForEnter();
    }

    //endregion

    private Player getNextPlayer() {
        if (this.currentPlayer != this.playerOne) {
            return this.playerOne;
        } else {
            return this.playerTwo;
        }
    }

    private void waitForEnter() {
        try {
            this.input.read();
        } catch (IOException ignored) {
        }
    }

    private boolean isGameOver() {
        return this.playerOne.isDead() ||
                this.playerTwo.isDead();
    }
}
