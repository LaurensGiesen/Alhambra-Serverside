package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    Location l1;
    Location l2;
    Location l3;
    Building b1 = new Building(Buildingtype.ARCADES, 5, new Walling(true, true, false, false));
    Building fountain = new Building(null, 0, new Walling(false, false, false, false));

    public void createLocations(){
        l1 = new Location(1,1);
        l2 = new Location(0, 0);
        l3 = new Location(-2, -5);

        l1.setBuilding(b1);
        l2.setBuilding(fountain);
    }

    @Test
    void getBuilding() {
        createLocations();
        assertEquals(b1, l1.getBuilding());
        assertEquals(fountain, l2.getBuilding());
        assertNull(l3.getBuilding());
    }

    @Test
    void setBuilding() {
        createLocations();

        l1.setBuilding(fountain);
        assertEquals(fountain, l1.getBuilding());

        l1.setBuilding(null);
        assertNull(l1.getBuilding());
    }

    @Test
    void isEmpty() {
        createLocations();

        assertFalse(l1.isEmpty());
        assertFalse(l2.isEmpty());
        assertTrue(l3.isEmpty());
    }

    @Test
    void isFountain() {
        createLocations();

        assertFalse(l1.isFountain());
        assertTrue(l2.isFountain());
        assertFalse(l3.isFountain());
    }
}