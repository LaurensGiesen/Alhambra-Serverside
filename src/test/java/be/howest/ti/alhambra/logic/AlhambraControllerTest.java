package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
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
        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.getGame(21888));
    }

    @Test
    void verifyPlayerToken() {
        int gameId = controller.server.newGame();
        String playerName = "joske";
        String token = controller.server.getGame(gameId).addPlayer(playerName);

        assertDoesNotThrow(() -> controller.verifyPlayerToken(token, gameId, playerName));
        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.verifyPlayerToken(token, 21988, playerName));
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
    
}





















