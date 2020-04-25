package be.howest.ti.alhambra.logic.coin;

import java.util.ArrayList;
import java.util.List;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.sort.SortByCoin;

public class Purse {

    private List<Coin> coins;

    public Purse() {
        coins = new ArrayList<>();
    }

    public void addCoin(Coin coin) {

        coins.add(coin);
    }

    public void removeCoin(Coin coin) {
        if(coins.contains(coin)) {
            coins.remove(coin);
        } else {
            throw new AlhambraEntityNotFoundException("Building not present");
        }
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