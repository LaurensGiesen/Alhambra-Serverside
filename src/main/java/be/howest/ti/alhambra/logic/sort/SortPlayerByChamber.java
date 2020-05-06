package be.howest.ti.alhambra.logic.sort;

import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.gamebord.Player;

import java.util.Comparator;

public class SortPlayerByChamber implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        return p2.getCity().getAmountOfBuildings(Buildingtype.CHAMBERS) - p1.getCity().getAmountOfBuildings(Buildingtype.CHAMBERS);
    }
}
