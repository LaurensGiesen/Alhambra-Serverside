package be.howest.ti.alhambra.logic.building;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Building {

    private Buildingtype type;
    private int cost;
    private Walling walls;
    private static Random r = new Random();

    public Building(Buildingtype type, int cost, Walling walls) {
        this.type = type;
        this.cost = cost;
        this.walls = walls;
    }

    public Buildingtype getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public Walling getWalls() {
        return walls;
    }

    public boolean isFountain() {
        return type == null;
    }

    public static List<Building> allBuilding() {
        List<Building> buildings = new ArrayList<>();

        //add pavilion
        for (int i = 0; i < 7; i++) {
            buildings.add(new Building(Buildingtype.PAVILION, getRandomCost(), getRandomWalling()));
        }
        //add seraglios
        for (int i = 0; i < 7; i++) {
            buildings.add(new Building(Buildingtype.SERAGLIO, getRandomCost(), getRandomWalling()));
        }
        //add arcades
        for (int i = 0; i < 9; i++) {
            buildings.add(new Building(Buildingtype.ARCADES, getRandomCost(), getRandomWalling()));
        }
        //add chambers
        for (int i = 0; i < 9; i++) {
            buildings.add(new Building(Buildingtype.CHAMBERS, getRandomCost(), getRandomWalling()));
        }
        //add gardens
        for (int i = 0; i < 11; i++) {
            buildings.add(new Building(Buildingtype.GARDEN, getRandomCost(), getRandomWalling()));
        }
        //add towers
        for (int i = 0; i < 11; i++) {
            buildings.add(new Building(Buildingtype.TOWER, getRandomCost(), getRandomWalling()));
        }
        return buildings;
    }

    private static int getRandomCost() {
        return r.nextInt(11) + 2; // between 2 and 13
    }

    private static Walling getRandomWalling() {
        //Create random number between 0 and 14
        //The number in binary represents the walls N-E-S-W
        //(n/8)%2 gives the binary number for the 4th digit (N)
        int rw = r.nextInt(14);  // between 0 en 14
        return new Walling((rw / 8) % 2 == 1, (rw / 4) % 2 == 1, (rw / 2) % 2 == 1, (rw / 1) % 2 == 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return cost == building.cost &&
                type == building.type &&
                Objects.equals(walls, building.walls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, cost, walls);
    }
}
