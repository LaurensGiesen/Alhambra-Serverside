package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Currency;
import org.junit.jupiter.api.Test;
import be.howest.ti.alhambra.logic.gamebord.Player;

import static org.junit.jupiter.api.Assertions.*;

class PopulateBank {

    @Test
    void populateBank() {

        Game g1 = new Game();

        Player p1 = new Player("Bart");
        Player p2 = new Player("José");

        g1.addPlayer(p1.getPlayerName());
        g1.addPlayer(p2.getPlayerName());

        g1.bank.addCoin(new Coin(Currency.BLUE, 2));

        assertEquals(1, g1.bank.getCoins().size());

        g1.bank.addCoin(new Coin(Currency.ORANGE, 9));

        assertEquals(2, g1.bank.getCoins().size());

        g1.startGame();

        assertEquals(4, g1.bank.getCoins().size());

        g1.startGame();

        assertEquals(4, g1.bank.getCoins().size());

        g1.bank.removeCoin(new Coin(Currency.BLUE, 2));

        g1.startGame();

        assertEquals(4, g1.bank.getCoins().size());


    }
}