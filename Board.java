package battleship;

import java.util.function.Consumer;

public class Board {
    public static final char MARK_POSITION = 'O';
    public static final char EMPTY_POSITION = '~';
    public static final char HIT_POSITION = '~';
    public static final char MISS_POSITION = '~';

    private final char[][] board;
    private static final int SIZE = 10;

    public Board() {
        this.board = getEmptyBoard();
    }

    public void print(Consumer<String> func) {
        String[][] printableBoard = this.getBoardWithMargins();
        for (int i = 0; i < printableBoard.length; i++) {
            for (int j = 0; j < printableBoard.length; j++) {
                func.accept(printableBoard[i][j] + " ");
            }

            func.accept(System.lineSeparator());
        }
    }

    private static char[][] getEmptyBoard() {
        char[][] board = new char[SIZE][SIZE];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = EMPTY_POSITION;
            }
        }

        return board;
    }

    private String[][] getBoardWithMargins() {
        int sizeWithMargins = SIZE + 1;
        var boardWithMargins = new String[sizeWithMargins][sizeWithMargins];

        setMargins(boardWithMargins);
        copyBoardValues(boardWithMargins);

        return boardWithMargins;
    }

    private void copyBoardValues(String[][] boardWithMargins) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                boardWithMargins[i + 1][j + 1] = Character.toString(this.board[i][j]);
            }
        }
    }

    private void setMargins(String[][] boardWithMargins) {
        boardWithMargins[0][0] = " ";
        for (int i = 1; i < boardWithMargins.length; i++) {
            boardWithMargins[0][i] = String.valueOf(i);
            boardWithMargins[i][0] = String.valueOf((char) ('A' + i - 1));
        }
    }

    public void mark(Position position) {
        int rawIndex = getRow(position);
        int colIndex = getCol(position);

        this.board[rawIndex][colIndex] = MARK_POSITION;
    }

    public boolean canMark(Position position) {
        int row = getRow(position);
        int col = getCol(position);

        return isEmptyPosition(row, col) &&
               isEmptyPosition(row - 1, col) &&
               isEmptyPosition(row + 1, col) &&
               isEmptyPosition(row, col - 1) &&
               isEmptyPosition(row, col + 1);
    }

    private boolean isEmptyPosition(int row, int col) {
        if (row < 0 || row >= SIZE ||
            col < 0 || col >= SIZE) {
            return true;
        }

        return this.board[row][col] == EMPTY_POSITION;
    }

    private int getRow(Position position) {
        return position.y - 'A';
    }

    private int getCol(Position position) {
        return position.x - 1;
    }
}
