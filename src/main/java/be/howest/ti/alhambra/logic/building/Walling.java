package be.howest.ti.alhambra.logic.building;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

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
}
