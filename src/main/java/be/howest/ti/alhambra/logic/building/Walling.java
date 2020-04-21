package be.howest.ti.alhambra.logic.building;

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
}
