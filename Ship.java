package battleship;

import java.util.Arrays;

public class Ship {
    final Coordinates coordinates;
    final ShipType type;
    ShipPart[] parts;

    Ship(Coordinates coordinates, ShipType type) {
        this.parts = new ShipPart[type.size];

        int i = 0;
        for (Position position : coordinates) {
            this.parts[i++] = new ShipPart(position);
        }

        this.coordinates = coordinates;
        this.type = type;
    }

    boolean isAlive() {
        return !isSank();
    }

    boolean isSank() {
        return Arrays.stream(parts).allMatch(shipPart -> shipPart.isHit);
    }

    boolean shoot(Position shotPosition) {
        boolean hit = false;

        for (ShipPart shipPart : this.parts) {
            boolean didHit = shipPart.shoot(shotPosition);
            if (didHit) {
                hit = true;
            }
        }

        return hit;
    }
}
