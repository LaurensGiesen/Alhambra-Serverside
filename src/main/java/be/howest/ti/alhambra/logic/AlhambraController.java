package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.Game.Server;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;

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

        return server.getGame(gameId).getPlayerByName(playerName).setReady(true);
    }
}
