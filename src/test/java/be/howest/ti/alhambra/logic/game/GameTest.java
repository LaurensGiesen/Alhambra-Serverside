package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.gamebord.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game createEmptyGame(){
        return new Game();
    }

    private Game createStartedGame(){
        Game game = new Game();
        game.addPlayer("A");
        game.addPlayer("B");
        game.addPlayer("C");
        game.getPlayerByName("A").setReady(true);
        game.getPlayerByName("B").setReady(true);
        game.getPlayerByName("C").setReady(true);
        TurnManager.startGame(game);
        return game;
    }
    
    @Test
    void addPlayer() {
        Game game = createEmptyGame();

        assertDoesNotThrow(()->game.addPlayer("A"));
        assertEquals(new Player("A"), game.getPlayerByName("A"));
        assertThrows(AlhambraGameRuleException.class, ()->game.addPlayer("A")); //No unique name

        game.addPlayer("B");
        game.addPlayer("C");
        game.addPlayer("D");
        game.addPlayer("E");
        game.addPlayer("F");
        assertThrows(AlhambraGameRuleException.class, ()->game.addPlayer("G")); //Max 6 players

        Game game2 = createStartedGame();
        assertThrows(AlhambraGameRuleException.class, ()->game2.addPlayer("D")); //No add possible after start
    }

    @Test
    void removePlayer() {
        Game g1 = createEmptyGame();
        g1.addPlayer("A");
        g1.addPlayer("B");

        assertThrows(AlhambraEntityNotFoundException.class, ()->g1.removePlayer("D")); //Player not present
        assertDoesNotThrow(()->g1.removePlayer("A"));
        assertNull(g1.getPlayerByName("A"));

        Game g2 = createStartedGame();
        assertDoesNotThrow(()->g2.removePlayer("A")); //Remove from started game

        Game g3 = createStartedGame();
        String currPlayer = g3.getCurrentPlayer();
        assertDoesNotThrow(()->g3.removePlayer(currPlayer)); //Remove currentPlayer
        assertNotEquals(currPlayer, g3.getCurrentPlayer()); //If current player removed, then turn has ended

        Game g4 = createStartedGame();
        assertDoesNotThrow(()->g4.removePlayer("A"));
        assertDoesNotThrow(()->g4.removePlayer("B"));
        assertTrue(g4.isEnded()); //If only 1 player is left of started game, game is finished

    }

    @Test
    void getScoringRound() {
        Game g = createStartedGame();
        assertTrue(g.getScoringRound()[0] > 23 && g.getScoringRound()[0] < 44);
        assertTrue(g.getScoringRound()[1] > 67 && g.getScoringRound()[1] < 88);
    }

    @Test
    void testEquals() {
        Game g = new Game();
        Game g2 = new Game();
        assertNotEquals(g, g2);
    }
}