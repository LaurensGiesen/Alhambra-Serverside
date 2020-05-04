package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.coin.Purse;
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
    private void createGame() {
        g1 = new Game();
        g2 = new Game();
        g3 = new Game();

        g1.addPlayer("A");
        g1.addPlayer("B");
        g1.addPlayer("C");
    }

    @Test
    void addPlayer() {
        assertDoesNotThrow(() -> g2.addPlayer("A"));
        assertEquals(new Player("A"), g2.getPlayerByName("A"));

        assertThrows(AlhambraGameRuleException.class, () -> g2.addPlayer("A")); //No unique name
        g2.addPlayer("B");
        g2.addPlayer("C");
        g2.addPlayer("D");
        g2.addPlayer("E");
        g2.addPlayer("F");
        assertThrows(AlhambraGameRuleException.class, () -> g2.addPlayer("G")); //Max 6 players
    }

    @Test
    void removePlayer() {
        assertDoesNotThrow(() -> g1.removePlayer("A"));
        assertNull(g1.getPlayerByName("A"));

        assertThrows(AlhambraEntityNotFoundException.class, () -> g1.removePlayer("D")); //Player not present
    }

    @Test
    void setCurrentPlayer() {
        Player p1 = new Player("player1");
        Player p2 = new Player("player2");
        Building b1 = new Building(Buildingtype.TOWER, 7, new Walling(false, false, false, false));
        Building b2 = new Building(Buildingtype.GARDEN, 9, new Walling(false, false, false, false));

        g3.addPlayer(p1.getPlayerName());
        g3.addPlayer(p2.getPlayerName());

        p1.getReserve().addBuilding(b1);
        p2.getCity().addBuilding(b2, new Location(1, 0));

//        assertEquals(p1, getCurrentPlayer());
//        assertEquals("player1", getCurrentPlayer().getPlayerName());

        p1.redesignCity(b1, new Location(1, 0));

//        assertEquals(p2, getCurrentPlayer());
//        assertEquals("player2", getCurrentPlayer().getPlayerName());

        p2.redesignCity(null, new Location(1, 0));

//        assertEquals(p1, getCurrentPlayer());
//        assertEquals("player1", getCurrentPlayer().getPlayerName());
    }

    @Test
    void populateBank() {

        Game g1 = new Game();

        Player p1 = new Player("Bart");
        Player p2 = new Player("José");

        g1.addPlayer(p1.getPlayerName());
        g1.addPlayer(p2.getPlayerName());

        g1.getBank().addCoin(new Coin(Currency.BLUE, 2));

        assertEquals(1, g1.getBank().getCoins().size());

        g1.getBank().addCoin(new Coin(Currency.ORANGE, 9));

        assertEquals(2, g1.getBank().getCoins().size());

        g1.startGame();

        assertEquals(4, g1.getBank().getCoins().size());

        g1.startGame();

        assertEquals(4, g1.getBank().getCoins().size());

        g1.getBank().removeCoin(new Coin(Currency.BLUE, 2));

        g1.startGame();

        assertEquals(4, g1.getBank().getCoins().size());


    }


    @Test
    void determineStarter() {
        g1.addStartMoney();
        g1.determineStarter();

        Player curr = g1.getCurrentPlayer();

        int currNr = curr.getMoney().getNumberOfCoins();
        int currAmount = curr.getMoney().getTotalAmount();
        int aNr = g1.getPlayerByName("A").getMoney().getNumberOfCoins();
        int bNr = g1.getPlayerByName("B").getMoney().getNumberOfCoins();
        int cNr = g1.getPlayerByName("C").getMoney().getNumberOfCoins();
        int aAmount = g1.getPlayerByName("A").getMoney().getTotalAmount();
        int bAmount = g1.getPlayerByName("B").getMoney().getTotalAmount();
        int cAmount = g1.getPlayerByName("C").getMoney().getTotalAmount();

        assertTrue(currNr < aNr || (currNr == aNr && currAmount <= aAmount));
        assertTrue(currNr < bNr || (currNr == bNr && currAmount <= bAmount));
        assertTrue(currNr < cNr || (currNr == cNr && currAmount <= cAmount));
    }
//    @Test
//    void populateMarket() {
//
//        Game g1 = new Game();
//
//        Player p1 = new Player("Bart");
//        Player p2 = new Player("José");
//
//        g1.addPlayer(p1.getPlayerName());
//        g1.addPlayer(p2.getPlayerName());
//
//        g1.startGame();
//
//        Coin c1 = new Coin(Currency.BLUE, 9);
//        Coin c2 = new Coin(Currency.BLUE, 8);
//
//        Purse temp = new Purse();
//        temp.addCoin(c1);
//        temp.addCoin(c2);
//
//        g1.buyBuilding("José", temp);
//
//        g1.populateMarket();
//
//        assertNotEquals(null, g1.getMarket().get(Currency.BLUE));
//
//    }
}