package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {

    Game g1;

    private void getGameReadyToStart() {
        g1 = new Game();
        g1.addPlayer("a");
        g1.addPlayer("b");
        g1.getPlayerByName("a").setReady(true);
        g1.getPlayerByName("b").setReady(true);
    }

    private void getStartedGame() {
        getGameReadyToStart();
        TurnManager.startGame(g1);
    }


    @Test
    void isReadyToStart() {
        Game g1 = new Game();
        assertFalse(TurnManager.isReadyToStart(g1));
        g1.addPlayer("a");
        g1.getPlayerByName("a").setReady(true);
        assertFalse(TurnManager.isReadyToStart(g1));
        g1.addPlayer("b");
        assertFalse(TurnManager.isReadyToStart(g1));
        g1.getPlayerByName("b").setReady(true);
        assertTrue(TurnManager.isReadyToStart(g1));
    }

    @Test
    void startGame() {
        getGameReadyToStart();

        assertDoesNotThrow(() -> TurnManager.startGame(g1));
        assertEquals(4, g1.getBank().getCoins().size());
        assertNotNull(g1.getMarket().get(Currency.BLUE));
        assertNotNull(g1.getMarket().get(Currency.ORANGE));
        assertNotNull(g1.getMarket().get(Currency.GREEN));
        assertNotNull(g1.getMarket().get(Currency.YELLOW));
        assertNotNull(g1.getCurrentPlayer());
        assertTrue(g1.getPlayerByName("a").getMoney().getNumberOfCoins() > 0);
        assertTrue(g1.getPlayerByName("a").getMoney().getTotalAmount() >= 20);
    }

    @Test
    void endTurn() {
        getStartedGame();
        String currPlayer = g1.getCurrentPlayer();
        assertDoesNotThrow(() -> TurnManager.endTurn(g1));
        assertNotEquals(currPlayer, g1.getCurrentPlayer());
        TurnManager.endTurn(g1);
        assertEquals(currPlayer, g1.getCurrentPlayer());
        assertEquals(4, g1.getBank().getNumberOfCoins());
        assertNotNull(g1.getMarket().get(Currency.BLUE));
        assertNotNull(g1.getMarket().get(Currency.YELLOW));
        assertNotNull(g1.getMarket().get(Currency.ORANGE));
        assertNotNull(g1.getMarket().get(Currency.GREEN));

        getStartedGame();
        g1.getBuildingStack().clear();
        g1.getMarket().replace(Currency.BLUE, null);
        assertDoesNotThrow(() -> TurnManager.endTurn(g1));
        assertTrue(g1.isEnded());

    }

    @Test
    void endGame() {
        getStartedGame();
        assertDoesNotThrow(()->TurnManager.endGame(g1));
        assertTrue(g1.isEnded());

        getGameReadyToStart();
        assertThrows(AlhambraGameRuleException.class, ()->TurnManager.endGame(g1));
    }
}