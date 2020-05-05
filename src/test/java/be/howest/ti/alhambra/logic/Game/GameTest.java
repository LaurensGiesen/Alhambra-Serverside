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
    void removePlayer(){
        Game game1 = createEmptyGame();
        game1.addPlayer("A");
        game1.addPlayer("B");

        assertDoesNotThrow(()->game1.removePlayer("A"));
        assertNull(game1.getPlayerByName("A"));

        assertThrows(AlhambraEntityNotFoundException.class, ()->game1.removePlayer("D")); //Player not present

        Game game2 = createStartedGame();
        assertDoesNotThrow(()->game2.removePlayer("A")); //Remove from started game
        Player currPlayer = game2.getCurrentPlayer();
        assertDoesNotThrow(()->game2.removePlayer(currPlayer.getPlayerName())); //Remove currentPlayer
        assertNotEquals(currPlayer, game1.getCurrentPlayer()); //If current player removed, then turn has ended

        Player currPlayer2 = game2.getCurrentPlayer();
        assertDoesNotThrow(()->game2.removePlayer(currPlayer2.getPlayerName())); //Remove currentPlayer
        assertTrue(game1.isEnded()); //If only 1 player is left of started game, game is finished
    }

    @Test
    void takeMoney() {
        Game game1 = createStartedGame();

        Coin coin1 = new Coin(Currency.GREEN, 1);
        Coin coin2 = new Coin(Currency.BLUE, 3);
        Coin coin3 = new Coin(Currency.YELLOW, 6);
        Coin coin4 = new Coin(Currency.BLUE, 12); //Non-existing coin

        game1.getBank().addCoin(coin1);
        game1.getBank().addCoin(coin2);
        game1.getBank().addCoin(coin3);

        Purse falseSinglePurse = new Purse();
        Purse singlePurse = new Purse();
        Purse falseDoublePurse = new Purse();
        Purse doublePurse = new Purse();

        falseSinglePurse.addCoin(coin4);
        singlePurse.addCoin(coin3);
        falseDoublePurse.addCoin(coin1);
        falseDoublePurse.addCoin(coin3);
        doublePurse.addCoin(coin1);
        doublePurse.addCoin(coin2);

        Player currPlayer = game1.getCurrentPlayer();
        Player inactivePlayer;
        if(currPlayer == game1.getPlayerByName("A")){
            inactivePlayer = game1.getPlayerByName("B");
        } else {
            inactivePlayer = game1.getPlayerByName("A");
        }

        assertThrows(AlhambraEntityNotFoundException.class, () -> game1.takeMoney(null, new Purse())); //No good input
        assertThrows(AlhambraGameRuleException.class, () -> game1.takeMoney(inactivePlayer.getPlayerName(), new Purse())); //Inactive player
        assertThrows(AlhambraEntityNotFoundException.class, () -> game1.takeMoney(currPlayer.getPlayerName(), falseSinglePurse)); //Coin not in bank
        assertThrows(AlhambraEntityNotFoundException.class, () -> game1.takeMoney(currPlayer.getPlayerName(), falseDoublePurse)); //2 coins with too high value
        assertDoesNotThrow(()->game1.takeMoney(currPlayer.getPlayerName(), singlePurse)); //Take 1 valid coin
        assertNotEquals(currPlayer, game1.getCurrentPlayer()); //After taking money, the turn changed
        assertFalse(game1.getBank().getCoins().contains(coin3)); //Taken coins are no longer in bank

        Player currPlayer2 = game1.getCurrentPlayer();
        assertDoesNotThrow(()->game1.takeMoney(currPlayer.getPlayerName(), doublePurse)); //Take 2 valid coins
        assertNotEquals(currPlayer2, game1.getCurrentPlayer()); //After taking money, the turn changed
        assertFalse(game1.getBank().getCoins().contains(coin3)); //Taken coins are no longer in bank
    }


//    Game g1; // 2 player game;
//    Game g2; //empty game
//    Game g3;

//    @BeforeEach
//    private void createGame(){
//        g1 = new Game();
//        g2 = new Game();
//        g3 = new Game();
//
//        g1.addPlayer("A");
//        g1.addPlayer("B");
//    }
//
//
//    @Test
//    void setCurrentPlayer() {
//        Player p1 = new Player("player1");
//        Player p2 = new Player("player2");
//        Building b1 = new Building(Buildingtype.TOWER, 7, new Walling(false,false,false,false));
//        Building b2 = new Building(Buildingtype.GARDEN, 9, new Walling(false,false,false,false));
//
//        g3.addPlayer(p1.getPlayerName());
//        g3.addPlayer(p2.getPlayerName());
//
//        p1.getReserve().addBuilding(b1);
//        p2.getCity().addBuilding(b2, new Location(1,0));
//
////        assertEquals(p1, getCurrentPlayer());
////        assertEquals("player1", getCurrentPlayer().getPlayerName());
//
//        p1.redesignCity(b1, new Location(1, 0));
//
////        assertEquals(p2, getCurrentPlayer());
////        assertEquals("player2", getCurrentPlayer().getPlayerName());
//
//        p2.redesignCity(null, new Location(1, 0));
//
////        assertEquals(p1, getCurrentPlayer());
////        assertEquals("player1", getCurrentPlayer().getPlayerName());
//    }
//
//    @Test
//    void populateBank() {
//
//        Game g1 = new Game();
//
//        Player p1 = new Player("Bart");
//        Player p2 = new Player("José");
//
//        g1.addPlayer(p1.getPlayerName());
//        g1.addPlayer(p2.getPlayerName());
//
//        g1.getBank().addCoin(new Coin(Currency.BLUE, 2));
//
//        assertEquals(1, g1.getBank().getCoins().size());
//
//        g1.getBank().addCoin(new Coin(Currency.ORANGE, 9));
//
//        assertEquals(2, g1.getBank().getCoins().size());
//
//        g1.startGame();
//
//        assertEquals(4, g1.getBank().getCoins().size());
//
//        g1.startGame();
//
//        assertEquals(4, g1.getBank().getCoins().size());
//
//        g1.getBank().removeCoin(new Coin(Currency.BLUE, 2));
//
//        g1.startGame();
//
//        assertEquals(4, g1.getBank().getCoins().size());
//    }
//
//
//    @Test
//    void determineStarter() {
//        g1.addStartMoney();
//        g1.determineStarter();
//
//        Player curr = g1.getCurrentPlayer();
//
//        int currNr = curr.getMoney().getNumberOfCoins();
//        int currAmount = curr.getMoney().getTotalAmount();
//        int aNr = g1.getPlayerByName("A").getMoney().getNumberOfCoins();
//        int bNr = g1.getPlayerByName("B").getMoney().getNumberOfCoins();
//        int cNr = g1.getPlayerByName("C").getMoney().getNumberOfCoins();
//        int aAmount = g1.getPlayerByName("A").getMoney().getTotalAmount();
//        int bAmount = g1.getPlayerByName("B").getMoney().getTotalAmount();
//        int cAmount = g1.getPlayerByName("C").getMoney().getTotalAmount();
//
//        assertTrue(currNr < aNr || (currNr == aNr && currAmount <= aAmount));
//        assertTrue(currNr < bNr || (currNr == bNr && currAmount <= bAmount));
//        assertTrue(currNr < cNr || (currNr == cNr && currAmount <= cAmount));
//    }
}