package battleship;

public class ShotStatus {
    boolean isShipSank;
    boolean isHit;

    ShotStatus(boolean isHit, boolean isShipSank) {
        this.isShipSank = isShipSank;
        this.isHit = isHit;
    }
}
