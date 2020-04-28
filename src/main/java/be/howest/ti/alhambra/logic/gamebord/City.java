package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Walling;


import java.util.HashSet;
import java.util.Set;

public class City {
    private Set<Location> locations = new HashSet<>();

    public City() {
        Building fountain = new Building(null, 0, new Walling(false, false, false, false));
        locations.add(new Location(0, 0, fountain));
    }

    
}
