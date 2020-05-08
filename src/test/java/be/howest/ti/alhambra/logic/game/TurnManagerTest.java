package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.gamebord.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {

    Game g1;

    private void getGameReadyToStart() {
        g1 = new Game();
        g1.addPlayer("A");
        g1.addPlayer("B");
        g1.getPlayerByName("A").setReady(true);
        g1.getPlayerByName("B").setReady(true);
    }

    private void getStartedGame() {
        getGameReadyToStart();
        TurnManager.startGame(g1);
    }


    @Test
    void isReadyToStart() {
        Game g1 = new Game();
        assertFalse(TurnManager.isReadyToStart(g1));
        g1.addPlayer("A");
        g1.getPlayerByName("A").setReady(true);
        assertFalse(TurnManager.isReadyToStart(g1));
        g1.addPlayer("B");
        assertFalse(TurnManager.isReadyToStart(g1));
        g1.getPlayerByName("B").setReady(true);
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
        assertTrue(g1.getPlayerByName("A").getMoney().getNumberOfCoins() > 0);
        assertTrue(g1.getPlayerByName("A").getMoney().getTotalAmount() >= 20);
    }

    @Test
    void endTurn() {
        getStartedGame();
        Player currPlayer = g1.getCurrentPlayer();
        assertDoesNotThrow(() -> TurnManager.endTurn(g1));
        assertNotEquals(currPlayer, g1.getCurrentPlayer());
        TurnManager.endTurn(g1);
        assertEquals(currPlayer, g1.getCurrentPlayer());
        assertEquals(4, g1.getBank().getNumberOfCoins());
        assertNotNull(g1.getMarket().get(Currency.BLUE));
        assertNotNull(g1.getMarket().get(Currency.YELLOW));
        assertNotNull(g1.getMarket().get(Currency.ORANGE));
        assertNotNull(g1.getMarket().get(Currency.GREEN));
    }
}