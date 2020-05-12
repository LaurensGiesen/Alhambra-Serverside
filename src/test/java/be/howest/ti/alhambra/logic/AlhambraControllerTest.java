package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.TurnManager;
import be.howest.ti.alhambra.logic.gamebord.City;
import be.howest.ti.alhambra.logic.gamebord.Location;
import be.howest.ti.alhambra.logic.gamebord.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlhambraControllerTest {

    AlhambraController controller;

    @BeforeEach
    void createController() {
        controller = new AlhambraController();
    }

    @Test
    void isExistingEntity() {
        int gameId = controller.server.newGame();
        String playerName = "joske";
        controller.server.getGame(gameId).addPlayer(playerName);

        assertThrows(AlhambraEntityNotFoundException.class , () -> controller.setNotReady(15000, playerName));
        assertThrows(AlhambraEntityNotFoundException.class , () -> controller.setNotReady(gameId, null));
    }

    @Test
    void createGame() {
        int gameId = Integer.parseInt(controller.createGame());

        assertEquals(1, controller.getServer().getGames().size());
    }

    @Test
    void joinGame() {
        String playerName = "jonas";
        int gameId = Integer.parseInt(controller.createGame());
        controller.joinGame(gameId, playerName);

        assertEquals(1, controller.getServer().getGame(gameId).getPlayers().size());
    }

    @Test
    void setReady() {

        int gameId = controller.server.newGame();
        String playerName = "jonas";
        controller.server.getGame(gameId).addPlayer(playerName);

        assertTrue(controller.setReady(gameId, playerName));
        assertTrue(controller.server.getGame(gameId).getPlayerByName(playerName).isReady());
        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.setReady(21220, playerName));
        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.setReady(gameId, "joske"));
    }

    @Test
    void setNotReady() {
        int gameId = controller.server.newGame();
        String playerName = "thomas";
        controller.server.getGame(gameId).addPlayer(playerName);

        assertTrue(controller.setReady(gameId, playerName));
        assertTrue(controller.setNotReady(gameId, playerName));
        assertFalse(controller.server.getGame(gameId).getPlayerByName(playerName).isReady());
        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.setReady(21220, playerName));
        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.setReady(gameId, "joske"));
    }

    @Test
    void getGame() {
        int gameId = controller.server.newGame();
        String playerName = "joske";
        controller.server.getGame(gameId).addPlayer(playerName);

        assertDoesNotThrow(() -> controller.getGame(gameId));
        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.getGame(15000));
    }

    @Test
    void verifyPlayerToken() {
        int gameId = controller.server.newGame();
        String playerName = "joske";
        String token = controller.server.getGame(gameId).addPlayer(playerName);

        assertDoesNotThrow(() -> controller.verifyPlayerToken(token, gameId, playerName));
        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.verifyPlayerToken(token, 15000, playerName));
        assertTrue(controller.verifyPlayerToken(token, gameId, null));
        assertFalse(controller.verifyPlayerToken("N5HJ16VOKQBN9DIDEMNP", gameId, playerName));
    }

    @Test
    void getAvailableLocations() {
        int gameId = controller.server.newGame();
        Walling w1 = new Walling(false, false, false, false);
        Walling w2 = new Walling(true, false, true, false);
        String playerName = "joske";
        controller.server.getGame(gameId).addPlayer(playerName);

        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.getAvailableLocations(21888, playerName, w1));
        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.getAvailableLocations(gameId, null, w1));
        assertEquals(2, controller.getAvailableLocations(gameId, playerName, w2).size());
        assertEquals(4, controller.getAvailableLocations(gameId, playerName, w1).size());
    }

    @Test
    void getBuildings() {
        assertEquals(54, controller.getBuildings().size());
    }

    @Test
    void getBuildingsTypes() {
        assertEquals(6, controller.getBuildingTypes().length);
    }

    @Test
    void build() {
        int gameId = controller.server.newGame();
        Player player = new Player("michiel");
        Player player1 = new Player("Quinten");

        Game game = controller.server.getGame(gameId);

        game.addPlayer(player.getPlayerName());
        game.addPlayer(player1.getPlayerName());

        TurnManager.startGame(game);

        Player currentPlayer = game.getPlayerByName(game.getCurrentPlayer());

        Building buildingInHand = new Building(Buildingtype.ARCADES,1, new Walling(false, false, false, false));

        game.getPlayerByName(currentPlayer.getPlayerName()).getBuildingInHand().addBuilding(buildingInHand);

        assertDoesNotThrow(() -> controller.build(gameId, currentPlayer.getPlayerName(), buildingInHand, new Location(1,0)));
        assertNotEquals(currentPlayer, game.getCurrentPlayer());
        assertFalse(currentPlayer.getBuildingInHand().getBuildings().contains(buildingInHand));
        assertEquals(buildingInHand, currentPlayer.getCity().getLocation(new Location(1,0)).getBuilding());
    }

    @Test
    void redesignCity() {
        int gameId = controller.server.newGame();
        Player player = new Player("michiel");
        Player player1 = new Player("Quinten");

        Game game = controller.server.getGame(gameId);

        game.addPlayer(player.getPlayerName());
        game.addPlayer(player1.getPlayerName());

        TurnManager.startGame(game);

        Player currentPlayer = game.getPlayerByName(game.getCurrentPlayer());

        Building buildingInReserve = new Building(Buildingtype.ARCADES, 1, new Walling(false, false, false, false));
        Building buildingOnCity = new Building(Buildingtype.TOWER, 1, new Walling(false, false, false, false));

        currentPlayer.getCity().addBuilding(buildingOnCity, new Location(1,0));
        currentPlayer.getReserve().addBuilding(buildingInReserve);

        controller.redesignCity(gameId, currentPlayer.getPlayerName(), buildingInReserve, new Location(1, 0));

        assertNotEquals(currentPlayer, game.getCurrentPlayer());
        assertFalse(currentPlayer.getReserve().getBuildings().contains(buildingInReserve));
        assertEquals(buildingInReserve, currentPlayer.getCity().getLocation(new Location(1, 0)).getBuilding());
        assertNotEquals(buildingOnCity, currentPlayer.getCity().getLocation(new Location(1, 0)).getBuilding());
        assertTrue(currentPlayer.getReserve().getBuildings().contains(buildingOnCity));

        TurnManager.endTurn(game);

        controller.redesignCity(gameId, currentPlayer.getPlayerName(), null, new Location(1,0));
        assertTrue(currentPlayer.getReserve().getBuildings().contains(buildingInReserve));

        TurnManager.endTurn(game);

        controller.redesignCity(gameId, currentPlayer.getPlayerName(), buildingOnCity, new Location(1,0));
        assertEquals(buildingOnCity, currentPlayer.getCity().getLocation(new Location(1, 0)).getBuilding());
        assertTrue(currentPlayer.getReserve().getBuildings().contains(buildingInReserve));
    }
}





















