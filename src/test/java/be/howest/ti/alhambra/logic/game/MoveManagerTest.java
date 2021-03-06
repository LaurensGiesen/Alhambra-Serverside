package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.AlhambraController;
import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.money.Purse;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.building.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveManagerTest {

    Game g;
    Purse purse;
    Player currPlayer;
    Player otherPlayer;
    Building b1;
    Building b2;
    Location l1;
    Location l2;

    private void createGame(){
        g = new Game();
        g.addPlayer("a");
        g.addPlayer("b");
        Player pA = g.getPlayerByName("a");
        Player pB = g.getPlayerByName("b");
        g.getPlayerByName("a").setReady(true);
        g.getPlayerByName("b").setReady(true);
        TurnManager.startGame(g);

        purse = new Purse();
        Coin c1 = new Coin(Currency.BLUE, 5);

        purse.addCoin(c1);

        currPlayer = g.getPlayerByName(g.getCurrentPlayer());
        otherPlayer = pA == currPlayer ? pB : pA;

        currPlayer.getMoney().addCoin(c1);

    }

    private void createGameToRedesign(){
        createGame();
        b1 = new Building(Buildingtype.PAVILION, 5, new Walling(false, false, false, false));
        b2 = new Building(Buildingtype.PAVILION, 7, new Walling(false, false, false, false));
        l1 = new Location(1, 0);
        l2 = new Location(-1, 0);
        currPlayer.getReserve().getBuildings().add(b1);
        currPlayer.getCity().addBuilding(b2, l2);
    }


    @Test
    void canTakeMoney() {
    Game game = new Game();
    Player player = new Player("michiel");
    Player player1 = new Player("quinten");
    Player player2 = new Player("laurens");
    game.addPlayer(player.getPlayerName());
    game.addPlayer(player1.getPlayerName());
    game.addPlayer(player2.getPlayerName());

    Coin coin = new Coin(Currency.BLUE, 18);

    TurnManager.startGame(game);

    Player currentPlayer = game.getPlayerByName(game.getCurrentPlayer());
    Purse bank = game.getBank();

    Purse selectedCoins = new Purse();
    selectedCoins.addCoin(bank.getCoins().get(1));

    Purse selectedWrongCoins = new Purse();
    selectedWrongCoins.addCoin(coin);

    Purse selectedCoinsTotalIsToHigh = new Purse();
    selectedCoinsTotalIsToHigh.addCoin(bank.getCoins().get(0));
    selectedCoinsTotalIsToHigh.addCoin(bank.getCoins().get(1));
    selectedCoinsTotalIsToHigh.addCoin(bank.getCoins().get(2));
    selectedCoinsTotalIsToHigh.addCoin(bank.getCoins().get(3));

    assertTrue(MoveManager.canTakeMoney(game, currentPlayer, selectedCoins));
    assertThrows(AlhambraEntityNotFoundException.class, () -> MoveManager.canTakeMoney(game, currentPlayer, selectedWrongCoins));
    assertThrows(AlhambraGameRuleException.class, () -> MoveManager.canTakeMoney(game, currentPlayer, selectedCoinsTotalIsToHigh));

    int currentSize = currentPlayer.getMoney().getCoins().size();

    MoveManager.takeMoney(game, currentPlayer, selectedCoins);

    assertEquals(currentSize + selectedCoins.getCoins().size(), currentPlayer.getMoney().getCoins().size());
    }


    @Test
    void canBuyBuilding() {
        createGame();
        Player pC = new Player("c");

        assertThrows(AlhambraEntityNotFoundException.class, ()->MoveManager.canBuyBuilding(g, pC, purse, Currency.BLUE)); //Player not in game
        assertThrows(AlhambraGameRuleException.class, ()->MoveManager.canBuyBuilding(g, otherPlayer, purse, Currency.BLUE)); //Player not current player

        g.getMarket().replace(Currency.BLUE, null);
        assertThrows(AlhambraEntityNotFoundException.class, () -> MoveManager.canBuyBuilding(g, currPlayer, purse, Currency.BLUE)); //No building in currency

        g.getMarket().replace(Currency.BLUE, new Building(Buildingtype.PAVILION, 8, null));

        assertThrows(AlhambraGameRuleException.class, () -> MoveManager.canBuyBuilding(g, currPlayer, purse, Currency.BLUE)); //Not enough money
        assertThrows(AlhambraGameRuleException.class, () -> MoveManager.canBuyBuilding(g, currPlayer, new Purse(), Currency.BLUE)); //No money

        Coin c2 = new Coin(Currency.BLUE, 4);
        purse.addCoin(c2);
        try {
            currPlayer.getMoney().removeCoin(c2);
        } catch(Exception AlhambraEntityNotFoundException){
            //Do nothing
        }

        assertThrows(AlhambraEntityNotFoundException.class, () -> MoveManager.canBuyBuilding(g, currPlayer, purse, Currency.BLUE)); //Player doesnt have the money

        currPlayer.getMoney().addCoin(c2);
        assertThrows(AlhambraGameRuleException.class, () -> MoveManager.canBuyBuilding(g, currPlayer, purse, Currency.GREEN));
        assertDoesNotThrow(() -> MoveManager.canBuyBuilding(g, currPlayer, purse, Currency.BLUE)); //Good situation

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

    @Test
    void canRedesignCity() {
        createGameToRedesign();

        assertThrows(AlhambraEntityNotFoundException.class, ()->MoveManager.canRedesignCity(g, currPlayer, null, null));
        assertThrows(AlhambraEntityNotFoundException.class, ()->MoveManager.canRedesignCity(g, currPlayer, null, l1)); //location empty
        assertThrows(AlhambraEntityNotFoundException.class, ()->MoveManager.canRedesignCity(g, currPlayer, b2, l1)); //building not in reserve
        assertThrows(AlhambraGameRuleException.class, ()->MoveManager.canRedesignCity(g, currPlayer, b1, new Location(0,0))); //no valid location

        assertTrue(MoveManager.canRedesignCity(g, currPlayer, b1, l2)); //Place building on empty location
        assertTrue(MoveManager.canRedesignCity(g, currPlayer, b1, l1)); //Place building on taken location
        assertTrue(MoveManager.canRedesignCity(g, currPlayer, null, l2)); //Place building from board to reserve
    }

    @Test
    void redesignCity() {
        //Place on empty location
        createGameToRedesign();
        assertDoesNotThrow(() -> MoveManager.redesignCity(currPlayer, b1, l1));
        assertFalse(currPlayer.getReserve().getBuildings().contains(b1));
        assertEquals(b1, currPlayer.getCity().getLocation(l1).getBuilding());

        //Place on taken location
        createGameToRedesign();
        assertDoesNotThrow(() -> MoveManager.redesignCity(currPlayer, b1, l2));
        assertFalse(currPlayer.getReserve().getBuildings().contains(b1));
        assertTrue(currPlayer.getReserve().getBuildings().contains(b2));
        assertEquals(b1, currPlayer.getCity().getLocation(l2).getBuilding());

        //Place in reserve
        createGameToRedesign();
        assertDoesNotThrow(() -> MoveManager.redesignCity(currPlayer, null, l2));
        assertTrue(currPlayer.getReserve().getBuildings().contains(b2));
        assertNull(currPlayer.getCity().getLocation(l2).getBuilding());
    }

    @Test
    void leaveGame() {

        String playerName = "jonas";
        String playerName2 = "quinten";
        String playerName3 = "lau";
        AlhambraController controller = new AlhambraController();
        int gameId = Integer.parseInt(controller.createGame());
        controller.joinGame(gameId, playerName);
        controller.joinGame(gameId, playerName2);
        controller.joinGame(gameId, playerName3);
        assertEquals(3, controller.getServer().getGame(gameId).getPlayers().size());
        controller.leaveGame(gameId, playerName2);
        assertEquals(2, controller.getServer().getGame(gameId).getPlayers().size());
        controller.leaveGame(gameId, playerName3);
        assertEquals(1, controller.getServer().getGame(gameId).getPlayers().size());

    }

    @Test
    void leaveGameStarted() {

        String playerName = "jonas";
        String playerName2 = "quinten";
        String playerName3 = "lau";
        AlhambraController controller = new AlhambraController();
        int gameId = Integer.parseInt(controller.createGame());
        controller.joinGame(gameId, playerName);
        controller.joinGame(gameId, playerName2);
        controller.joinGame(gameId, playerName3);
        controller.setReady(gameId, playerName);
        controller.setReady(gameId, playerName2);
        controller.setReady(gameId, playerName3);

        assertEquals(3, controller.getServer().getGame(gameId).getPlayers().size());
        assertFalse(controller.getServer().getGame(gameId).isEnded());
        controller.leaveGame(gameId, playerName);
        controller.leaveGame(gameId, playerName2);
        assertTrue(controller.getServer().getGame(gameId).isEnded());
    }
}