package be.howest.ti.alhambra.logic.sort;

import be.howest.ti.alhambra.logic.game.Player;

import java.util.Comparator;

public class SortPlayerByPurse implements Comparator<Player> {
    @Override
    public int compare(Player p1, Player p2) {
        if (p1.getMoney().getNumberOfCoins() > p2.getMoney().getNumberOfCoins()
                || (p1.getMoney().getNumberOfCoins() == p2.getMoney().getNumberOfCoins()
                && p1.getMoney().getTotalAmount() > p2.getMoney().getTotalAmount())
        ) {
            return 1;
        }
        if(p1 == p2){
            return 0;
        }
        return -1;
    }
}
