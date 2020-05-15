package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game createEmptyGame(){
        return new Game();
    }

    private Game createStartedGame(){
        Game game = new Game();
        game.addPlayer("a");
        game.addPlayer("b");
        game.addPlayer("c");
        game.getPlayerByName("a").setReady(true);
        game.getPlayerByName("b").setReady(true);
        game.getPlayerByName("c").setReady(true);
        TurnManager.startGame(game);
        return game;
    }

    @Test
    void addPlayer() {
        Game game = createEmptyGame();

        assertDoesNotThrow(()->game.addPlayer("a"));
        assertEquals(new Player("a"), game.getPlayerByName("a"));
        assertThrows(AlhambraGameRuleException.class, ()->game.addPlayer("a")); //No unique name

        game.addPlayer("b");
        game.addPlayer("c");
        game.addPlayer("d");
        game.addPlayer("e");
        game.addPlayer("f");
        assertThrows(AlhambraGameRuleException.class, ()->game.addPlayer("g")); //Max 6 players

        Game game2 = createStartedGame();
        assertThrows(AlhambraGameRuleException.class, ()->game2.addPlayer("d")); //No add possible after start
    }

    @Test
    void removePlayer() {
        Game g1 = createEmptyGame();
        g1.addPlayer("a");
        g1.addPlayer("b");

        assertThrows(AlhambraEntityNotFoundException.class, ()->g1.removePlayer("d")); //Player not present
        assertDoesNotThrow(()->g1.removePlayer("a"));
        assertNull(g1.getPlayerByName("a"));

        Game g2 = createStartedGame();
        assertDoesNotThrow(()->g2.removePlayer("a")); //Remove from started game

        Game g3 = createStartedGame();
        String currPlayer = g3.getCurrentPlayer();
        assertDoesNotThrow(()->g3.removePlayer(currPlayer)); //Remove currentPlayer
        assertNotEquals(currPlayer, g3.getCurrentPlayer()); //If current player removed, then turn has ended

        Game g4 = createStartedGame();
        assertDoesNotThrow(()->g4.removePlayer("a"));
        assertDoesNotThrow(()->g4.removePlayer("b"));
        assertTrue(g4.isEnded()); //If only 1 player is left of started game, game is finished

    }

    @Test
    void getScoringRound() {
        Game g = createStartedGame();
        assertTrue(g.getScoringRound()[1] >= 23 && g.getScoringRound()[1] < 44);
        assertTrue(g.getScoringRound()[0] >= 67 && g.getScoringRound()[0] < 88);
    }

    @Test
    void testEquals() {
        Game g = new Game();
        Game g2 = new Game();
        assertNotEquals(g, g2);
    }
}