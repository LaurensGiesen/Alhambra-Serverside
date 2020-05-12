package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    Server server;

    public void createServer() {
        server = new Server();
        server.newGame();
        server.newGame();
        server.newGame();
    }

    @Test
    void newGame() {
        createServer();
        assertEquals(3, server.getGames().size());
    }

    @Test
    void getGame() {
        createServer();
        assertThrows(AlhambraEntityNotFoundException.class, () -> server.getGame(7));
    }

    @Test
    void resetGames() {
        createServer();
        server.resetGames();
        assertEquals(0, server.getGames().size());
    }

    @Test
    void getNotStartedGameIds() {
        server = new Server();
        server.newGame();
        int gameId = server.newGame();
        int gameId2 = server.newGame();

        server.getGame(gameId).setStarted(true);

        assertTrue(server.getNotStartedGameIds().contains(gameId2));
        assertFalse(server.getNotStartedGameIds().contains(gameId));
        ;
    }
}
