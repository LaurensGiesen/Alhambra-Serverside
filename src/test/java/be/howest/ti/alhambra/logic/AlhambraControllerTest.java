package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.gamebord.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlhambraControllerTest {

    AlhambraController controller;

    @BeforeEach
    void createController(){
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
}