package battleship;

public enum Ship {
    AircraftCarrier(5),
    Battleship(4),
    Submarine(3),
    Cruiser(3),
    Destroyer(2);

    final int size;

    Ship(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return this.name().replaceAll("(.)([A-Z])", "$1 $2");
    }
}
