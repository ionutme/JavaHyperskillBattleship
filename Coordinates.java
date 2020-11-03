package battleship;

import java.util.Iterator;

import battleship.exceptions.InvalidShipLocationException;

public class Coordinates implements Iterable<Position> {
    final Position p1;
    final Position p2;

    public Coordinates(String p1, String p2) {
        Position first = new Position(p1);
        Position second = new Position(p2);

        // order p1 then p2
        if (isLeftToRight(first, second) ||
            isUpToDown(first, second)) {
            this.p1 = first;
            this.p2 = second;
        } else {
            this.p1 = second;
            this.p2 = first;
        }

        validate();
    }

    //region VALIDATE

    private void validate() {
        if (!isVertical() &&
            !isHorizontal()) {
            throw new InvalidShipLocationException();
        }
    }

    //endregion

    //region ORIENTATION

    /**
     * F3, F7
     */
    public boolean isHorizontal() {
        return p1.y == p2.y;
    }

    public boolean isHorizontal(Position p1, Position p2) {
        return p1.y == p2.y;
    }

    /**
     * A1, D1
     */
    public boolean isVertical() {
        return p1.x == p2.x;
    }

    public boolean isVertical(Position p1, Position p2) {
        return p1.x == p2.x;
    }

    private boolean isUpToDown(Position first, Position second) {
        return isVertical(first, second) && first.y < second.y;
    }

    private boolean isLeftToRight(Position first, Position second) {
        return isHorizontal(first, second) && first.x < second.x;
    }

    //endregion

    //region DISTANCE

    public int getDistance() {
        return isVertical()
               ? getVerticalDistance()
               : getHorizontalDistance();
    }

    /**
     * A1 - D1 = 4
     */
    private int getVerticalDistance() {
        return getDistance(p1.y, p2.y);
    }

    /**
     * F3 - F7 = 5
     */
    private int getHorizontalDistance() {
        return getDistance(p1.x, p2.x);
    }

    private int getDistance(int c1, int c2) {
        return Math.abs(c1 - c2) + 1;
    }

    //endregion

    //region ITERATOR

    @Override
    public Iterator<Position> iterator() {
        return new CoordinateIterator(this);
    }

    //endregion
}
