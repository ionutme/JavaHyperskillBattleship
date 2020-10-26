package battleship;

public class Position {
    final int x;
    final char y;

    Position(int x, char y) {
        this.x = x;
        this.y = y;
    }

    Position(String string) {
        this(getX(string), getY(string));
    }

    private static int getX(String string) {
        String row = string.substring(1);

        return Integer.parseInt(row);
    }

    private static char getY(String string) {
        char col = string.charAt(0);

        return Character.toUpperCase(col);
    }
}
