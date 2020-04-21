package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Buildingplace {

    private List<Building> buildings;

    public Buildingplace() {

       buildings = new ArrayList<>();
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void addBuilding(Building building) {

        buildings.add(building);
    }

    public void removeBuilding(Building building) {
        if(buildings.contains(building)) {
            buildings.remove(building);
        } else {
            throw new AlhambraEntityNotFoundException("Building not present");
        }
    }
}
