package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.coin.Purse;

import static be.howest.ti.alhambra.logic.coin.Currency.BLUE;


public class StartUp {
    public static void main(String[] args) {
        new StartUp().run();
    }

    private void run() {
        Coin c5 = new Coin(BLUE, 12);
        Purse p3 = new Purse();
        p3.addCoin(c5);
        System.out.println(c5.getCurrency());

    }
}

