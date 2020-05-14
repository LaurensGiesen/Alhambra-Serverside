package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.money.Purse;
import be.howest.ti.alhambra.logic.game.*;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.building.Location;
import be.howest.ti.alhambra.logic.game.Player;

import java.util.List;
import java.util.Set;

public class AlhambraController {

    /* ------------ FIELDS ------------ */

    Server server;


    /* ------------ CONSTRUCTOR ------------ */

    public AlhambraController() {
        server = new Server();
    }


    /* ------------ GETTERS ------------ */

    public Server getServer() {
        return server;
    }


    /* ------------ PUBLIC METHODS ------------ */

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

        isExistingEntity(gameId, playerName);

        server.getGame(gameId).getPlayerByName(playerName).setReady(true);
        if (TurnManager.isReadyToStart(server.getGame(gameId))) {
            TurnManager.startGame(server.getGame(gameId));
        }

        return true;
    }

    public Set<Game> getGames() {
        return server.getNotStartedGames();
    }

    public List<Integer> getNotStartedGameIds() {
        return server.getNotStartedGameIds();
    }

    public boolean verifyPlayerToken(String token, int gameId, String playerName) {
        if (server.getGame(gameId) == null) {
            throw new AlhambraEntityNotFoundException("Game not present");
        }
        if (playerName == null) {
            for (Player p : server.getGame(gameId).getPlayers()) {
                if (p.isValidToken(token)) {
                    return true;
                }
            }
            return false;
        } else {
            return server.getGame(gameId).getPlayerByName(playerName).isValidToken(token);
        }
    }

    public boolean verifyAdminToken(String adminToken) {

        return server.isValidAdminToken(adminToken);
    }

    public boolean setNotReady(int gameId, String playerName) {

        isExistingEntity(gameId, playerName);

        server.getGame(gameId).getPlayerByName(playerName).setReady(false);

        return true;
    }

    public Object getGame(int gameId) {

        if (server.getGame(gameId) == null) {
            throw new AlhambraEntityNotFoundException("Game not present");
        }
        return server.getGame(gameId);
    }

    public List<Location> getAvailableLocations(int gameId, String playerName, Walling walls) {

        isExistingEntity(gameId, playerName);

        return server.getGame(gameId).getPlayerByName(playerName).getCity().getAvailableLocations(walls);
    }

    public Object buyBuilding(int gameId, String playerName, Purse coins, Currency currency) {
        isExistingEntity(gameId, playerName);

        Game game = server.getGame(gameId);
        Player player = server.getGame(gameId).getPlayerByName(playerName);

        if (MoveManager.canBuyBuilding(game, player, coins, currency)) {
            MoveManager.buyBuilding(player, game.getMarket(), coins);
        }

        return game;
    }

    public Object takeMoney(int gameId, String playerName, Purse coins) {
        isExistingEntity(gameId, playerName);

        Game game = server.getGame(gameId);
        Player player = server.getGame(gameId).getPlayerByName(playerName);

        if (MoveManager.canTakeMoney(game, player, coins)) {
            MoveManager.takeMoney(game, player, coins);

        }

        return game;
    }

    public Object build(int gameId, String playerName, Building building, Location locations) {
        isExistingEntity(gameId, playerName);

        Game game = server.getGame(gameId);
        Player player = server.getGame(gameId).getPlayerByName(playerName);

        if (MoveManager.canBuildBuilding(game, player, building, locations)) {
            MoveManager.buildBuilding(player, building, locations);
            if (Boolean.TRUE.equals(player.getExtraTurn())) {
                player.setExtraTurn(false);
            } else
                TurnManager.endTurn(game);
        }

        return game;
    }

    public Object redesignCity(int gameId, String playerName, Building building, Location locations) {
        isExistingEntity(gameId, playerName);

        Game game = server.getGame(gameId);
        Player player = server.getGame(gameId).getPlayerByName(playerName);

        if (MoveManager.canRedesignCity(game, player, building, locations)) {
            MoveManager.redesignCity(player, building, locations);
            TurnManager.endTurn(game);
        }

        return game;
    }

    public Object leaveGame(int gameId, String playerName) {
        if (server.getGame(gameId).isStarted()) {

            if (playerName.equals(server.getGame(gameId).getCurrentPlayer())) {
                TurnManager.endTurn(server.getGame(gameId));
            }
            if (server.getGame(gameId).getPlayers().size() == 2) {
                TurnManager.endGame(server.getGame(gameId));
            }
        }
        return server.getGame(gameId).removePlayer(playerName);
    }

    public void clearGames() {
        server.resetGames();

    }


    /* ------------ PUBLIC METHODS ------------ */

    private void isExistingEntity(int gameId, String playerName) {
        if (server.getGame(gameId) == null) {
            throw new AlhambraEntityNotFoundException("Game does not exist");
        }

        if (server.getGame(gameId).getPlayerByName(playerName) == null) {
            throw new AlhambraEntityNotFoundException("Player does not exist");
        }
    }
}
