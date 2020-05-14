package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.money.Purse;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.building.Location;

import java.util.Map;

public class MoveManager {

    /* ------------ CONSTRUCTOR ------------ */

    private MoveManager() {}


    /* ------------ PUBLIC METHODS ------------ */

    public static boolean canTakeMoney(Game game, Player player, Purse coinsToTake) {
        canPlay(game, player);

        for (Coin coin : coinsToTake.getCoins()) {
            if (!game.getBank().getCoins().contains(coin)) {
                throw new AlhambraEntityNotFoundException("No such coin in Bank");
            }
        }
        if (coinsToTake.getNumberOfCoins() > 1 && coinsToTake.getTotalAmount() > 5) {
            throw new AlhambraGameRuleException("2 coins with a value higher as 5");
        }
        return true;

    }

    public static void takeMoney(Game game, Player player, Purse coins) {
        for (Coin coin : coins.getCoins()) {
            game.getBank().removeCoin(coin);
            game.getPlayerByName(player.getPlayerName()).getMoney().addCoin(coin);
        }
        TurnManager.endTurn(game);
    }

    public static boolean canBuyBuilding(Game game, Player player, Purse coins, Currency currency) {
        canPlay(game, player);

        Building building = game.getMarket().get(currency);
        if(coins.getCurrency() != currency) {
            throw new AlhambraGameRuleException("Wrong currency");
        }
        if (building == null) {
            throw new AlhambraEntityNotFoundException("No building in that currency");
        }
        if (building.getCost() > coins.getTotalAmount()) {
            throw new AlhambraGameRuleException("Not enough money to buy");
        }
        for (Coin coin : coins.getCoins()) {
            if (!player.getMoney().getCoins().contains(coin)) {
                throw new AlhambraEntityNotFoundException("Player does not have the money");
            }
        }

        return true;
    }

    public static void buyBuilding(Player player, Map<Currency, Building> market, Purse coins) {
        //take money from player, put building from market to buildingInHand of player
        Currency currency = coins.getCurrency();
        Building building = market.get(currency);

        if (building.getCost() == coins.getTotalAmount()) {
            player.setExtraTurn(true);
        }

        useMoney(player.getMoney(), coins);
        market.replace(currency, null);
        player.addBuildingToHand(building);
    }

    public static void useMoney(Purse money, Purse coins) {
        for (Coin coin : coins.getCoins()) {
            money.removeCoin(coin);
        }
    }

    public static boolean canBuildBuilding(Game game, Player player, Building building, Location location) {
        canPlay(game, player);

        if (!player.getBuildingInHand().getBuildings().contains(building)) {
            throw new AlhambraEntityNotFoundException("Building not in hand");
        }
        if (location != null && !player.getCity().isValidPlacing(building, location)) {
            throw new AlhambraGameRuleException("Invalid location for this building");
        }

        return true;
    }

    public static void buildBuilding(Player player, Building building, Location location) {
        if (location == null) {
            buildBuildingInReserve(player, building);
        } else {
            buildBuildingInAlhambra(player, building, location);
        }
    }

    public static boolean canRedesignCity(Game game, Player player, Building building, Location location) {
        canPlay(game, player);
        if (building == null && location == null) {
            throw new AlhambraEntityNotFoundException("Illegal input");
        }
        if (building == null && player.getCity().getLocation(location).getBuilding() == null) {
            throw new AlhambraEntityNotFoundException("The location is empty");
        }
        if (building != null && !player.getReserve().getBuildings().contains(building)) {
            throw new AlhambraEntityNotFoundException("No such building in the reserve");
        }

        if (building != null && !player.getCity().isValidPlacing(building, location)) {
            throw new AlhambraGameRuleException("No valid location to build");
        }

        return true;
    }

    public static void redesignCity(Player player, Building building, Location location) {
        if (building == null) {
            MoveManager.replaceFromBoardToReserve(player, location);
        } else if (player.getCity().getLocation(location).getBuilding() == null) {
            MoveManager.replaceFromReserveToEmptyLocation(player, building, location);
        } else {
            MoveManager.replaceFromReserveToUsedLocation(player, building, location);
        }
    }


    /* ------------ PRIVATE METHODS ------------ */

    private static boolean canPlay(Game game, Player player) {
        if (game.getPlayerByName(player.getPlayerName()) == null) {
            throw new AlhambraEntityNotFoundException("No such player in the game");
        }
        if (!game.getCurrentPlayer().equals(player.getPlayerName())) {
            throw new AlhambraGameRuleException("Stop cheating, it's not your turn!");
        }

        return true;
    }

    private static void buildBuildingInReserve(Player player, Building building) {
        player.getReserve().addBuilding(building);
        player.getBuildingInHand().removeBuilding(building);
    }

    private static void buildBuildingInAlhambra(Player player, Building building, Location location) {
        player.getCity().addBuilding(building, location);
        player.getBuildingInHand().removeBuilding(building);
    }

    private static void replaceFromBoardToReserve(Player player, Location location) {
        Building building = player.getCity().removeBuilding(location);
        player.getReserve().addBuilding(building);
    }

    private static void replaceFromReserveToEmptyLocation(Player player, Building building, Location location) {
        player.getCity().addBuilding(building, location);
        player.getReserve().removeBuilding(building);
    }

    private static void replaceFromReserveToUsedLocation(Player player, Building building, Location location) {
        Building oldBuilding = player.getCity().replaceBuilding(building, location);
        player.getReserve().addBuilding(oldBuilding);
        player.getReserve().removeBuilding(building);
    }

}
