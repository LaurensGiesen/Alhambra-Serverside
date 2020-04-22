package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BuildingPlaceTest {

    @Test
    void BuildingPlace() {
        Walling w = new Walling(true, false, false, true);
        Building b = new Building(Buildingtype.TOWER, 10,w);
    BuildingPlace bp = new BuildingPlace();
    bp.addBuilding(b);
    
    }

}
