package be.howest.ti.alhambra.webapi;

import be.howest.ti.alhambra.logic.AlhambraController;
import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.money.Purse;
import be.howest.ti.alhambra.logic.building.Location;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;


public class DefaultAlhambraOpenAPI3Bridge implements AlhambraOpenAPI3Bridge {
    private String parameterGameId = "gameId";
    private String parameterPlayerName = "playerName";

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

        int gameId = Integer.parseInt(ctx.request().getParam(parameterGameId));
        String playerName = ctx.request().getParam(parameterPlayerName);


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

        if (details) {
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
        controller.clearGames();
        return null;

    }

    public Object joinGame(RoutingContext ctx) {
        LOGGER.info("joinGame");

        String playerName = ctx.getBodyAsJson().getString("playerName");
        int gameId = Integer.parseInt(ctx.request().getParam(parameterGameId));

        return controller.joinGame(gameId, playerName);
    }

    public Object leaveGame(RoutingContext ctx) {
        LOGGER.info("leaveGame");
        int gameId = Integer.parseInt(ctx.request().getParam(parameterGameId));
        String playerName = ctx.request().getParam(parameterPlayerName);
        return controller.leaveGame(gameId, playerName);
    }

    public Object setReady(RoutingContext ctx) {
        LOGGER.info("setReady");

        int gameId = Integer.parseInt(ctx.request().getParam(parameterGameId));
        String playerName = ctx.request().getParam(parameterPlayerName);

        return controller.setReady(gameId, playerName);
    }

    public Object setNotReady(RoutingContext ctx) {
        LOGGER.info("setNotReady");

        int gameId = Integer.parseInt(ctx.request().getParam(parameterGameId));
        String playerName = ctx.request().getParam(parameterPlayerName);

        return controller.setNotReady(gameId, playerName);
    }

    public Object takeMoney(RoutingContext ctx) {
        LOGGER.info("takeMoney");

        int gameId = Integer.parseInt(ctx.request().getParam(parameterGameId));
        String playerName = ctx.request().getParam(parameterPlayerName);

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

        int gameId = Integer.parseInt(ctx.request().getParam(parameterGameId));
        String playerName = ctx.request().getParam(parameterPlayerName);

        Purse coins = new Purse();

        JsonObject obj = new JsonObject(ctx.getBodyAsString());

        Currency currency = Currency.valueOf(obj.getString("currency").toUpperCase());
        JsonArray coinArray = obj.getJsonArray("coins");

        for (int i = 0; i < coinArray.size(); i++) {
            JsonObject element = coinArray.getJsonObject(i);
            Coin coin = element.mapTo(Coin.class);
            coins.addCoin(coin);
        }
        return controller.buyBuilding(gameId, playerName, coins, currency);
    }

    public Object redesign(RoutingContext ctx) {
        LOGGER.info("redesign");

        int gameId = Integer.parseInt(ctx.request().getParam(parameterGameId));
        String playerName = ctx.request().getParam(parameterPlayerName);

        JsonObject obj = new JsonObject(ctx.getBodyAsString());
        JsonObject jsonBuilding = obj.getJsonObject("building");
        Building building;
        if (jsonBuilding == null) {
            building = null;
        } else {
            building = jsonBuilding.mapTo(Building.class);
        }
        JsonObject jsonLocation = obj.getJsonObject("location");
        Location location;
        if (jsonLocation == null) {
            location = null;
        } else {
            location = jsonLocation.mapTo(Location.class);
        }


        return controller.redesignCity(gameId, playerName, building, location);
    }

    public Object build(RoutingContext ctx) {
        LOGGER.info("build");

        int gameId = Integer.parseInt(ctx.request().getParam(parameterGameId));
        String playerName = ctx.request().getParam(parameterPlayerName);

        JsonObject obj = new JsonObject(ctx.getBodyAsString());
        JsonObject jsonBuilding = obj.getJsonObject("building");
        Building building = jsonBuilding.mapTo(Building.class);
        JsonObject jsonLocation = obj.getJsonObject("location");
        Location location;
        if (jsonLocation == null) {
            location = null;
        } else {
            location = jsonLocation.mapTo(Location.class);
        }


        LOGGER.info(location);

        return controller.build(gameId, playerName, building, location);
    }

    public Object getGame(RoutingContext ctx) {
        LOGGER.info("getGame");

        int gameId = Integer.parseInt(ctx.request().getParam(parameterGameId));
        return controller.getGame(gameId);
    }
}
