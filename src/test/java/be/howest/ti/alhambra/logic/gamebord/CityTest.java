package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {
    public City createCity (){
        Building b1 = new Building(Buildingtype.ARCADES, 1, new Walling(true, false, false, false));
        Building b2 = new Building(Buildingtype.ARCADES, 1, new Walling(false, true, false, false));
        Building b3 = new Building(Buildingtype.ARCADES, 1, new Walling(false, false, true, false));
        Building b4 = new Building(Buildingtype.ARCADES, 1, new Walling(false, false, false, true));


        City c = new City();

        c.addBuilding(b1, new Location(-1, 0));
        c.addBuilding(b2, new Location(0, 1));
        c.addBuilding(b3, new Location(1, 0));
        c.addBuilding(b4, new Location(0, -1));

        

        return c;
    }
}