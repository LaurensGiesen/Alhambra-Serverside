package be.howest.ti.alhambra.logic.building;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Citywall {

    /* ------------ FIELDS ------------ */
    private Location location;
    private WallingDirection direction;

    /* ------------ CONSTRUCTOR ------------ */
    public Citywall(Location location, WallingDirection direction) {
        this.location = location;
        this.direction = direction;
    }

    /* ------------ GETTERS ------------ */
    public Location getLocation() {
        return location;
    }

    public WallingDirection getDirection() {
        return direction;
    }

    public Queue<Citywall> getConnectedCitywalls() {
        Queue<Citywall> connectedCitywalls = new LinkedList<>();

        if (direction == WallingDirection.NORTH || direction == WallingDirection.SOUTH) {
            connectedCitywalls.add(new Citywall(location, WallingDirection.EAST));
            connectedCitywalls.add(new Citywall(location, WallingDirection.WEST));
            connectedCitywalls.add(new Citywall(location.getNeighbourLocation(WallingDirection.WEST), direction));
            connectedCitywalls.add(new Citywall(location.getNeighbourLocation(WallingDirection.EAST), direction));
            connectedCitywalls.add(new Citywall(location.getNeighbourLocation(direction).getNeighbourLocation(WallingDirection.EAST), WallingDirection.WEST));
            connectedCitywalls.add(new Citywall(location.getNeighbourLocation(direction).getNeighbourLocation(WallingDirection.WEST), WallingDirection.EAST));
        } else {
            connectedCitywalls.add(new Citywall(location, WallingDirection.NORTH));
            connectedCitywalls.add(new Citywall(location, WallingDirection.SOUTH));
            connectedCitywalls.add(new Citywall(location.getNeighbourLocation(WallingDirection.SOUTH), direction));
            connectedCitywalls.add(new Citywall(location.getNeighbourLocation(WallingDirection.NORTH), direction));
            connectedCitywalls.add(new Citywall(location.getNeighbourLocation(direction).getNeighbourLocation(WallingDirection.NORTH), WallingDirection.SOUTH));
            connectedCitywalls.add(new Citywall(location.getNeighbourLocation(direction).getNeighbourLocation(WallingDirection.SOUTH), WallingDirection.NORTH));
        }

        return connectedCitywalls;
    }


    /* ------------ EQUALS & HASH ------------ */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citywall citywall = (Citywall) o;
        return Objects.equals(location, citywall.location) &&
                direction == citywall.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, direction);
    }
}
