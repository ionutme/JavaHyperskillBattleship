package battleship;

import java.util.Iterator;

public class CoordinateIterator implements Iterator<Position> {

    final Coordinates coordinates;
    final int size;

    int currentIndex;

    CoordinateIterator(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.size = coordinates.getDistance();

        currentIndex = 0;
    }

    @Override
    public Position next() {
        int x;
        char y;

        if (coordinates.isHorizontal()) {
            x = getNext(coordinates.p1.x);
            y = coordinates.p1.y;
        } else {
            x = coordinates.p1.x;
            y = getNext(coordinates.p1.y);
        }

        return new Position(x, y);
    }

    @Override
    public boolean hasNext() {
        return currentIndex < size;
    }

    private int getNext(int coordinate) {
        return coordinate + currentIndex++;
    }

    private char getNext(char coordinate) {
        return (char) (coordinate + currentIndex++);
    }
}
