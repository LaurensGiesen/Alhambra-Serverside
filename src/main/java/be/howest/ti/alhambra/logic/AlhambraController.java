package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.coin.Currency;

public class AlhambraController {
    public Currency[] getCurrencies() {
        return Currency.values();
    }
}
