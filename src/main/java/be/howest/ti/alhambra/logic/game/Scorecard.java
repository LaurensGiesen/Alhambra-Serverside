package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Buildingtype;


import java.util.HashMap;
import java.util.Map;

public class Scorecard {
    private Map<Buildingtype, int[][]> scores = new HashMap<>();

    public Scorecard() {
        scores.put(Buildingtype.PAVILION, new int[][]{{1, 0, 0}, {8, 1, 0}, {16, 8, 1}});
        scores.put(Buildingtype.SERAGLIO, new int[][]{{2, 0, 0}, {9, 2, 0}, {17, 9, 2}});
        scores.put(Buildingtype.ARCADES, new int[][]{{3, 0, 0}, {10, 3, 0}, {18, 10, 3}});
        scores.put(Buildingtype.CHAMBERS, new int[][]{{4, 0, 0}, {11, 4, 0}, {19, 11, 4}});
        scores.put(Buildingtype.GARDEN, new int[][]{{5, 0, 0}, {12, 5, 0}, {20, 12, 5}});
        scores.put(Buildingtype.TOWER, new int[][]{{6, 0, 0}, {13, 6, 0}, {21, 13, 6}});
    }

    public int getScore(Buildingtype buildingtype, int round, int position) {
        return scores.get(buildingtype)[round - 1][position - 1];
    }
}
