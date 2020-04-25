package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {
    public City createCity (){
        Building bN = new Building(Buildingtype.ARCADES, 1, new Walling(true, false, false, false));
        Building bE = new Building(Buildingtype.ARCADES, 3, new Walling(false, true, false, false));
        Building bS = new Building(Buildingtype.ARCADES, 2, new Walling(false, false, true, false));
        Building bW = new Building(Buildingtype.ARCADES, 4, new Walling(false, false, false, true));


        City c = new City();

        c.addBuilding(bN, new Location(-1, 0));
        c.addBuilding(bN, new Location(0, -1));
        c.addBuilding(bW, new Location(0, -2));
        c.addBuilding(bW, new Location(1, -2));
        c.addBuilding(bW, new Location(2, -2));
        c.addBuilding(bS, new Location(2, -1));

        return c;
    }

    @Test
    void isEmpty() {
        City c = createCity();

        assertTrue(c.isEmpty(new Location(1, -1)));
        assertTrue(c.isEmpty(new Location(7, -1)));
        assertTrue(c.isEmpty(new Location(0, -7)));
        assertFalse(c.isEmpty(new Location(0, 0)));
        assertFalse(c.isEmpty(new Location(-1, 0)));
        assertFalse(c.isEmpty(new Location(1, -2)));
    }

    @Test
    void isReplaceable() {
        City c = createCity();

        assertTrue(c.isReplaceable(new Location(-1, 0)));
        assertTrue(c.isReplaceable(new Location(0, -2)));
        assertFalse(c.isReplaceable(new Location(0,0)));
        assertFalse(c.isReplaceable(new Location(1,-1)));
        assertFalse(c.isReplaceable(new Location(7,0)));
        assertFalse(c.isReplaceable(new Location(0,7)));
    }

    @Test
    void isValidPlacing() {
        Building bN = new Building(Buildingtype.ARCADES, 1, new Walling(true, false, false, false));
        Building bS = new Building(Buildingtype.ARCADES, 2, new Walling(false, false, true, false));

        City c = createCity();

        assertTrue(c.isValidPlacing(bS, new Location(-1, -1)));
        assertTrue(c.isValidPlacing(bS, new Location(-1, 1)));
        assertTrue(c.isValidPlacing(bS, new Location(3, -2)));

        assertFalse(c.isValidPlacing(bN, new Location(-1,-1)));
        assertFalse(c.isValidPlacing(bS, new Location(-2,0)));
        assertFalse(c.isValidPlacing(bN, new Location(-1,2)));
        assertFalse(c.isValidPlacing(bS, new Location(1,0)));
    }
}