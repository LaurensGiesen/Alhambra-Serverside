package be.howest.ti.alhambra.logic.sort;

import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.game.Player;

import java.util.Comparator;

public class SortPlayerBySeraglio implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        return p2.getCity().getAmountOfBuildings(Buildingtype.SERAGLIO) - p1.getCity().getAmountOfBuildings(Buildingtype.SERAGLIO);
    }
}
