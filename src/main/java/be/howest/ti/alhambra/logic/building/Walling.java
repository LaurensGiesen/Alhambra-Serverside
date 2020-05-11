package be.howest.ti.alhambra.logic.building;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Walling {

    private Map<WallingDirection, Boolean> walls = new HashMap<>();

    public Walling(boolean north, boolean east, boolean south, boolean west) {
        this.walls.put(WallingDirection.NORTH, north);
        this.walls.put(WallingDirection.EAST, east);
        this.walls.put(WallingDirection.SOUTH, south);
        this.walls.put(WallingDirection.WEST, west);
    }

    public Map<WallingDirection, Boolean> getWalls() {
        return walls;
    }


    @JsonIgnore public boolean getWallNorth() {
        return walls.get(WallingDirection.NORTH);
    }

    @JsonIgnore public boolean getWallEast() {
        return walls.get(WallingDirection.EAST);
    }

    @JsonIgnore public boolean getWallSouth() {
        return walls.get(WallingDirection.SOUTH);
    }

    @JsonIgnore public boolean getWallWest() {
        return walls.get(WallingDirection.WEST);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Walling walling = (Walling) o;
        return Objects.equals(walls, walling.walls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walls);
    }
}
