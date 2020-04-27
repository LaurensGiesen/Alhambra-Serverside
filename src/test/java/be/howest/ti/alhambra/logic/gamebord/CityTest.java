package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {
    Building bN = new Building(Buildingtype.ARCADES, 1, new Walling(true, false, false, false));
    Building bE = new Building(Buildingtype.ARCADES, 3, new Walling(false, true, false, false));
    Building bS = new Building(Buildingtype.ARCADES, 2, new Walling(false, false, true, false));
    Building bW = new Building(Buildingtype.ARCADES, 4, new Walling(false, false, false, true));
    Building b = new Building(Buildingtype.ARCADES, 1, new Walling(false, false, false, false));

    City c;

    public void createCity (){
        c = new City();
        c.addBuilding(bN, new Location(-1, 0));
        c.addBuilding(bN, new Location(0, -1));
        c.addBuilding(bW, new Location(0, -2));
        c.addBuilding(bW, new Location(1, -2));
        c.addBuilding(bW, new Location(2, -2));
        c.addBuilding(bS, new Location(2, -1));
    }

    @Test
    void addBuilding() {
        createCity();

        assertDoesNotThrow(()->c.addBuilding(bN, new Location(0, 1)));
        assertEquals(bN, c.getLocation(new Location(0, 1)).getBuilding());
        assertDoesNotThrow(()->c.addBuilding(b, new Location(3, -2))); //without adjoining wall
        assertEquals(b, c.getLocation(new Location(3, -2)).getBuilding());
        assertDoesNotThrow(()->c.addBuilding(bN, new Location(3, -1))); //with adjoining wall
        assertEquals(bN, c.getLocation(new Location(3, -1)).getBuilding());

        assertThrows(AlhambraGameRuleException.class, ()->c.addBuilding(b, new Location(-1, 0))); //No empty location
        assertThrows(AlhambraGameRuleException.class, ()->c.addBuilding(b, new Location(0, 0))); //No empty location
        assertThrows(AlhambraGameRuleException.class, ()->c.addBuilding(bN, new Location(-1, -1))); //No adjoining walls
        assertThrows(AlhambraGameRuleException.class, ()->c.addBuilding(bN, new Location(-2, 0))); //Not reachable on foot
        assertThrows(AlhambraGameRuleException.class, ()->c.addBuilding(b, new Location(-1, 2))); //Not joined on one side
        assertThrows(AlhambraGameRuleException.class, ()->c.addBuilding(b, new Location(1, 0))); //Leaves empty space

        assertEquals(b, c.getLocation(new Location(3, -2)).getBuilding());
    }

    @Test
    void removeBuilding() {
        createCity();

        c.addBuilding(b, new Location(3, -2));

        assertDoesNotThrow(()->c.removeBuilding(new Location(3, -2)));
        assertNull(c.getLocation(new Location(3, -2)));

        assertThrows(AlhambraEntityNotFoundException.class, ()->c.removeBuilding(new Location(7, -7))); //Off grid location
        assertThrows(AlhambraEntityNotFoundException.class, ()->c.removeBuilding(new Location(3, -3))); //No building present
        assertThrows(AlhambraGameRuleException.class, ()->c.removeBuilding(new Location(1, -2))); //Make gap in Alhambra
        assertThrows(AlhambraGameRuleException.class, ()->c.removeBuilding(new Location(0,0))); //Remove fountain
    }

    @Test
    void replaceBuilding() {
        createCity();

        c.addBuilding(bS, new Location(-1, -1));

        assertThrows(AlhambraEntityNotFoundException.class, ()->c.replaceBuilding(b, new Location(3, -2))); //No building present
        assertThrows(AlhambraEntityNotFoundException.class, ()->c.replaceBuilding(b, new Location(0, 0))); //Replace fountain
        assertThrows(AlhambraEntityNotFoundException.class, ()->c.replaceBuilding(b, new Location(-1, -1))); //No adjoining sides

        c.addBuilding(b, new Location(3, -2));
        assertDoesNotThrow(()->c.replaceBuilding(bS, new Location(3, -2)));
        assertEquals(bS, c.getLocation(new Location(3, -2)).getBuilding());

        c.addBuilding(bN, new Location(3, -1));
        Building bNS = new Building(Buildingtype.ARCADES, 1, new Walling(true, false, true, false));
        assertDoesNotThrow(()->c.replaceBuilding(bNS, new Location(3, -2)));
    }

    @Test
    void isValidPlacing() {
        createCity();

        assertTrue(c.isValidPlacing(bS, new Location(-1, -1)));
        assertTrue(c.isValidPlacing(bS, new Location(-1, 1)));
        assertTrue(c.isValidPlacing(bS, new Location(3, -2)));

        assertFalse(c.isValidPlacing(bN, new Location(-1,-1))); //No adjoining sides
        assertFalse(c.isValidPlacing(bN, new Location(1,-1))); //No adjoining sides
        assertFalse(c.isValidPlacing(bS, new Location(-2,0))); //Not reachable on foot
        assertFalse(c.isValidPlacing(bN, new Location(-1,2))); //Not joined
        assertFalse(c.isValidPlacing(bS, new Location(1,0))); //Leaves empty space
    }



    //    @Test
//    void isEmpty() {
//        City c = createCity();
//
//        assertTrue(c.isEmpty(new Location(1, -1)));
//        assertTrue(c.isEmpty(new Location(7, -1)));
//        assertTrue(c.isEmpty(new Location(0, -7)));
//        assertFalse(c.isEmpty(new Location(0, 0)));
//        assertFalse(c.isEmpty(new Location(-1, 0)));
//        assertFalse(c.isEmpty(new Location(1, -2)));
//    }
//
//    @Test
//    void isReplaceable() {
//        City c = createCity();
//
//        assertTrue(c.isReplaceable(new Location(-1, 0)));
//        assertTrue(c.isReplaceable(new Location(0, -2)));
//        assertFalse(c.isReplaceable(new Location(0,0)));
//        assertFalse(c.isReplaceable(new Location(1,-1)));
//        assertFalse(c.isReplaceable(new Location(7,0)));
//        assertFalse(c.isReplaceable(new Location(0,7)));
//    }

}