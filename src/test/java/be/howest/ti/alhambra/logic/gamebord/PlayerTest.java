package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player p1;
    Building b1 = new Building(Buildingtype.TOWER, 7, new Walling(false,false,false,false));
    Building b2 = new Building(Buildingtype.ARCADES, 5, new Walling(false, false, false, false));

    Location l1;

    @BeforeEach
    public void createPlayer(){
        p1 = new Player("player1");
        p1.getReserve().addBuilding(b1);
        p1.getCity().addBuilding(b2, new Location(-1, 0));
    }

    @Test
    void setReady() {
        assertFalse(p1.isReady());
        p1.setReady(true);
        assertTrue(p1.isReady());
        p1.setReady(false);
        assertFalse(p1.isReady());
    }

    @Test
    void isValidToken() {
        String token = p1.getToken();

        assertTrue(p1.isValidToken(token));
        assertFalse(p1.isValidToken(token + "aaa"));
        assertFalse(p1.isValidToken(null));
    }

    @Test
    void addScore() {
        assertEquals(0, p1.getScore());
        p1.addScore(50);
        assertEquals(50, p1.getScore());
        p1.addScore(20);
        assertEquals(70, p1.getScore());
        p1.addScore(0);
        assertEquals(70, p1.getScore());
        assertThrows(AlhambraGameRuleException.class, ()->p1.addScore(-50));
    }

    @Test
    void redesignCityBoardToReserve() {
        p1.redesignCity(null, new Location(-1, 0));
        assertTrue(p1.getReserve().getBuildings().contains(b2));
        assertTrue(p1.getReserve().getBuildings().contains(b1));
        assertTrue(p1.getCity().getLocation(new Location(-1, 0)).isEmpty());
        assertThrows(AlhambraEntityNotFoundException.class, ()->p1.redesignCity(null, null));
    }

    @Test
    void redesignCityReserveToBoard() {
        p1.redesignCity(b1, new Location(1, 0));
        assertFalse(p1.getReserve().getBuildings().contains(b1));
        assertEquals(b1, p1.getCity().getLocation(new Location(1, 0)).getBuilding());
        assertEquals(b2, p1.getCity().getLocation(new Location(-1, 0)).getBuilding());

        assertThrows(AlhambraEntityNotFoundException.class, ()->p1.redesignCity(b2, new Location(0, 1)));
    }

    @Test
    void redesignCityReplace() {
        p1.redesignCity(b1, new Location(-1, 0));

        assertThrows(AlhambraEntityNotFoundException.class, ()->p1.redesignCity(b1, new Location(-1, 0)));

        assertTrue(p1.getReserve().getBuildings().contains(b2));
        assertFalse(p1.getReserve().getBuildings().contains(b1));
        assertEquals(b1, p1.getCity().getLocation(new Location(-1, 0)).getBuilding());
    }


//    @Test
//    void buildBuildingOnAlhambra() {
//        p1.addBuildingToHand(b1);
//        p1.buildBuilding(b1, new Location(0,-1));
//        assertFalse(p1.getBuildingInHand().getBuildings().contains(b1));
//        assertEquals(b1, p1.getCity().getLocation(new Location(0, -1)).getBuilding());
//    }
//
//    @Test
//    void buildBuildingInReserve() {
//        p1.addBuildingToHand(b1);
//        p1.buildBuilding(b1,null);
//        assertFalse(p1.getBuildingInHand().getBuildings().contains(b1));
//        assertTrue(p1.getReserve().getBuildings().contains(b1));
//    }
}
