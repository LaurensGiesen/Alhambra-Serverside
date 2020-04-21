package be.howest.ti.alhambra.logic;

import java.util.ArrayList;
import java.util.List;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.sort.SortByCoin;

public class Purse {

    public List<Coin> coins;

    public Purse() {
        coins = new ArrayList<>();
    }

    public boolean addCoin(Coin coin) {
        return coins.add(coin);
    }

    public boolean removeCoin(Coin coin) {
        return coins.remove(coin);
    }

    public List<Coin> getCoins() {

        coins.sort(new SortByCoin());
        return coins;

    }

    public int getTotalAmount() {
        return coins.stream().map(Coin::getAmount).reduce(0, Integer::sum);
    }

    public Currency getCurrency() {
        if (coins.stream().allMatch(c -> c.getCurrency().equals(coins.get(0).getCurrency()))) {
            return coins.get(0).getCurrency();
        }else {
            throw new AlhambraEntityNotFoundException("exceptional situation");
        }
    }
}