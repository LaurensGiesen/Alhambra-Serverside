package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.gamebord.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game g1; // 2 player game;
    Game g2; //empty game

    @BeforeEach
    private void createGame(){
        g1 = new Game();

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
}