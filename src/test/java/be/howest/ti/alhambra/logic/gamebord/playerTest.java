package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingPlace;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class playerTest {
    Walling w1;
    Walling w2;
    Building b1;
    Building b2;
    BuildingPlace reserve;
    City locations;
    Location lc;
    Player player1;

    public void locateBuildings() {
        w1 = new Walling(true, false, false, true);
        w2 = new Walling(false, true, false, true);
        b1 = new Building(Buildingtype.TOWER, 10,w1);
        b2 = new Building(Buildingtype.PAVILION, 12,w2);
        reserve = new BuildingPlace();
        reserve.addBuilding(b1);
        lc = new Location(15,16);
        locations = new City();
        locations.addBuilding(b2,lc);
        player1 = new Player("Michiel");
    }
    public void redesignBuilding() {

    }
    @Test
    void testScore() {
        locateBuildings();
        player1.addScore(15);
//        assertEquals(Player1.getScore(), 15);
    }
    @Test
    void testRedesignBuilding() {
        redesignBuilding();
    }

}
