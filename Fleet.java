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

    public List<Ship> getAliveShips() {
        return ships.stream().filter(Ship::isAlive).collect(Collectors.toList());
    }

    public int countAliveShips() {
        return (int) ships.stream().filter(Ship::isAlive).count();
    }

    public boolean isSank() {
        return ships.stream().allMatch(Ship::isSank);
    }
}
