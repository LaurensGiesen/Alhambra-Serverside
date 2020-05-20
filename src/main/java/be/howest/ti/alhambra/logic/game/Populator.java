package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingRepo;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.money.Purse;


import java.util.*;

public class Populator {


    /* ------------ CONSTRUCTOR ------------ */

    private Populator() {
    }

    /* ------------ PUBLIC METHODS ------------ */

    public static boolean populateMarket(Queue<Building> buildingStack, Map<Currency, Building> market) {
        if (buildingStack.isEmpty()) {
            return false;
        }
        for (Currency c : market.keySet()) {
            market.computeIfAbsent(c, k -> buildingStack.poll());
        }
        return true;
    }

    public static void populateBank(Queue<Coin> coinStack, Purse bank) {
        if (coinStack.size() < 4) {
            populateCoinStack(coinStack);
        }
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
        List<Building> allBuildings = new ArrayList<>(BuildingRepo.getAllBuildings());
        Collections.shuffle(allBuildings);
        buildingStack.addAll(allBuildings);

    }

    public static void giveStartMoney(Queue<Coin> coinStack, Purse money) {
        while (money.getTotalAmount() < 20) {
            money.addCoin(coinStack.poll());
        }
    }

}
