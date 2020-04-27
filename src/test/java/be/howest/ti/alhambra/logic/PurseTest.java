package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.coin.Purse;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static be.howest.ti.alhambra.logic.coin.Currency.BLUE;
import static be.howest.ti.alhambra.logic.coin.Currency.GREEN;
import static org.junit.jupiter.api.Assertions.*;

public class PurseTest {
    Purse p1;
    Purse p2;
    Coin c1;
    Coin c2;
    Coin c3;

    public void createPurses() {
        p1 = new Purse();
        p2 = new Purse();
        c1 = new Coin(BLUE, 12);
        c2 = new Coin(GREEN, 4);
        c3 = new Coin(BLUE, 14);
        p1.addCoin(c1);
        p1.addCoin(c2);
        p2.addCoin(c1);
        p2.addCoin(c3);

    }

    @Test
    void PurseTest() {
        createPurses();
        assertEquals(p2.getCurrency(), BLUE);
        assertThrows(AlhambraEntityNotFoundException.class, () -> p1.getCurrency()); //different coins in purse
        assertEquals(p1.getTotalAmount(), 16);
        assertTrue(p1.getCoins().contains(c1));
        p1.removeCoin(c1);
        p1.removeCoin(c2);
        List<Coin> coins;
        coins = new ArrayList<>();
        assertEquals(p1.getCoins(), coins);
    }
}
