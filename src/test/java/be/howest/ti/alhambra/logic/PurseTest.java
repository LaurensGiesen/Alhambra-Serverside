package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.coin.Purse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static be.howest.ti.alhambra.logic.coin.Currency.BLUE;
import static be.howest.ti.alhambra.logic.coin.Currency.GREEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PurseTest {
    @Test // enkel getCurrency bij foutieve en correcte invoer nog testen
    void PurseTest() {
        Coin c1 = new Coin(BLUE, 12);
        Coin c2 = new Coin(GREEN, 4);
        Purse p1 = new Purse();
        p1.addCoin(c1);
        p1.addCoin(c2);
//        assertEquals(c1.getCurrency(), "blue");
        assertEquals(p1.getTotalAmount(), 16);
        assertTrue(p1.getCoins().contains(c1));
        p1.removeCoin(c1);
        p1.removeCoin(c2);
        List<Coin> coins;
        coins = new ArrayList<>();
        assertEquals(p1.getCoins(),coins);
    }
}
