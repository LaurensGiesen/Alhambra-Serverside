package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.coin.Purse;


import java.util.*;

public class Populator {

    private Populator() {
    }

    public static void populateMarket(Queue<Building> buildingStack, Map<Currency, Building> market) {
        for (Currency c : market.keySet()) {
            market.computeIfAbsent(c, k -> buildingStack.poll());
        }
    }

    public static void populateBank(Queue<Coin> coinStack, Purse bank) {
        while (bank.getNumberOfCoins() < 4) {
            bank.addCoin(coinStack.poll());
        }
    }

    public static void populateCoinStack(Queue<Coin> coinStack) {
        List<Coin> allCoins = new ArrayList<>(Coin.allCoins());
        Collections.shuffle(allCoins);
        coinStack.addAll(allCoins);
    }

    public static void populateBuildingStack(Queue<Building> buildingStack) {
        List<Building> allBuildings = new ArrayList<>(Building.allBuilding());
        Collections.shuffle(allBuildings);

        buildingStack.addAll(allBuildings);

    }

    public static void giveStartMoney(Queue<Coin> coinStack, Purse money) {
        while (money.getTotalAmount() < 20) {
            money.addCoin(coinStack.poll());
        }
    }

}
