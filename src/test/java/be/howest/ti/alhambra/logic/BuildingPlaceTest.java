package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BuildingPlaceTest {

    @Test //Test for addBuilding and removeBuilding
    void BuildingPlace() {
        Walling w = new Walling(true, false, false, true);
        Building b = new Building(Buildingtype.TOWER, 10,w);
    BuildingPlace bp = new BuildingPlace();
    bp.addBuilding(b);
    assertTrue(bp.getBuildings().contains(b));
    bp.removeBuilding(b);
    List<Building> buildings;
    buildings = new ArrayList<>();
    assertEquals(bp.getBuildings(), buildings);
    }


}
