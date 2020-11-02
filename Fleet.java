package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Fleet {
    final List<Ship> ships;

    Fleet() {
        ships = new ArrayList<>(ShipType.values().length);
    }

    public void add(Ship ship) {
        ships.add(ship);
    }

    public boolean shoot(Position shotPosition) {
        int countPrevAliveShips = countAliveShips();

        boolean hit = false;
        for (Ship ship : getAliveShips()) {

            boolean didHit = ship.shoot(shotPosition);
            if (didHit) {
                hit = true;
            }
        }

        return hit &&
                countPrevAliveShips != countAliveShips();
    }

    public boolean isSank() {
        return ships.stream().allMatch(Ship::isSank);
    }

    private List<Ship> getAliveShips() {
        return ships.stream().filter(Ship::isAlive).collect(Collectors.toList());
    }

    private int countAliveShips() {
        return (int) ships.stream().filter(Ship::isAlive).count();
    }
}
