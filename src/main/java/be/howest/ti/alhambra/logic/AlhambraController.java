package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.Server;
import be.howest.ti.alhambra.logic.game.TurnManager;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
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

    public boolean setReady(int gameId, String playerName) {

        if (server.getGame(gameId) == null) {
            throw new AlhambraEntityNotFoundException("Game does not exist");
        }

        if (server.getGame(gameId).getPlayerByName(playerName) == null) {
            throw new AlhambraEntityNotFoundException("Player does not exist");
        }

        server.getGame(gameId).getPlayerByName(playerName).setReady(true);
        if(TurnManager.isReadyToStart(server.getGame(gameId))){
            TurnManager.startGame(server.getGame(gameId));
        }

        return true;
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

    public boolean setNotReady(int gameId, String playerName) {

        if (server.getGame(gameId) == null) {
            throw new AlhambraEntityNotFoundException("Game does not exist");
        }

        if (server.getGame(gameId).getPlayerByName(playerName) == null) {
            throw new AlhambraEntityNotFoundException("Player does not exist");
        }

        server.getGame(gameId).getPlayerByName(playerName).setReady(false);

        return true;
    }

}
