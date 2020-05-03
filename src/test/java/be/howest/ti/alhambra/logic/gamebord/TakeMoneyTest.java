package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.Game.Game;
import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.coin.Purse;


import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TakeMoneyTest {
    Game game = new Game();
    Coin coin1 = new Coin(Currency.GREEN, 1);
    Coin coin2 = new Coin(Currency.BLUE, 3);
    Coin coin3 = new Coin(Currency.YELLOW, 6);
    Coin coin4 = new Coin(Currency.YELLOW, 9);
    Coin coin5 = null;
    Purse purseOfLaurens = new Purse();


    @Test
    void takeMoney1Test() {
        game.takeMoney("Laurens", purseOfLaurens, coin4);
        assertTrue(purseOfLaurens.getCoins().contains(coin4));
    }

    @Test
    void takeMoney2Test() {
        game.takeMoney("Laurens", purseOfLaurens, coin1, coin2);
        assertTrue(purseOfLaurens.getCoins().contains(coin1));
        assertTrue(purseOfLaurens.getCoins().contains(coin2));
    }

    @Test
    void takeMoney3Test() {
        assertThrows(AlhambraGameRuleException.class, () ->game.takeMoney("Laurens", purseOfLaurens, coin3, coin2));
    }

    @Test
    void takeMoney4Test() {
        assertThrows(AlhambraGameRuleException.class, () ->game.takeMoney("Laurens", purseOfLaurens, coin5));

    }
}
