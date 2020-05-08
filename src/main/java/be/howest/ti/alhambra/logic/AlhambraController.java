package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.Game.Game;
import be.howest.ti.alhambra.logic.Game.Server;
import be.howest.ti.alhambra.logic.coin.Currency;
import java.util.List;
import java.util.Set;

public class AlhambraController {

    Server server;

    public AlhambraController() {
        server = new Server();
    }

    public Server getServer() {
        return server;
    }

    public Currency[] getCurrencies() {
        return Currency.values();
    }

    public String createGame() {
        return "" + server.newGame();
    }

    public String joinGame(int gameId, String playerName) {
        return server.getGame(gameId).addPlayer(playerName);

    }

    public Set<Game> getGames() {
        return server.getGames();
    }

    public List<Integer> getNotStartedGameIds() {
        return server.getNotStartedGameIds();
    }

    public boolean verifyPlayerToken(String token, int gameId, String playerName) {
        return server.getGame(gameId).getPlayerByName(playerName).isValidToken(token);
    }
}
