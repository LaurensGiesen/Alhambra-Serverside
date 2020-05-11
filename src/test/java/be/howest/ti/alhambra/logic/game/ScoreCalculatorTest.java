package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.gamebord.Location;
import be.howest.ti.alhambra.logic.gamebord.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreCalculatorTest {

    @Test
    void scoreWithoutWalls() {
        Building pavilion = new Building(Buildingtype.PAVILION, 1, new Walling(false, false, false, false));
        Building arcade = new Building(Buildingtype.ARCADES, 1, new Walling(false, false, false, false));

        Player p1 = new Player("A");
        Player p2 = new Player("B");
        Player p3 = new Player("C");

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

    void scoreWithWalls() {
        //TODO: add test for scoring with walls included
    }
}