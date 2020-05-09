package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.coin.Purse;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.gamebord.Player;

import java.util.Map;

public class MoveManager {

    private MoveManager() {}

    private static boolean canPlay(Game game, Player player ){
        if (game.getPlayerByName(player.getPlayerName()) == null) {
            throw new AlhambraEntityNotFoundException("No such player in the game");
        }
        if (game.getCurrentPlayer() != game.getPlayerByName(player.getPlayerName())) {
            throw  new AlhambraGameRuleException("Stop cheating, it's not your turn!");
        }

        return true;
    }

    public static boolean canTakeMoney(Game game, Player player){
        canPlay(game, player);

        if (game.getPlayerByName(player.getPlayerName()) == null) {
            throw new AlhambraEntityNotFoundException("No such player in the game");
        }
        if (game.getPlayerByName(player.getPlayerName()) != game.getCurrentPlayer()) {
            throw new AlhambraGameRuleException("Not the current player");
        }
        for (Coin coin : player.getMoney().getCoins()) {
            if (!game.getBank().getCoins().contains(coin)) {
                throw new AlhambraEntityNotFoundException("No such coin in Bank");
            }
        }
        if (player.getMoney().getNumberOfCoins() > 1 && player.getMoney().getTotalAmount() > 5) {
            throw new AlhambraGameRuleException("2 coins with a value higher as 5");
        }

            return true;

    }

    public static void takeMoney(Game game, Player player, Purse coins) {
        for (Coin coin : coins.getCoins()) {
            game.getBank().removeCoin(coin);
            game.getPlayerByName(player.getPlayerName()).getMoney().addCoin(coin);
        }
    }

    public static boolean canBuyBuilding(Game game, Player player, Purse coins){
        canPlay(game, player);

        Currency currency = coins.getCurrency();
        Building building = game.getMarket().get(currency);
        if (building == null) {
            throw new AlhambraEntityNotFoundException("No building in that currency");
        }
        if (building.getCost() > coins.getTotalAmount()) {
            throw new AlhambraGameRuleException("Not enough money to buy");
        }
        for(Coin coin : coins.getCoins()){
            if(!player.getMoney().getCoins().contains(coin)){
                throw new AlhambraEntityNotFoundException("Player does not have the money");
            }
        }

        return true;

    }

    public static void buyBuilding(Player player, Map<Currency, Building> market, Purse coins){
        //take money from player, put building from market to buildingInHand of player
        Currency currency = coins.getCurrency();
        Building building = market.get(currency);


        useMoney(player.getMoney(), coins);
        market.replace(currency, null);
        player.addBuildingToHand(building);
    }

    public static void useMoney(Purse money, Purse coins){
        for(Coin coin : coins.getCoins()){
            money.removeCoin(coin);
        }
    }

}
