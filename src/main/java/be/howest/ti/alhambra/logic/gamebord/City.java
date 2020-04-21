package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Walling;

public class City {
    private Building[][] grid = new Building[][] {{null, null, null},{null, null, null},{null, null, null}};

    public City() {
        Building fountain = new Building(null, 0, new Walling(false, false, false, false));
        grid[1][1] = fountain;
    }



}
