package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.gamebord.Player;

import java.util.Comparator;

public class OrderByPurse implements Comparator<Player> {
    @Override
    public int compare(Player p1, Player p2) {
        if (p1.getMoney().getNumberOfCoins() > p2.getMoney().getNumberOfCoins()
                || (p1.getMoney().getNumberOfCoins() == p2.getMoney().getNumberOfCoins()
                && p1.getMoney().getTotalAmount() > p2.getMoney().getTotalAmount())
        ) {
            return 1;
        }
        return -1;
    }
}
