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
        board = getEmptyBoard();
    }

    //region PRINT

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
                String marker = getMarkRepresentation(printableBoard[i][j], showShips);

                func.accept(marker + " ");
            }

            func.accept(System.lineSeparator());
        }
    }

    private String getMarkRepresentation(String mark, boolean showShip) {
        return showShip || !mark.equals(Character.toString(MARK_POSITION))
               ? mark
               : Character.toString(EMPTY_POSITION);
    }

    //endregion

    //region BOARD & MARGINS

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
        var boardWithMargins = new String[SIZE + 1][SIZE + 1];

        setMargins(boardWithMargins);
        setBoardValues(boardWithMargins);

        return boardWithMargins;
    }

    private void setBoardValues(String[][] boardWithMargins) {
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

    //endregion

    //region MARK

    public void mark(Position position) {
        mark(position, MARK_POSITION);
    }

    public void markShot(Position position, boolean hit) {
        char marker = hit ? HIT_POSITION : MISS_POSITION;

        mark(position, marker);
    }

    private void mark(Position position, char marker) {
        int row = getRow(position);
        int col = getCol(position);

        this.board[row][col] = marker;
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

    //endregion

    //region SHOOT

    public boolean shoot(Position position) {
        int row = getRow(position);
        int col = getCol(position);

        char boardMark = getShotMark(row, col);

        this.board[row][col] = boardMark;

        return boardMark == HIT_POSITION;
    }

    private char getShotMark(int row, int col) {
        char prevMark = this.board[row][col];

        return prevMark == MARK_POSITION ||
               prevMark == HIT_POSITION
               ? HIT_POSITION
               : MISS_POSITION;
    }

    //endregion

    //region POSITION

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

    //endregion
}
