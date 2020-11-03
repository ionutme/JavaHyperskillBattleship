package battleship;

public class ShipPart {
    final Position position;
    boolean isHit;

    ShipPart(Position position) {
        this.position = position;
        this.isHit = false;
    }

    public boolean shoot(Position position) {
        if (isHit(position)) {
            isHit = true;
        }

        return isHit;
    }

    public boolean isHit(Position shotPosition) {
        return position.x == shotPosition.x &&
               position.y == shotPosition.y;
    }
}
