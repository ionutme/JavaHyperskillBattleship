package battleship;

import java.util.function.Consumer;

public class Board {
    public static final char MARK_POSITION = 'O';
    public static final char EMPTY_POSITION = '~';
    public static final char HIT_POSITION = 'X';
    public static final char MISS_POSITION = 'M';

    private final char[][] board;
    private static final int SIZE = 10;

    public Board() {
        this.board = getEmptyBoard();
    }

    public void print(Consumer<String> func) {
        print(func, true);
    }

    public void printShots(Consumer<String> func) {
        print(func, false);
    }

    private void print(Consumer<String> func, boolean showShips) {
        String[][] printableBoard = this.getBoardWithMargins();
        for (int i = 0; i < printableBoard.length; i++) {
            for (int j = 0; j < printableBoard.length; j++) {
                String mark = getMarkRepresentation(printableBoard[i][j], showShips);

                func.accept(mark + " ");
            }

            func.accept(System.lineSeparator());
        }
    }

    private String getMarkRepresentation(String mark, boolean showShip) {
        return showShip || !mark.equals(Character.toString(MARK_POSITION))
               ? mark
               : Character.toString(EMPTY_POSITION);
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
        int row = getRow(position);
        int col = getCol(position);

        this.board[row][col] = MARK_POSITION;
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

    public boolean shoot(Position position) {
        int row = getRow(position);
        int col = getCol(position);

        return shoot(row, col);
    }

    private boolean shoot(int row, int col) {
        char prevMark = this.board[row][col];
        this.board[row][col] = prevMark == MARK_POSITION ||
                               prevMark == HIT_POSITION
                               ? HIT_POSITION
                               : MISS_POSITION;

        return this.board[row][col] == HIT_POSITION;
    }

    private boolean isEmptyPosition(int row, int col) {
        if (!isValidPosition(row, col)) {
            return true;
        }

        return this.board[row][col] == EMPTY_POSITION;
    }

    public boolean isValidPosition(Position position) {
        int row = getRow(position);
        int col = getCol(position);

        return isValidPosition(row, col);
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < SIZE &&
               col >= 0 && col < SIZE;
    }

    private int getRow(Position position) {
        return position.y - 'A';
    }

    private int getCol(Position position) {
        return position.x - 1;
    }
}
