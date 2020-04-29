package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {
    Building bN = new Building(Buildingtype.ARCADES, 1, new Walling(true, false, false, false));
    Building bE = new Building(Buildingtype.GARDEN, 3, new Walling(false, true, false, false));
    Building bS = new Building(Buildingtype.PAVILION, 2, new Walling(false, false, true, false));
    Building bW = new Building(Buildingtype.SERAGLIO, 4, new Walling(false, false, false, true));
    Building bNW = new Building(Buildingtype.TOWER, 1, new Walling(true, false, false, true));
    Building bESW = new Building(Buildingtype.CHAMBERS, 1, new Walling(false, true, true, true));
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
        assertTrue(c.getLocation(new Location(3, -2)).isEmpty());

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
        assertThrows(AlhambraGameRuleException.class, ()->c.replaceBuilding(b, new Location(0, 0))); //Replace fountain
        assertThrows(AlhambraGameRuleException.class, ()->c.replaceBuilding(b, new Location(-1, -1))); //No adjoining sides

        c.addBuilding(b, new Location(3, -2));
        assertDoesNotThrow(()->c.replaceBuilding(bS, new Location(3, -2)));
        assertEquals(bS, c.getLocation(new Location(3, -2)).getBuilding());
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

    @Test
    void getLengthWall() {
        createCity();

        assertEquals(3, c.getLengthWall()); //Check normal in 1 direction

        c.addBuilding(bS, new Location(-1, -1));
        c.replaceBuilding(bNW, new Location(0, -2));

        assertEquals(4, c.getLengthWall()); //Test for inside wall

        c.addBuilding(bS, new Location(-1, -2));
        assertEquals(4, c.getLengthWall()); //Test for additional inside wall

        c.addBuilding(bESW, new Location(3, -2));
        assertEquals(7, c.getLengthWall()); //Test for normal in many directions
    }

    @Test
    void getAvailableLocations() {
        createCity();

        Walling wN = new Walling(true, false, false, false);
        Walling wS = new Walling(false, false, true, false);
        Walling wA = new Walling(true, true, true, true); 

        List<Location> locationsN = new ArrayList<>();
        locationsN.add(new Location(0,1));
        locationsN.add(new Location(-1,1));
        locationsN.add(new Location(-1,-2));
        locationsN.add(new Location(2,0));

        assertEquals(locationsN, c.getAvailableLocations(wN));

        List<Location> locationsS = new ArrayList<>();
        locationsS.add(new Location(0,1));
        locationsS.add(new Location(-1,1));
        locationsS.add(new Location(-1,-1));
        locationsS.add(new Location(3,-2));
        locationsS.add(new Location(2,0));

        assertEquals(locationsS, c.getAvailableLocations(wS));

        List<Location> locationsA = new ArrayList<>();

        assertEquals(locationsA, c.getAvailableLocations(wA));
    }
}