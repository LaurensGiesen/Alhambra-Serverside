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
        createServer();

        server.getGame(21575).setStarted(true);
        server.getGame(21576).setStarted(false);
        server.getGame(21577).setStarted(false);

        server.getNotStartedGameIds();

        assertEquals(2, server.getNotStartedGameIds().size());
    }
}
