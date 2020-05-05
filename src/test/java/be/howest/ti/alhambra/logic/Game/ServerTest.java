package be.howest.ti.alhambra.logic.Game;

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
        assertTrue(server.getGames().size() == 3);
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
        assertTrue(server.getGames().size() == 0);
    }
}
