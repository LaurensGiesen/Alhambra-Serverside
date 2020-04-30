package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingPlace;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
        p1.redesignCity(b1, new Location(-1, 0));
    }



    Walling w1;
    Walling w2;
    Building b1;
    Building b2;
    Building b3;
    BuildingPlace reserve;
    City city;
    Location lc;
    Player player1;

    public void locateBuildings() {
        w1 = new Walling(true, false, false, true);
        w2 = new Walling(false, true, false, true);

        player1 = new Player("Michiel");
    }
    public void redesignBuilding1() {
        b2 = new Building(Buildingtype.PAVILION, 12,w2);
        lc = new Location(15,16);
        city = new City();
        city.addBuilding(b2,lc);
        player1.redesignCity(b2, lc);
    }

    public void redesignBuilding2() { //uit de reserve naar board
        b1 = new Building(Buildingtype.TOWER, 10,w1);
        reserve = new BuildingPlace();
        reserve.addBuilding(b1);
        player1.redesignCity(b1, city);
    }

    @Test
    void testScore() {
        locateBuildings();
        player1.addScore(15);
//        assertEquals(Player1.getScore(), 15);
    }
    @Test
    void testRedesign1() {
        redesignBuilding1();
        List<Location> empty;
        empty = new ArrayList<>();
        assertEquals(city,empty);
        assertTrue(player1.getReserve().getBuildings().contains(b2));
    }
    @Test
    void testRedesign2() {
        redesignBuilding1();
        redesignBuilding2();
        List<Building> empty;
        empty = new ArrayList<>();
        assertEquals(player1.getReserve().getBuildings(),empty);
        assertTrue(city.getLocation().contains(b1));
    }


}
