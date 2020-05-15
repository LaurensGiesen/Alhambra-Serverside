package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.building.Location;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreCalculatorTest {

    @Test
    void scoreWithoutWalls() {
        Building pavilion = new Building(Buildingtype.PAVILION, 1, new Walling(false, false, false, false));
        Building arcade = new Building(Buildingtype.ARCADES, 1, new Walling(false, false, false, false));

        Player p1 = new Player("a");
        Player p2 = new Player("b");
        Player p3 = new Player("c");

        p1.getCity().addBuilding(pavilion, new Location(0, 1));
        p1.getCity().addBuilding(pavilion, new Location(0, 2));
        p2.getCity().addBuilding(pavilion, new Location(0, 1));
        p3.getCity().addBuilding(pavilion, new Location(0, 1));

        p1.getCity().addBuilding(arcade, new Location(0, 3));
        p3.getCity().addBuilding(arcade, new Location(0, 2));
        p3.getCity().addBuilding(arcade, new Location(0, 3));

        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);

        ScoreCalculator.score(players, 1);
        assertEquals(1, p1.getScore());
        assertEquals(0, p2.getScore());
        assertEquals(3, p3.getScore());

        ScoreCalculator.score(players, 2);
        assertEquals(12, p1.getScore());
        assertEquals(0, p2.getScore());
        assertEquals(13, p3.getScore());

        ScoreCalculator.score(players, 3);
        assertEquals(38, p1.getScore());
        assertEquals(4, p2.getScore());
        assertEquals(35, p3.getScore());
    }

    @Test
    void scoreWithWalls() {
        Building pavilion = new Building(Buildingtype.PAVILION, 1, new Walling(true, false, false, false));
        Building arcade = new Building(Buildingtype.ARCADES, 1, new Walling(true, true, false, false));
        Building tower = new Building(Buildingtype.TOWER, 1, new Walling(false, true, false, false));

        Player p1 = new Player("a");
        Player p2 = new Player("b");
        Player p3 = new Player("c");

        //2 pavilion, 1 arcade, 1 tower, wall of 5
        p1.getCity().addBuilding(pavilion, new Location(-1, 0));
        p1.getCity().addBuilding(pavilion, new Location(-1, -1));
        p1.getCity().addBuilding(arcade, new Location(-1, 1));
        p1.getCity().addBuilding(tower, new Location(0, 1));

        //3 pavilion, 1 arcade, no tower, wall of 5
        p2.getCity().addBuilding(pavilion, new Location(-1, 0));
        p2.getCity().addBuilding(pavilion, new Location(-1, -1));
        p2.getCity().addBuilding(pavilion, new Location(-1, -2));
        p2.getCity().addBuilding(arcade, new Location(-1, 1));


        //no pavilion, 1 arcade, 3 tower, wall of 3
        p3.getCity().addBuilding(arcade, new Location(-1, 0));
        p3.getCity().addBuilding(tower, new Location(0, 1));
        p3.getCity().addBuilding(tower, new Location(1, 1));
        p3.getCity().addBuilding(tower, new Location(2, 1));

        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);

        ScoreCalculator.score(players, 1);
        assertEquals(3, p1.getScore());
        assertEquals(4, p2.getScore());
        assertEquals(7, p3.getScore());

        ScoreCalculator.score(players, 2);
        assertEquals(16, p1.getScore());
        assertEquals(18, p2.getScore());
        assertEquals(24, p3.getScore());

        ScoreCalculator.score(players, 3);
        assertEquals(49, p1.getScore());
        assertEquals(46, p2.getScore());
        assertEquals(55, p3.getScore());
    }
}