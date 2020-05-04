package be.howest.ti.alhambra.logic.sort;

import java.util.Comparator;
import be.howest.ti.alhambra.logic.coin.Coin;

public class SortByCoin implements Comparator<Coin> {
    public int compare(Coin o1, Coin o2) {
        int compare;
        compare = o1.getCurrency().compareTo(o2.getCurrency());
        if (compare != 0) {
            return compare;
        }
        return o1.getAmount() - o2.getAmount();
    }
}
