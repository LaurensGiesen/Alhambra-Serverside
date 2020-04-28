package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.*;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BuildingPlaceTest {
    Walling w1;
    Building b1;
    Building b2;
    BuildingPlace bp;
public void createBuildingPlace() {
    w1 = new Walling(true, false, false, true);
    b1 = new Building(Buildingtype.TOWER, 10,w1);
    bp = new BuildingPlace();
    bp.addBuilding(b1);
}
    @Test
    void BuildingPlace() {
    createBuildingPlace();
    assertTrue(bp.getBuildings().contains(b1));
    bp.removeBuilding(b1);
    List<Building> buildings;
    buildings = new ArrayList<>();
    assertEquals(bp.getBuildings(), buildings);
    assertThrows(AlhambraEntityNotFoundException.class, () -> bp.removeBuilding(b2));
    }


}
