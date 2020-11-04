package battleship;

import battleship.exceptions.InvalidCoordinatesException;
import battleship.exceptions.InvalidShipLengthException;
import battleship.exceptions.InvalidShipLocationException;
import battleship.exceptions.ShipLocationTooCloseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Game {
    //region PLAYER FIELDS

    final Player playerOne;
    final Player playerTwo;
    private Player currentPlayer;

    //endregion

    //region I/O FIELDS

    private final InputStream input;
    private final PrintStream output;
    private final Scanner inputReader;

    //endregion

    public Game(InputStream input, PrintStream out) {
        this.input = input;
        this.output = out;
        this.inputReader = new Scanner(this.input);

        this.playerOne = new Player("1");
        this.playerTwo = new Player("2");
        this.currentPlayer = playerOne;
    }

    public void start() {
        placeShips();

        startShooting();

        printCongratulations();
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
            output.printf("Enter the coordinates of the %s (%d cells):", shipType, shipType.size);

            placeShip(player, shipType);

            player.printBoard(output::print);
        }
    }

    private void placeShip(Player player, ShipType shipType) {
        String p1 = getInputPosition();
        String p2 = getInputPosition();

        try {
            player.placeShip(new Coordinates(p1, p2), shipType);
        } catch (InvalidShipLocationException |
                 InvalidShipLengthException |
                 ShipLocationTooCloseException exception) {
            output.println(exception.getMessage() + " " + "Try Again:");

            // recursive retry
            placeShip(player, shipType);
        }
    }

    //endregion

    //region SHOOT

    private void startShooting() {
        while (!isGameOver()) {
            // wait user to press ENTER
            printPressEnterAndPassToAnotherPlayer();

            // show both boards
            printBoards();
            printItIsYourTurn();

            // print shooting result
            printShotResult(shoot(getOpponent(), getInputPosition()));

            // change turn
            currentPlayer = getOpponent();
        }
    }

    private Shot shoot(Player target, String chosenTarget) {
        var shotPosition = new Position(chosenTarget);

        Shot shot = shoot(target, shotPosition);
        currentPlayer.markShotsBoard(shotPosition, shot);

        return shot;
    }

    private Shot shoot(Player player, Position shotPosition) {
        try {
            return player.shoot(shotPosition);
        } catch (InvalidCoordinatesException exception) {
            output.println(exception.getMessage() + " " + "Try Again:");

            // recursive retry
            return shoot(player, shotPosition);
        }
    }

    //endregion

    //region PRINT

    private void printPlaceShips(Player player) {
        output.printf("Player %s, place your ships on the game field\n", player.name);

        new Board().print(output::print);
    }

    private void printBoards() {
        currentPlayer.printShots(output::print);

        output.println("---------------------");

        currentPlayer.printBoard(output::print);

        output.println();
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

        output.println(result);
    }

    private void printPressEnterAndPassToAnotherPlayer() {
        output.println("Press Enter and pass the move to another player");

        waitForEnter();
    }

    private void printItIsYourTurn() {
        output.printf("Player %s, it's your turn:\n", this.currentPlayer.name);
    }

    private void printCongratulations() {
        output.println("You sank the last ship. You won. Congratulations!");
    }

    //endregion

    //region STATE

    private boolean isGameOver() {
        return this.playerOne.isDead() ||
                this.playerTwo.isDead();
    }

    //endregion

    //region PLAYERS

    private Player getOpponent() {
        if (this.currentPlayer != this.playerOne) {
            return this.playerOne;
        } else {
            return this.playerTwo;
        }
    }

    //endregion

    //region INPUT

    private void waitForEnter() {
        try {
            input.read();
        } catch (IOException ignored) {
        }
    }

    private String getInputPosition() {
        return inputReader.next();
    }

    //endregion
}
