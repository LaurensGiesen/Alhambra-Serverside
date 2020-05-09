package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.Server;
import be.howest.ti.alhambra.logic.game.TurnManager;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.gamebord.Location;
import be.howest.ti.alhambra.logic.gamebord.Player;

import java.util.ArrayList;
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

    public List<Building> getBuildings() {
        return Building.allBuilding();
    }

    public Buildingtype[] getBuildingTypes() {
        return Buildingtype.values();
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
        if(server.getGame(gameId) ==  null) {
            throw new AlhambraEntityNotFoundException("Game not present");
        }
        if (playerName == null) {
            for(Player p: server.getGame(gameId).getPlayers()) {
                if (p.isValidToken(token)) {
                    return true;
                }
            }
            return false;
        } else {
            return server.getGame(gameId).getPlayerByName(playerName).isValidToken(token);
        }
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

    public Object getGame(int gameId) {

        if (server.getGame(gameId) == null) {
            throw new AlhambraEntityNotFoundException("Game not present");
        }
        return server.getGame(gameId);
    }

    public List<Location> getAvailableLocations(int gameId,String playerName, Walling walls) {

        if (server.getGame(gameId) == null) {
            throw new AlhambraEntityNotFoundException("Game does not exist");
        }

        if (server.getGame(gameId).getPlayerByName(playerName) == null) {
            throw new AlhambraEntityNotFoundException("Player does not exist");
        }

        return server.getGame(gameId).getPlayerByName(playerName).getCity().getAvailableLocations(walls);
    }

    public List<Object> buyBuilding(int gameId, String playerName) {

        if (server.getGame(gameId) == null) {
            throw new AlhambraEntityNotFoundException("Game does not exist");
        }

        if (server.getGame(gameId).getPlayerByName(playerName) == null) {
            throw new AlhambraEntityNotFoundException("Player does not exist");
        }

        Object bank = server.getGame(gameId).getBank();
        Object market =  server.getGame(gameId).getMarket();
        Object player = server.getGame(gameId).getPlayers();
        Object started = server.getGame(gameId).isStarted();
        Object ended = server.getGame(gameId).isEnded();
        Object currentPlayer = server.getGame(gameId).getCurrentPlayer().getPlayerName();
        Object buildingInHand = server.getGame(gameId).getPlayerByName(playerName).getBuildingInHand();

        List<Object> list = new ArrayList<>();
        list.add(bank);
        list.add(market);
        list.add(player);
        list.add(buildingInHand);
        list.add(started);
        list.add(ended);
        list.add(currentPlayer);

        return list;
    }

}
