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
    Building bE = new Building(Buildingtype.GARDEN, 3, new Walling(false, true, false, false));
    Building bS = new Building(Buildingtype.PAVILION, 2, new Walling(false, false, true, false));
    Building bW = new Building(Buildingtype.SERAGLIO, 4, new Walling(false, false, false, true));
    Building b = new Building(Buildingtype.TOWER, 1, new Walling(false, false, false, false));

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

    @Test
    void isRemovable() {
        createCity();

        assertTrue(c.isRemovable(new Location(-1, 0)));
        assertTrue(c.isRemovable(new Location(2, -1)));

        assertFalse(c.isRemovable(new Location(0, 0))); //Fountain
        assertFalse(c.isRemovable(new Location(1,-2))); //Leaves empty space
        assertThrows(AlhambraEntityNotFoundException.class, ()->c.isRemovable(new Location(1, 0))); //No building present
    }

    @Test
    void getAmountBuildings() {
        createCity();

        assertEquals(3, c.getAmountOfBuildings(Buildingtype.SERAGLIO));
        assertEquals(0, c.getAmountOfBuildings(Buildingtype.TOWER));
        assertEquals(2, c.getAmountOfBuildings(Buildingtype.ARCADES));
        assertEquals(1, c.getAmountOfBuildings(Buildingtype.PAVILION));
    }
}