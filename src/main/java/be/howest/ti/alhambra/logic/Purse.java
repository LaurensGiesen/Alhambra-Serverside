package be.howest.ti.alhambra.logic;

import java.util.ArrayList;
import java.util.List;

import be.howest.ti.alhambra.logic.Coin;
import be.howest.ti.alhambra.logic.Currency;

public class Purse {
    private int totalAmount;
    private Currency currency;

    public int getTotalAmount() {
        return totalAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

}
