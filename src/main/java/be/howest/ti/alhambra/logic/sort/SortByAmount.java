package be.howest.ti.alhambra.logic.sort;

import be.howest.ti.alhambra.logic.gamebord.Player;

import java.util.Comparator;

public class SortByAmount implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        return o2.getAmountOfBuildingType() - o1.getAmountOfBuildingType();
    }
}
