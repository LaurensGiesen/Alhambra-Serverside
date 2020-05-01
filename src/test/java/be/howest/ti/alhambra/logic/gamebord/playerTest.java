package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;

import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import org.junit.jupiter.api.Test;

public class playerTest {
    Player p1;
    City city;
    Building b1 = new Building(Buildingtype.TOWER, 7, new Walling(true,false,false,false));
    Building b2 = new Building(Buildingtype.ARCADES, 5, new Walling(false, false, false, false));

    Location l1;

    public void createPlayer(){
        p1 = new Player("player1");
        p1.getReserve().addBuilding(b1);
        p1.getCity().addBuilding(b2, new Location(-1, 0));
    }

    @Test
    void testRedesign() {
        createPlayer();
//        p1.redesignCity(b1, new Location(-1, 0));
    }

}
