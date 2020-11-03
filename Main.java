package battleship;

import battleship.exceptions.*;

import java.lang.reflect.GenericArrayType;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var in = new Scanner(System.in);

        var game = new Game(System.in);
        game.start();
    }
}
