package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.coin.Purse;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.gamebord.Location;
import be.howest.ti.alhambra.logic.gamebord.Player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveManagerTest {

    Game g;
    Purse purse;
    Player currPlayer;
    Player otherPlayer;

    private void createGame(){
        g = new Game();
        g.addPlayer("A");
        g.addPlayer("B");
        Player pA = g.getPlayerByName("A");
        Player pB = g.getPlayerByName("B");
        g.getPlayerByName("A").setReady(true);
        g.getPlayerByName("B").setReady(true);
        TurnManager.startGame(g);

        purse = new Purse();
        Coin c1 = new Coin(Currency.BLUE, 5);

        purse.addCoin(c1);

        currPlayer = g.getCurrentPlayer();
        otherPlayer = pA == currPlayer ? pB : pA;

        currPlayer.getMoney().addCoin(c1);

    }


    @Test
    void canTakeMoney() {
    }


    @Test
    void canBuyBuilding() {
        createGame();
        Player pC = new Player("C");

        assertThrows(AlhambraEntityNotFoundException.class, ()->MoveManager.canBuyBuilding(g, pC, purse)); //Player not in game
        assertThrows(AlhambraGameRuleException.class, ()->MoveManager.canBuyBuilding(g, otherPlayer, purse)); //Player not current player

        g.getMarket().replace(Currency.BLUE, null);
        assertThrows(AlhambraEntityNotFoundException.class, () -> MoveManager.canBuyBuilding(g, currPlayer, purse)); //No building in currency

        g.getMarket().replace(Currency.BLUE, new Building(Buildingtype.PAVILION, 8, null));

        assertThrows(AlhambraGameRuleException.class, () -> MoveManager.canBuyBuilding(g, currPlayer, purse)); //Not enough money

        Coin c2 = new Coin(Currency.BLUE, 4);
        purse.addCoin(c2);

        assertThrows(AlhambraEntityNotFoundException.class, () -> MoveManager.canBuyBuilding(g, currPlayer, purse)); //Player doesnt have the money

        currPlayer.getMoney().addCoin(c2);

        assertDoesNotThrow(() -> MoveManager.canBuyBuilding(g, currPlayer, purse)); //Good situation
    }

    @Test
    void buyBuilding() {
        createGame();
        MoveManager.buyBuilding(currPlayer, g.getMarket(), purse);

        assertNull(g.getMarket().get(Currency.BLUE));
        assertFalse(currPlayer.getBuildingInHand().getBuildings().isEmpty());
        assertNotEquals(0, currPlayer.getMoney().getNumberOfCoins());
    }

    @Test
    void canBuildBuilding() {
        createGame();
        Building b1 = new Building(Buildingtype.PAVILION, 5, new Walling(false, false, false, false));
        Building b2 = new Building(Buildingtype.TOWER, 6, new Walling(false, false, false, false));
        currPlayer.getBuildingInHand().addBuilding(b1);
        Location l1 = new Location(2, 0);
        Location l2 = new Location(1, 0);

        assertThrows(AlhambraEntityNotFoundException.class, ()-> MoveManager.canBuildBuilding(g, currPlayer, b2, l1)); //Building not in hand
        assertThrows(AlhambraGameRuleException.class, ()-> MoveManager.canBuildBuilding(g, currPlayer, b1, l1)); //location not null && invalid placing
        assertTrue(MoveManager.canBuildBuilding(g, currPlayer, b1, l2)); //location not null && valid placing
        assertTrue(MoveManager.canBuildBuilding(g, currPlayer, b1, null)); //location null
    }

    @Test
    void buildBuilding() {
        createGame();
        Building b1 = new Building(Buildingtype.PAVILION, 5, new Walling(false, false, false, false));
        Location l1 = new Location(1, 0);
        currPlayer.getBuildingInHand().addBuilding(b1);

        //Build to Alhambra
        assertDoesNotThrow(()->MoveManager.buildBuilding(currPlayer, b1, l1));
        assertEquals(b1, currPlayer.getCity().getLocation(l1).getBuilding());
        assertFalse(currPlayer.getBuildingInHand().getBuildings().contains(b1));

        //Build to Reserve
        createGame();
        currPlayer.getBuildingInHand().addBuilding(b1);
        assertDoesNotThrow(()->MoveManager.buildBuilding(currPlayer, b1, null));
        assertTrue(currPlayer.getReserve().getBuildings().contains(b1));
        assertFalse(currPlayer.getBuildingInHand().getBuildings().contains(b1));
    }
}