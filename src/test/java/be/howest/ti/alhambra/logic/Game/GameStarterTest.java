package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStarterTest {


    Game g1;
    Game g2;
    Game g3;

    @BeforeEach
    private void createGames() {
        g1 = new Game();
        g1.addPlayer("A");
        g1.addPlayer("B");
        g1.addPlayer("C");
        g1.getPlayerByName("A").setReady(true);
        g1.getPlayerByName("B").setReady(true);

        g2 = new Game();
        g2.addPlayer("A");
        g2.getPlayerByName("A").setReady(true);

        g3 = new Game();
        g3.addPlayer("A");
        g3.addPlayer("B");
        g3.addPlayer("C");
        g3.getPlayerByName("A").setReady(true);
        g3.getPlayerByName("B").setReady(true);
        g3.getPlayerByName("C").setReady(true);
    }


    @Test
    void isReadyToStart() {
        createGames();

        assertFalse(GameStarter.isReadyToStart(g1)); //Not all players are ready
        assertFalse(GameStarter.isReadyToStart(g2)); //Not enough players
        assertTrue(GameStarter.isReadyToStart(g3)); //Normal situation
    }

    @Test
    void startGame() {
        createGames();

        assertThrows(AlhambraGameRuleException.class, () -> GameStarter.startGame(g1)); //Not all players ready
        assertThrows(AlhambraGameRuleException.class, () -> GameStarter.startGame(g2)); //Not enough players
        assertDoesNotThrow(() -> GameStarter.startGame(g3));

        assertTrue(g3.isStarted());
    }
}