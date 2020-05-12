package be.howest.ti.alhambra.webapi;

import be.howest.ti.alhambra.logic.AlhambraController;
import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Purse;
import be.howest.ti.alhambra.logic.gamebord.Location;
import be.howest.ti.alhambra.logic.gamebord.Player;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;


public class DefaultAlhambraOpenAPI3Bridge implements AlhambraOpenAPI3Bridge {

    private final AlhambraController controller;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAlhambraOpenAPI3Bridge.class);

    public DefaultAlhambraOpenAPI3Bridge() {
        this.controller = new AlhambraController();
    }

    public boolean verifyAdminToken(String adminToken) {
        LOGGER.info("verifyPlayerToken");
        return controller.verifyAdminToken(adminToken);
    }

    public boolean verifyPlayerToken(String token, String gameId, String playerName) {
        LOGGER.info("verifyPlayerToken");
        return controller.verifyPlayerToken(token, Integer.parseInt(gameId), playerName);
    }

    public Object getBuildings(RoutingContext ctx) {
        LOGGER.info("getBuildings");
        return controller.getBuildings();
    }

    public Object getAvailableBuildLocations(RoutingContext ctx) {
        LOGGER.info("getAvailableBuildLocations");

        boolean north = Boolean.parseBoolean(ctx.request().getParam("north"));
        boolean east = Boolean.parseBoolean(ctx.request().getParam("east"));
        boolean south = Boolean.parseBoolean(ctx.request().getParam("south"));
        boolean west = Boolean.parseBoolean(ctx.request().getParam("west"));

        Walling walls = new Walling(north, east, south, west);

        int gameId = Integer.parseInt(ctx.request().getParam("gameId"));
        String playerName = ctx.request().getParam("playerName");


        return controller.getAvailableLocations(gameId, playerName, walls);
    }

    public Object getBuildingTypes(RoutingContext ctx) {
        LOGGER.info("getBuildingTypes");
        return controller.getBuildingTypes();
    }

    public Object getCurrencies(RoutingContext ctx) {
        LOGGER.info("getCurrencies");
        return controller.getCurrencies();
    }

    public Object getScoringTable(RoutingContext ctx) {
        LOGGER.info("getScoringTable");
        return null;
    }

    public Object getGames(RoutingContext ctx) {
        LOGGER.info("getGames");
        boolean details = Boolean.parseBoolean(ctx.request().getParam("details"));

        if(details){
            return controller.getGames().toArray();
        } else {
            return controller.getNotStartedGameIds().toArray();
        }
    }

    public Object createGame(RoutingContext ctx) {
        LOGGER.info("createGame");
        return controller.createGame();
    }

    public Object clearGames(RoutingContext ctx) {
        LOGGER.info("clearGames");
        return null;
    }

    public Object joinGame(RoutingContext ctx) {
        LOGGER.info("joinGame");

        String body = ctx.getBodyAsString();
        Player players = Json.decodeValue(body, Player.class);
        String playerName = players.getPlayerName();

        int gameId = Integer.parseInt(ctx.request().getParam("gameId"));

        return controller.joinGame(gameId, playerName);
    }


    public Object leaveGame(RoutingContext ctx) {
        LOGGER.info("leaveGame");
        return null;
    }

    public Object setReady(RoutingContext ctx) {
        LOGGER.info("setReady");

        int gameId = Integer.parseInt(ctx.request().getParam("gameId"));
        String playerName = ctx.request().getParam("playerName");

        return controller.setReady(gameId, playerName);
    }

    public Object setNotReady(RoutingContext ctx) {
        LOGGER.info("setNotReady");

        int gameId = Integer.parseInt(ctx.request().getParam("gameId"));
        String playerName = ctx.request().getParam("playerName");

        return controller.setNotReady(gameId, playerName);
    }

    public Object takeMoney(RoutingContext ctx) {
        LOGGER.info("takeMoney");

        int gameId = Integer.parseInt(ctx.request().getParam("gameId"));
        String playerName = ctx.request().getParam("playerName");

        Purse coins = new Purse();

        JsonArray coinArray = new JsonArray(ctx.getBodyAsString());

        for (int i = 0; i < coinArray.size(); i++) {
            JsonObject element = coinArray.getJsonObject(i);
            Coin coin = element.mapTo(Coin.class);
            coins.addCoin(coin);
        }

        return controller.takeMoney(gameId, playerName, coins);
    }

    public Object buyBuilding(RoutingContext ctx) {
        LOGGER.info("buyBuilding");

        int gameId = Integer.parseInt(ctx.request().getParam("gameId"));
        String playerName = ctx.request().getParam("playerName");

        Purse coins = new Purse();

        JsonObject obj = new JsonObject(ctx.getBodyAsString());
        JsonArray coinArray = obj.getJsonArray("coins");

        for (int i = 0; i < coinArray.size(); i++) {
            JsonObject element = coinArray.getJsonObject(i);
            Coin coin = element.mapTo(Coin.class);
            coins.addCoin(coin);
        }
        return controller.buyBuilding(gameId, playerName, coins);
    }

    public Object redesign(RoutingContext ctx) {
        LOGGER.info("redesign");

        int gameId = Integer.parseInt(ctx.request().getParam("gameId"));
        String playerName = ctx.request().getParam("playerName");

        JsonObject obj = new JsonObject(ctx.getBodyAsString());
        JsonObject jsonBuilding = obj.getJsonObject("building");
        Building building = jsonBuilding.mapTo(Building.class);
        JsonObject jsonLocation = obj.getJsonObject("location");
        Location location = jsonLocation.mapTo(Location.class);


        return controller.redesignCity(gameId, playerName, building, location);
    }

    public Object build(RoutingContext ctx) {
        LOGGER.info("build");

        int gameId = Integer.parseInt(ctx.request().getParam("gameId"));
        String playerName = ctx.request().getParam("playerName");

        JsonObject obj = new JsonObject(ctx.getBodyAsString());
        JsonObject jsonBuilding = obj.getJsonObject("building");
        Building building = jsonBuilding.mapTo(Building.class);
        JsonObject jsonLocation = obj.getJsonObject("location");
        Location location = jsonLocation.mapTo(Location.class);

        return controller.build(gameId, playerName, building, location);
    }

    public Object getGame(RoutingContext ctx) {
        LOGGER.info("getGame");

        int gameId = Integer.parseInt(ctx.request().getParam("gameId"));
        return controller.getGame(gameId);
    }
}
