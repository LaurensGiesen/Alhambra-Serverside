package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.Game.Server;
import be.howest.ti.alhambra.logic.coin.Currency;

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
}
