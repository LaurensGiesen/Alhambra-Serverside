package be.howest.ti.alhambra.logic.building;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class BuildingPlace {
    /* ------------ FIELDS ------------ */
    private List<Building> buildings;

    /* ------------ CONSTRUCTOR ------------ */
    public BuildingPlace() {
        buildings = new ArrayList<>();
    }


    /* ------------ GETTERS ------------ */
    public List<Building> getBuildings() {
        return buildings;
    }

    /* ------------ PUBLIC METHODS ------------ */
    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public void removeBuilding(Building building) {
        if (buildings.contains(building)) {
            buildings.remove(building);
        } else {
            throw new AlhambraEntityNotFoundException("Building not present");
        }
    }

}
