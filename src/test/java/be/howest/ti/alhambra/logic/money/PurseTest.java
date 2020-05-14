package be.howest.ti.alhambra.logic.money;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Purse;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static be.howest.ti.alhambra.logic.money.Currency.BLUE;
import static be.howest.ti.alhambra.logic.money.Currency.GREEN;
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
    void testPurse() {
        createPurses();
        assertEquals(BLUE, p2.getCurrency());
        assertThrows(AlhambraEntityNotFoundException.class, () -> p1.getCurrency()); //different coins in purse
        assertEquals(16, p1.getTotalAmount());
        assertTrue(p1.getCoins().contains(c1));
        p1.removeCoin(c1);
        p1.removeCoin(c2);
        List<Coin> coins;
        coins = new ArrayList<>();
        assertEquals(p1.getCoins(), coins);
    }
}
