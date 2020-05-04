package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.gamebord.Location;
import be.howest.ti.alhambra.logic.gamebord.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {


    Game g1; // 2 player game;
    Game g2; //empty game
    Game g3;


    @BeforeEach
    private void createGame(){
        g1 = new Game();
        g2 = new Game();
        g3 = new Game();

        g1.addPlayer("A");
        g1.addPlayer("B");
    }

    @Test
    void addPlayer() {
        assertDoesNotThrow(()->g2.addPlayer("A"));
        assertEquals(new Player("A"), g2.getPlayerByName("A"));

        assertThrows(AlhambraGameRuleException.class, ()->g2.addPlayer("A")); //No unique name
        g2.addPlayer("B");
        g2.addPlayer("C");
        g2.addPlayer("D");
        g2.addPlayer("E");
        g2.addPlayer("F");
        assertThrows(AlhambraGameRuleException.class, ()->g2.addPlayer("G")); //Max 6 players
    }

    @Test
    void removePlayer() {
        assertDoesNotThrow(()->g1.removePlayer("A"));
        assertNull(g1.getPlayerByName("A"));

        assertThrows(AlhambraEntityNotFoundException.class, ()->g1.removePlayer("D")); //Player not present
    }
    
    @Test
    void setCurrentPlayer() {
        Player p1 = new Player("player1");
        Player p2 = new Player("player2");
        Building b1 = new Building(Buildingtype.TOWER, 7, new Walling(false,false,false,false));
        Building b2 = new Building(Buildingtype.GARDEN, 9, new Walling(false,false,false,false));

        g3.addPlayer(p1.getPlayerName());
        g3.addPlayer(p2.getPlayerName());

        p1.getReserve().addBuilding(b1);
        p2.getCity().addBuilding(b2, new Location(1,0));

//        assertEquals(p1, getCurrentPlayer());
//        assertEquals("player1", getCurrentPlayer().getPlayerName());

        p1.redesignCity(b1, new Location(1, 0));

//        assertEquals(p2, getCurrentPlayer());
//        assertEquals("player2", getCurrentPlayer().getPlayerName());

        p2.redesignCity(null, new Location(1, 0));

//        assertEquals(p1, getCurrentPlayer());
//        assertEquals("player1", getCurrentPlayer().getPlayerName());
    }


}