package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.coin.Purse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class PopulatorTest {

    Queue<Building> buildingStack = new LinkedList<>();
    Queue<Coin> coinStack = new LinkedList<>();


    private Map<Currency, Building> createEmptyMarket(){
        Map<Currency, Building> market = new HashMap<>();
        market.put(Currency.BLUE, null);
        market.put(Currency.GREEN, null);
        market.put(Currency.ORANGE, null);
        market.put(Currency.YELLOW, null);

        return market;
    }


    @Test
    void populateCoinStack() {
        Queue<Coin> coinStack1 = new LinkedList<>();
        Queue<Coin> coinStack2 = new LinkedList<>();

        Populator.populateCoinStack(coinStack1);
        Populator.populateCoinStack(coinStack2);

        assertEquals(108, coinStack1.size()); //All coins have been added
        assertNotEquals(coinStack1.peek(), coinStack2.peek()); //Deck has been shuffled
    }

    @Test
    void populateBuildingStack() {
        Queue<Building> buildingStack1 = new LinkedList<>();
        Queue<Building> buildingStack2 = new LinkedList<>();

        Populator.populateBuildingStack(buildingStack1);
        Populator.populateBuildingStack(buildingStack2);

        assertEquals(54, buildingStack1.size()); //All coins have been added
        assertNotEquals(buildingStack1.peek(), buildingStack2.peek()); //Deck has been shuffled

    }

    @Test
    void populateMarket() {
        Map<Currency, Building> market = createEmptyMarket();
        Populator.populateBuildingStack(buildingStack);

        int sizeBuildingStack = buildingStack.size();
        Populator.populateMarket(buildingStack, market);
        assertEquals(sizeBuildingStack - 4, buildingStack.size());
        assertNotNull(market.get(Currency.BLUE));
        assertNotNull(market.get(Currency.GREEN));
        assertNotNull(market.get(Currency.YELLOW));
        assertNotNull(market.get(Currency.ORANGE));

        Building bBlue = market.get(Currency.BLUE);
        Building bGreen = market.get(Currency.GREEN);
        Building bOrange = market.get(Currency.ORANGE);
        market.replace(Currency.YELLOW, null);
        Populator.populateMarket(buildingStack, market);
        assertNotNull(market.get(Currency.YELLOW));
        assertEquals(bBlue, market.get(Currency.BLUE));
        assertEquals(bGreen, market.get(Currency.GREEN));
        assertEquals(bOrange, market.get(Currency.ORANGE));
    }

    @Test
    void populateBank() {
        Purse bank = new Purse();
        Purse bank2 = new Purse();
        Populator.populateCoinStack(coinStack);
        bank2.addCoin(coinStack.poll());
        bank2.addCoin(coinStack.poll());

        Populator.populateBank(coinStack, bank);
        Populator.populateBank(coinStack, bank2);
        assertEquals(4, bank.getNumberOfCoins()); //From empty bank
        assertEquals(4, bank2.getNumberOfCoins()); //From not empty bank
        assertEquals(100, coinStack.size());
    }

    @Test
    void giveStartMoney() {
        Purse money = new Purse();
        Populator.populateCoinStack(coinStack);

        Populator.giveStartMoney(coinStack, money);
        assertTrue(money.getTotalAmount() > 20);
    }
}